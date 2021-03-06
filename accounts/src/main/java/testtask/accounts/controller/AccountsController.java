package testtask.accounts.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testtask.accounts.model.Account;
import testtask.accounts.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Api(value = "accounts", description = "Rest API for accounts operations", tags = "Accounts API")
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public List getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> get(@PathVariable("id") Long id) {
        Account account = accountService.get(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/ClientId/{id}")
    public ResponseEntity<List<Account>> getByClientId(@PathVariable("id") Long clientId) {
        return new ResponseEntity<>(accountService.findByClientId(clientId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Account> create(@RequestBody Account account) {
        account = accountService.create(account);
        return new ResponseEntity<>(account, HttpStatus.OK);

    }

    @PostMapping("/list")
    public ResponseEntity<List<Account>> createList(@RequestBody List<Account> accounts) {
        List<Account> created = accountService.create(accounts);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        accountService.update(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/list/client/{clientId}")
    public ResponseEntity<List<Account>> updateList(@PathVariable Long clientId, @RequestBody List<Account> accounts) {
        List<Account> updatedAccounts = accountService.updateAllAccountsOfClient(accounts, clientId);
        return new ResponseEntity(updatedAccounts, HttpStatus.OK);
    }

//    @PatchMapping("/list")
//    public ResponseEntity patchUpdate() {
//        // todo
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        accountService.delete(accountService.get(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/clientId/{clientId}")
    public ResponseEntity deleteByClientId(@PathVariable("clientId") Long clientId) {
        accountService.deleteAllAccountsOfClient(clientId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/list")
    public ResponseEntity delete(@RequestBody List<Account> accounts) {
        accountService.delete(accounts);
        return new ResponseEntity(HttpStatus.OK);
    }

}
