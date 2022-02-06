package com.pingr.Accounts.Accounts.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

@JsonSerialize
public class AccountCreatedEvent extends AccountEvent {

    private AccountCreatedEvent(Long accountId, Map<String, Object> payload) {
        super(accountId, payload);
    }

    public static AccountCreatedEvent of(Map<String, Object> accountMapView) {
        return new AccountCreatedEvent(
                (Long) accountMapView.get("id"),
                accountMapView
        );
    }
}
