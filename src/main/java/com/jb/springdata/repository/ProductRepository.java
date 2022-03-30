package com.jb.springdata.repository;

import com.jb.springdata.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products WHERE products.name LIKE  %:q% ", nativeQuery = true)
    List<Product> findByTitle(@Param("q") String q);
}
