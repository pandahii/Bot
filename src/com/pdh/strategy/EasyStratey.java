package com.pdh.strategy;

import com.pdh.bot.BotReceive;
import com.pdh.core.Deck;
import com.pdh.poker.Card;

/**
 * 1.得到手牌时：
 * 		a.读取迭代N次的模拟对应的数据
 * 		b.设定阈值，当更具设定的采取对应的ACTION
 * 		注：需求要的信息由:Card[2]
 * 3.得到牌面3张公共牌
 * 
 * @author panda
 *
 */
public class EasyStratey {
	private PreFlop preflop;
	
	public String preFlopStageStratey(Card[] card){
		double chance =0.0;
		preflop = PreFlop.getPreFlop();
		preflop.insetFile(Deck.getDeck().getPlayernum());
		try {
			chance = preflop.getStrength(card);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(chance>0.5){
			
		}
		return "";
	}
}
