package com.pdh.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pdh.core.Deck;
import com.pdh.player.My;
import com.pdh.player.Player;
import com.pdh.poker.Card;

/**
 * 接收服务器发送的信息，并解析
 * @author panda
 */
public class BotReceive extends Thread{
	private int round = 1;
	private int num = 0;
	private BufferedReader buffr;
	private static BotReceive ins = new BotReceive();
	private static Deck deck = Deck.getDeck();
	private static My my = new My();
	private BotSend send = BotSend.getBotSend();
	
	private BotReceive(){
		try {
			buffr = new BufferedReader(new InputStreamReader(BotHand.getSocket().getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BotReceive getBotReceive(){
		return ins;
	}
	
	public static My getMy() {
		return my;
	}

	public static void setMy(My my) {
		BotReceive.my = my;
	}

	/**
	 * seat/ 	
	 * button: 2222 2000 8000 
	 * small blind: 8888 2000 8000 
	 * big blind: 5555 2000 8000
	 * 4444 2000 8000
	 * 3333 2000 8000
	 * 7777 2000 8000
	 * 6666 2000 8000
	 * 1111 2000 8000
	 * /seat 
	 * button: pid jetton money eol
	 * @param line
	 * @throws IOException 
	 */
	public void seatMsg(String line) throws IOException{
		Player pl = null;
		if(line.indexOf("seat/")>=0){
			deck.setState("seat");
			deck.setPlayernum(0);
			while(line.indexOf("/seat")<0&&line!=null){
				int id=0;
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				System.out.println(line+"\tparts size:"+parts.length);
				if(parts.length==4){
					pl = new Player();
					pl.setRoly(parts[0].substring(0, parts[0].length()-1));
					id= Integer.parseInt(parts[1]);
					pl.setId(id);
					pl.setJetton(Integer.parseInt(parts[2]));	
					pl.setMeoney(Integer.parseInt(parts[3]));
					deck.setPlayernum(deck.getPlayernum()+1);
				}else if(parts.length==5){
					pl = new Player();
					pl.setRoly(parts[0]+parts[1].substring(0, parts[1].length()-1));
					id= Integer.parseInt(parts[2]);
					pl.setId(id);
					pl.setJetton(Integer.parseInt(parts[3]));
					pl.setMeoney(Integer.parseInt(parts[4]));
					deck.setPlayernum(deck.getPlayernum()+1);
				}else if(parts.length==3){
				    pl = new Player();
					pl.setRoly("paly");
					id= Integer.parseInt(parts[0]);
					pl.setId(id);
					pl.setJetton(Integer.parseInt(parts[1]));	
					pl.setMeoney(Integer.parseInt(parts[2]));
					deck.setPlayernum(deck.getPlayernum()+1);
				}
				if(pl!=null){
					if(deck.getPlayers().size()<1){
						deck.getPlayers().add(pl);
					}else{
						boolean flag = false;
						Player pl2 = null;
						for(Player pl1:deck.getPlayers()){
							if(id == pl1.getId()){
								pl2=pl1;
								flag=true;
							}
						}
						if(!flag){
							if(null!=pl2)
								deck.getPlayers().remove(pl2);
							deck.getPlayers().add(pl);
						}
					}
				}
				line = buffr.readLine();
			}
		}
	}

	/**
	 * blind/
	 * 8888: 50 
	 * 5555: 100 
	 * /blind 
	 * @param line
	 * @throws IOException 
	 */
	public void blindMsg(String line) throws IOException{
		if(line.indexOf("blind/")>=0){//盲注
			deck.setState("blind");
			boolean flag = false;
			while(line.indexOf("/blind")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==2){
					if(!flag){
						deck.setSmallblind(Integer.parseInt(parts[1]));
						flag=true;
					}else{
						deck.setBigblind(Integer.parseInt(parts[1]));
					}
				}
				line = buffr.readLine();
			}
		}
	}
	
	/**
	 * hold/ 
	 * DIAMONDS 8
	 * HEARTS 2 
	 * /hold 	
	 * @param line
	 * @throws IOException
	 */
	public void holdMsg(String line) throws IOException{
		if(line.indexOf("hold/")>=0){//盲注
			deck.setState("hold");
			boolean flag = false;
			Card[] cards = new Card[2];
			while(line.indexOf("/hold")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==2){
					if(!flag){
						cards[0]=new Card(parts[0],parts[1]);
						flag=true;
					}else{
						cards[1]=new Card(parts[0],parts[1]);
					}
				}
				line = buffr.readLine();
			}
			my.setHoldcards(cards);
		}
	}
	
	/**
	 * inquire/ 
	 * 2222 0 8000 2000 all_in 
	 * 1111 0 8000 2000 all_in 
	 * 6666 1700 8000 300 raise 
	 * 7777 1800 8000 200 raise 
	 * 3333 1900 8000 100 call 
	 * 4444 2000 8000 0 fold 
	 * 5555 1900 8000 100 blind 
	 * 8888 1950 8000 50 blind 
	 * total pot: 4750 
	 * /inquire 
	 * @param line
	 * @throws IOException
	 */
	public void inquireMsg(String line)throws IOException{
		if(line.indexOf("inquire/")>=0){
			boolean isaction = false;
			deck.setState("inquire");
			while(line.indexOf("/inquire")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==5){
					if(null!=my){
						if(Integer.parseInt(parts[0])!=my.getId()){
							isaction = true;
							
						}
					}
				}else if(parts.length==3){
					deck.setTotalpot(Integer.parseInt(parts[2]));
				}
				line = buffr.readLine();
			}
			if(isaction)
				send.action("fold \n");
			//action
		}			
	}
	
	/**
	 * flop/ 
	 * SPADES J 
	 * SPADES Q 
	 * CLUBS 3 
	 * /flop 
	 * @param line
	 * @throws IOException
	 */
	public void flopMsg(String line)throws IOException{
		Card[] commoncards = deck.getCards();
		int index =0;
		if(line.indexOf("flop/")>=0){
			deck.setState("flop");
			while(line.indexOf("/flop")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==2){
					commoncards[index] = new Card(parts[0],parts[1]);
					index++;
				}
				line = buffr.readLine();
			}
		}
	}
	
