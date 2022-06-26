package ma.EMSI.controllers;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.EMSI.dtos.BankAccountDTO;
import ma.EMSI.dtos.CreditDTO;
import ma.EMSI.dtos.DebitDTO;
import ma.EMSI.dtos.TransferRequestDTO;
import ma.EMSI.exceptions.BalanceNotSufficientException;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
public class BankAccountRestController {
	
	private BankAccountService bankAccountService;

	public BankAccountRestController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	@GetMapping("/accounts/{accountId}")
	public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
		return bankAccountService.getBankAccount(accountId);
	}

	@GetMapping("/accounts")
	public List<BankAccountDTO> listAccounts(){
		return bankAccountService.bankAccountList();
	}
	@PostMapping("/accounts/debit")
	public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
		this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
		return debitDTO;
	}
	@PostMapping("/accounts/credit")
	public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
		this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
		return creditDTO;
	}
	@PostMapping("/accounts/transfer")
	public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BalanceNotSufficientException {
		this.bankAccountService.transfer(
				transferRequestDTO.getAccountSource(),
				transferRequestDTO.getAccountDestination(),
				transferRequestDTO.getAmount());
	}
	

	
	
	

}
