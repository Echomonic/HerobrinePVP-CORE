package net.herobrine.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
					|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)) {

				if (args.length != 1) {
					player.sendMessage(ChatColor.RED + "Invalid usage! Usage: /unban <player>");

				} else {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					if (target != null) {
						if (Bukkit.getBannedPlayers().contains(target)) {
							Bukkit.getBannedPlayers().remove(target);
							player.sendMessage(ChatColor.GREEN + "The player " + ChatColor.GOLD + target.getName()
									+ ChatColor.GREEN + " has been unbanned successfully.");
						} else {
							player.sendMessage(ChatColor.GRAY + "You cannot unban " + ChatColor.GOLD + target.getName()
									+ ChatColor.GRAY + " because they are not banned.");
						}
					} else {
						player.sendMessage(ChatColor.GRAY + "You cannot unban " + ChatColor.GOLD + args[0]
								+ ChatColor.GRAY + " because they are an unknown player.");
					}
				}

			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission to do this!");
			}
		}

		else {
			sender.sendMessage("You can only use this command as a player!");
		}

		return false;
	}

}