	/**
	 * turn/ 
	 * SPADES 6 
	 * /turn
	 * @param line
	 * @throws IOException
	 */
	public void turnMsg(String line)throws IOException{
		if(line.indexOf("turn/")>=0){
			deck.setState("trun");
			while(line.indexOf("/turn")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==2){
					deck.getCards()[3] = new Card(parts[0],parts[1]);
				}
				line = buffr.readLine();
			}
		}
	}
	
	/**
	 * river/ 
	 * SPADES A 
	 * /river 
	 * @param line
	 * @throws IOException
	 */
	public void riverMsg(String line)throws IOException{
		if(line.indexOf("river/")>=0){
			deck.setState("river");
			while(line.indexOf("/river")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==2){
					deck.getCards()[4] = new Card(parts[0],parts[1]);
				}
				line = buffr.readLine();
			}
		}
	}
	
	/**
	 * shutdown/
	 * common/
	 * /common
	 * 5: 5555 CLUBS 7 CLUBS K HIGH_CARD 
	 * 3: 3333 CLUBS 2 HEARTS Q ONE_PAIR 
	 * 2: 7777 SPADES 3 CLUBS A FLUSH 
	 * 4: 6666 CLUBS 9 HEARTS K HIGH_CARD 
	 * 6: 1111 DIAMONDS 2 DIAMONDS K HIGH_CARD 
	 * 1: 2222 DIAMONDS 9 SPADES 4 FLUSH 
	 * shutdown/
	 * @param line
	 * @throws IOException
	 */
	public void shutdownMsg(String line)throws IOException{
		if(line.indexOf("showdown/")>=0){
			deck.setState("showdown");
			while(line.indexOf("/showdown")<0&&line!=null){
				commonMsg(line);
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==7){
					
				}
				line = buffr.readLine();
			}
		}
	}
	
	/**
	 * common/ 
	 * SPADES J 
	 * SPADES Q 
	 * CLUBS 3 
	 * SPADES 6 
	 * SPADES A 
	 * /common
	 * @param line
	 * @throws IOException
	 */
	public void commonMsg(String line)throws IOException{
		if(line.indexOf("common/")>=0){
			deck.setState("showdown/common");
			while(line.indexOf("/common")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==2){
					
				}
//				System.out.println("common :"+line);
				line = buffr.readLine();
			}
//			System.out.println("common end:"+line);
		}
	}
	
	/**
	 * pot-win/ 
	 * 2222: 12050 
	 * /pot-win 
	 * @param line
	 * @throws IOException
	 */
	public void potwinMsg(String line)throws IOException{
		if(line.indexOf("pot-win/")>=0){
			deck.setState("pot-win");
			while(line.indexOf("/pot-win")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==2){
					
				}
				line = buffr.readLine();
			}
			round++;
		}
	}
	
	/**
	 * notify/ eol
	 * pid jetton money bet blind | check | call | raise | all_in | fold 
	 * total pot: num eol
	 * /notify eol
	 * @param line
	 * @throws IOException
	 */
	public void notify(String line)throws IOException{
		if(line.indexOf("notify/")>=0){
			deck.setState("notify");
			while(line.indexOf("/notify")<0&&line!=null){
				line.trim();
				if( line.length() == 0 ) { continue; }
				String[] parts = line.split(" ");
				if(parts.length==3){
					
				}else if(parts.length==5){
					
				}else if(parts.length==6){
					
				}
//				System.out.println("notify :"+line);
				line = buffr.readLine();
			}
//			System.out.println("notify end:"+line);
		}
	}
	public void gameoverMsg(String line)throws IOException{
		if(line.indexOf("game-over")>=0){
			deck.setState("gameover");
			receiveClose();
			send.sendClose();
			BotHand.getSocket().close();
		}
	}
	/**
	 *对接收到是消息进行解析 
	 */
	public void analyze(){
		try {
    		String line = buffr.readLine();
    		while(line!=null){
    			line.trim();
    			notify(line);
    			inquireMsg(line);
    			seatMsg(line);
    			blindMsg(line);
    			holdMsg(line);
    			flopMsg(line);
    			turnMsg(line);
    			riverMsg(line);
    			shutdownMsg(line);
    			potwinMsg(line);
    			gameoverMsg(line);
				//System.out.println(line);
				if( line.length() == 0 ) { continue; }
				num++;
				System.out.println("收到第"+num+"行信息,现在状态是:第"+round+"场,收到"+
						deck.getState()+"包--参局人数"+deck.getPlayernum()+"-----"+line+"------------------------------------");
				line = buffr.readLine();
			}
		} catch (IOException ioe) {
			if(!Deck.getDeck().getState().equals("gameover"))
				ioe.printStackTrace();
		}
	}
	public void run(){
		analyze();
	}
	
	/**
	 * 关闭buff
	 */
	public void receiveClose(){
		if(null!=buffr){
			try {
				buffr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

