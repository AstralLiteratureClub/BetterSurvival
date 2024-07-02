package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Raider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RaiderBadOmenReplacementListener implements Listener {
	private final BetterSurvival survival;

	public RaiderBadOmenReplacementListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Raider raider){
			if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent entityDamageByEntityEvent){
				if (!(entityDamageByEntityEvent.getDamager() instanceof Player player)){
					return;
				}
				EntityEquipment entityEquipment = raider.getEquipment();
				ItemStack helmet = entityEquipment.getHelmet();
				if (helmet.getType().name().endsWith("_BANNER")){
					if (event.getDrops().stream().anyMatch(item->item.getType()==Material.OMINOUS_BOTTLE)){
						event.getDrops().removeIf(item->item.getType()== Material.OMINOUS_BOTTLE);
						PotionEffect effect = player.getPotionEffect(PotionEffectType.BAD_OMEN);
						int badOmen = 0;
						if (effect == null){
							badOmen++;
						} else {
							badOmen = effect.getAmplifier()+1;
						}
						player.removePotionEffect(PotionEffectType.BAD_OMEN);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 100*60*20, badOmen));
					}
				}
			}
		}
	}
}
