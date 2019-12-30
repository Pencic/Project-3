package com.openclassrooms.shopmanager.product;

import com.openclassrooms.shopmanager.cart.CartLine;
import com.openclassrooms.shopmanager.cart.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private CartService cartService;

    @Autowired
    public ProductService(ProductRepository repository, CartService cartService) {
        this.productRepository = repository;
        this.cartService = cartService;
    }

    /**
     * @return all products from the inventory
     */
    public List<ProductEntity> getAllProducts() {

        return productRepository.findAll();
    }

    public List<ProductEntity> getAllAdminProducts() {

        return productRepository.findAllByOrderByIdDesc();
    }

    public ProductEntity getByProductId(Long productId){
        return productRepository.findById(productId).get();
    }

    public ProductEntity createProduct(Product productModel){
        ProductEntity product = new ProductEntity();
        product.setDescription(productModel.getDescription());
        product.setDetails(productModel.getDetails());
        product.setName(productModel.getName());
        product.setPrice(productModel.getPrice());
        product.setQuantity(productModel.getQuantity());

       return productRepository.save(product);
    }

    public void deleteProduct(Long productId){
        // TODO What happens if a product has been added to a cart and then later gets removed from the inventory ?
        // delete the product form the cart by using the specific method
        // => the choice is up to the student
        productRepository.deleteById(productId);
    }

    public void updateProductQuantities(CartService cartService){

        for (CartLine cartLine : cartService.getCartLineList()) {
            Optional<ProductEntity> productOptional = productRepository.findById(cartLine.getProduct().getId());
            if (productOptional.isPresent()){
                ProductEntity product = productOptional.get();
                product.setQuantity(product.getQuantity() - cartLine.getQuantity());
                if (product.getQuantity()<1){
                    productRepository.delete(product);
                }else {
                    productRepository.save(product);
                }
            }
        }
    }

}
