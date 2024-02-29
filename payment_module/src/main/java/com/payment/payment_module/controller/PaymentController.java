package com.payment.payment_module.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.payment.payment_module.entity.Bill;
import com.payment.payment_module.entity.Customer;
import com.payment.payment_module.entity.Payment;
import com.payment.payment_module.exception.PaymentNotFoundException;

import com.payment.payment_module.service.PaymentService;



@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	
	// Post
		@PostMapping("/add/{billId}/{custId}")
		public ResponseEntity<String> BillPayment(@RequestBody Payment payment,

				@PathVariable("billId") int id,

				@PathVariable("custId") int custId) throws Exception {

			// fetch the bill object based on billId

			Optional<Bill> optionalBill = paymentService.getBillById(id);

			if (!optionalBill.isPresent())

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("entered invalid billId");

			// fetch the customer object based on customerId

			Optional<Customer> optionalCustomer = paymentService.getCustomerById(custId);

			if (!optionalCustomer.isPresent())

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("entered invalid custId");

			Bill bill = optionalBill.get();

			Customer customer = optionalCustomer.get();

			payment.setBill(bill);

			payment.setCustomer(customer);

			// save the payment object

			paymentService.assign(payment);
			//LoggerUtil.logInfo("Payment Successful ");
			return ResponseEntity.status(HttpStatus.OK).body("Payment Succesfull!!!");

		}


	
	//GetAll
		@GetMapping("/api/payment/getall")
		public List<Payment> getAllPaymentRecords(){
			List<Payment> list = paymentService.getAllPaymentRecords();
			//LoggerUtil.logInfo(" All Payments details ");
			return list;
		}
		
		// Get By Payment Id
		@GetMapping("/api/payment/{paymentId}")
		public ResponseEntity<Object> getPaymentById(@PathVariable("paymentId") int paymentId)
				throws PaymentNotFoundException {
			Optional<Payment> optional = paymentService.getPaymentById(paymentId);
			if (!optional.isPresent())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");

			Payment payment = optional.get();
		//	LoggerUtil.logInfo("Payment Details by ID ");
			return ResponseEntity.status(HttpStatus.OK).body(payment);
		}

		// Delete
		@DeleteMapping("/api/payment/delete/{paymentId}")

		public ResponseEntity<String> deletePaymentById(@PathVariable("paymentId") int id, @RequestBody Payment payment)
				throws PaymentNotFoundException {

			paymentService.deletePaymentById(id);
			//LoggerUtil.logInfo("Payment Deleted ");
			return ResponseEntity.status(HttpStatus.OK).body("Payment Record is Deleted");

		}

		// 1.Get Payment by Customer id

		@GetMapping("/api/payment/customer/{cid}")
		public List<Payment> getPaymentByCustomerId(@PathVariable("cid") int cid) throws Exception {

			List<Payment> list = paymentService.getPaymentByCustomerId(cid);
			return list;
		}

}



