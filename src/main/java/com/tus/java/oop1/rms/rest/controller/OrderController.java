package com.tus.java.oop1.rms.rest.controller;

import com.tus.java.oop1.rms.data.models.*;
import com.tus.java.oop1.rms.data.repository.api.IMenuItemRepo;
import com.tus.java.oop1.rms.data.repository.api.IOrderRepository;
import com.tus.java.oop1.rms.data.repository.api.IUserRepository;
import com.tus.java.oop1.rms.data.repository.api.IEmployeeRepository;
import com.tus.java.oop1.rms.data.service.DiscountService;
import com.tus.java.oop1.rms.rest.controller.func.DiscountPredicate;
import com.tus.java.oop1.rms.utils.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import com.tus.java.oop1.rms.data.service.OrderService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IMenuItemRepo menuItemRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private MessageSource messageSource;

    @Autowired private
    OrderService orderService;

    private static final double DISCOUNT_RATE = 0.05;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

    public String getLocalizedMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    @PostMapping
    public String orderMenuItem(@RequestBody List<Long> menuItemIds, @RequestParam Long userId,
                                @RequestParam Long employeeId,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale)
            throws InterruptedException {
        // Validate menu item IDs
        //Using functionalInterface of Predicate and decide to ignore menuItem if it's not present
        menuItemIds.removeIf(itemId -> {
            boolean itemNotPresent = !menuItemRepository.findById(itemId).isPresent();
            if (itemNotPresent) {
                System.out.println("MenuItem with id " + itemId + " does not exist.");
            }
            return itemNotPresent;
        });

        // Create and save the order
        Order order = new Order();
        List<MenuItem> menuItems = menuItemRepository.findAllById(menuItemIds);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //TODO: 1- Lambda and streams usage
        List<Callable<Double>> discountTasks = menuItems.stream()
                .map(item -> (Callable<Double>) () -> discountService.calculateDiscount(item))
                .toList();

        List<Future<Double>> results = executorService.invokeAll(discountTasks);

        executorService.shutdown();

        // Log minimum price
        menuItems.stream()
                .mapToDouble(MenuItem::getPrice)
                .min()
                .ifPresent(minPrice -> logger.info("Minimum price: " + minPrice));

        // Log maximum price
        menuItems.stream()
                .mapToDouble(MenuItem::getPrice)
                .max()
                .ifPresent(maxPrice -> logger.info("Maximum price: " + maxPrice));

        // Log total item count
        long totalCount = menuItems.stream().count();
        logger.info("Total item count: " + totalCount);

        // Log first menu item
        menuItems.stream()
                .findFirst()
                .ifPresent(firstItem -> logger.info("First menu item: " + firstItem.getName()));

        // Check if all menu items have a price greater than 5
        boolean allMatch = menuItems.stream()
                .allMatch(item -> item.getPrice() > 5);
        logger.info("All items have price > 5: " + allMatch);

        // Check if any menu item has a price greater than 50
        boolean anyMatch = menuItems.stream()
                .anyMatch(item -> item.getPrice() > 50);
        logger.info("Any item has price > 50: " + anyMatch);

        // Log all prices and check if there is any free item
        menuItems.forEach(item -> {
            logger.info("Price: " + item.getPrice());
            if (item.getPrice() == 0) {
                logger.warn("Free item found: " + item.getName());
            }
        });

        // Sort menu items by price and log them
        menuItems.stream()
                .sorted((item1, item2) -> Double.compare(item1.getPrice(), item2.getPrice()))
                .forEach(sortedItem -> logger.info("Sorted item: " + sortedItem.getName() + " - Price: " + sortedItem.getPrice()));


        // Calculate total price using streams
        double totalPrice = menuItems.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();

        logger.info("Total price: " + totalPrice);
        // Apply discount if eligible
        // apply discount if price > 100
        // discountPredicate accept integer so casting would suffice for double values
        double finalPrice = totalPrice >= 100 ? totalPrice * (1 - DISCOUNT_RATE) : totalPrice;
        logger.info("Final price: " + finalPrice);
                // Get the user
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return "Invalid user ID.";
        }
        User user = userOpt.get();

        // Get the user and determine if they are a GoldUser
        boolean isGoldUser = user != null && user.isGoldUser();
        if (isGoldUser) {
            user = new GoldUser(user.getName(), user.getAddress(), user.getEmail(), user.getPhoneNumbers().toArray(new String[0]), user.getOrderHistory());
        }

        // Check for allergies
        for (Allergies allergy : user.getAllergies()) {
            for (MenuItem menuItem : menuItems) {
                switch (allergy) {
                    case PEANUTS:
                        if (menuItem.containsIngredient("peanuts")) {
                            return "Order cannot proceed. User is allergic to peanuts.";
                        }
                        break;
                    case SHELLFISH:
                        if (menuItem.containsIngredient("shellfish")) {
                            return "Order cannot proceed. User is allergic to shellfish.";
                        }
                        break;
                    case DAIRY:
                        if (menuItem.containsIngredient("dairy")) {
                            return "Order cannot proceed. User is allergic to dairy.";
                        }
                        break;
                    // Add other cases as needed
                    default:
                        break;
                }
            }
        }

        order.setMenuItems(menuItems);
        System.out.println("Saving an order : " + order.toString());

        order.setDate(LocalDateTime.now().format(formatter));

        // Get the employee
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if ( !employeeOpt.isPresent()) {
            return getLocalizedMessage("order.invalid.employee", locale);
        }

        if (user instanceof GoldUser) {
            finalPrice = finalPrice * ((GoldUser) user).extraDiscount(); // 8% discount for GoldUser
            logger.info("Price after gold user discount: " + finalPrice);
        } else {
            logger.info("User is not a GoldUser.");
        }

        order.setTotalPrice(finalPrice);
        order.setStatus(OrderStatus.In_Progress);

        orderRepository.save(order);
        //This is call by REFERENCE and order object is not refreshed with orderId which was auto generated
        // if we used --> order = orderRepository.save(order); we could see orderId printed correctly
        System.out.println("Saving an order : " + order.toString());

        return getLocalizedMessage("menu.orderSuccess", locale);
    }

    @GetMapping public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

}

