package com.fmi.springbootcrud.fetcher;

import com.fmi.springbootcrud.data.Product;
import com.fmi.springbootcrud.repository.ProductRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@DgsComponent
public class ProductFetcher {

    @Autowired
    private ProductRepository productRepository;

    @DgsData(parentType = "Query", field = "products")
    @RolesAllowed({"ADMIN", "USER"})
    public Iterable<Product> getAll() {
        return this.productRepository.findAll();
    }

    @DgsData(parentType = "Mutation", field = "createProduct")
    @RolesAllowed("ADMIN")
    public boolean createProduct(@InputArgument(value = "name") String name,
                                @InputArgument(value = "price") Double price)  {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return this.productRepository.save(product) != null;
    }
}
