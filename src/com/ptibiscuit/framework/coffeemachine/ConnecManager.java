package com.ptibiscuit.framework.coffeemachine;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnecManager extends Thread {

	public ServerSocket ssk;
	public ArrayList<ConnecListener> listList = new ArrayList();
	private boolean stop = false;

	public void run() {
		while (!this.stop) {
			try {
				Socket skNew = this.ssk.accept();
				Client c = new Client(skNew);
				for (ConnecListener cl : this.listList) {
					cl.onConnection(c);
				}
			} catch (IOException e) {
				Client c;
				System.out.println("Erreur dans l'utilisation de CoffeeMachine");
			}
		}
	}

	public void setStop() {
		this.stop = true;
	}

	public void setSsk(ServerSocket ssk) {
		this.ssk = ssk;
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.coffeemachine.ConnecManager
 * JD-Core Version:    0.6.0
 */