package com.openclassrooms.shopmanager.order;
import org.aspectj.lang.annotation.Before;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.openclassrooms.shopmanager.cart.Cart;
import com.openclassrooms.shopmanager.cart.CartLine;
import com.openclassrooms.shopmanager.cart.CartService;
import com.openclassrooms.shopmanager.order.Order;
import com.openclassrooms.shopmanager.order.OrderRepository;
import com.openclassrooms.shopmanager.order.OrderService;
import com.openclassrooms.shopmanager.product.ProductController;
import com.openclassrooms.shopmanager.product.ProductEntity;
import com.openclassrooms.shopmanager.product.ProductRepository;
import com.openclassrooms.shopmanager.product.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;
    
    @Mock
    ProductController productController;
    
    @InjectMocks
    OrderService orderService;
    
    @InjectMocks
    OrderRepository orderRepository;
    
    @InjectMocks
    CartService cartService;
    
    @InjectMocks
    Cart cart;
    
    @InjectMocks
    CartLine cartLine;
    
    @Mock
    ProductEntity productEntity;
     
    
    @Test
    public void addToCart_checkIfProductAdded_productAdded () { 
    	
    	orderService = new OrderService(orderRepository, productService, cartService);
    	
    	ProductEntity productEntity = new ProductEntity();
    	productEntity.setName("TestName");
    	productEntity.setId(1L);
    	productEntity.setPrice(1.0);
    	productEntity.setQuantity(1);
    	
    	when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
    	
    	boolean isItAdded = orderService.addToCart(1L);  
    	
    	assertTrue(isItAdded);
    	assertTrue(!cartService.getCartLineList().isEmpty());
    	assertEquals(1L, cartService.getCartLineList().get(0).getProduct().getId(), 0);
    	
    }
    
    @Test
    public void removeFromCart_checkIfProductRemoved_productRemoved () {
    	
    	orderService = new OrderService(orderRepository, productService, cartService);
    	
    	ProductEntity product1 = new ProductEntity();
    	product1.setName("TestName");
    	product1.setId(1L);
    	product1.setPrice(1.0);
    	product1.setQuantity(1);
    	
    	cartService.addItem(product1, 1);
    	
    	orderService.removeFromCart(1L);
    	
    	assertTrue(cartService.getCartLineList().isEmpty());
    	
    }
    
    @Test
    public void createOrder_checkIfOrderCreated_orderCreated () { 
    	
    	orderService = new OrderService(orderRepository, productService, cartService);
    	
    	ProductEntity product = new ProductEntity();
    	product.setName("TestName");
    	product.setId(1L);
    	product.setPrice(1.0);
    	product.setQuantity(1);
    	
    	cartService.addItem(product, 1);
    	
    	assertFalse(cartService.getCartLineList().isEmpty());
    	
    	Order order1 = new Order();
    	order1.setId(1L);
    	order1.setName("TestName1");
    	
    	orderService.createOrder(order1);
    	
    	assertTrue(cartService.getCartLineList().isEmpty());
    	
    	
    	
    }
}
