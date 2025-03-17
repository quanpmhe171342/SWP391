/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author d
 */
public class Cart {

    private int cartID;
    private int userID;

    public Cart() {
    }

    public Cart(int cartID, int userID) {
        this.cartID = cartID;
        this.userID = userID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Cart{" + "cartID=" + cartID + ", userID=" + userID + '}';
    }

}
