package com.fmi.springbootcrud.repository;

import com.fmi.springbootcrud.data.Product;
import com.netflix.graphql.dgs.DgsComponent;
import org.springframework.data.repository.CrudRepository;

@DgsComponent
public interface ProductRepository extends CrudRepository<Product, Long> {
}
