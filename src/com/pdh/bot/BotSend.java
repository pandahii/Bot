package com.pdh.bot;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * �������������Ϣ
 * @author panda
 */
 public class BotSend{
	private PrintWriter sendout;
	private static BotSend send = new BotSend();
	private BotSend(){
		try {
			sendout = new PrintWriter(BotHand.getSocket().getOutputStream(),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BotSend getBotSend(){
		return send;
	} 
	
	/**
	 * reg: pid pname eol
	 * @param pid
	 * @param botname
	 */
	public void register(int pid,String botname){			
		sendout.println("reg: "+pid+" "+botname+" \n");
	}
	
	/**
	 * reg: pid pname need_notify eol
	 * @param pid
	 * @param botname
	 * @param msg <- "need_notify"
	 */
	public void registerneed(int pid,String botname){
		sendout.println("reg: "+pid+" "+botname+" need_notify \n");
	}
	
	/**
	 * 11	�ж���Ϣ��action-msg��
	 * check | call | raise num | all_in | fold eol
	 * ����          | ��ע	|  ��ע        10  |  ȫѺ         | ����    |
	 */
	public void action(String action){
		sendout.println("reg: +action+ \n");
	}
	public void sendClose(){
		sendout.close();
	}
}