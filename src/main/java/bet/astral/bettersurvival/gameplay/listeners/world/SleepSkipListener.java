package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SleepSkipListener implements Listener {
	private final BetterSurvival survival;

	public SleepSkipListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onSleep(PlayerDeepSleepEvent event){
		World world = event.getPlayer().getWorld();
		List<Player> players = world.getPlayers();
		int playersSleeping = 0;
		int playersSleepingRequired = survival.sleepRequirements.get(players.size()) != null ? survival.sleepRequirements.get(players.size()) : -1;
		if (playersSleepingRequired==-1){
			int current = players.size();
			while (playersSleepingRequired==-1){
				current--;
				playersSleepingRequired = survival.sleepRequirements.get(players.size()) != null ? survival.sleepRequirements.get(players.size()) : -1;
				if (current==-1){
					return;
				}
			}
		}
		if (players.size()>1){
			Set<Player> plrs = new HashSet<>();
			for (Player p : players){
				if (p.isDeeplySleeping() && !p.isSleepingIgnored()){
					playersSleeping++;
					plrs.add(p);
				}
			}
			if (playersSleepingRequired<=playersSleeping){
				world.sendMessage(Component.text("Enough of players are sleeping skipping the night.").color(NamedTextColor.YELLOW));
				for (Player p : players){
					if (!plrs.contains(p) && !p.isSleepingIgnored() && !p.isDeeplySleeping()){
						p.setSleepingIgnored(true);
					}
				}
				Bukkit.getScheduler().runTaskLaterAsynchronously(survival, ()->{
					world.getPlayers().forEach(player -> player.setSleepingIgnored(false));
				}, 20);
			}
		}
	}
}
