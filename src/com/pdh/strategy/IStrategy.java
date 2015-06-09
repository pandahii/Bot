package com.pdh.strategy;


public interface IStrategy {
	/**
	 * 策略等级 
	 */
	public enum AGGRESSIVITY{//
			CONSERVATIVE,//保守
			MODERATE,    //温和的
			RISKY;       //危险的
	}
	
	String printLastAction();
	
	String printStrategy();
}
