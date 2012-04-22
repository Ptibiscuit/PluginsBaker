package com.ptibiscuit.framework.coffeemachine;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class CoffeeMachine
		  implements ConnecListener, MessageListener, DeconnecListener {

	private int port;
	private ServerSocket sk;
	private ConnecManager cm = new ConnecManager();
	private ArrayList<MessageListener> listBuffer = new ArrayList();
	private ArrayList<DeconnecListener> listDeco = new ArrayList();
	private ArrayList<Client> clients = new ArrayList();

	public CoffeeMachine(int pt) {
		this.port = pt;
		addConnectionListener(this);

		addMessageListener(this);
	}

	public void start() throws IOException {
		this.sk = new ServerSocket(this.port);
		this.cm.setSsk(this.sk);
		this.cm.start();
	}

	public void addConnectionListener(ConnecListener cl) {
		this.cm.listList.add(cl);
	}

	public void addDeconnectionListener(DeconnecListener dl) {
		this.listDeco.add(dl);
	}

	public void addMessageListener(MessageListener bl) {
		this.listBuffer.add(bl);
	}

	public void onConnection(Client cl) {
		this.clients.add(cl);
		cl.addMessageListener(this);
		cl.addDeconnecListener(this);
	}

	public void onDeconnection(Client cl) {
		this.clients.remove(cl);

		for (DeconnecListener dc : this.listDeco) {
			if (dc != this) {
				dc.onDeconnection(cl);
			}
		}
	}

	public void close()
			  throws IOException {
		this.sk.close();
		if (this.cm != null) {
			this.cm.setStop();
		}
		for (Client cl : (ArrayList<Client>) this.clients.clone()) {
			cl.deconnection();
		}
	}

	public void onMessage(String m, Client c) {
		if (m.equals("(")) {
			c.send(")");
		} else if (m.equals(")")) {
			c.setPongReceived();
		}

		for (MessageListener dc : this.listBuffer) {
			if (dc != this) {
				dc.onMessage(m, c);
			}
		}
	}

	public ArrayList<Client> getClients() {
		return this.clients;
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.coffeemachine.CoffeeMachine
 * JD-Core Version:    0.6.0
 */