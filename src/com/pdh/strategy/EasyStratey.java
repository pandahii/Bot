package com.pdh.strategy;

import com.pdh.bot.BotReceive;
import com.pdh.core.Deck;
import com.pdh.poker.Card;

/**
 * 1.�õ�����ʱ��
 * 		a.��ȡ����N�ε�ģ���Ӧ������
 * 		b.�趨��ֵ���������趨�Ĳ�ȡ��Ӧ��ACTION
 * 		ע������Ҫ����Ϣ��:Card[2]
 * 3.�õ�����3�Ź�����
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
