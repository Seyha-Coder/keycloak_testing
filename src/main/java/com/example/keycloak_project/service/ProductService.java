package com.example.keycloak_project.service;

import com.example.keycloak_project.model.dto.ProductDto;
import com.example.keycloak_project.model.request.ProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDto save(ProductRequest request);
    Page<ProductDto> findAll(int page, int size, String sortBy, String direction);
    ProductDto findOne(Long id);
    void delete(Long id);
    ProductDto update(Long id, ProductRequest request);
}
