package com.example.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardWithActions extends Card {

	private List<Action> actions;

	public CardWithActions(String id,String name, String desc) {
		super(name, desc);
	}


	public List<Action> getActions() {
		return actions;
	}

}
