package net.herobrine.core;

import java.util.Calendar;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempBanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)
					|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
					|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.MOD)) {

				if (args.length <= 1) {
					player.sendMessage(ChatColor.RED
							+ "Invalid usage! Usage: /tempban <player> <duration> <reason>\nNOTE: You must provide a reason + duration for the ban!\n In this ban system, you must use 'mo' for months and 'm' for minutes. Though, minutes could be changed to 'mi' or 'minutes' instead since it's less commonly used.");
				}
				if (args.length > 2) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					int al = args.length;
					StringBuilder sb = new StringBuilder(args[2]);
					for (int i = 3; i < al; i++) {
						sb.append(' ').append(args[i]);
						// reason is sb.toString();
					}
					// IF cal BECOMES WRONG AFTER BAN
					// MAKE A NEW banCal VARIABLE
					// STILL USE cal FOR CALCULATIONS, SINCE IT WILL BE SERVER TIME, BUT SET DATE TO
					// banCal
					// banCal WILL BE A FLEXIBLE CALENDAR THAT WILL BE ACCESSED WITH EVERY TEMPBAN,
					// IT IS MADE SPECIFICALLY TO SET EXPIRY DATES!
					Calendar cal = Calendar.getInstance();
					if (args[1].endsWith("y") || args[1].endsWith("yr") || args[1].endsWith("years")) {
						int banYears = Integer.parseInt(args[1]);
						player.sendMessage(ChatColor.GOLD + "[DEBUG]" + " Calendar is now set to:" + cal.getTime());
						cal.set(cal.get(cal.YEAR) + banYears, cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH),
								cal.get(cal.HOUR_OF_DAY), cal.get(cal.MINUTE), cal.get(cal.SECOND));
						player.sendMessage(
								ChatColor.GOLD + "[DEBUG]" + " Ban expiry date is now set to:" + cal.getTime());

					} else if (args[1].endsWith("mo") || args[1].endsWith("months")) {

						int banMonths = Integer.parseInt(args[1]);
						player.sendMessage(ChatColor.GOLD + "[DEBUG]" + " Calendar is now set to:" + cal.getTime());
						cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH) + banMonths, cal.get(cal.DAY_OF_MONTH),
								cal.get(cal.HOUR_OF_DAY), cal.get(cal.MINUTE), cal.get(cal.SECOND));
						player.sendMessage(
								ChatColor.GOLD + "[DEBUG]" + " Ban expiry date is now set to:" + cal.getTime());

					} else if (args[1].endsWith("d") || args[1].endsWith("days")) {

						int banDays = Integer.parseInt(args[1]);
						player.sendMessage(ChatColor.GOLD + "[DEBUG]" + " Calendar is now set to:" + cal.getTime());
						cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH) + banDays,
								cal.get(cal.HOUR_OF_DAY), cal.get(cal.MINUTE), cal.get(cal.SECOND));
						player.sendMessage(
								ChatColor.GOLD + "[DEBUG]" + " Ban expiry date is now set to:" + cal.getTime());

					} else if (args[1].endsWith("h") || args[1].endsWith("hr") || args[1].endsWith("hrs")
							|| args[1].endsWith("hours")) {

						int banHours = Integer.parseInt(args[1]);
						player.sendMessage(ChatColor.GOLD + "[DEBUG]" + " Calendar is now set to:" + cal.getTime());
						cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH),
								cal.get(cal.HOUR_OF_DAY) + banHours, cal.get(cal.MINUTE), cal.get(cal.SECOND));
						player.sendMessage(
								ChatColor.GOLD + "[DEBUG]" + " Ban expiry date is now set to:" + cal.getTime());

						player.sendMessage(ChatColor.RED + "You did not provide a time for the ban duration.");

					} else if (args[1].endsWith("m") || args[1].endsWith("minutes")) {

						int banMinutes = Integer.parseInt(args[1]);
						player.sendMessage(ChatColor.GOLD + "[DEBUG]" + " Calendar is now set to:" + cal.getTime());
						cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH),
								cal.get(cal.HOUR_OF_DAY), cal.get(cal.MINUTE) + banMinutes, cal.get(cal.SECOND));
						player.sendMessage(
								ChatColor.GOLD + "[DEBUG]" + " Ban expiry date is now set to:" + cal.getTime());

					} else if (args[1].endsWith("s") || args[1].endsWith("seconds")) {

						int banSeconds = Integer.parseInt(args[1]);
						player.sendMessage(ChatColor.GOLD + "[DEBUG]" + " Calendar is now set to:" + cal.getTime());
						cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH),
								cal.get(cal.HOUR_OF_DAY), cal.get(cal.MINUTE), cal.get(cal.SECOND) + banSeconds);
						player.sendMessage(
								ChatColor.GOLD + "[DEBUG]" + " Ban expiry date is now set to:" + cal.getTime());
					}
					if (target != null) {
						player.sendMessage(ChatColor.GOLD + "[DEBUG]" + " I got to the ban section of the class!");
						if (target.isOnline()) {
							Player onlineTarget = (Player) target;
							Bukkit.getBanList(Type.NAME).addBan(target.getName(),
									ChatColor.RED + "You have been temporarily banned from this server!\n"
											+ ChatColor.GREEN + "Reason: " + ChatColor.GOLD + sb.toString(),
									cal.getTime(), null);
							onlineTarget.kickPlayer(sb.toString());

						} else {
							Bukkit.getBanList(Type.NAME).addBan(target.getName(),
									ChatColor.RED + "You have been temporarily banned from this server!\n"
											+ ChatColor.GREEN + "Reason: " + ChatColor.GOLD + sb.toString()
											+ "\n Your ban will expire on " + cal.getTime(),
									cal.getTime(), null);
						}
					} else {
						player.sendMessage(ChatColor.GRAY + "You cannot ban " + ChatColor.RED + args[0] + ChatColor.GRAY
								+ " as they have never played before or do not exist and therefore have never broken any rules on the server.");
					}

				} else {
					player.sendMessage(ChatColor.RED
							+ "Invalid usage! Usage: /tempban <player> <duration> <reason>\nNOTE: You must provide a reason + duration for the ban!");
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
