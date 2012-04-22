/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.framework.permission;

import com.ptibiscuit.framework.PluginsBaker;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.permission.plugins.Permission_SuperPerms;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermissionHandler {
	private Permission permHandler;
	private String prefix;

	public enum PermissionType {
		GROUPS_MANAGER,
		PERMISSIONSEX
	}

	public PermissionHandler(String prefix)
	{
		this.prefix = prefix;
	}
	
	public boolean setupPermissions() {
		RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permHandler = permissionProvider.getProvider();
        }
        return (permHandler != null);
	}
	
	public boolean has(Permissible sender, String d, boolean onlyForOpInCaseOfNotPermissions) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		String permission = this.prefix + "." + d;
		if (setupPermissions() && this.isUsingPermissions()) {
			return this.permHandler.has(player, permission);
		} else if (onlyForOpInCaseOfNotPermissions) {
			return sender.isOp();
		} else {
			return true;
		}
				  /*
		if ((type != null) && (PluginsBaker.getDestiConfiguration().getBoolean("pluginsbaker.using_permissions", true))) {
			if (type == PermissionType.GROUPS_MANAGER) {
				return gp.getWorldsHolder().getWorldPermissions(player).has(player, permission);
			} else if (type == PermissionType.PERMISSIONSEX) {
				return player.hasPermission(permission);
			}
		}

		if (onlyForOpInCaseOfNotPermissions) {
			return player.isOp();
		}
		return true;*/
	}

	public boolean isUsingPermissions() {
		if (PluginsBaker.getDestiConfiguration().getBoolean("destiplugins.using_permissions", true)) {
			return !(this.permHandler instanceof Permission_SuperPerms);
		}

		return false;
	}
	
	public Permission getPermission()
	{
		return this.permHandler;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
}
