package net.springboot.service;

import java.util.List;

import net.springboot.model.Product;

public interface ProductService {
	
    Product createProduct(Product product);

    Product updateProduct(Product product);

    List < Product > getAllProduct();

    Product getProductById(long productId);

    void deleteProduct(long id);
}