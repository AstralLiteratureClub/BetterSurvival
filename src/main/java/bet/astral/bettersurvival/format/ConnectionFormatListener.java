package bet.astral.bettersurvival.format;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.util.Date;

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
				.deserialize(msg.replace("%player%", event.getPlayer().getName()))
				.hoverEvent(
						HoverEvent.showText(
								Component.text().decoration(TextDecoration.ITALIC, false).
										append(Component.text("Sent ").color(NamedTextColor.YELLOW)
												.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true))
												.appendNewline()
												.append(Component.text("Click here to message ")
														.color(NamedTextColor.GREEN).append(event.getPlayer().name()).color(NamedTextColor.GREEN))))
				)
				.clickEvent(ClickEvent.suggestCommand("/message " + event.getPlayer().getName()))
		);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		String msg = survival.quits.get(survival.random.nextInt(survival.quits.size()));
		event.quitMessage(survival
				.miniMessage
				.deserialize(msg.replace("%player%", event.getPlayer().getName()))
				.hoverEvent(
						HoverEvent.showText(
								Component.text().decoration(TextDecoration.ITALIC, false).
										append(Component.text("Sent ").color(NamedTextColor.YELLOW)
												.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true))))));
	}
}
