package bet.astral.bettersurvival.gameplay.listeners.player;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathLocationListener implements Listener {
	private final BetterSurvival survival;

	public DeathLocationListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent event){
		Location location = event.getEntity().getLocation();
		event.getPlayer().sendMessage(Component.text("Your death location is at ").color(NamedTextColor.DARK_RED).append(Component.text("x", NamedTextColor.GRAY))
				.append(Component.text((int) location.getX()).color(NamedTextColor.WHITE)).append(Component.text(", y").color(NamedTextColor.GRAY))
				.append(Component.text((int) location.getY()).color(NamedTextColor.WHITE)).append(Component.text(", z").color(NamedTextColor.GRAY))
				.append(Component.text((int) location.getX()).color(NamedTextColor.WHITE)).append(Component.text(", ").color(NamedTextColor.GRAY)).appendSpace()
				.append(Component.text(location.getWorld().getEnvironment().name()).color(NamedTextColor.YELLOW)));
	}
}
