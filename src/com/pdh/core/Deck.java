package com.pdh.core;

import java.util.ArrayList;
import java.util.List;

import com.pdh.player.Player;
import com.pdh.poker.Card;

public class Deck {
	private int playernum=0;
	private String state;
	private int bigblind;
	private int smallblind;
	private int totalpot;
	private int bet;
	private static List<Player> players;
	private Card[] cards;
	private static Deck deck = new Deck();
	private Deck(){
		bigblind=0;
		smallblind=0;
		totalpot=0;
		bet =0;
		players=new ArrayList<Player>();
		cards = new Card[5];
	}
	public int getPlayernum() {
		return playernum;
	}
	public void setPlayernum(int playernum) {
		this.playernum = playernum;
	}
	public int getBigblind() {
		return bigblind;
	}
	public void setBigblind(int bigblind) {
		this.bigblind = bigblind;
	}
	public int getSmallblind() {
		return smallblind;
	}
	public void setSmallblind(int smallblind) {
		this.smallblind = smallblind;
	}
	public int getTotalpot() {
		return totalpot;
	}
	public void setTotalpot(int totalpot) {
		this.totalpot = totalpot;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		Deck.players = players;
	}
	public static Deck getDeck() {
		if(null==deck){
			deck = new Deck();
		}
		return deck;
	}
	public static void setDeck(Deck deck) {
		Deck.deck = deck;
	}
	public Card[] getCards() {
		return cards;
	}
	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
