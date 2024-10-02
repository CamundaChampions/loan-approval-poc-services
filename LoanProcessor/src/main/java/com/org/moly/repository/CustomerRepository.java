package com.org.moly.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.org.moly.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	
	
	Optional<Customer> findById(Long id);

}
