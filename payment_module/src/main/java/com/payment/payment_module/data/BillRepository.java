package com.payment.payment_module.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.payment_module.entity.Bill;

public interface BillRepository  extends JpaRepository<Bill, Integer>{

}
