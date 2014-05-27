package me.Gugino.economy;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlus extends JavaPlugin implements Listener{
	public Logger logger = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		logger.info("[EconomyPlus] has been enabled");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable() {
		logger.info("[EconomyPlus] has been disabled!");
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command commandd, String label, String[] args){
		
		Player player = (Player) sender;
		
		if(label.equalsIgnoreCase("ep-pay")){
			if(args.length == 0){
				player.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus] " + ChatColor.AQUA + "Invalid Syntax: /ep-pay <player> <amount>");
				return true;
			}
		}
		
		return false;
	}
}