package com.tus.java.oop1.rms.data.models;

import com.tus.java.oop1.rms.data.record.RestaurantBranchRecord;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "menu_items")
public class MenuItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long menuId;

        private String name;
        private Double price;

        @Embedded
        private RestaurantBranchRecord branch;

        private Boolean outOfStock;

        @Version
        private Long version;

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "menu_item_ingredients", joinColumns = @JoinColumn(name = "menu_item_id"))
        @Column(name = "ingredient")
        private Set<String> ingredients;

        public boolean containsIngredient(String ingredient) {
                return ingredients != null && ingredients.contains(ingredient.toLowerCase());
        }

        // Getters and Setters
        public Long getMenuId() {
                return menuId;
        }

        public void setMenuId(Long menuId) {
                this.menuId = menuId;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public RestaurantBranchRecord getBranch() {
                return branch;
        }

        public void setBranch(RestaurantBranchRecord branch) {
                this.branch = branch;
        }

        public Boolean getOutOfStock() {
                return outOfStock;
        }

        public void setOutOfStock(Boolean outOfStock) {
                this.outOfStock = outOfStock;
        }

        public Long getVersion() {
                return version;
        }

        public void setVersion(Long version) {
                this.version = version;
        }

        public Set<String> getIngredients() {
                return ingredients;
        }

        public void setIngredients(Set<String> ingredients) {
                this.ingredients = ingredients;
        }
}