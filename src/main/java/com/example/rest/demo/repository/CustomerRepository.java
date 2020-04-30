package com.example.rest.demo.repository;

import com.example.rest.demo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}