package com.pdh.player;

import com.pdh.poker.Card;

public class My extends Player{
	private Card[] holdcards;

	public Card[] getHoldcards() {
		return holdcards;
	}

	public void setHoldcards(Card[] holdcards) {
		this.holdcards = holdcards;
	}
}
