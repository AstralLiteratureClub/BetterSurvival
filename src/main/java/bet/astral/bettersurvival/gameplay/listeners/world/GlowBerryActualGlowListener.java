package bet.astral.bettersurvival.gameplay.listeners.world;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GlowBerryActualGlowListener implements Listener {
	@EventHandler
	private void onEat(PlayerItemConsumeEvent event){
		if (event.getItem().getType()== Material.GLOW_BERRIES){
			event.getPlayer().addPotionEffect(new PotionEffect(
					PotionEffectType.GLOWING,
					100,
					1,
					false,
					true,
					true,
					null
			));
		}
	}
}
