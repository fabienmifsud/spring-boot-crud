package com.fmi.springbootcrud.repository;

import com.fmi.springbootcrud.data.Client;
import com.netflix.graphql.dgs.DgsComponent;
import org.springframework.data.repository.CrudRepository;

@DgsComponent
public interface ClientRepository extends CrudRepository<Client, Long> {
}
