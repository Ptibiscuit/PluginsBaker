package com.ptibiscuit.framework.coffeemachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BufferManager extends Thread {

	private BufferedReader plec;
	public ArrayList<SubMessageListener> bl = new ArrayList();
	private boolean disabled = false;

	public BufferManager(BufferedReader br) throws UnsupportedEncodingException, IOException {
		this.plec = br;
	}

	public void addSubMessageListener(SubMessageListener ml) {
		this.bl.add(ml);
	}

	public void run() {
		while (!this.disabled) {
			try {
				String ligne;
				while ((ligne = this.plec.readLine()) != null) {
					for (SubMessageListener b : this.bl) {
						b.onSubMessage(ligne);
					}
				}
			} catch (IOException e) {
			}
		}
	}

	public void setStop() {
		this.disabled = true;
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.coffeemachine.BufferManager
 * JD-Core Version:    0.6.0
 */