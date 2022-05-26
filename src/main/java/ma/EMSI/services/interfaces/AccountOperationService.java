package ma.EMSI.services.interfaces;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.EMSI.dtos.AccountHistoryDTO;
import ma.EMSI.dtos.AccountOperationDTO;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface AccountOperationService {
    List<AccountOperationDTO> historique(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;


}
