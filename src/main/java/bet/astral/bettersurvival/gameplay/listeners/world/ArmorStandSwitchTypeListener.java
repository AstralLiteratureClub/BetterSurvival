package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.jetbrains.annotations.NotNull;

public class ArmorStandSwitchTypeListener implements Listener {
	private final BetterSurvival survival;
	public ArmorStandSwitchTypeListener(BetterSurvival betterSurvival) {
		this.survival = betterSurvival;
	}

	@EventHandler
	public void onClick(@NotNull PlayerInteractAtEntityEvent event){
		if (!event.getPlayer().isSneaking()){
			return;
		}
		if (event.getRightClicked() instanceof ArmorStand armorStand){
			if (armorStand.isSmall() && armorStand.hasArms()){
				armorStand.setArms(false);
				armorStand.setSmall(false);
			} else if (armorStand.isSmall()){
				armorStand.setArms(true);
			} else if (armorStand.hasArms()){
				armorStand.setSmall(true);
				armorStand.setArms(false);
			} else {
				armorStand.setArms(true);
			}
		}
	}
}
