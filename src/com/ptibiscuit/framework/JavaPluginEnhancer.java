package com.ptibiscuit.framework;

import com.ptibiscuit.framework.permission.PermissionHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class JavaPluginEnhancer extends JavaPlugin {

	protected Properties lang = new Properties();
	protected String prefixChat;
	protected PermissionHandler permissionHandler;
	protected MyLogger myLog;
	protected static Server server;

	public void setup(String prefixChat, String prefixPermissions, boolean createDirectory) {
		server = getServer();
		this.prefixChat = (prefixChat + ChatColor.WHITE);
		this.permissionHandler = new PermissionHandler(prefixPermissions);
		this.permissionHandler.setupPermissions();
		this.myLog = new MyLogger(this.getDescription().getName());
		PermissionHelper.setupPermissions(getServer());
		if (createDirectory) {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}

			File config = new File(getDataFolder() + "/config.yml");
			if (!config.exists()) {
				try {
					config.createNewFile();
					onConfigurationDefault(this.getConfig());
					this.saveConfig();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				loadLang();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void onConfigurationDefault(FileConfiguration paramConfiguration);

	public abstract void onLangDefault(Properties paramProperties);

	public boolean loadLang(String pre) throws IOException {
		return loadLang(new File(pre));
	}

	public boolean loadLang() throws IOException {
		return loadLang(new File(getDataFolder(), "lang.properties"));
	}

	public boolean loadLang(File f) throws IOException {
		boolean returned = true;

		if (!f.exists()) {
			f.createNewFile();
			returned = false;
		}

		FileInputStream fis = new FileInputStream(f);
		this.lang.load(fis);
		fis.close();
		if (!returned) {
			onLangDefault(this.lang);
			saveLang(new File(getDataFolder(), "lang.properties"), "Default Lang Configuration");
		}
		return returned;
	}

	public void saveLang(File f, String comments) throws IOException {
		PrintWriter pw = new PrintWriter(f);
		this.lang.store(pw, comments);
		pw.close();
	}

	public void sendPreMessage(CommandSender p, String key) {
		p.sendMessage(this.prefixChat + " " + getSentence(key));
	}

	public void sendPreMessage(String key) {
		getServer().broadcastMessage(this.prefixChat + " " + getSentence(key));
	}

	public void sendMessage(CommandSender p, String message) {
		p.sendMessage(this.prefixChat + " " + message);
	}

	public void sendMessage(String message) {
		getServer().broadcastMessage(this.prefixChat + " " + message);
	}

	public void saveLang(String comments) throws IOException {
		saveLang(new File(getDataFolder(), "lang.properties"), comments);
	}

	@Override
	public abstract void onDisable();

	@Override
	public abstract void onEnable();

	public Properties getLang() {
		return this.lang;
	}

	public String getSentence(String t) {
		return this.lang.getProperty(t);
	}

	public MyLogger getMyLogger() {
		return this.myLog;
	}

	public String getPrefixChat() {
		return this.prefixChat;
	}

	public String getPrefixPermissions() {
		return this.permissionHandler.getPrefix();
	}

	public MyLogger getMyLog() {
		return myLog;
	}

	public PermissionHandler getPermissionHandler() {
		return permissionHandler;
	}
	
	public static Server getStaticServer() {
		return server;
	}
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\CommuBan\dist\DestiPlugins.jar
 * Qualified Name:     com.ptibiscuit.framework.JavaPluginEnhancer
 * JD-Core Version:    0.6.0
 */