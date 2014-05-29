package me.Gugino.economy;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlus extends JavaPlugin implements Listener{
	public Logger logger = Logger.getLogger("Minecraft");
	public String currencySymbol = "$"; 
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		logger.info("[EconomyPlus] has been enabled");
		getConfig().options().copyDefaults(true);
		
		if(!(getConfig().contains(".currency"))){
			getConfig().set(".currency", "" + currencySymbol);
		}else{
			saveConfig();
		}
		
		saveConfig();
	}
	
	public void onDisable() {
		logger.info("[EconomyPlus] has been disabled!");
		saveConfig();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		if(!(getConfig().contains(p.getDisplayName()))){
			getConfig().set(p.getDisplayName() + ".balance", 0);
			saveConfig();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command commandd, String label, String[] args){
		
		Player player = (Player) sender;
		
		if(label.equalsIgnoreCase("ep")){
			player.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus - Help]");
			player.sendMessage(ChatColor.AQUA + "/ep-bal");
			player.sendMessage(ChatColor.AQUA + "/ep-pay <player> <amount>");
		}
		
		if(label.equalsIgnoreCase("ep-bal")){
			player.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus] " + ChatColor.AQUA + "Balance: " + ChatColor.AQUA + getConfig().getString(".currency") + getConfig().getString(player.getDisplayName() + ".balance"));
		}
		
		if(label.equalsIgnoreCase("ep-pay")){
			if(args.length == 0 || args.length == 1){
				player.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus] " + ChatColor.AQUA + "Invalid Syntax: /ep-pay <player> <amount>");
				return true;
			}
			if(args.length == 2){
				Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
				String transferAmount = args[1];
				int finalBal = Integer.parseInt(transferAmount);
				int playerBalance = getConfig().getInt(player.getDisplayName() + ".balance");
				if(playerBalance < finalBal){
					player.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus] " + ChatColor.DARK_RED + "Insufficient Funds! " + ChatColor.AQUA + "Current Balance: " + getConfig().getString(".currency") + getConfig().getString(player.getDisplayName() + ".balance"));
				}else{
					getConfig().set(targetPlayer.getDisplayName() + ".balance", getConfig().getInt(targetPlayer.getDisplayName()+ ".balance") +  finalBal);
					getConfig().set(player.getDisplayName() + ".balance", getConfig().getInt(player.getDisplayName() + ".balance") - finalBal);
					saveConfig();
				
					targetPlayer.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus] " + ChatColor.AQUA + player.getName() + " has sent you " + getConfig().getString(".currency") + transferAmount);
					player.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus] " + ChatColor.AQUA + "You have sent " + targetPlayer.getDisplayName() + " " + getConfig().getString(".currency") +transferAmount);
					player.sendMessage(ChatColor.DARK_AQUA + "[EconomyPlus] " + ChatColor.AQUA + getConfig().getString(".currency") + transferAmount + " has been taken from your account.");
				}
			}
		}
		
		return false;
	}
}