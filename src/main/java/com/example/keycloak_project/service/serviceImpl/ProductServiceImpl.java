package com.example.keycloak_project.service.serviceImpl;

import com.example.keycloak_project.exception.CustomNotfoundException;
import com.example.keycloak_project.model.dto.ProductDto;
import com.example.keycloak_project.model.dto.UserDto;
import com.example.keycloak_project.model.entity.Product;
import com.example.keycloak_project.model.request.ProductRequest;
import com.example.keycloak_project.repository.ProductRepository;
import com.example.keycloak_project.service.KeycloakService;
import com.example.keycloak_project.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final KeycloakService keycloakService;
    private final ModelMapper modelMapper;
    public ProductServiceImpl(ProductRepository productRepository, KeycloakService keycloakService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.keycloakService = keycloakService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto save(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setUserId(request.getUserId());
        productRepository.save(product);
        UserDto userDto = keycloakService.getUserById(product.getUserId());
        ProductDto productDto = new ProductDto(product, userDto);
        return productDto;
    }
//    @Override
//    public List<ProductDto> findAll() {
//        List<Product> products = productRepository.findAll();
//        List<ProductDto> productDtos = new ArrayList<>();
//        for (Product product : products) {
//            UserDto userDto = keycloakService.getUserById(product.getUserId());
//            ProductDto productDto = new ProductDto(product, userDto);
//            productDtos.add(productDto);
//        }
//        return productDtos;
//    }
//    @Override
//    public Page<ProductDto> findAll(int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        Page<Product> productPage = productRepository.findAll(pageable);
//
//        List<ProductDto> productDtos = productPage.getContent().stream()
//                .map(product -> {
//                    UserDto userDto = keycloakService.getUserById(product.getUserId());
//                    return new ProductDto(product, userDto);
//                })
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(productDtos, pageable, productPage.getTotalElements());
//    }
//
    @Override
    public Page<ProductDto> findAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(product -> {
            UserDto userDto = keycloakService.getUserById(product.getUserId());
            return new ProductDto(product, userDto);
        });
    }

    @Override
    public ProductDto findOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new CustomNotfoundException("Product not found")
        );
        UserDto userDto = keycloakService.getUserById(product.getUserId());
        ProductDto productDto = new ProductDto(product, userDto);
        return productDto;
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new CustomNotfoundException("Product not found")
        );
        productRepository.delete(product);
    }

    @Override
    public ProductDto update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new CustomNotfoundException("Product not found")
        );
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setUserId(request.getUserId());
        productRepository.save(product);
        UserDto userDto = keycloakService.getUserById(product.getUserId());
        ProductDto productDto = new ProductDto(product, userDto);
        return productDto;
    }
}
