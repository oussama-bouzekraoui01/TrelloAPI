package com.example.trello.domain;

import com.example.trello.Trello;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloEntity {

    @JsonIgnore
    protected Trello trelloService;

    @SuppressWarnings("unchecked")
    public <T extends TrelloEntity> T setInternalTrello(Trello trelloService) {
        this.trelloService = trelloService;
        return (T) this;
    }

    protected Trello getTrelloService() {
        if (trelloService == null)
            throw new IllegalStateException("The trelloService not initialized. Please call setInternalTrello before.");

        return trelloService;
    }
}
