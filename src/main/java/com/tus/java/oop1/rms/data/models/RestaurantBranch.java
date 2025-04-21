package com.tus.java.oop1.rms.data.models;

import jakarta.persistence.*;

@Entity
@Table(name = "branches")
public class RestaurantBranch {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long branchId;
        String address;
        String properties;

        public Long getVersion() {
                return version;
        }

        public void setVersion(Long version) {
                this.version = version;
        }

        @Version
        private Long version;

        public Long getBranchId() {
                return branchId;
        }

        public void setBranchId(Long branchId) {
                this.branchId = branchId;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getProperties() {
                return properties;
        }

        public void setProperties(String properties) {
                this.properties = properties;
        }
}