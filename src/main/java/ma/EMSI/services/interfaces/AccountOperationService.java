package ma.EMSI.services.interfaces;


import ma.EMSI.dtos.AccountHistoryDTO;
import ma.EMSI.dtos.AccountOperationDTO;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountOperationService {
    List<AccountOperationDTO> historique(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;


}
