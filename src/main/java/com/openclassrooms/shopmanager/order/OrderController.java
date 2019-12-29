package com.openclassrooms.shopmanager.order;

import com.openclassrooms.shopmanager.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class OrderController {

    private OrderService orderService;
    private CartService cartService;

    @Autowired
    public OrderController( OrderService orderService, CartService cartService)
    {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/order/cart")
    public String getCart(Model model)
    {
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }

    @PostMapping("/order/addToCart")
    public String addToCart(@RequestParam("productId") Long productId)
    {
        boolean success = orderService.addToCart(productId);

        if (success) {
            return "redirect:/order/cart";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("order/removeFromCart")
    public String removeFromCart(@RequestParam Long productId)
    {
        orderService.removeFromCart(productId);

        return "redirect:/order/cart";
    }

    @GetMapping("/order")
    public String getOrderForm(Order order)
    {
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@Valid @ModelAttribute("order") Order order, BindingResult result)
    {
        if (orderService.isCartEmpty()){
            result.reject("cart.empty");
        }

        if (!result.hasErrors()) {
            orderService.createOrder(order);
            return "orderCompleted";
        } else {
            return "order";
        }
    }
}
