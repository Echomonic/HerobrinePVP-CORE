package net.herobrine.core;

public enum Songs {
	FIND("find.nbs", "find"), WIN("win.nbs", "win"), LOSE("lose.nbs", "lose"), WAITING_THEME("wait.nbs", "wait"),
	COOL_SONG("cool.nbs", "cool"), DRAW("draw.nbs", "draw"), FIND_RARE("find_rare.nbs", "find_rare");

	private String songName;
	private String name;

	private Songs(String songName, String name) {
		this.songName = songName;
		this.name = name;
	}

	public String getSongName() {
		return songName;
	}

	public String getName() {
		return name;
	}

}
