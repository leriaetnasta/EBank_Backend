package ma.EMSI.services.interfaces;


import ma.EMSI.dtos.*;
import ma.EMSI.entities.BankAccount;
import ma.EMSI.entities.CurrentAccount;
import ma.EMSI.entities.SavingAccount;
import ma.EMSI.enums.AccountStatus;
import ma.EMSI.enums.OperationType;
import ma.EMSI.exceptions.*;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


@Service

public interface BankAccountService {

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;



    List<BankAccountDTO> bankAccountList();

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}

