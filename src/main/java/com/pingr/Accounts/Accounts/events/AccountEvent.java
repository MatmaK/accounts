package com.pingr.Accounts.Accounts.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

abstract class AccountEvent {
    @JsonProperty
    protected final String eventType;

    @JsonProperty
    protected final Long accountId;

    @JsonProperty
    protected final Map<String, Object> payload;

    protected AccountEvent(Long accountId, Map<String, Object> payload) {
        this.eventType = this.getClass().getSimpleName();
        this.accountId = accountId;
        this.payload = payload;
    }

}
