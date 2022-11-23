package net.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.springboot.model.Product;

public interface ProductRepository extends MongoRepository < Product, Long > {

}
