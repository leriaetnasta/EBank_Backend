package ma.EMSI.services.interfaces;


import ma.EMSI.dtos.CustomerDTO;
import ma.EMSI.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> listCustomers();
    void deleteCustomer(Long customerId);
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;


}
