package com.jb.springdata.servicio;


import com.jb.springdata.modelo.Product;
import com.jb.springdata.repositorio.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImple implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> listProducts() {

        return  productRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Product product) {
        int res=0;
        Product products = productRepository.save(product);
        if(!products.equals(null)){
            res=1;
        }

    }

    @Override
    @Transactional
    public void delete(Product product) {
        productRepository.delete(product);

    }

    @Override
    @Transactional(readOnly = true)
    public Product findproduct(Product product) {
        return productRepository.findById(product.getIdProduct()).orElse(null);
    }
}
