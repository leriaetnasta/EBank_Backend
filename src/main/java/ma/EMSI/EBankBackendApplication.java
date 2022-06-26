package ma.EMSI;

import ma.EMSI.dtos.BankAccountDTO;
import ma.EMSI.dtos.CurrentBankAccountDTO;
import ma.EMSI.dtos.CustomerDTO;
import ma.EMSI.dtos.SavingBankAccountDTO;
import ma.EMSI.entities.AccountOperation;
import ma.EMSI.entities.CurrentAccount;
import ma.EMSI.entities.Customer;
import ma.EMSI.entities.SavingAccount;
import ma.EMSI.enums.AccountStatus;
import ma.EMSI.enums.OperationType;
import ma.EMSI.exceptions.CustomerNotFoundException;
import ma.EMSI.repositories.AccountOperationRepository;
import ma.EMSI.repositories.BankAccountRepository;
import ma.EMSI.repositories.CustomerRepository;
import ma.EMSI.services.interfaces.BankAccountService;
import ma.EMSI.services.interfaces.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBankBackendApplication.class, args);
	}

	//@Bean
	//pour tester, pour chaque client, il a un compte et pour chaque compte il a des operations
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService, CustomerService customerService){
		return args -> {
			Stream.of("LOubna","Hayat","Imane").forEach(name->{
				CustomerDTO customer=new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerService.saveCustomer(customer);
			});
			//listcustomers contient la liste des clients
			// on l'utilise pour creer un compte pour chaque client
			customerService.listCustomers().forEach(customer->{
				try {
					//savecurrentbankaccount au lieu de setters
					bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
			for (BankAccountDTO bankAccount:bankAccounts){
				for (int i = 0; i <10 ; i++) {
					String accountId;
					if(bankAccount instanceof SavingBankAccountDTO){
						accountId=((SavingBankAccountDTO) bankAccount).getId();
					} else{
						accountId=((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
				}
			}
		};
	}



}
