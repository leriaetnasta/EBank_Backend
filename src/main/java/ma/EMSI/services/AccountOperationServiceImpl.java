package ma.EMSI.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.EMSI.dtos.AccountHistoryDTO;
import ma.EMSI.dtos.AccountOperationDTO;
import ma.EMSI.entities.AccountOperation;
import ma.EMSI.entities.BankAccount;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.mappers.BankAccountMapperImpl;
import ma.EMSI.repositories.AccountOperationRepository;
import ma.EMSI.repositories.BankAccountRepository;
import ma.EMSI.services.interfaces.AccountOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService {
    private BankAccountMapperImpl dtoMapper;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;


    @Override
    public List<AccountOperationDTO> historique(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId); // we created a list that recieves the output of findByBankAccountId
        // which is a methode that returns all of the operations done by the account of which its id is passed as a parametre
        return accountOperations.stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
}
