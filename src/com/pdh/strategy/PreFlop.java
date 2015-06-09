package com.pdh.strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.pdh.poker.Card;
import com.pdh.poker.CardHeight;

/**
 * 翻牌前部署（rollouts）
 * PreFlop（翻牌前） ：两个属性 ：suitedPreFlop     -- 两张牌同花色
 * 					            ：unsuitedPreFlop   -- 两张牌不同花色
 * 				   ：两个方法 ：getStrength(list) -- 作用获取对应牌的获胜概率
 *				   ：构造函数 ：PreFlop(no,p)     -- 读取文件，参数no是参与的人数，参数p是文件路径
 */
public class PreFlop {

	private static double[][] suitedPreFlop;
	private static double[][] unsuitedPreFlop;
	private static PreFlop inst = new PreFlop();
	/**
	 * Reads the hand strengths for a given number of players
	 * @param players Number of players
	 * @param pathname Path to the file with the calculated hand strengths
	 * @throws Exception
	 */
	private PreFlop(){}
	
	public static PreFlop getPreFlop(){
		return inst;
	}
	
	public void insetFile(int players, String pathname){
		if(!pathname.endsWith("/")) {
			pathname = pathname.concat("/");
		}
		try {
			suitedPreFlop = new Reader().readFile(pathname + players
					+ "_suited.csv");
			unsuitedPreFlop = new Reader().readFile(pathname + players
					+ "_unsuited.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insetFile(int players){
		insetFile(players,"./rollouts/");
	}
	
	/**
	 * 
	 * @param holeCards Hole cards
	 * @return Hand strength of the given hole cards
	 * @throws Exception
	 */
	public double getStrength(Card[] holeCards) throws Exception {
		if (holeCards.length != 2) {
			throw new Exception("Not correct amount of hole cards");
		}
		//System.out.print("坐标：["+holeCards.get(0).value.ordinal()+"]["+holeCards.get(1).value.ordinal()+"] = ");
		if (holeCards[0].getSuit().equals(holeCards[1].getSuit())) { // suited
			// in cause of a triangular matrix you have to look on the right sight
			if (holeCards[0].getHeight().ordinal() < holeCards[1].getHeight()
					.ordinal()) {
				return suitedPreFlop[holeCards[0].getHeight().ordinal()][holeCards[1].getHeight().ordinal()];
			} else {
				return suitedPreFlop[holeCards[1].getHeight().ordinal()][holeCards[0]
						.getHeight().ordinal()];
			}
		} else { // unsuited
			// in cause of a triangular matrix you have to look on the right sight
			if (holeCards[0].getHeight().ordinal() <= holeCards[1].getHeight()
					.ordinal()) {
				return unsuitedPreFlop[holeCards[0].getHeight().ordinal()][holeCards[1].getHeight().ordinal()];
			} else {
				return unsuitedPreFlop[holeCards[1].getHeight().ordinal()][holeCards[0].getHeight().ordinal()];
			}
		}
	}
}

	class Reader {	
	@SuppressWarnings("resource")
	public double[][] readFile(String pathname) throws Exception {
		//建立对应空模板
		double[][] values = new double[CardHeight.values().length][CardHeight.values().length];
		for(int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				values[i][j] = 0;
			}
		}
		//新建文件，接着是文件写入
		File file = new File(pathname);
		FileReader fr = null;//文件io
		fr = new FileReader(file);
		BufferedReader in = new BufferedReader(fr);//将文件io放入io缓冲区中
		String line = null;
		for (int i = 0; i < CardHeight.values().length; i++) {//行数
			if((line = in.readLine()) == null) {//读取一行
				throw new Exception("File disrupted");
			}
			line = line.trim();//去空白
			String[] strings = line.split("\t");//分割
			for (int j = 0; j < strings.length; j++) {
				if(strings[j].isEmpty()) {
					break;
				}
				//写入模板中
				values[i][j+CardHeight.values().length-strings.length] = Double.parseDouble(strings[j]);
			}
		}
		fr.close();
		return values;
	}
}
