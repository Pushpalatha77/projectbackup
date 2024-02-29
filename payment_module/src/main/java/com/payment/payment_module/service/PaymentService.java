package com.payment.payment_module.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.payment_module.data.BillRepository;
import com.payment.payment_module.data.CustomerRepository;
import com.payment.payment_module.data.PaymentRepository;
import com.payment.payment_module.entity.Bill;
import com.payment.payment_module.entity.Customer;
import com.payment.payment_module.entity.Payment;
import com.payment.payment_module.exception.BillNotFoundException;
import com.payment.payment_module.exception.PaymentNotFoundException;

import java.util.Random;
import java.util.stream.Collectors;


@Service
public class PaymentService extends Exception{

	@Autowired

	private PaymentRepository paymentRepository;

	
	@Autowired

	private BillRepository billRepository;
	

	@Autowired

	private CustomerRepository customerRepository;


	public double latePaidAmount(Payment payment) {

		double finePerDay = 13;

		long days = ChronoUnit.DAYS.between(payment.getPaymentDate(), payment.getBill().getDueDate());

		double fineAmount = Math.abs(days * finePerDay);

		return fineAmount;

	}

	
	//Post Mapping
	public void assign(Payment payment) {
	    payment.setTotalAmount(payment.getBill().getAmount() + latePaidAmount(payment));
	    
	    // Generate transaction ID
	    String transactionId = generateTransactionId();
	    payment.setTransactionId(transactionId);
	    
	    paymentRepository.save(payment);
	}

	private String generateTransactionId() {
	    // Define the length of the transaction ID
	    int length = 10;
	    
	    // Define the characters allowed in the transaction ID
	    String allowedCharacters = "0123456789";
	    
	    Random random = new Random();
	    StringBuilder transactionIdBuilder = new StringBuilder(length);
	    
	    // Generate random digits for the transaction ID
	    for (int i = 0; i < length; i++) {
	        int randomIndex = random.nextInt(allowedCharacters.length());
	        char randomChar = allowedCharacters.charAt(randomIndex);
	        transactionIdBuilder.append(randomChar);
	    }
	    
	    return transactionIdBuilder.toString();
	}

	
	//GetAll
	public List<Payment> getAllPaymentRecords() {
		// TODO Auto-generated method stub
		return paymentRepository.findAll();
	}
	

	//Get By Payment Id
	public Optional<Payment> getPaymentById(int id) throws PaymentNotFoundException{
		// TODO Auto-generated method stub
		Optional<Payment> payment = paymentRepository.findById(id);
		if (!payment.isPresent()) {
			throw new PaymentNotFoundException("Payment not found with ID: " + id);
		}
		return payment;
	}
	
	
	//Delete Mapping
		public void deletePaymentById(int id) throws PaymentNotFoundException{
			Optional<Payment> payment = paymentRepository.findById(id);
			if (!payment.isPresent()) {
				throw new PaymentNotFoundException("Payment not found with ID: " + id);
			}
			paymentRepository.deleteById(id);
		}

		
	// Get payment by customer ID
	public List<Payment> getPaymentByCustomerId(int cid) throws PaymentNotFoundException{
		// Fetch all payments from the DB
		List<Payment> list = paymentRepository.findAll();

		List<Payment> filteredList = list.stream().filter(e -> e.getCustomer().getId() == cid)
				.collect(Collectors.toList());
		
		return filteredList;
	}
	
	public Optional<Bill> getBillById(int id) throws BillNotFoundException {

		Optional<Bill> bill = billRepository.findById(id);
		if (!bill.isPresent()) {
			throw new BillNotFoundException("Bill not found with ID: " + id);
		}
		return bill;
	}
	
	public Optional<Customer> getCustomerById(int id) {
		// TODO Auto-generated method stub
		Optional<Customer> optional = customerRepository.findById(id);
		return optional;
	}


	
	
}

