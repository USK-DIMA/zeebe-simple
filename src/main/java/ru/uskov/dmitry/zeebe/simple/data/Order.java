package ru.uskov.dmitry.zeebe.simple.data;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Order {

    private static final AtomicLong idGenerator = new AtomicLong();
    private static final Random valueGenerator = new Random();

    private long orderId;
    private long orderValue;

    public Order() {
        orderValue = -1;
    }

    public Order(long orderId, long orderValue) {
        this.orderId = orderId;
        this.orderValue = orderValue;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getOrderValue() {
        return orderValue;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setOrderValue(long orderValue) {
        this.orderValue = orderValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                orderValue == order.orderValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderValue);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderValue=" + orderValue +
                '}';
    }

    public static Order create() {
        return new Order(System.currentTimeMillis(), valueGenerator.nextInt(200));
    }

}
