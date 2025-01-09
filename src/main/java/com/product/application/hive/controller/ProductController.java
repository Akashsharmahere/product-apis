package com.product.application.hive.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.application.hive.model.Product;
import com.product.application.hive.model.User;
import com.product.application.hive.service.ProductService;
import com.product.application.hive.service.UserService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<Product> saveProductDetails(@RequestBody Product product) {
	    System.out.println("Received Product: " + product);
	    Product savedProduct = productService.saveProduct(product);
	    System.out.println("Saved Product: " + savedProduct);
	    return ResponseEntity.status(201).body(savedProduct);
	}
	
	@GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
	    Optional<Product> product = productService.getProductById(id);
	    return product.isPresent() 
	            ? ResponseEntity.ok(product) 
	            : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProducts(@PathVariable Long id, @RequestBody Product product){
		try {
            Product updatedProduct = productService.updateProduct(id, product);
            
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } catch (Exception e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping("/user/add")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
	    try {
	        User user = userService.getUserById(product.getUser().getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        product.setUser(user);  // Set the user for the product
	        Product savedProduct = productService.saveProduct(product);
	        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Failed to add product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


}
