package com.pdh.bot;

public class game {
	public static void main(String[] args) {
		BotHand.setLocalip("192.168.1.213");
		BotHand.setLocalport(6008);
		BotHand.setServerip("192.168.1.217");
		BotHand.setServerport(6000);
		BotReceive receive = BotReceive.getBotReceive();
		BotSend send = BotSend.getBotSend();
		send.registerneed(8888, "panda");
		receive.start();
	}
}
