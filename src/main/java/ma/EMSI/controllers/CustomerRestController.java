package ma.EMSI.controllers;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.EMSI.dtos.CustomerDTO;
import ma.EMSI.exceptions.CustomerNotFoundException;
import ma.EMSI.services.interfaces.CustomerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ebank/customer")
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRestController {
	
	private CustomerService customerService;
	
	@GetMapping("/list")
	@ResponseBody
	public List<CustomerDTO> customers(){
		return customerService.listCustomers();
	}
	
	@GetMapping("/list/{id}")
	@ResponseBody
	public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
		return customerService.getCustomer(customerId);
	}
	
	@PostMapping("/save")
	@ResponseBody
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO request) {
		return customerService.saveCustomer(request);
	}
	
	@PutMapping("/update/{customerId}")
	@ResponseBody
	public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
		customerDTO.setId(customerId);
		return customerService.updateCustomer(customerDTO);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
	}
}
