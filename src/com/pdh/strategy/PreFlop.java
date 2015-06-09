package com.pdh.strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.pdh.poker.Card;
import com.pdh.poker.CardHeight;

/**
 * ����ǰ����rollouts��
 * PreFlop������ǰ�� ���������� ��suitedPreFlop     -- ������ͬ��ɫ
 * 					            ��unsuitedPreFlop   -- �����Ʋ�ͬ��ɫ
 * 				   ���������� ��getStrength(list) -- ���û�ȡ��Ӧ�ƵĻ�ʤ����
 *				   �����캯�� ��PreFlop(no,p)     -- ��ȡ�ļ�������no�ǲ��������������p���ļ�·��
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
		//System.out.print("���꣺["+holeCards.get(0).value.ordinal()+"]["+holeCards.get(1).value.ordinal()+"] = ");
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
		//������Ӧ��ģ��
		double[][] values = new double[CardHeight.values().length][CardHeight.values().length];
		for(int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				values[i][j] = 0;
			}
		}
		//�½��ļ����������ļ�д��
		File file = new File(pathname);
		FileReader fr = null;//�ļ�io
		fr = new FileReader(file);
		BufferedReader in = new BufferedReader(fr);//���ļ�io����io��������
		String line = null;
		for (int i = 0; i < CardHeight.values().length; i++) {//����
			if((line = in.readLine()) == null) {//��ȡһ��
				throw new Exception("File disrupted");
			}
			line = line.trim();//ȥ�հ�
			String[] strings = line.split("\t");//�ָ�
			for (int j = 0; j < strings.length; j++) {
				if(strings[j].isEmpty()) {
					break;
				}
				//д��ģ����
				values[i][j+CardHeight.values().length-strings.length] = Double.parseDouble(strings[j]);
			}
		}
		fr.close();
		return values;
	}
}
