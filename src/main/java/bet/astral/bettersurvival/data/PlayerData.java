package bet.astral.bettersurvival.data;

import com.google.gson.annotations.Expose;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
	@Expose
	private final UUID uniqueId;
	@Expose
	private final Map<UUID, Component> nickNames = new HashMap<>();

	public PlayerData(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}
}
