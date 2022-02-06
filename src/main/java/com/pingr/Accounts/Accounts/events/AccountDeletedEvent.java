package com.pingr.Accounts.Accounts.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

@JsonSerialize
public class AccountDeletedEvent extends AccountEvent{

    private AccountDeletedEvent(Long accountId, Map<String, Object> payload) {
        super(accountId, payload);
    }

    public static AccountDeletedEvent of(Map<String, Object> accountMapView) {
        return new AccountDeletedEvent(
                (Long) accountMapView.get("id"),
                accountMapView
        );
    }
}
