package com.ptibiscuit.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PluginsBaker extends JavaPluginEnhancer {

	private ArrayList<JavaPluginEnhancer> plugins = new ArrayList();
	private static FileConfiguration destiConfiguration;
	private File pluginsFolder;

	@Override
	public void onConfigurationDefault(FileConfiguration c) {
		c.set("destiplugins.using_permissions", true);
	}

	public void onLangDefault(Properties p) {
	}

	public void onDisable() {
		this.myLog.startFrame();
		this.myLog.addInFrame("PluginsBaker disabled !");
		this.myLog.displayFrame(false);
	}

	public void onEnable() {
		destiConfiguration = this.getConfig();

		setup(ChatColor.DARK_GREEN + "[PluginsBaker]", "pluginsbaker", true);
		this.myLog.startFrame();
		this.myLog.addInFrame("PluginsBaker by Ptibiscuit");
		this.myLog.addCompleteLineInFrame();
		this.myLog.addInFrame("Allows you to use many plugins like:");
		this.myLog.addInFrame("SuperEvent, iProfessions, iGates, ...");
		this.myLog.addCompleteLineInFrame();
		this.myLog.addInFrame("Thanks using PluginBaker, Have Fun ! =)");
		this.myLog.displayFrame(false);
	}

	public static FileConfiguration getDestiConfiguration() {
		return destiConfiguration;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sendMessage(sender, "be_player");
			return true;
		}
		Player p = (Player) sender;

		return true;
	}
}