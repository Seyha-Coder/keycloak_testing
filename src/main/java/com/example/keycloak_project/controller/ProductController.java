package com.example.keycloak_project.controller;

import com.example.keycloak_project.model.dto.ProductDto;
import com.example.keycloak_project.model.request.ProductRequest;
import com.example.keycloak_project.model.response.ApiResponse;
import com.example.keycloak_project.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@SecurityRequirement(name = "oauth")

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody ProductRequest request) {
        ProductDto productDto = productService.save(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Product saved successfully")
                .status(HttpStatus.CREATED)
                .code(201)
                .payload(productDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Page<ProductDto> productDtoList = productService.findAll(page, size,sortBy,sortDirection);
        List<ProductDto> products = productDtoList.getContent();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Product retrieve successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(products)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable Long id) {
        ProductDto productDto = productService.findOne(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Product with id"+ id +" retrieve successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(productDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id ,@RequestBody ProductRequest request) {
        ProductDto productDto = productService.update(id, request);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Product updated successfully")
                .status(HttpStatus.OK)
                .code(201)
                .payload(productDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Product delete successfully")
                .status(HttpStatus.OK)
                .code(200)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
