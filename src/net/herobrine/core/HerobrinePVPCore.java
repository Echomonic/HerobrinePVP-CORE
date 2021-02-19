package net.herobrine.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;

import net.herobrine.gamecore.GameCoreMain;
import net.herobrine.wallsg.BlockHuntMain;

public class HerobrinePVPCore extends JavaPlugin {
	private static FileManager fileManager;

	@Override
	public void onEnable() {
		fileManager = new FileManager(this);
		if (getGameCoreAPI() == null) {
			System.out.println("[HBPVP-CORE] You need the game core to use the HBPVP-CORE plugin.");
			Bukkit.getPluginManager().disablePlugin(this);

		}

		else {
			System.out.println("[HBPVP-CORE] Hooked into the Game Core!");
		}
		if (getBlockHuntAPI() == null) {
			System.out.println(
					"[HBPVP-CORE] Missing Minigame Plugin: BlockHunt.\n[HBPVP-CORE] All minigame plugins are required for the core to function properly, disabling...");
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			System.out.println("[HBPVP-CORE] Successfully hooked into Block Hunt!");
		}

		if (getNBAPI() == null) {
			System.out.println(
					"[HBPVP-CORE] Missing Note Block API! This plugin is required for many sound effects. Disabling..");
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			System.out.println("[HBPVP-CORE] Successfully hooked into Note Block API!");
		}

		// if (getCRAPI() == null) {
		// System.out.println(
		// "[HBPVP-CORE] Missing Minigame Plugin: Clash Royale \n[HBPVP-CORE] All
		// minigame plugins are required for the core to function properly,
		// disabling...");
		// } else {
		// System.out.println("[HBPVP-CORE] Successfully hooked into Clash Royale!");
		// }
		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		getCommand("rank").setExecutor(new RankCommand());
		getCommand("emotes").setExecutor(new EmotesCommand());
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("gmc").setExecutor(new GMCCommand());
		getCommand("gms").setExecutor(new GMSCommand());
		getCommand("gma").setExecutor(new GMACommand());
		getCommand("gmsp").setExecutor(new GMSPCommand());
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("ban").setExecutor(new BanCommand());
		getCommand("tempban").setExecutor(new TempBanCommand());
		getCommand("mute").setExecutor(new MuteCommand());
		getCommand("unban").setExecutor(new UnbanCommand());
		getCommand("unmute").setExecutor(new UnmuteCommand());
		getCommand("speed").setExecutor(new SpeedCommand());
		getCommand("coins").setExecutor(new CoinsCommand());
		getCommand("trophies").setExecutor(new TrophyCommand());
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("message").setExecutor(new MessageCommand());
		getCommand("announce").setExecutor(new AnnounceCommand());
		getCommand("kaboom").setExecutor(new KaboomCommand());
		getCommand("nick").setExecutor(new NickCommand());
		getCommand("forcecredits").setExecutor(new ForceCreditsCommand());
		getCommand("rankcolor").setExecutor(new PlusColorCMD());
		getCommand("mutechat").setExecutor(new MuteChatCMD());
		getCommand("songtest").setExecutor(new SongTestCMD());
	}

	public static FileManager getFileManager() {
		return fileManager;
	}

	public void getRank(Player player) {
		HerobrinePVPCore.getFileManager().getRank(player);
	}

	public ChatColor getRankColor(Player player) {
		Ranks rank = HerobrinePVPCore.getFileManager().getRank(player);
		return rank.getColor();
	}

	public boolean isBuildEnabled(Player player) {
		if (BuildCommand.buildEnabledPlayers.contains(player)) {
			return true;
		}
		return false;
	}

