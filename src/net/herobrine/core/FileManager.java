package net.herobrine.core;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

// UUID:Rank

public class FileManager {
	private File file;
	private File coins;
	private File trophies;
	private File plusColor;
	// private File bans;
	// private File mutes;
	private YamlConfiguration config;
	private YamlConfiguration coinConfig;
	private YamlConfiguration trophyConfig;
	private YamlConfiguration plusColorConfig;

	// private YamlConfiguration banConfig;
	// private YamlConfiguration muteConfig;

	public FileManager(HerobrinePVPCore main) {
		file = new File(main.getDataFolder(), "rankdata.yml");
		coins = new File(main.getDataFolder(), "coins.yml");
		trophies = new File(main.getDataFolder(), "trophies.yml");
		plusColor = new File(main.getDataFolder(), "color.yml");
		// bans = new File(main.getDataFolder(), "bans.yml");
		// mutes = new File(main.getDataFolder(), "mutes.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!coins.exists()) {
			try {
				coins.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!trophies.exists()) {
			try {
				trophies.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (!plusColor.exists()) {
			try {
				plusColor.createNewFile();
			}

			catch (IOException e) {
				e.printStackTrace();

			}
		}
		// if (!bans.exists()) {
		// try {
		// bans.createNewFile();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
//
		// if (!mutes.exists()) {
		// try {
		// mutes.createNewFile();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		config = YamlConfiguration.loadConfiguration(file);
		coinConfig = YamlConfiguration.loadConfiguration(coins);
		trophyConfig = YamlConfiguration.loadConfiguration(trophies);
		plusColorConfig = YamlConfiguration.loadConfiguration(plusColor);
		// banConfig = YamlConfiguration.loadConfiguration(bans);
		// muteConfig = YamlConfiguration.loadConfiguration(mutes);
	}

	public void setRank(Player player, Ranks rank) {
		config.set(player.getUniqueId().toString(), rank.name());
		save();
	}

// 
	public void setPlusColor(Player player, ChatColor color) {
		String colorCode = null;
		switch (color) {
		case RED:
			colorCode = "&c";
			break;
		case YELLOW:
			colorCode = "&e";

			break;

		case WHITE:
			colorCode = "&f";
			break;

		case GREEN:
			colorCode = "&a";
			break;

		case UNDERLINE:
			System.out.println("INVALID COLOR: Underline. Special Plus Colors are not allowed!");
			break;

		case DARK_GREEN:
			colorCode = "&2";
			break;

		case STRIKETHROUGH:
			System.out.println("INVALID COLOR: Strikethrough. Special Plus Colors are not allowed!");
			break;

		case RESET:
			System.out.println("INVALID COLOR: Reset. Special Plus Colors are not allowed!");
			break;

		case MAGIC:
			System.out.println("INVLALID COLOR: Magic. Special Plus Colors are not allowed!");
			break;

		case LIGHT_PURPLE:
			colorCode = "&d";
			break;

		case ITALIC:
			System.out.println("INVLALID COLOR: Italic. Special Plus Colors are not allowed!");
			break;

		case GRAY:
			colorCode = "&7";
			break;

		case GOLD:
			colorCode = "&6";
			break;

		case DARK_RED:
			colorCode = "&4";
			break;

		case DARK_PURPLE:
			colorCode = "&5";
			break;

		case BLACK:
			colorCode = "&0";
			break;

		case DARK_GRAY:
			colorCode = "&8";
			break;

		case DARK_BLUE:
			colorCode = "&1";
			break;

		case BLUE:
			colorCode = "&9";
			break;

		case DARK_AQUA:
			colorCode = "&3";
			break;

		case AQUA:
			colorCode = "&b";
			break;

		case BOLD:
			System.out.println("INVLALID COLOR: Bold. Special Plus Colors are not allowed!");
			break;

		default:
			return;

		}

		plusColorConfig.set(player.getUniqueId().toString(), colorCode);
		save();
		System.out.println(
				"color.yml updated with the following data: " + player.getUniqueId().toString() + ":" + colorCode);
		if (player.isOnline()) {
			if (getRank(player).hasPlusColor()) {
				if (player.getPlayer().getScoreboard().getTeam("rank") != null) {
					player.getPlayer().getScoreboard().getTeam("rank").setSuffix(getRank(player.getPlayer()).getColor()
							+ getRank(player.getPlayer()).getName()
							+ ChatColor.translateAlternateColorCodes('&', getPlusColor(player.getUniqueId()) + "+"));
				}

				player.setPlayerListName(getRank(player).getColor() + getRank(player).getName()
						+ ChatColor.translateAlternateColorCodes('&', getPlusColor(player.getUniqueId()) + "+") + " "
						+ getRank(player).getColor() + player.getName());

			}

		}
	}

	public String getPlusColor(UUID uuid) {
		return plusColorConfig.getString(uuid.toString());
	}

	public void setRank(UUID uuid, Ranks rank) {
		config.set(uuid.toString(), rank.name());
		save();
	}

	public Ranks getRank(Player player) {
		return Ranks.valueOf(config.getString(player.getUniqueId().toString()));
	}

	public void setCoins(Player player, int coins) {
		coinConfig.set(player.getUniqueId().toString(), coins);

		save();
	}

	// public void addPermBan(Player player, String reason) {
	// banConfig.set(player.getUniqueId().toString() + ":PERM", reason);
	// }

	// public void removeBan(Player player) {
	// if (banConfig.contains(player.getUniqueId().toString())) {
	// banConfig.get(player.getUniqueId().toString().replaceAll(null,
	// player.getUniqueId().toString() + ":NULL"));
//
	// }
	// }

	// public String getBanReason(Player player) {
	// return banConfig.getString(player.getUniqueId().toString());
	// }

// getMuteReason

	// public void addTempBan(Player player, int duration, String type, String
	// reason) {
//
	// }

	// public void addPermMute(Player player, String reason) {
//
	// }

	// public void addTempMute(Player player, int duration, String type, String
	// reason) {
//
	// }

//	public void removeMute(Player player) {
//
//	}

	public void setCoins(UUID uuid, int coins) {
		coinConfig.set(uuid.toString(), coins);
		save();
	}

	public void addCoins(UUID uuid, int coins) {

		coinConfig.set(uuid.toString(), coinConfig.getInt(uuid.toString()) + coins);
		save();
	}

	public void addCoins(Player player, int coins) {
		coinConfig.set(player.getUniqueId().toString(), coinConfig.getInt(player.getUniqueId().toString()) + coins);
		save();
	}

	public void removeCoins(UUID uuid, int coins) {
		coinConfig.set(uuid.toString(), coinConfig.getInt(uuid.toString()) - coins);
		save();
	}

	public void removeCoins(Player player, int coins) {
		coinConfig.set(player.getUniqueId().toString(), coinConfig.getInt(player.getUniqueId().toString()) - coins);
		save();
	}

	public int getCoins(Player player) {
		return coinConfig.getInt(player.getUniqueId().toString());

	}

	public int getCoins(UUID uuid) {
		return coinConfig.getInt(uuid.toString());
	}

	public void setTrophies(Player player, int trophies) {
		trophyConfig.set(player.getUniqueId().toString(), trophies);
		save();
	}

	public void setTrophies(UUID uuid, int trophies) {
		trophyConfig.set(uuid.toString(), trophies);
		save();
	}

	public void addTrophies(Player player, int trophies) {
		trophyConfig.set(player.getUniqueId().toString(),
				trophyConfig.getInt(player.getUniqueId().toString()) + trophies);
		save();
	}

	public void addTrophies(UUID uuid, int trophies) {
		trophyConfig.set(uuid.toString(), trophyConfig.getInt(uuid.toString()) + trophies);
		save();
	}

	public void removeTrophies(Player player, int trophies) {
		trophyConfig.set(player.getUniqueId().toString(),
				trophyConfig.getInt(player.getUniqueId().toString()) - trophies);
		save();
	}

	public void removeTrophies(UUID uuid, int trophies) {
		trophyConfig.set(uuid.toString(), trophyConfig.getInt(uuid.toString()) - trophies);
		save();
	}

	public int getTrophies(Player player) {
		return trophyConfig.getInt(player.getUniqueId().toString());
	}

	public int getTrophies(UUID uuid) {
		return trophyConfig.getInt(uuid.toString());
	}

	private void save() {
		try {
			config.save(file);
			coinConfig.save(coins);
			trophyConfig.save(trophies);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Ranks getRank(UUID uniqueId) {
		return Ranks.valueOf(config.getString(uniqueId.toString()));

	}
}
