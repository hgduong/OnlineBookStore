/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TungPham
 */
public class CartItem {

    int cartId;
    int bookId;
    int quantity;
    float price;
    boolean isChecked;
    
    String bookName;
    float bookPrice;
    public CartItem() {
    }

    public CartItem(int cartId, int bookId, int quantity, float price, boolean isChecked) {
        this.cartId = cartId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.isChecked = isChecked;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    
    @Override
    public String toString() {
        return "CartItem{" + "cartId=" + cartId + ", bookId=" + bookId + ", quantity=" + quantity + ", Price=" + price + ", isChecked=" + isChecked + ", bookName=" + bookName + '}';
    }
    
    
    
}
