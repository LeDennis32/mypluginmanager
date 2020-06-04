package ledennis.mypluginmanager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPluginManager extends JavaPlugin {
	
	public static String prefix = "§8[§amyPluginManager§8] §7";
	
	private static PluginManager getPluginManager() {
		return Bukkit.getPluginManager();
	}
	
	private static Plugin findPlugin(String name) {
		for(Plugin plugin : getPluginManager().getPlugins()) {
			if(plugin.getName().equalsIgnoreCase(name)) {
				return plugin;
			}
		}
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equals("pm")) {
			if(args.length == 2) {
				
				if(args[0].equalsIgnoreCase("info")) {
					
					if(sender.hasPermission("pm.info")) {
						
						Plugin plugin = findPlugin(args[1]);
						if(plugin != null) {
							
							sender.sendMessage(prefix + "Info for plugin §a" + plugin.getName() + "§7:");
							sender.sendMessage("§7Enabled: " + (plugin.isEnabled() ? "§aYes" : "§cNo"));
							sender.sendMessage("§7Main Class: §a" + plugin.getDescription().getMain());
							sender.sendMessage("§7Version: §a" + plugin.getDescription().getVersion());
							
							if(plugin.getDescription().getAuthors().size() > 1) {
								String msg = "§7Authors: ";
								for(String author : plugin.getDescription().getAuthors()) {
									msg += "§a" + author + "§7, ";
								}
								msg = msg.trim().substring(0, msg.length() - 2);
								sender.sendMessage(msg);
							} else if(plugin.getDescription().getAuthors().size() > 0) {
								sender.sendMessage("§7Author: §a" + plugin.getDescription().getAuthors().get(0));
							} else {
								sender.sendMessage("§7Author: §cnot set");
							}
							
							sender.sendMessage("§7Description: " 
											+ (plugin.getDescription().getDescription() == null
										    	? "§cnone"
										    	: "§a" + plugin.getDescription().getDescription()));
							
							if(plugin.getDescription().getDepend().size() > 1) {
								String msg = "§7Dependencies: ";
								for(String depend : plugin.getDescription().getDepend()) {
									msg += (getPluginManager().isPluginEnabled(depend) ? "§a" : "§c") + depend + "§7, ";
								}
								msg = msg.trim().substring(0, msg.length() - 2);
								sender.sendMessage(msg);
							} else if(plugin.getDescription().getDepend().size() > 0) {
								String depend = plugin.getDescription().getDepend().get(0);
								sender.sendMessage("§7Dependency: " + (getPluginManager().isPluginEnabled(depend) ? "§a" : "§c") + depend);
							} else {
								sender.sendMessage("§7Dependencies: §cnone");
							}
							
							if(plugin.getDescription().getSoftDepend().size() > 1) {
								String msg = "§7Soft Dependencies: ";
								for(String depend : plugin.getDescription().getSoftDepend()) {
									msg += (getPluginManager().isPluginEnabled(depend) ? "§a" : "§c") + depend + "§7, ";
								}
								msg = msg.trim().substring(0, msg.length() - 2);
								sender.sendMessage(msg);
							} else if(plugin.getDescription().getSoftDepend().size() > 0) {
								String depend = plugin.getDescription().getSoftDepend().get(0);
								sender.sendMessage("§7Soft Dependency: " + (getPluginManager().isPluginEnabled(depend) ? "§a" : "§c") + depend);
							} else {
								sender.sendMessage("§7Soft Dependencies: §cnone");
							}
							
						} else {
							plugin404(sender);
						}
						
					} else {
						noPermission(sender);
					}
					
				} else {
					syntax(sender);
				}
				
			} else {
				syntax(sender);
			}
		}
		
		return true;
	}
	
	private static void syntax(CommandSender sender) {
		sender.sendMessage(prefix + "There is no syntax help available right now.");
	}
	
	private static void noPermission(CommandSender sender) {
		sender.sendMessage(prefix + "You don't have permission to do that!");
	}
	
	private static void plugin404(CommandSender sender) {
		sender.sendMessage(prefix + "That plugin doesn't exist.");
	}
	
}
