package com.tus.java.oop1.rms.data.models;

import com.tus.java.oop1.rms.utils.OrderStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long orderId;

        @ManyToOne
        @JoinColumn(name = "branch_id")
        private RestaurantBranch branch;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToMany
        @JoinTable(
                name = "order_menu_items",
                joinColumns = @JoinColumn(name = "order_id"),
                inverseJoinColumns = @JoinColumn(name = "menu_item_id")
        )
        private List<MenuItem> menuItems;

        public void setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems; }

        public String getDate() {
                return date;
        }

        public void setDate(String date) {
                this.date = date;
        }

        // Method to set the date to now with the specified format
        public void setDateNow() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
                this.date = LocalDateTime.now().format(formatter);
        }

        private String date;

        @Enumerated(EnumType.STRING)
        private OrderStatus status;

        public Double getTotalPrice() {
                return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
                this.totalPrice = totalPrice;
        }

        /*
         byte --> short --> char --> int --> long --> float --> double
         we dont need much casting here as double will provide needed precision
         Normally we use BigDecimal formally but for this class I believe double is enough good
         Even if I have integer price WIDENING will be done by Java seemlessly */
        private Double totalPrice;

        public Long getId() {
                return orderId;
        }

        public void setId(Long id) {
                this.orderId = id;
        }

        public OrderStatus getStatus() {
                return status;
        }

        public void setStatus(OrderStatus status) {
                this.status = status;
        }
}

