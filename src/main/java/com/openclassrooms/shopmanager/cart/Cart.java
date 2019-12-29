package com.openclassrooms.shopmanager.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartLine> cartLineList = new ArrayList<>();

    public Cart(List<CartLine> cartLineList) {
        this.cartLineList = cartLineList;
    }

    public List<CartLine> getCartLineList() {
        return cartLineList;
    }

    public void setCartLineList(List<CartLine> cartLineList) {
        this.cartLineList = cartLineList;
    }

    /**
     * @return total value of a cart
     */
    public double getTotalValue()
    {
        return getCartLineList().stream().mapToDouble(CartLine::getSubtotal).sum();

    }

    /**
     * @return Get average value of a cart
     */
    public double getAverageValue()
    {
        int totalQuantity = getCartLineList().stream().mapToInt(CartLine::getQuantity).sum();

        if (totalQuantity > 0) {
            return getTotalValue() / totalQuantity;
        } else {
            return 0;
        }
    }
}
