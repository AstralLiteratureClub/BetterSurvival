package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SoulSandValleySkeletonHorseSpawnListener implements Listener {
	private final Random random = new Random(System.nanoTime());
	private final BetterSurvival survival;

	public SoulSandValleySkeletonHorseSpawnListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	public void onSpawn(@NotNull EntitySpawnEvent event){
		if (event.getEntity().getWorld().getEnvironment() != World.Environment.NETHER){
			return;
		}
		if (event.getEntityType()== EntityType.SKELETON) {
			Skeleton skeleton = (Skeleton) event.getEntity();
			Location location = event.getLocation();
			World world = location.getWorld();
			if (skeleton.isInsideVehicle()){
				return;
			}
			if (world.getBiome(location) == Biome.SOUL_SAND_VALLEY){
				if (random.nextDouble()<0.0175){
					SkeletonHorse skeletonHorse = (SkeletonHorse) world.spawnEntity(location, EntityType.SKELETON_HORSE, CreatureSpawnEvent.SpawnReason.MOUNT);
					skeletonHorse.setHealth(random.nextInt(6, 15));
					skeletonHorse.setJumpStrength(random.nextDouble(1, 3.5));
					skeletonHorse.addPassenger(skeleton);
				}
			}
		}
	}
}
