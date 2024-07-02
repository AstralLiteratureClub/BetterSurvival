package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import org.bukkit.block.*;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickThroughItemFramesListeners implements Listener {
	private final BetterSurvival survival;

	public ClickThroughItemFramesListeners(BetterSurvival betterSurvival) {
		this.survival = betterSurvival;
	}

	@EventHandler
	public void onClick(PlayerItemFrameChangeEvent event) {
		if (event.getPlayer().isSneaking()) {
			return;
		}
		if (event.getAction() != PlayerItemFrameChangeEvent.ItemFrameChangeAction.ROTATE) {
			return;
		}
		ItemFrame itemFrame = event.getItemFrame();
		BlockFace blockFace = itemFrame.getAttachedFace();
		Block block = event.getItemFrame().getLocation().getWorld().getBlockAt(event.getItemFrame().getLocation()).getRelative(blockFace);
		if (block.getState() instanceof Lidded lidded) {
			Player player = event.getPlayer();
			if (lidded instanceof EnderChest) {
				player.openInventory(player.getEnderChest());
				event.setCancelled(true);
			} else if (lidded instanceof Container container) {
				player.openInventory(container.getInventory());
				event.setCancelled(true);
			}
		} else if (block.getState() instanceof Container container) {
			event.getPlayer().openInventory(container.getInventory());
			event.setCancelled(true);
		}
	}
}
