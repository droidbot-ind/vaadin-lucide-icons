package io.droidbot.vlucide.demo;

public enum PresetColor {

	DEFAULT("Default", ""), BLACK("Black", "#000000"), WHITE("White", "#ffffff"), RED("Red", "#ef4444"),
	ORANGE("Orange", "#f97316"), AMBER("Amber", "#f59e0b"), YELLOW("Yellow", "#eab308"), LIME("Lime", "#84cc16"),
	GREEN("Green", "#22c55e"), EMERALD("Emerald", "#10b981"), TEAL("Teal", "#14b8a6"), CYAN("Cyan", "#06b6d4"),
	SKY("Sky", "#0ea5e9"), BLUE("Blue", "#3b82f6"), INDIGO("Indigo", "#6366f1"), VIOLET("Violet", "#8b5cf6"),
	PURPLE("Purple", "#a855f7"), FUCHSIA("Fuchsia", "#d946ef"), PINK("Pink", "#ec4899"), ROSE("Rose", "#f43f5e");

	private final String label;
	private final String hex;

	PresetColor(String label, String hex) {
		this.label = label;
		this.hex = hex;
	}

	public String getDisplayName() {
		return label;
	}

	public String getHex() {
		return hex;
	}

	@Override
	public String toString() {
		return label;
	}
}
