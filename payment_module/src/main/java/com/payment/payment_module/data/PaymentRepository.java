package com.payment.payment_module.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.payment_module.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
