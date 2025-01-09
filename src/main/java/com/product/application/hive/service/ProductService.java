package com.product.application.hive.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.application.hive.model.Product;
import com.product.application.hive.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	public Product saveProduct(Product product) {
		product.setCreatedAt(LocalDateTime.now());
		return productRepo.save(product);
	}
	
	public List<Product> getAllProduct(){
		return productRepo.findAll();
	}
	
	public Optional<Product> getProductById(Long id) {
		return productRepo.findById(id);
	}
	
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}
	
	public Product updateProduct(Long productId, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepo.findById(productId);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            return productRepo.save(existingProduct);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
    }
}
