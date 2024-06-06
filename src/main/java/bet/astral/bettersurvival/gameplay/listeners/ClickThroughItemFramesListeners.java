package bet.astral.bettersurvival.gameplay.listeners;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.block.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ClickThroughItemFramesListeners implements Listener {
	private final BetterSurvival survival;
	public ClickThroughItemFramesListeners(BetterSurvival betterSurvival) {
		this.survival = betterSurvival;
	}

	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent event){
		if (event.getPlayer().isSneaking()){
			return;
		}
		Entity entity = event.getRightClicked();
		if (entity instanceof ItemFrame itemFrame){
			if (itemFrame.isInvisible()){
				BlockFace blockFace = itemFrame.getAttachedFace();
				BlockFace opposite = blockFace.getOppositeFace();
				Block block = itemFrame.getLocation().getBlock().getRelative(opposite);
				if (block.getState() instanceof Lidded lidded){
					Player player = event.getPlayer();
					if (lidded instanceof EnderChest enderChest){
						player.openInventory(player.getEnderChest());
						enderChest.open();
					} else if (lidded instanceof Container container){
						player.openInventory(container.getInventory());
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
