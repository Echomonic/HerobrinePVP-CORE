package net.herobrine.core;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

// UUID:Rank

public class FileManager {
	private HerobrinePVPCore main;
	private File file;
	private File coins;
	private File trophies;
	private File plusColor;
	private YamlConfiguration rankConfig;
	private YamlConfiguration coinConfig;
	private YamlConfiguration trophyConfig;
	private YamlConfiguration plusColorConfig;
	private List<String> playerNameStorage = new ArrayList<>();

	public FileManager(HerobrinePVPCore main) {
		// mainConfig = new File(main.getDataFolder(), "config.yml");
		this.main = main;
		main.saveDefaultConfig();
		file = new File(main.getDataFolder(), "rankdata.yml");
		coins = new File(main.getDataFolder(), "coins.yml");
		trophies = new File(main.getDataFolder(), "trophies.yml");
		plusColor = new File(main.getDataFolder(), "color.yml");

		rankConfig = YamlConfiguration.loadConfiguration(file);
		coinConfig = YamlConfiguration.loadConfiguration(coins);
		trophyConfig = YamlConfiguration.loadConfiguration(trophies);
		plusColorConfig = YamlConfiguration.loadConfiguration(plusColor);

		convertFromLegacy();
	}

	public boolean isUserRegistered(Player player) {

		if (main.getConfig().get("players." + player.getUniqueId().toString()) != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isUserRegistered(UUID uuid) {
		if (main.getConfig().get("players." + uuid.toString()) != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isUserRegistered(String uuid) {
		if (main.getConfig().get("players." + uuid) != null) {
			return true;
		} else {
			return false;
		}

	}

	public void registerUser(Player player) {

		List<String> playerName = new ArrayList<>();
		playerName.add(player.getName());
		List<String> defaultEmotes = new ArrayList<>();
		List<String> defaultCosmetics = new ArrayList<>();
		List<String> defaultClasses = new ArrayList<>();
		main.getConfig().set("players." + player.getUniqueId().toString() + ".rank", Ranks.MEMBER.name());
		main.getConfig().set("players." + player.getUniqueId().toString() + ".plusColor", "&d");
		main.getConfig().set("players." + player.getUniqueId().toString() + ".level", 1);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".xp", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".joinDate",
				System.currentTimeMillis() / 1000L);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".lastLogin",
				System.currentTimeMillis() / 1000L);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".playerNames", playerName);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".ipAddress",
				player.getAddress().toString());
		main.getConfig().set("players." + player.getUniqueId().toString() + ".isBanned", false);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".isMuted", false);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".activePunishmentId", "");
		main.getConfig().set("players." + player.getUniqueId().toString() + ".currencies.trophies", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".currencies.coins", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".punishments", "");
		main.getConfig().set("players." + player.getUniqueId().toString() + ".unlockedItems.cosmetics",
				defaultCosmetics);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".unlockedItems.emotes", defaultEmotes);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".unlockedItems.classes", defaultClasses);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bh.wins", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bh.roundsWon", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bh.roundsPlayed", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bh.blocksMined", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bh.blocksFound", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bc.wins", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bc.kills", 0);
		main.getConfig().set("players." + player.getUniqueId().toString() + ".stats.games.bc.roundsPlayed", 0);
		main.saveConfig();
	}

	public void registerUser(UUID uuid) {
		DateFormat df = new SimpleDateFormat("MM-dd-yy-k-m-s");
		Date dateobj = new Date();
		List<String> playerName = new ArrayList<>();
		List<String> defaultEmotes = new ArrayList<>();
		List<String> defaultCosmetics = new ArrayList<>();
		List<String> defaultClasses = new ArrayList<>();
		main.getConfig().set("players." + uuid.toString() + ".rank", Ranks.MEMBER.name());
		main.getConfig().set("players." + uuid.toString() + ".plusColor", "&d");
		main.getConfig().set("players." + uuid.toString() + ".level", 1);
		main.getConfig().set("players." + uuid.toString() + ".xp", 0);
		main.getConfig().set("players." + uuid.toString() + ".joinDate", df.format(dateobj));
		main.getConfig().set("players." + uuid.toString() + ".lastLogin", System.currentTimeMillis() / 1000L);
		main.getConfig().set("players." + uuid.toString() + ".lastDisconnect", System.currentTimeMillis() / 1000L);
		main.getConfig().set("players." + uuid.toString() + ".playerNames", playerName);
		main.getConfig().set("players." + uuid.toString() + ".ipAddress", "");
		main.getConfig().set("players." + uuid.toString() + ".isBanned", false);
		main.getConfig().set("players." + uuid.toString() + ".isMuted", false);
		main.getConfig().set("players." + uuid.toString() + ".activePunishmentId", "");
		main.getConfig().set("players." + uuid.toString() + ".currencies.trophies", 0);
		main.getConfig().set("players." + uuid.toString() + ".currencies.coins", 0);
		main.getConfig().set("players." + uuid.toString() + ".punishments", "");
		main.getConfig().set("players." + uuid.toString() + ".unlockedItems.cosmetics", defaultCosmetics);
		main.getConfig().set("players." + uuid.toString() + ".unlockedItems.emotes", defaultEmotes);
		main.getConfig().set("players." + uuid.toString() + ".unlockedItems.classes", defaultClasses);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bh.wins", 0);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bh.roundsWon", 0);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bh.roundsPlayed", 0);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bh.blocksMined", 0);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bh.blocksFound", 0);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bc.wins", 0);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bc.kills", 0);
		main.getConfig().set("players." + uuid.toString() + ".stats.games.bc.roundsPlayed", 0);
		main.saveConfig();
	}

	public void convertFromLegacy() {

		HashMap<String, Object> rankObjects = (HashMap<String, Object>) rankConfig.getValues(false);

		for (String string : rankObjects.keySet()) {
			UUID uuid = UUID.fromString(string);
			if (!isUserRegistered(uuid)) {
				registerUser(uuid);
				setRank(uuid, getRankFromLegacy(uuid));
				setCoins(uuid, getCoinsFromLegacy(uuid));
				setTrophies(uuid, getTrophiesFromLegacy(uuid));
			}

		}

	}

	public void updateIP(Player player, String ip) {
		main.getConfig().set("players." + player.getUniqueId().toString() + ".ipAddress", ip);
		main.saveConfig();
	}

	public String getIP(Player player) {

		return main.getConfig().getString("players." + player.getUniqueId().toString() + ".ipAddress");

	}

	public String getIP(UUID uuid) {
		return main.getConfig().getString("players." + uuid.toString() + ".ipAddress");
	}

	public void updatePlayerNames(Player player) {

		main.getConfig().set("players." + player.getUniqueId().toString() + ".playerNames", player.getName());
		main.saveConfig();
		player.sendMessage(ChatColor.RED + "Your playername was updated in the config!");
	}

	public String getNameFromUUID(UUID uuid) {

		return main.getConfig().getString("players." + uuid.toString() + ".playerNames");
	}

	public UUID getUUIDFromName(String name) {

		HashMap<String, Object> objects = (HashMap<String, Object>) main.getConfig().getConfigurationSection("players")
				.getValues(false);

		for (String string : objects.keySet()) {
			if (isUserRegistered(string)) {

				System.out.println("string is registered!");

				if (main.getConfig().getStringList("players." + string + ".playerNames") != null) {

					if (main.getConfig().getStringList("players." + string + ".playerNames").contains(name)) {
						UUID uuid = UUID.fromString(string);
						return uuid;
					}

				}

			}

		}

		return null;

	}

	public void updateLastJoin(Player player) {
		DateFormat df = new SimpleDateFormat("MM-dd-yy-k-m-s");
		Date dateobj = new Date();
		main.getConfig().set("players." + player.getUniqueId().toString() + ".lastLogin",
				System.currentTimeMillis() / 1000L);
		main.saveConfig();
	}

	public void updateLastDisconnect(Player player) {
		DateFormat df = new SimpleDateFormat("MM-dd-yy-k-m-s");
		Date dateobj = new Date();
		main.getConfig().set("players." + player.getUniqueId().toString() + ".lastDisconnect",
				System.currentTimeMillis() / 1000L);
		main.saveConfig();
	}

	public void setRank(Player player, Ranks rank) {
		main.getConfig().set("players." + player.getUniqueId().toString() + ".rank", rank.name());
		main.saveConfig();
	}

