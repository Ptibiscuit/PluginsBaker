package com.ptibiscuit.framework;

import java.io.PrintStream;
import java.util.ArrayList;

public class MyLogger {

	private String prefix;
	private ArrayList<String> logs;

	public MyLogger(String pre) {
		this.prefix = pre;
	}

	public void startFrame() {
		this.logs = new ArrayList();
	}

	public void addInFrame(String l) {
		this.logs.add(l);
	}

	public void addInFrame(String l, boolean succeed) {
		String suc = succeed ? "[V]" : "[X]";

		this.logs.add(suc + " " + l);
	}

	public void addCompleteLineInFrame() {
		this.logs.add("/-/-/");
	}

	public void displayFrame(String lastMessage, boolean displayPrefix) {
		addInFrame(lastMessage);
		displayFrame(displayPrefix);
	}

	public void displayFrame(String lastMessage) {
		displayFrame(lastMessage, true);
	}

	public void displayFrame() {
		displayFrame(true);
	}

	public void displayFrame(boolean displayPrefix) {
		int maxLength = 0;
		for (String s : this.logs) {
			if (maxLength < s.length()) {
				maxLength = s.length();
			}
		}
		String startAndEnd = createStringNumberChar("-", maxLength + 4);

		write(startAndEnd, displayPrefix);
		for (String s : this.logs) {
			if (s.equals("/-/-/")) {
				s = "";
				for (int i = 0; i < maxLength; i++) {
					s = s + "-";
				}
			}

			String spaces = "";
			for (int i = 0; i < maxLength - s.length(); i++) {
				spaces = spaces + " ";
			}

			String message = "| " + s + spaces + " |";
			write(message, displayPrefix);
		}
		write(startAndEnd, displayPrefix);
		closeFrame();
	}

	public void closeFrame() {
		this.logs = null;
	}

	protected String createStringNumberChar(String s, int nbre) {
		String focus = "";
		while (nbre > 0) {
			focus = focus + s;
			nbre--;
		}
		return focus;
	}

	private String getBug(boolean b) {
		if (b) {
			return "[V]";
		}
		return "[X]";
	}

	public void config(String m, boolean bug) {
		write("[CONFIG] " + getBug(bug) + " " + m, true);
	}

	public void log(String m) {
		write(m, true);
	}

	private void write(String m, boolean displayPrefix) {
		String prefixChoice = displayPrefix ? "[" + this.prefix + "] " : "";
		System.out.println(prefixChoice + m);
	}

	public void severe(String m) {
		write("[SEVER]" + m, true);
	}

	public void warning(String m) {
		write("[WARNING]" + m, true);
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.MyLogger
 * JD-Core Version:    0.6.0
 */