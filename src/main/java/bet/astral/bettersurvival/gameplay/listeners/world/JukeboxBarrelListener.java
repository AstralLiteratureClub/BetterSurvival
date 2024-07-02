package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class JukeboxBarrelListener implements Listener {
	private final BetterSurvival survival;

	public JukeboxBarrelListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	private void onJukeboxClick(@NotNull PlayerInteractEvent event) {
		if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.JUKEBOX) {
			if (event.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.BARREL) {
				if (event.getPlayer().isSneaking() && event.getPlayer().getInventory().getItemInMainHand().isEmpty()) {
					Block block = event.getClickedBlock().getRelative(BlockFace.DOWN);
					Barrel barrel = (Barrel) block.getState();
					event.setCancelled(true);
					event.setUseInteractedBlock(Event.Result.DENY);
					event.setUseItemInHand(Event.Result.DENY);
					event.getPlayer()
							.openInventory(
									barrel.getInventory()
							);
					if (!barrel.isOpen()) {
						barrel.open();
					}
				}
			}
		}
	}
}