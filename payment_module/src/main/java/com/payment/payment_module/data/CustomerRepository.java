package com.payment.payment_module.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.payment_module.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{

}
