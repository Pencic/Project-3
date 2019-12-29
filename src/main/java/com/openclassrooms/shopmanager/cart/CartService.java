package com.openclassrooms.shopmanager.cart;

import com.openclassrooms.shopmanager.product.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private List<CartLine> cartLineList = new ArrayList<>();

    /**
     *
     * @return the actual cartline list
     */
    public List<CartLine> getCartLineList() {
        return cartLineList;
    }

    /**
     * Adds a getProductById in the cart or increment its quantity in the cart if already added
     * @param product getProductById to be added
     * @param quantity the quantity
     */
    public void addItem(ProductEntity product, int quantity) {

        Optional<CartLine> cartLine = cartLineList.stream().filter(cl -> cl.getProduct().equals(product)).findFirst();

        if (cartLine.isPresent()){
            cartLine.get().setQuantity(cartLine.get().getQuantity() + quantity);

        }else {
            CartLine newCartLine = new CartLine();
            newCartLine.setQuantity(quantity);
            newCartLine.setProduct(product);
            cartLineList.add(newCartLine);
        }
    }

    /**
     * Removes a getProductById form the cart
     * @param productId the getProductById to be removed
     */
    public void removeLine(Long productId) {
        getCartLineList().removeIf(l -> l.getProduct().getId().equals(productId));
    }


    /**
     * Clears a the cart of all added products
     */
    public void clear()
    {
        List<CartLine> cartLines = getCartLineList();
        cartLines.clear();
    }

    public Cart getCart(){
        return new Cart(this.getCartLineList());
    }
}

