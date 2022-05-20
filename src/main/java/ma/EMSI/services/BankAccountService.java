package ma.EMSI.services;

import ma.EMSI.dtos.*;
import ma.EMSI.exceptions.BalanceNotSufficientException;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.exceptions.CustomerNotFoundException;

import java.util.List;


public interface BankAccountService {
	
	CustomerDTO saveCustomer(CustomerDTO customerDTO);
	
	CustomerDTO updateCustomer(CustomerDTO customerDTO);
	
	List<CustomerDTO> listCustomers();
	
	void deleteCustomer(Long customerId);
	
	CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
	
	CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
	
	SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
	
	BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
	
	void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
	
	void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
	
	void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

	List<BankAccountDTO> listBankAccount();

	List<AccountOperationDTO> historique(String accountId);

	AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
