/**
 * www.TheAIGames.com 
 * Heads Up Omaha pokerbot
 *
 * Last update: May 07, 2014
 *
 * @author Jim van Eeden, Starapple
 * @version 1.0
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */


package com.pdh.poker;

import java.util.HashMap;
import java.util.Map;

/**
 * A Card class object represents one card
 */
public class Card
{
	private CardHeight height;//卡牌大小枚举属性，eg：ACE,DEUCE...
	private CardSuit suit;//卡牌花型枚举属性，rg：CLUBS,SPADES...
	private int number;//number between 0 and 51
	private static Map<String,Card> stringToCard;

	
	/**
	 * Creates a card object based on a number between 0 and 51
	 */
	public Card(int num)
	{
		// number  : 0 1 2 3 4 5 6 7 8  9 10 11 12 13 14 15 16 17...
		// suit    : S S S S S S S S S  S S  S  H  H  H  H  H ...
		// size    : 2 3 4 5 6 7 8 9 10 J Q  K  A
		number = num;
		int findSuit = number / 13;
		switch(findSuit)
		{
			case 0 : suit = CardSuit.SPADES; break;//黑桃
			case 1 : suit = CardSuit.HEARTS; break;//红心
			case 2 : suit = CardSuit.CLUBS; break; //梅花
			default : suit = CardSuit.DIAMONDS;    //方块
		}
		
		int findHeight = number % 13;
		switch(findHeight)
		{
			case 0 : height = CardHeight.TWO; break;
			case 1 : height = CardHeight.THREE; break;
			case 2 : height = CardHeight.FOUR; break;
			case 3 : height = CardHeight.FIVE; break;
			case 4 : height = CardHeight.SIX; break;
			case 5 : height = CardHeight.SEVEN; break;
			case 6 : height = CardHeight.EIGHT; break;
			case 7 : height = CardHeight.NINE; break;
			case 8 : height = CardHeight.TEN; break;
			case 9 : height = CardHeight.JACK; break;
			case 10 : height = CardHeight.QUEEN; break;
			case 11 : height = CardHeight.KING; break;
			default : height = CardHeight.ACE;
		}
	}
	
	public Card(String cardsuit,String findHeight){
		suit = Enum.valueOf(CardSuit.class, cardsuit);
		int s=0,h=0;
		char c = 0;
		if(findHeight.length()>1){
			c='T';
		}else{
			c=findHeight.charAt(0);
		}
		switch(c)
		{
			case '2' : height = CardHeight.TWO;h = 0;break;
			case '3' : height = CardHeight.THREE; h = 1;break;
			case '4' : height = CardHeight.FOUR; h = 2;break;
			case '5' : height = CardHeight.FIVE; h = 3;break;
			case '6' : height = CardHeight.SIX; h = 4;break;
			case '7' : height = CardHeight.SEVEN;h = 5; break;
			case '8' : height = CardHeight.EIGHT;h = 6; break;
			case '9' : height = CardHeight.NINE;h = 7; break;
			case 'T' : height = CardHeight.TEN; h = 8;break;
			case 'J' : height = CardHeight.JACK;h = 9;break;
			case 'Q' : height = CardHeight.QUEEN;h = 10; break;
			case 'K' : height = CardHeight.KING;h = 11; break;
			case 'A' : height = CardHeight.ACE; h = 12;break;
			default : break;
		}
		switch(suit){
			case SPADES:s = 0;break;
			case HEARTS:s = 1;break;
			case CLUBS:s = 2;break;
			case DIAMONDS:s = 3;break;
			default :break;
		}
		number = s*13+h;
	}
	/**
	 * Returns the Card object that corresponds with the given card string. The first time this method is called, a
	 * map of all Cards corresponding with correct input strings is created.
	 * @param string : the string to be converted to a Card
	 */
	public static Card getCard(String string)
	{
		if(stringToCard == null)
		{
			stringToCard = new HashMap<String,Card>();
			for(int i = 0; i < 52; ++i)
			{
				Card card = new Card(i);
				stringToCard.put(card.toString(), card);
			}
		}
		return stringToCard.get(string);
	}

	/**
	 * Returns the number of the card as a long.
	 */
	public long getNumber()
	{
		int suitShift = number / 13;
		int heightShift = number % 13;
		return (1l << (16*suitShift + heightShift));
	}
	
	/**
	 * Returns the height of this card.
	 */
	public CardHeight getHeight()
	{
		return height;
	}
	
	/**
	 * Returns the suit of this card.
	 */
	public CardSuit getSuit()
	{
		return suit;
	}
	
	/**
	 * Returns a String representation of this card.
	 */
	public String toString()
	{
		String str = null;
		int findHeight = number % 13;
		switch(findHeight)
		{
			case 0 : str = "2"; break;
			case 1 : str = "3"; break;
			case 2 : str = "4"; break;
			case 3 : str = "5"; break;
			case 4 : str = "6"; break;
			case 5 : str = "7"; break;
			case 6 : str = "8"; break;
			case 7 : str = "9"; break;
			case 8 : str = "T"; break;
			case 9 : str = "J"; break;
			case 10 : str = "Q"; break;
			case 11 : str = "K"; break;
			case 12 : str = "A";
		}
		int findSuit = number / 13;
		switch(findSuit)
		{
			case 0 : str += "s"; break;
			case 1 : str += "h"; break;
			case 2 : str += "c"; break;
			default : str += "d";
		}		
		
		return str;
	}
}
