package net.herobrine.core;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)
					|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
					|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.MOD)) {

				if (args.length == 0) {
					player.sendMessage(ChatColor.RED
							+ "Invalid usage! Usage: /ban <player> <reason>\nNOTE: You must provide a reason for the ban!");
				}
				if (args.length > 1) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					int al = args.length;
					StringBuilder sb = new StringBuilder(args[1]);
					for (int i = 2; i < al; i++) {
						sb.append(' ').append(args[i]);
						// reason is sb.toString();
					}
					if (target != null) {
						if (target.isOnline()) {
							Player onlineTarget = (Player) target;
							Bukkit.getBanList(Type.NAME)
									.addBan(target.getName(), ChatColor.RED + "You have been banned from this server!\n"
											+ ChatColor.GREEN + "Reason: " + ChatColor.GOLD + sb.toString(), null,
											null);
							onlineTarget.kickPlayer(sb.toString());

						} else {
							Bukkit.getBanList(Type.NAME)
									.addBan(target.getName(), ChatColor.RED + "You have been banned from this server!\n"
											+ ChatColor.GREEN + "Reason: " + ChatColor.GOLD + sb.toString(), null,
											null);
						}
					} else {
						player.sendMessage(ChatColor.GRAY + "You cannot ban " + ChatColor.RED + args[0] + ChatColor.GRAY
								+ " as they have never played before or do not exist and therefore have never broken any rules on the server.");
					}

				} else {
					player.sendMessage(ChatColor.RED
							+ "Invalid usage! Usage: /ban <player> <reason>\nNOTE: You must provide a reason for the ban!");
				}

			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission to do this!");
			}

		}

		else {
			sender.sendMessage("This command can only be executed by a player!");
		}

		return false;
	}

}
