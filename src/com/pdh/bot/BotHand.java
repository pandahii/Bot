package com.pdh.bot;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ����������socket�����ӣ�ע�ᣬ������Ϣ��������Ϣ��������Ϣ
 * @author panda
 *
 */
public class BotHand {
	private static String serverip = "192.168.1.217";
	private static int serverport = 6000;
	private static String localip = "192.168.1.213";
	private static int localport = 6008;
	private static Socket socket;
	public BotHand(){
		socket=getSocket();
	}
	public void socketClose(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ʵ����socket-���������ӷ�������ͨ��
	 * @param serverip
	 * @param serverport
	 * @param localip
	 * @param localport
	 * @return
	 */
	public static Socket getSocket(String serverip,int serverport,String localip,int localport){
		setLocalip(localip);
		setLocalport(localport);
		setServerip(serverip);
		setServerport(serverport);
		try {
			//socket = new Socket(serverip,serverport,InetAddress.getByName(localip),localport);
			socket = new Socket(); 
			socket.setReuseAddress(true); 
			socket.bind(new InetSocketAddress(localip,localport));
			socket.connect( new InetSocketAddress(serverip, serverport));
		} catch (UnknownHostException e) {
			System.out.println("not cannot find address for the host local ip :"+localip+"");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	/**
	 * ʵ����socket-���������ӷ�������ͨ��
	 * @return
	 */
	public static Socket getSocket(){
		if(null==socket){
			try {
				//socket = new Socket(serverip,serverport,InetAddress.getByName(localip),localport);
				socket = new Socket(); 
				socket.setReuseAddress(true); 
				socket.bind(new InetSocketAddress(localip,localport));
				socket.connect( new InetSocketAddress(serverip, serverport));
			} catch (UnknownHostException e) {
				System.out.println("not cannot find address for the host local ip :"+localip+"");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return socket;
	}
	/*һЩget* set* ����*/
	public static String getServerip() {
		return serverip;
	}
	public static void setServerip(String serverip) {
		BotHand.serverip = serverip;
	}
	public static int getServerport() {
		return serverport;
	}
	public static void setServerport(int serverport) {
		BotHand.serverport = serverport;
	}
	public static String getLocalip() {
		return localip;
	}
	public static void setLocalip(String localip) {
		BotHand.localip = localip;
	}
	public static int getLocalport() {
		return localport;
	}
	public static void setLocalport(int localport) {
		BotHand.localport = localport;
	}
}