// 

	// only set to PRODUCTION or DEV
//	public void setEnvironment(String environment) {
//
	// mainConfigFile.set("Environment", environment);
	// save();
	// }

//	public String getEnvironment() {
//		return mainConfigFile.getString("Environment");
//	}

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
		main.getConfig().set("players." + player.getUniqueId().toString() + ".plusColor", colorCode);
		main.saveConfig();
		System.out.println("Plus Color Changed: " + player.getUniqueId().toString() + ":" + colorCode);
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
		return main.getConfig().getString("players." + uuid.toString() + ".plusColor");
	}

	public void setRank(UUID uuid, Ranks rank) {
		main.getConfig().set("players." + uuid.toString() + ".rank", rank.name());
		main.saveConfig();
	}

	public Ranks getRank(Player player) {
		return Ranks.valueOf(main.getConfig().getString("players." + player.getUniqueId().toString() + ".rank"));
	}

	public Ranks getRankFromLegacy(Player player) {
		return Ranks.valueOf(rankConfig.getString(player.getUniqueId().toString()));
	}

	public void setCoins(Player player, int coins) {
		main.getConfig().set("players." + player.getUniqueId() + ".currencies.coins", coins);
		main.saveConfig();
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
		main.getConfig().set("players." + uuid.toString() + ".currencies.coins", coins);
		main.saveConfig();
	}

	public void addCoins(UUID uuid, int coins) {

		main.getConfig().set("players." + uuid.toString() + ".currencies.coins",
				main.getConfig().getInt("players." + uuid.toString() + ".currencies.coins") + coins);
		main.saveConfig();
	}

	public void addCoins(Player player, int coins) {
		main.getConfig().set("players." + player.getUniqueId().toString() + ".currencies.coins",
				main.getConfig().getInt("players." + player.getUniqueId().toString() + ".currencies.coins") + coins);
		main.saveConfig();
	}

	public void removeCoins(UUID uuid, int coins) {
		main.getConfig().set("players." + uuid.toString() + ".currencies.coins",
				main.getConfig().getInt("players." + uuid.toString() + ".currencies.coins") - coins);
		main.saveConfig();
	}

	public void removeCoins(Player player, int coins) {
		main.getConfig().set("players." + player.getUniqueId().toString() + ".currencies.coins",
				main.getConfig().getInt("players." + player.getUniqueId().toString() + ".currencies.coins") - coins);
		main.saveConfig();
	}

	public int getCoins(Player player) {
		return main.getConfig().getInt("players." + player.getUniqueId().toString() + ".currencies.coins");

	}

	public int getCoins(UUID uuid) {
		return main.getConfig().getInt("players." + uuid.toString() + ".currencies.coins");
	}

	public int getCoinsFromLegacy(Player player) {
		return coinConfig.getInt(player.getUniqueId().toString());
	}

	public int getCoinsFromLegacy(UUID uuid) {
		return coinConfig.getInt(uuid.toString());
	}

	public void setTrophies(Player player, int trophies) {
		main.getConfig().set("players." + player.getUniqueId().toString() + ".currencies.trophies", trophies);
		main.saveConfig();
	}

	public void setTrophies(UUID uuid, int trophies) {
		main.getConfig().set("players." + uuid.toString() + ".currencies.trophies", trophies);
		main.saveConfig();
	}

	public void addTrophies(Player player, int trophies) {
		main.getConfig().set("players." + player.getUniqueId().toString() + ".currencies.trophies",
				main.getConfig().getInt("players." + player.getUniqueId().toString() + ".currencies.trophies")
						+ trophies);
		main.saveConfig();
	}

	public void addTrophies(UUID uuid, int trophies) {
		main.getConfig().set("players." + uuid.toString() + ".currencies.trophies",
				main.getConfig().getInt("players." + uuid.toString() + ".currencies.trophies") + trophies);
		main.saveConfig();
	}

	public void removeTrophies(Player player, int trophies) {
		main.getConfig().set("players." + player.getUniqueId().toString() + ".currencies.trophies",
				main.getConfig().getInt("players." + player.getUniqueId().toString() + ".currencies.trophies")
						- trophies);
		main.saveConfig();
	}

	public void removeTrophies(UUID uuid, int trophies) {
		main.getConfig().set("players." + uuid.toString() + ".currencies.trophies",
				main.getConfig().getInt("players." + uuid.toString() + ".currencies.trophies") - trophies);
		main.saveConfig();
	}

	public int getTrophies(Player player) {
		return main.getConfig().getInt("players." + player.getUniqueId().toString() + ".currencies.trophies");
	}

	public int getTrophies(UUID uuid) {
		return main.getConfig().getInt("players." + uuid.toString() + ".currencies.trophies");
	}

	public int getTrophiesFromLegacy(Player player) {
		return trophyConfig.getInt(player.getUniqueId().toString());
	}

	public int getTrophiesFromLegacy(UUID uuid) {
		return trophyConfig.getInt(uuid.toString());
	}

	private void save() {
		try {
			// config.save(file);
			coinConfig.save(coins);
			trophyConfig.save(trophies);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public Ranks getRank(UUID uniqueId) {
		return Ranks.valueOf(main.getConfig().getString("players." + uniqueId.toString() + ".rank"));

	}

	public Ranks getRankFromLegacy(UUID uniqueId) {
		return Ranks.valueOf(rankConfig.getString(uniqueId.toString()));
	}
}
