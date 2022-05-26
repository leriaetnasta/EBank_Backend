package ma.EMSI.services.interfaces;


import ma.EMSI.dtos.CustomerDTO;
import ma.EMSI.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> listCustomers();
    void deleteCustomer(Long customerId);
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;


}
