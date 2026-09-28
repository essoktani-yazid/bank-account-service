package ma.enset.bankaccountservice.web;

import ma.enset.bankaccountservice.entities.BankAccount;
import ma.enset.bankaccountservice.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountRestController {

    private BankAccountRepository bankAccountRepository;

    public AccountRestController(BankAccountRepository bankAccountRepository){
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return this.bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return this.bankAccountRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                String.format("Account %s not found", id)
                        )
                );
    }

    @PostMapping("/BankAccounts")
    public BankAccount save(@RequestBody BankAccount bankAccount){
        bankAccount.setId(UUID.randomUUID().toString());
        return this.bankAccountRepository.save(bankAccount);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount){
        BankAccount account = this.bankAccountRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Account %s not found", id))
        );

        if(bankAccount.getBalance() != null)
            account.setBalance(bankAccount.getBalance());

        if(bankAccount.getCreatedAt() != null)
            account.setCreatedAt(bankAccount.getCreatedAt());

        if(bankAccount.getCurrency() != null)
            account.setCurrency(bankAccount.getCurrency());

        if(bankAccount.getType() != null)
            account.setType(bankAccount.getType());

        return this.bankAccountRepository.save(account);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void delete(@PathVariable String id){
        this.bankAccountRepository.deleteById(id);
    }

}
