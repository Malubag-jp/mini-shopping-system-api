package com.srllc.bootcamp_project.mini_shopping_system.domain.service;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.ProductDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDto productDto);
    Product updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
    Product getProductById(Long id);
}
