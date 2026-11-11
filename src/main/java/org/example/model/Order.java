package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStatus status;
    private Discount discount = new NoDiscount();

    public Order(Builder builder) {
        this.customerName = builder.customerName;
        this.items = builder.items;
        this.status = OrderStatus.NEW;
    }

    public void addItem(OrderItem item){
        if(isPaid()) {
            throw new IllegalStateException("Cannot add items to a paid order!");
        }else{
            items.add(item);
        }
    }

    public double calculateTotal(){
        double subtotal = 0;
        for(OrderItem item : items){
            subtotal += item.calculateTotal();
        }
        return discount.apply(subtotal);
    }

    public void markAsPaid(){
        if(items.isEmpty() == true){
            throw new IllegalStateException("Order is empty!");
        }else {
            this.status = OrderStatus.PAID;
        }
    }

    public void applyDiscount(Discount discount){
        this.discount = discount;
    }

    public boolean isPaid(){
        return this.status == OrderStatus.PAID;
    }

    public List<OrderItem> getItems() {
        return items;
    }
    public String getCustomerName() {
        return customerName;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private String customerName;
        private List<OrderItem> items = new ArrayList<>();
        public Builder customerName(String customerName){
            this.customerName = customerName;
            return this;
        }
        public Builder addItem(OrderItem item){
            this.items.add(item);
            return this;
        }
        public Order build(){
            if(customerName == null || customerName.trim().isEmpty()){
                throw new IllegalArgumentException("Customer name cannot be null or empty");
            }else{
                return new Order(this);
            }
        }
    }
}
