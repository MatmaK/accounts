package com.pingr.Accounts.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository repo;
    private final ProducerService producer;

    @Autowired
    public AccountService(AccountRepository repo, ProducerService producer) {
        this.repo = repo;
        this.producer = producer;
    }

    public List<Account> findAll() {
        return repo.findAll();
    }

    //   tem ID, está salva no banco
    //     |
    //     |         não tem ID, não está no banco ainda
    //     |                                  |
    //     v                                  v
    public Account createAccount(Account account) throws IllegalArgumentException {
        try {
            Account acc = this.repo.save(account);
            this.producer.emitAccountCreatedEvent(acc);
            return acc;
        } catch(Exception e) {
            throw new IllegalArgumentException("Account creation violates restrictions" + "[account: " + account + "]");
        }
    }

    public Map<String, Map<String, Object>> updateAccount(Long id, Account account) throws IllegalArgumentException {
        try {
            Optional<Account> optionalAccount = this.repo.findById(id);

            if (optionalAccount.isEmpty())
                throw new IllegalArgumentException();

            account.setId(id);
            //Pegar o map antes da alteração, pois, aparentemente a alteração é 'runtime'
            Map<String, Object> previousAccountMapView = optionalAccount.get().toMap();

            this.repo.save(account);
            this.producer.emitAccountUpdatedEvent(previousAccountMapView, account.toMap());

            return Map.of("previous", previousAccountMapView, "current", account.toMap());
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Account updating violates restrictions" + "[account: " + account + "]");
        }
    }

    public void deleteAccount(Long id) throws IllegalArgumentException {
        try {
            Optional<Account> optionalAccount = this.repo.findById(id);

            if (optionalAccount.isEmpty())
                throw new Exception();

            Account account = optionalAccount.get();
            Map<String, Object> accountMapView = account.toMap();

            this.repo.delete(account);
            this.producer.emitAccountDeletedEvent(accountMapView);
        }
        catch(Exception e) {
            throw new IllegalArgumentException("Id not found");
        }
    }
}
