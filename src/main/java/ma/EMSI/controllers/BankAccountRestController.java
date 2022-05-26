package ma.EMSI.controllers;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.EMSI.dtos.BankAccountDTO;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebank/account")
@Slf4j

@NoArgsConstructor
public class BankAccountRestController {
	
	private BankAccountService bankAccountService;

	public BankAccountRestController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}
	
	@GetMapping("/get/{accountId}")
	public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
		return bankAccountService.getBankAccount(accountId);
	}
	
	@GetMapping("/list")
	public List<BankAccountDTO> listAccount()  {
		return bankAccountService.listBankAccount();
	}
	

	
	
	

}
