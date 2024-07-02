package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SpawnBabyOnDeathListener implements Listener {
	private final BetterSurvival survival;

	public SpawnBabyOnDeathListener(BetterSurvival survival) {
		this.survival = survival;
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		double chance = survival.random.nextDouble();
		if (chance>0.9){
			Location location = event.getPlayer().getLocation();

			location.getWorld().spawnEntity(location, EntityType.VILLAGER, CreatureSpawnEvent.SpawnReason.BREEDING, (entity -> {
				Villager villager = (Villager) entity;
				Player player = event.getPlayer();
				villager.setBaby();
				villager.setAgeLock(true);
				villager.setRemoveWhenFarAway(false);
				villager.setProfession(Villager.Profession.values()[survival.random.nextInt(Villager.Profession.values().length)]);
				villager.setVillagerType(Villager.Type.values()[survival.random.nextInt(Villager.Type.values().length)]);
				villager.customName(player.name().color(NamedTextColor.GOLD).append(Component.text("'s baby").color(NamedTextColor.GOLD)));
			}));
		}
	}
}
