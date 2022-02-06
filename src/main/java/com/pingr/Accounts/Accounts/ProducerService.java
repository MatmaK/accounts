package com.pingr.Accounts.Accounts;

import com.pingr.Accounts.Accounts.events.AccountCreatedEvent;
import com.pingr.Accounts.Accounts.events.AccountDeletedEvent;
import com.pingr.Accounts.Accounts.events.AccountUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

// forma de se comunicar com o Kakfa
// eventos que vamos publicar
// - accountCreated
// - accountUpdated
// - accountDeleted

@Service
public class ProducerService {

    @Value(value = "${topics.acc_created}")
    private String accountCreatedTopic;

    @Value(value = "${topics.acc_updated}")
    private String accountUpdatedTopic;

    @Value(value = "${topics.acc_deleted}")
    private String accountDeletedTopic;

    @Autowired // injeção de dependências
    private KafkaTemplate<String, Object> template;

    public void emitAccountCreatedEvent(Account account) {
        this.template.send(
                this.accountCreatedTopic,
                AccountCreatedEvent.of(account.toMap())
        );
    }

    public void emitAccountUpdatedEvent(Map<String, Object> beforeUpdateAcc, Map<String, Object> currentAccountMapView) {
        this.template.send(
                this.accountUpdatedTopic,
                AccountUpdatedEvent.of(beforeUpdateAcc, currentAccountMapView));
    }

    public void emitAccountDeletedEvent(Map<String, Object> accountMapView) {
        this.template.send(
                this.accountDeletedTopic,
                AccountDeletedEvent.of(accountMapView));
    }
}