	public static void buildSidebar(Player player) {

		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

		Objective obj = board.registerNewObjective("board", "dummy");
		obj.setDisplayName(ChatColor.RED + "Herobrine PVP");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		Score title = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&bThe best streamer event server!"));
		title.setScore(7);

		Score blank = obj.getScore(" ");
		blank.setScore(6);
		Team rank = board.registerNewTeam("rank");

		rank.addEntry(ChatColor.WHITE.toString());
		rank.setPrefix(ChatColor.AQUA + "Rank: ");
		if (HerobrinePVPCore.getFileManager().getRank(player).hasPlusColor()) {
			rank.setSuffix(getFileManager().getRank(player).getColor() + getFileManager().getRank(player).getName()
					+ translateString(getFileManager().getPlusColor(player.getUniqueId()) + "+"));
		} else {
			rank.setSuffix(HerobrinePVPCore.getFileManager().getRank(player).getColor()
					+ HerobrinePVPCore.getFileManager().getRank(player).getName());
		}
		obj.getScore(ChatColor.WHITE.toString()).setScore(5);

		Team coins = board.registerNewTeam("coins");

		coins.addEntry(ChatColor.YELLOW.toString());
		coins.setPrefix(ChatColor.YELLOW + "Coins: ");
		coins.setSuffix(
				ChatColor.translateAlternateColorCodes('&', "&e") + HerobrinePVPCore.getFileManager().getCoins(player));
		obj.getScore(ChatColor.YELLOW.toString()).setScore(4);

		Team trophies = board.registerNewTeam("trophies");

		trophies.addEntry(ChatColor.GOLD.toString());
		trophies.setPrefix(ChatColor.GOLD + "Trophies: ");
		trophies.setSuffix(ChatColor.translateAlternateColorCodes('&', "&6")
				+ HerobrinePVPCore.getFileManager().getTrophies(player));
		obj.getScore(ChatColor.GOLD.toString()).setScore(3);

		Score blank2 = obj.getScore("  ");
		blank2.setScore(2);

		Score ip = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&cherobrinepvp.beastmc.com"));
		ip.setScore(1);
		player.setCustomName(HerobrinePVPCore.getFileManager().getRank(player).getColor()
				+ HerobrinePVPCore.getFileManager().getRank(player).getName() + " " + player.getName());
		player.setScoreboard(board);

	}

	public static void buildAPISidebar(Player player, Scoreboard board) {

		player.setScoreboard(board);
	}

	public BlockHuntMain getBlockHuntAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("BlockHunt");
		if (plugin instanceof BlockHuntMain) {
			return (BlockHuntMain) plugin;
		} else {
			return null;
		}

	}

	public GameCoreMain getGameCoreAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GameCore");
		if (plugin instanceof GameCoreMain) {
			return (GameCoreMain) plugin;
		} else {
			return null;
		}

	}

	public NoteBlockAPI getNBAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("NoteBlockAPI");
		if (plugin instanceof NoteBlockAPI) {
			return (NoteBlockAPI) plugin;
		} else {
			return null;
		}
	}

	/**
	 * Only works in 1.8+.
	 *
	 * @param name
	 * @param player
	 */
	public static void changeName(String name, Player player) {
		try {
			Method getHandle = player.getClass().getMethod("getHandle", (Class<?>[]) null);
			// Object entityPlayer = getHandle.invoke(player);
			// Class<?> entityHuman = entityPlayer.getClass().getSuperclass();
			/**
			 * These methods are no longer needed, as we can just access the profile using
			 * handle.getProfile. Also, because we can just use the method, which will not
			 * change, we don't have to do any field-name look-ups.
			 */
			try {
				Class.forName("com.mojang.authlib.GameProfile");
				// By having the line above, only 1.8+ servers will run this.
			} catch (ClassNotFoundException e) {
				/**
				 * Currently, there is no field that can be easily modified for lower versions.
				 * The "name" field is final, and cannot be modified in runtime. The only
				 * workaround for this that I can think of would be if the server creates a
				 * "dummy" entity that takes in the player's input and plays the player's
				 * animations (which will be a lot more lines)
				 */
				Bukkit.broadcastMessage("CHANGE NAME METHOD DOES NOT WORK IN 1.7 OR LOWER!");
				return;
			}
			Object profile = getHandle.invoke(player).getClass().getMethod("getProfile")
					.invoke(getHandle.invoke(player));
			Field ff = profile.getClass().getDeclaredField("name");
			ff.setAccessible(true);
			ff.set(profile, ChatColor.translateAlternateColorCodes('&', name));
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.hidePlayer(player);
				players.showPlayer(player);
			}
			player.hidePlayer(player);
			player.showPlayer(player);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchFieldException e) {
			/**
			 * Merged all the exceptions. Less lines
			 */
			e.printStackTrace();
		}
	}

	public static String translateString(String string) {

		return ChatColor.translateAlternateColorCodes('&', string);
	}

	// public ClashRoyaleMain getCRAPI() {
	// Plugin plugin =
	// Bukkit.getServer().getPluginManager().getPlugin("ClashRoyale");
	// if (plugin instanceof ClashRoyaleMain) {
	// return(ClashRoyaleMain)plugin;
	// }
//
	// else {
	// return null;
	// }
//	}

}
