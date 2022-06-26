package ma.EMSI.controllers;

import java.util.List;

import lombok.AllArgsConstructor;
import ma.EMSI.dtos.CustomerDTO;
import ma.EMSI.exceptions.CustomerNotFoundException;
import ma.EMSI.services.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;


import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")


public class CustomerRestController {
	
	private CustomerService customerService;

	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return customerService.listCustomers();
	}

	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
		return customerService.getCustomer(customerId);
	}

	@PostMapping("/customers")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
		return customerService.saveCustomer(customerDTO);
	}

	@PutMapping("/customers/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
		customerDTO.setId(customerId);
		return customerService.updateCustomer(customerDTO);
	}

	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
	}
}
