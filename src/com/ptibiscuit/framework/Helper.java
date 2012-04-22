package com.ptibiscuit.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Helper {

	public static Location convertStringToLocation(String s) {
		String[] data = s.split("/");
		return new Location(JavaPluginEnhancer.getStaticServer().getWorld(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]));
	}

	public static ArrayList<String> convertLocationArrayToStringArray(Collection<Location> l) {
		ArrayList news = new ArrayList();
		for (Location loc : l) {
			news.add(convertLocationToString(loc));
		}
		return news;
	}

	public static void surelyGiveObject(Player p, ItemStack st) {
		Inventory in = p.getInventory();
		if (in.firstEmpty() != -1) {
			in.addItem(new ItemStack[]{st});
		} else {
			p.getLocation().getWorld().dropItemNaturally(p.getLocation(), st);
		}
	}

	public static ItemStack convertStringToItem(String s) {
		String[] datas = s.split("/");
		return new ItemStack(Integer.parseInt(datas[1]), Integer.parseInt(datas[0]), Short.parseShort(datas[3]), Byte.valueOf(Byte.parseByte(datas[2])));
	}

	public static String convertBlockDataToString(Block b) {
		return b.getTypeId() + "/" + b.getData();
	}

	public static String convertItemToString(ItemStack it) {
		return it.getAmount() + "/" + it.getTypeId() + "/" + it.getData().getData() + "/" + it.getDurability();
	}

	public static String convertLocationToString(Location l) {
		return l.getWorld().getName() + "/" + l.getX() + "/" + l.getY() + "/" + l.getZ() + "/" + l.getYaw() + "/" + l.getPitch();
	}

	public static List getRandomFromList(Collection list) {
		return (List) list.toArray()[(int) (java.lang.Math.random() * (list.size() - 1))];
	}
}