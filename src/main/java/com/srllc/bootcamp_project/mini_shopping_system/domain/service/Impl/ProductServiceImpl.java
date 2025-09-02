package com.srllc.bootcamp_project.mini_shopping_system.domain.service.Impl;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dao.ProductDao;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.ProductDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Product;
import com.srllc.bootcamp_project.mini_shopping_system.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Override
    public Product createProduct(ProductDto productDto) {
        log.info("Creating new product: {}", productDto.getProductName());

        Product product = new Product();
        product.setName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        Product savedProduct = productDao.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product updateProduct(Long id, ProductDto productDto) {
        log.info("Updating product with ID: {}", id);

        Product existingProduct = productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));

        existingProduct.setName(productDto.getProductName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStock(productDto.getStock());

        Product updatedProduct = productDao.save(existingProduct);
        log.info("Product updated successfully with ID: {}", updatedProduct.getId());

        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);

        if (!productDao.existsById(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }

        productDao.deleteById(id);
        log.info("Product deleted successfully with ID: {}", id);

    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productDao.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Fetching product with ID: {}", id);

        return productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
    }
}
