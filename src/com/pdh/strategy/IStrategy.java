package com.pdh.strategy;


public interface IStrategy {
	/**
	 * ���Եȼ� 
	 */
	public enum AGGRESSIVITY{//
			CONSERVATIVE,//����
			MODERATE,    //�º͵�
			RISKY;       //Σ�յ�
	}
	
	String printLastAction();
	
	String printStrategy();
}
