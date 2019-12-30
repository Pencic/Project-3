package com.openclassrooms.shopmanager.order;

import com.openclassrooms.shopmanager.cart.CartService;
import com.openclassrooms.shopmanager.product.ProductEntity;
import com.openclassrooms.shopmanager.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;
    private CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, CartService cartService)
    {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.cartService = cartService;
    }

    public boolean addToCart(Long productId){
        ProductEntity product = productService.getByProductId(productId);
        if (product !=null){
            cartService.addItem(product , 1);
            return true;
        }
        return false;
    }

    /**
     *
     * @param order Order to be saved
     */
    public void saveOrder(Order order)
    {
        orderRepository.save(order);
        productService.updateProductQuantities(cartService);
    }


    public void removeFromCart(Long productId) {
        cartService.removeLine(productId);
    }

    public boolean isCartEmpty() {
        return cartService.getCartLineList().isEmpty();
    }

    public void createOrder(Order order) {

        order.setLines(cartService.getCartLineList());
        saveOrder(order);
        this.cartService.clear();
    }
}
