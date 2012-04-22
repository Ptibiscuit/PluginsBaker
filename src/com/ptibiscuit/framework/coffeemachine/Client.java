package com.ptibiscuit.framework.coffeemachine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;

public class Client
		  implements SubMessageListener, SubDeconnecListener {

	private Socket sk;
	private PrintWriter pred;
	private BufferManager bufferThread = null;
	private PingPongThread pingThread = null;
	private boolean connected = true;
	private ArrayList<MessageListener> ml = new ArrayList();
	private ArrayList<DeconnecListener> dl = new ArrayList();

	public Client(Socket ssk)
			  throws UnsupportedEncodingException, IOException {
		this.sk = ssk;
		this.pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.sk.getOutputStream(), "ISO-8859-15")), true);

		this.bufferThread = new BufferManager(new BufferedReader(new InputStreamReader(this.sk.getInputStream(), "ISO-8859-15")));
		this.bufferThread.addSubMessageListener(this);
		this.bufferThread.start();

		this.pingThread = new PingPongThread(this.pred);
		this.pingThread.addSubDeconnecListener(this);
		this.pingThread.start();
	}

	public void send(String m) {
		this.pred.println(m);
	}

	public void deconnection() {
		this.pred.close();
		this.bufferThread.setStop();
		this.pingThread.setStop();
		this.connected = false;

		for (DeconnecListener dlOne : this.dl) {
			dlOne.onDeconnection(this);
		}
	}

	public Socket getSk() {
		return this.sk;
	}

	public void addMessageListener(MessageListener ml) {
		this.ml.add(ml);
	}

	public void addDeconnecListener(DeconnecListener dl) {
		this.dl.add(dl);
	}

	public void setPongReceived() {
		this.pingThread.setPongReceived();
	}

	public void onSubMessage(String m) {
		for (MessageListener mList : this.ml) {
			mList.onMessage(m, this);
		}
	}

	public boolean isConnected() {
		return this.connected;
	}

	public void onSubDeconnect() {
		deconnection();
		for (DeconnecListener dlOne : this.dl) {
			dlOne.onDeconnection(this);
		}
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.coffeemachine.Client
 * JD-Core Version:    0.6.0
 */