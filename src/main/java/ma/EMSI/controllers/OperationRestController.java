package ma.EMSI.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.EMSI.dtos.AccountHistoryDTO;
import ma.EMSI.dtos.AccountOperationDTO;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.services.interfaces.AccountOperationService;
import ma.EMSI.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class OperationRestController {
    private AccountOperationService accountOperationService;
    @GetMapping("/operations/{accountId}")
    public List<AccountOperationDTO> getHistorique(@PathVariable String accountId){
        return accountOperationService.historique(accountId); // retourne l'id de compte
    }

    @GetMapping("/pageOperations/{accountId}")
    public AccountHistoryDTO getAccountHistorique(
            @PathVariable String accountId,
            @RequestParam(name ="page", defaultValue = "0") int page,
            @RequestParam(name ="size", defaultValue = "5") int size) throws BankAccountNotFoundException{

        return accountOperationService.getAccountHistory(accountId, page, size);
    }
}
