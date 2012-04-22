package com.ptibiscuit.framework.coffeemachine;

import java.io.PrintWriter;
import java.util.ArrayList;

public class PingPongThread extends Thread {

	private boolean follow = true;
	private boolean received = false;
	private int timedout = 10000;
	private long actualPing = 0L;
	private long lastSendPing = 0L;
	private PrintWriter pw = null;
	private ArrayList<SubDeconnecListener> subDecoL = new ArrayList();

	public PingPongThread(PrintWriter cl) {
		this.pw = cl;
	}

	public void run() {
		while (this.follow) {
			this.pw.println("(");
			this.lastSendPing = System.currentTimeMillis();
			try {
				Thread.sleep(this.timedout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!this.received) {
				for (SubDeconnecListener sdl : this.subDecoL) {
					sdl.onSubDeconnect();
				}
				continue;
			}

			this.received = false;
		}
	}

	public void addSubDeconnecListener(SubDeconnecListener p) {
		this.subDecoL.add(p);
	}

	public void setPongReceived() {
		this.actualPing = (System.currentTimeMillis() - this.lastSendPing);

		this.received = true;
	}

	public void setStop() {
		this.follow = false;
	}

	public long getActualPing() {
		return this.actualPing;
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.coffeemachine.PingPongThread
 * JD-Core Version:    0.6.0
 */