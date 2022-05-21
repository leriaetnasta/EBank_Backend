package ma.EMSI.controllers;

import ma.EMSI.dtos.AccountHistoryDTO;
import ma.EMSI.dtos.AccountOperationDTO;
import ma.EMSI.exceptions.BankAccountNotFoundException;
import ma.EMSI.services.interfaces.AccountOperationService;
import ma.EMSI.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
