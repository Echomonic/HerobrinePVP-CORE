package net.herobrine.core;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import net.herobrine.gamecore.GameCoreMain;
import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.Games;
import net.herobrine.gamecore.Manager;
import net.herobrine.gamecore.Teams;

public class Listeners implements Listener {

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			e.setCancelled(true);
			player.setFoodLevel(20);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player player = e.getPlayer();
		player.setWalkSpeed(.2F);
		ItemStack selector = new ItemStack(Material.COMPASS, 1);
		ItemMeta selectorMeta = selector.getItemMeta();
		selectorMeta.setDisplayName(ChatColor.GREEN + "Game Selector");
		selector.setItemMeta(selectorMeta);
		player.setHealth(20.0);
		player.setGameMode(GameMode.SURVIVAL);
		for (PotionEffect effect : player.getActivePotionEffects()) {

			player.removePotionEffect(effect.getType());
		}
		if (!player.hasPlayedBefore()) {
			HerobrinePVPCore.getFileManager().setRank(player.getUniqueId(), Ranks.MEMBER);

			player.getInventory().setItem(0, selector);
			player.updateInventory();
			HerobrinePVPCore.getFileManager().setCoins(player, 0);
			HerobrinePVPCore.getFileManager().setTrophies(player, 0);
			HerobrinePVPCore.getFileManager().setPlusColor(player, ChatColor.LIGHT_PURPLE);
			e.setJoinMessage(ChatColor.GRAY + "Welcome to Herobrine PVP, " + ChatColor.GOLD + player.getName()
					+ ChatColor.GRAY + "!" + "\n" + ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] "
					+ ChatColor.GRAY + player.getName());

		} else {

			if (HerobrinePVPCore.getFileManager().getPlusColor(player.getUniqueId()) == null) {
				HerobrinePVPCore.getFileManager().setPlusColor(player, ChatColor.LIGHT_PURPLE);
			}

			player.getInventory().clear();
			player.getEquipment().setHelmet(null);
			player.getEquipment().setChestplate(null);
			player.getEquipment().setLeggings(null);
			player.getEquipment().setBoots(null);
			e.getPlayer().getEnderChest().clear();
			player.getInventory().setItem(0, selector);
			player.updateInventory();
			e.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + ChatColor.GRAY
					+ player.getName());
			GameCoreMain.getInstance().resetTitle(player);

			if (HerobrinePVPCore.getFileManager().getRank(player).getPermLevel() >= 6) {
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.OWNER)
							|| HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.ADMIN)
							|| HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.MOD)
							|| HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.HELPER)) {
						Ranks rank = HerobrinePVPCore.getFileManager().getRank(player);
						players.sendMessage(ChatColor.AQUA + "[STAFF] " + rank.getColor() + rank.getName() + " "
								+ player.getName() + ChatColor.YELLOW + " joined.");

					}

				}
			}
			if (player.getEquipment() != null) {
				player.getEquipment().clear();
				player.getInventory().setItem(0, selector);
				player.updateInventory();
			}
		}
		GameCoreMain.getInstance().sendTitle(player, "&cWelcome to Herobrine PVP",
				"&7Enjoy your stay, " + HerobrinePVPCore.getFileManager().getRank(player).getColor() + player.getName(),
				1, 3, 1);
		HerobrinePVPCore.buildSidebar(player);
		if (HerobrinePVPCore.getFileManager().getRank(player).hasPlusColor()) {
			player.setPlayerListName(HerobrinePVPCore.getFileManager().getRank(player).getColor()
					+ HerobrinePVPCore.getFileManager().getRank(player).getName()
					+ HerobrinePVPCore
							.translateString(HerobrinePVPCore.getFileManager().getPlusColor(player.getUniqueId()) + "+")
					+ " " + HerobrinePVPCore.getFileManager().getRank(player).getColor() + player.getName());
		} else {
			player.setPlayerListName(HerobrinePVPCore.getFileManager().getRank(player).getColor()
					+ HerobrinePVPCore.getFileManager().getRank(player).getName() + " " + player.getName());
		}

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + ChatColor.GRAY
				+ e.getPlayer().getName());
		e.getPlayer().getEnderChest().clear();
		if (HerobrinePVPCore.getFileManager().getRank(e.getPlayer()).getPermLevel() >= 6) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.OWNER)
						|| HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.ADMIN)
						|| HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.MOD)
						|| HerobrinePVPCore.getFileManager().getRank(players).equals(Ranks.HELPER)) {
					Ranks rank = HerobrinePVPCore.getFileManager().getRank(e.getPlayer());
					players.sendMessage(ChatColor.AQUA + "[STAFF] " + rank.getColor() + rank.getName() + " "
							+ e.getPlayer().getName() + ChatColor.YELLOW + " disconnected.");

				}

			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		Player player = e.getPlayer();
		Ranks rank = HerobrinePVPCore.getFileManager().getRank(player);

		if (!(rank.getPermLevel() >= 7) && MuteChatCMD.isChatToggled()) {

			e.setCancelled(true);

			return;

		}

		if (rank.getPermLevel() >= 5) {

			for (Emotes emote : Emotes.values()) {

				if (e.getMessage().contains(emote.getKey())) {

					e.setMessage(e.getMessage().replaceAll(emote.getKey(), emote.getDisplay()));

				}

			}

		}

		if (Manager.isPlaying(player)) {
			if (Manager.getArena(player).getState().equals(GameState.LIVE)
					&& Manager.getGame(Manager.getArena(player)).isTeamGame()) {
				for (UUID uuid : Manager.getArena(player).getPlayers()) {
					Player arenaPlayers = Bukkit.getPlayer(uuid);

					if (Manager.getArena(player).getTeam(arenaPlayers)
							.equals(Manager.getArena(player).getTeam(player))) {
						if (Manager.getArena(player).getTeam(player).equals(Teams.BLUE)) {

							if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.MEMBER)) {
								arenaPlayers.sendMessage(ChatColor.BLUE + "[BLUE] " + rank.getColor() + rank.getName()
										+ " " + player.getName() + ": " + ChatColor.GRAY + e.getMessage());
							} else if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
									|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)) {

								arenaPlayers.sendMessage(ChatColor.BLUE + "[BLUE] " + rank.getColor() + rank.getName()
										+ " " + player.getName() + ChatColor.RESET + ": "
										+ ChatColor.translateAlternateColorCodes('&', e.getMessage()));

							} else {
								if (rank.hasPlusColor()) {
									arenaPlayers
											.sendMessage(ChatColor.BLUE + "[BLUE] " + rank.getColor() + rank.getName()
													+ HerobrinePVPCore.translateString(HerobrinePVPCore.getFileManager()
															.getPlusColor(player.getUniqueId()) + "+")
													+ rank.getColor() + " " + player.getName() + ": " + ChatColor.RESET
													+ e.getMessage());
								} else {
									arenaPlayers
											.sendMessage(ChatColor.BLUE + "[BLUE] " + rank.getColor() + rank.getName()
													+ " " + player.getName() + ": " + ChatColor.RESET + e.getMessage());
								}

							}
						} else {
							if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.MEMBER)) {

								arenaPlayers.sendMessage(ChatColor.RED + "[RED] " + rank.getColor() + rank.getName()
										+ " " + player.getName() + ": " + ChatColor.GRAY + e.getMessage());
							} else if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
									|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)) {

								arenaPlayers.sendMessage(ChatColor.RED + "[RED] " + rank.getColor() + rank.getName()
										+ " " + player.getName() + ChatColor.RESET + ": "
										+ ChatColor.translateAlternateColorCodes('&', e.getMessage()));

							} else {

								if (rank.hasPlusColor()) {
									arenaPlayers
											.sendMessage(ChatColor.RED + "[RED] " + rank.getColor() + rank.getName()
													+ HerobrinePVPCore.translateString(HerobrinePVPCore.getFileManager()
															.getPlusColor(player.getUniqueId()) + "+")
													+ rank.getColor() + " " + player.getName() + ": " + ChatColor.RESET
													+ e.getMessage());
								} else {
									arenaPlayers.sendMessage(ChatColor.RED + "[RED] " + rank.getColor() + rank.getName()
											+ " " + player.getName() + ": " + ChatColor.RESET + e.getMessage());
								}

							}
						}
					}

				}
			} else {

				for (UUID uuid : Manager.getArena(player).getPlayers()) {
					Player arenaPlayers = Bukkit.getPlayer(uuid);
					Games arenaGame = Manager.getGame(Manager.getArena(player));
					if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.MEMBER)) {
						arenaPlayers.sendMessage(arenaGame.getColor() + "[" + arenaGame.getKey()
								+ Manager.getArena(player).getID() + "] " + rank.getColor() + rank.getName() + " "
								+ player.getName() + ": " + ChatColor.GRAY + e.getMessage());
					} else if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
							|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)) {

						arenaPlayers.sendMessage(arenaGame.getColor() + "[" + arenaGame.getKey()
								+ Manager.getArena(player).getID() + "] " + rank.getColor() + rank.getName() + " "
								+ player.getName() + ChatColor.RESET + ": "
								+ ChatColor.translateAlternateColorCodes('&', e.getMessage()));

					} else {

						if (rank.hasPlusColor()) {
							arenaPlayers.sendMessage(arenaGame.getColor() + "[" + arenaGame.getKey()
									+ Manager.getArena(player).getID() + "] " + rank.getColor() + rank.getName()
									+ HerobrinePVPCore.translateString(
											HerobrinePVPCore.getFileManager().getPlusColor(player.getUniqueId()) + "+")
									+ rank.getColor() + " " + player.getName() + ": " + ChatColor.RESET
									+ e.getMessage());
						} else {
							arenaPlayers.sendMessage(arenaGame.getColor() + "[" + arenaGame.getKey()
									+ Manager.getArena(player).getID() + "] " + rank.getColor() + rank.getName() + " "
									+ player.getName() + ": " + ChatColor.RESET + e.getMessage());
						}

					}

				}

			}
		} else {
			for (Player onlinePlayers : e.getRecipients()) {

				if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.MEMBER)) {
					onlinePlayers.sendMessage(rank.getColor() + rank.getName() + " " + player.getName() + ": "
							+ ChatColor.GRAY + e.getMessage());
				} else if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
						|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)) {

					onlinePlayers.sendMessage(rank.getColor() + rank.getName() + " " + player.getName()
							+ ChatColor.RESET + ": " + ChatColor.translateAlternateColorCodes('&', e.getMessage()));

				} else {
					if (rank.hasPlusColor()) {
						onlinePlayers.sendMessage(rank.getColor() + rank.getName()
								+ HerobrinePVPCore.translateString(
										HerobrinePVPCore.getFileManager().getPlusColor(player.getUniqueId()) + "+")
								+ rank.getColor() + " " + player.getName() + ": " + ChatColor.RESET + e.getMessage());
					} else {
						onlinePlayers.sendMessage(rank.getColor() + rank.getName() + " " + player.getName() + ": "
								+ ChatColor.RESET + e.getMessage());
					}

				}
			}
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onItemClick(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().hasItemMeta()) {
			if (e.getPlayer().getItemInHand().getType().equals(Material.COMPASS)
					&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.GREEN + "Game Selector")
					&& e.getPlayer().getItemInHand().getItemMeta().getEnchants().isEmpty()) {
				Menus.applyGameModeSelection(e.getPlayer());

			}
		} else {
			return;
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		Player player = (Player) e.getWhoClicked();
		if (Manager.isPlaying(player)) {
			if (!Manager.getArena(player).getState().equals(GameState.LIVE)) {
				if (!BuildCommand.buildEnabledPlayers.contains(player)) {
					e.setCancelled(true);
				}

			}
		} else {

			if (BuildCommand.buildEnabledPlayers.contains(player)) {
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
			}
		}
		if (ChatColor.translateAlternateColorCodes('&', e.getClickedInventory().getTitle())
				.equals(ChatColor.GRAY + "Game Selection")) {
			if (e.getCurrentItem() != null) {
				switch (e.getCurrentItem().getType()) {
				case DIAMOND_SWORD:
					player.sendMessage(
							ChatColor.RED + "This game is not currently available! Reason: Not developed yet");
					// player.sendMessage(
					// ChatColor.GRAY + "Sending you to a game of " + ChatColor.AQUA + "Survival
					// Games");
					break;
				case BOW:
					player.sendMessage(
							ChatColor.RED + "This game is not currently available! Reason: Not developed yet");
					// player.sendMessage(
					// ChatColor.GRAY + "Sending you to a game of " + ChatColor.GOLD + "One In The
					// Chamber");
					break;
				case BED:
					player.sendMessage(
							ChatColor.RED + "This game is not currently available! Reason: Not developed yet");
					// player.sendMessage(
					// ChatColor.GRAY + "Sending you to a game of " + ChatColor.LIGHT_PURPLE + "Bed
					// Wars");
					break;
				case LAVA_BUCKET:
					player.sendMessage(
							ChatColor.RED + "This game is not currently available! Reason: Not developed yet");
					// player.sendMessage(
					// ChatColor.GRAY + "Sending you to a game of " + ChatColor.RED + "Survive The
					// Disaster");
					break;

				case EYE_OF_ENDER:
					player.sendMessage(
							ChatColor.RED + "This game is not currently available! Reason: Not developed yet");
					// player.sendMessage(ChatColor.GRAY + "Sending you to a game of " +
					// ChatColor.GREEN + "Skywars");
					break;
				case GRASS:
					GameCoreMain.getInstance().startQueue(player, Games.BLOCK_HUNT);
					break;
				default:
					return;
				}
				player.closeInventory();
			}
		} else {
			return;
		}

	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		if (e.getEntity() instanceof Player) {

		}
	}

	@EventHandler

	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(e.toWeatherState());

	}

}
