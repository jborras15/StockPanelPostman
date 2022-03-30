package com.jb.springdata.service;

import com.jb.springdata.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);
    public List<Product> listProducts();
    public  void save(Product product);
    public  void delete(Product product);
    public  Product actualizar(Product product);
    public Product findproduct(Long id);
    public List<Product> findByTitle(String q);
}
