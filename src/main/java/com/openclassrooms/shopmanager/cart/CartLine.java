package com.openclassrooms.shopmanager.cart;

import com.openclassrooms.shopmanager.product.ProductEntity;

public class CartLine {

   private int orderLineID;
   private ProductEntity product;
   private int quantity;

   public double getSubtotal() {
       return quantity * product.getPrice();
   }

   public int getOrderLineID() {
       return orderLineID;
   }

   public void setOrderLineID(int orderLineID) {
       this.orderLineID = orderLineID;
   }

   public ProductEntity getProduct() {
       return product;
   }

   public void setProduct(ProductEntity product) {
       this.product = product;
   }

   public int getQuantity() {
       return quantity;
   }

   public void setQuantity(int quantity) {
       this.quantity = quantity;
   }
}
