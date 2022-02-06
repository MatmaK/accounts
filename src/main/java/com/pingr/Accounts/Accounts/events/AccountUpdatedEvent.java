package com.pingr.Accounts.Accounts.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

@JsonSerialize
public class AccountUpdatedEvent extends AccountEvent {

    @JsonProperty
    private final Map<String, Object> previousPayload;

    private AccountUpdatedEvent(Long accountId, Map<String, Object> payload, Map<String, Object> previousPayload) {
        super(accountId, payload);
        this.previousPayload = previousPayload;
    }

    public static AccountUpdatedEvent of(Map<String, Object> previousAccountMapView, Map<String, Object> currentAccountMapView) {
        return new AccountUpdatedEvent(
                (Long) currentAccountMapView.get("id"),
                currentAccountMapView,
                previousAccountMapView
        );
    }
}
