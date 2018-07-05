package com.xxm.review.domain;


public class InfoConfig {

    public static class Builder {
        private String name;
        private int age;
        private String address;


        public Builder() {
            name = "tom";
            age = 3;
            address = "";
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setAge(int age) {
            this.age = age;
            return this;
        }


        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }
    }
}
