/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.framework.mysql;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.MemorySection;

/**
 *
 * @author ANNA
 */
public class ConnectionManager {
	private HashMap<String, mysqlCore> connections = new HashMap<String, mysqlCore>();
	private static ConnectionManager instance;
	
	public void loadConnections(MemorySection config)
	{
		Logger log = Logger.getLogger("Minecraft");
		for (Entry<String, Object> entry : config.getValues(false).entrySet())
		{
			MemorySection data = (MemorySection) entry.getValue();
			String tag = entry.getKey();
			
			mysqlCore mysqlConnection = new mysqlCore(log, "PluginsBaker", data.getString("host"), data.getString("database"), data.getString("login"), data.getString("password"));
			mysqlConnection.initialize();
			try {
				mysqlConnection.checkConnection();
			} catch (MalformedURLException ex) {
				Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			this.connections.put(tag, mysqlConnection);
		}
	}
	public static ConnectionManager getInstance()
	{
		if (instance == null)
			instance = new ConnectionManager();
		return instance;
	}
}
