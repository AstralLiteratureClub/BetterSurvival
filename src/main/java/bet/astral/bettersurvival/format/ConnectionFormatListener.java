package bet.astral.bettersurvival.format;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionFormatListener implements Listener {
	private final BetterSurvival survival;

	public ConnectionFormatListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		String msg = survival.joins.get(survival.random.nextInt(survival.joins.size()));
		event.joinMessage(survival
				.miniMessage
				.deserialize(msg)
				.replaceText(builder -> builder.match("%player%").replacement(event.getPlayer().getName())));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		String msg = survival.quits.get(survival.random.nextInt(survival.quits.size()));
		event.quitMessage(survival
				.miniMessage
				.deserialize(msg)
				.replaceText(builder -> builder.match("%player%").replacement(event.getPlayer().getName())));
	}
}
