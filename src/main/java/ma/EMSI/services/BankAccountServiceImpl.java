package ma.EMSI.services;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.EMSI.dtos.*;
import ma.EMSI.entities.*;
import ma.EMSI.enums.OperationType;
import ma.EMSI.exceptions.BalanceNotSufficientException;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.exceptions.CustomerNotFoundException;
import ma.EMSI.mappers.BankAccountMapperImpl;
import ma.EMSI.repositories.AccountOperationRepository;
import ma.EMSI.repositories.BankAccountRepository;
import ma.EMSI.repositories.CustomerRepository;
import ma.EMSI.services.interfaces.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {


	private CustomerRepository customerRepository;
	private BankAccountRepository bankAccountRepository;
	private AccountOperationRepository accountOperationRepository;
	private BankAccountMapperImpl dtoMapper;

	@Override
	public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException {
		Customer customer=customerRepository.findById(customerId).orElse(null);
		if(customer==null)
			throw new CustomerNotFoundException("Customer not found");
		CurrentAccount currentAccount=new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setCreatedAt(new Date()); currentAccount.setBalance(initialBalance);
		currentAccount.setOverDraft(overDraft); currentAccount.setCustomer(customer);
		CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
		return dtoMapper.fromCurrentBankAccount(savedBankAccount);}
	@Override
	public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer=customerRepository.findById(customerId).orElse(null);
		if(customer==null)
			throw new CustomerNotFoundException("Customer not found");
		SavingAccount savingAccount=new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setCreatedAt(new Date()); savingAccount.setBalance(initialBalance);
		savingAccount.setInterestRate(interestRate);
		savingAccount.setCustomer(customer);
		SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
		return dtoMapper.fromSavingBankAccount(savedBankAccount);}

	@Override
	public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount=bankAccountRepository.findById(accountId)
				.orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
		if(bankAccount instanceof SavingAccount){//test sur le type de compte
			SavingAccount savingAccount= (SavingAccount) bankAccount;
			return dtoMapper.fromSavingBankAccount(savingAccount);//transferer vers un dto
		} else {
			CurrentAccount currentAccount= (CurrentAccount) bankAccount;
			return dtoMapper.fromCurrentBankAccount(currentAccount);}}
	@Override
	public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
		BankAccount bankAccount=bankAccountRepository.findById(accountId)
				.orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
		if(bankAccount.getBalance()<amount)
			throw new BalanceNotSufficientException("Balance not sufficient");
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.DEBIT);
		accountOperation.setAmount(amount); accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date()); accountOperation.setBankAccount(bankAccount);
		accountOperationRepository.save(accountOperation); bankAccount.setBalance(bankAccount.getBalance()-amount);
		bankAccountRepository.save(bankAccount);}
	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		BankAccount bankAccount=bankAccountRepository.findById(accountId)
				.orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.CREDIT);
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date());
		accountOperation.setBankAccount(bankAccount);
		accountOperationRepository.save(accountOperation);
		bankAccount.setBalance(bankAccount.getBalance()+amount);
		bankAccountRepository.save(bankAccount);}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amount) throws
			BankAccountNotFoundException, BalanceNotSufficientException {
		debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
		credit(accountIdDestination,amount,"Transfer from "+accountIdSource);}
	@Override
	public List<BankAccountDTO> listBankAccount(){
		List<BankAccount> bankAccounts = bankAccountRepository.findAll();
		List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
			if (bankAccount instanceof SavingAccount) {
				SavingAccount savingAccount = (SavingAccount) bankAccount;
				return dtoMapper.fromSavingBankAccount(savingAccount);
			} else {
				CurrentAccount currentAccount = (CurrentAccount) bankAccount;
				return dtoMapper.fromCurrentBankAccount(currentAccount);
			}
		}).collect(Collectors.toList());//collecter des objets de type dto
		return bankAccountDTOS;}



	

}
