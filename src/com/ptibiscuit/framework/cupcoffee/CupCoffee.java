package com.ptibiscuit.framework.cupcoffee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class CupCoffee
		  implements SubMessageListener, SubDeconnecListener {

	private String ip;
	private int port;
	private Socket sk = null;
	private BufferedReader br;
	private BufferManager bm;
	private PrintWriter pw;
	private PingPongThread ppt;
	private boolean connected = false;
	private ArrayList<MessageListener> mlList = new ArrayList();
	private ArrayList<DeconnecListener> dlList = new ArrayList();

	public CupCoffee(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void deconnection() {
		this.ppt.setStop();
		this.bm.setStop();
		try {
			this.br.close();
			this.pw.close();
			this.sk.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.connected = false;
	}

	public boolean start() throws UnknownHostException, IOException {
		this.sk = new Socket(this.ip, this.port);
		if (this.sk != null) {
			this.br = new BufferedReader(new InputStreamReader(this.sk.getInputStream(), "ISO-8859-15"));
			this.pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.sk.getOutputStream())), true);

			this.bm = new BufferManager(this.br);
			this.bm.addSubMessageListener(this);
			this.bm.start();

			this.ppt = new PingPongThread(this.pw);
			this.ppt.addSubDeconnecListener(this);
			this.ppt.start();
			this.connected = true;
			return true;
		}

		return false;
	}

	public void send(String m) {
		this.pw.println(m);
	}

	public void addDeconnectionListener(DeconnecListener ml) {
		this.dlList.add(ml);
	}

	public void addMessageListener(MessageListener ml) {
		this.mlList.add(ml);
	}

	public void onSubMessage(String m) {
		if (m.equals("(")) {
			send(")");
		} else if (m.equals(")")) {
			this.ppt.setPongReceived();
		}

		for (MessageListener ms : this.mlList) {
			ms.onMessage(m);
		}
	}

	public void onSubDeconnect() {
		deconnection();
		for (DeconnecListener dl : this.dlList) {
			dl.onDeconnection(this);
		}
	}

	public long getPing() {
		return this.ppt.getActualPing();
	}

	public boolean isConnected() {
		return this.connected;
	}

	public Socket getSk() {
		return this.sk;
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.cupcoffee.CupCoffee
 * JD-Core Version:    0.6.0
 */