package com.ptibiscuit.framework;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public class PermissionHelper {

	private static PermissionType type = null;
	private static GroupManager gp;

	public enum PermissionType {

		GROUPS_MANAGER,
		PERMISSIONSEX
	}

	public static boolean setupPermissions(Server sv) {
		if (sv.getPluginManager().getPlugin("GroupManager") != null) {
			gp = (GroupManager) sv.getPluginManager().getPlugin("GroupManager");
			type = PermissionType.GROUPS_MANAGER;
			return true;
		} else if (sv.getPluginManager().getPlugin("PermissionsEx") != null) {
			type = PermissionType.PERMISSIONSEX;
			return true;
		}

		return true;
	}

	public static boolean has(Permissible sender, String d, boolean onlyForOpInCaseOfNotPermissions) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		if ((type != null) && (PluginsBaker.getDestiConfiguration().getBoolean("pluginsbaker.using_permissions", true))) {
			if (type == PermissionType.GROUPS_MANAGER) {
				return gp.getWorldsHolder().getWorldPermissions(player).has(player, d);
			} else if (type == PermissionType.PERMISSIONSEX) {
				return player.hasPermission(d);
			}
		}

		if (onlyForOpInCaseOfNotPermissions) {
			return player.isOp();
		}
		return true;
	}

	public static boolean isUsingPermissions() {
		if (PluginsBaker.getDestiConfiguration().getBoolean("destiplugins.using_permissions", true)) {
			return (type != null);
		}

		return false;
	}
}