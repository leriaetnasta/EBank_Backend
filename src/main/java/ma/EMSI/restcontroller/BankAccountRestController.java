package ma.EMSI.restcontroller;


import lombok.extern.slf4j.Slf4j;
import ma.EMSI.dtos.AccountHistoryDTO;
import ma.EMSI.dtos.AccountOperationDTO;
import ma.EMSI.dtos.BankAccountDTO;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebank/account")
@Slf4j
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
	
	@GetMapping("/operations/{accountId}")
	public List<AccountOperationDTO> getHistorique(@PathVariable String accountId){
		return bankAccountService.historique(accountId);
	}
	
	@GetMapping("/pageOperations/{accountId}")
	public AccountHistoryDTO getAccountHistorique(
			@PathVariable String accountId,
			@RequestParam(name ="page", defaultValue = "0") int page,
			@RequestParam(name ="size", defaultValue = "5") int size) throws BankAccountNotFoundException{
		
		return bankAccountService.getAccountHistory(accountId, page, size);
	}
	
	
	

}
