package com.example.gymapp.model;

public class Bill {
    private int billId;
    private int userId;
    private int cartId;
    private double totalAmount;
    private String billDate;

    // Constructor không có tham số
    public Bill(int userId, int cartId, double totalAmount, String billDate) {
        this.userId = userId;
        this.cartId = cartId;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
    }

    // Constructor có tham số
    public Bill(int billId, int userId, int cartId, double totalAmount, String billDate) {
        this.billId = billId;
        this.userId = userId;
        this.cartId = cartId;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
    }

    // Getter và Setter
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
