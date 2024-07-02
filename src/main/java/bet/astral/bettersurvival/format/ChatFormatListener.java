package bet.astral.bettersurvival.format;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

public class ChatFormatListener implements Listener {
	private final BetterSurvival survival;

	public ChatFormatListener(BetterSurvival survival) {
		this.survival = survival;
	}



	@EventHandler(priority = EventPriority.LOWEST)
	private void onChat(AsyncChatDecorateEvent event){
		event.result(event.result()
				.hoverEvent(
						HoverEvent.showText(Component.text("Click here to copy this message.").color(NamedTextColor.GREEN))
				).clickEvent(ClickEvent.copyToClipboard(PlainTextComponentSerializer.plainText().serialize(event.result()))));
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		event.renderer(
				ChatRenderer.viewerUnaware(
						// Add custom nicknames
						new ChatRenderer.ViewerUnaware() {
							@Override
							public @NotNull Component render(@NotNull Player player, @NotNull Component displayname, @NotNull Component message) {
								return survival.miniMessage.deserialize(survival.chatFormat)
										.replaceText(builder -> builder.match("%player%").replacement(Component.text(player.getName())))
										.replaceText(builder -> builder.match("%message%").replacement(message)
										).hoverEvent(
												HoverEvent.showText(
														Component.text().decoration(TextDecoration.ITALIC, false).
																append(Component.text("Sent ").color(NamedTextColor.YELLOW)
																		.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true))
																		.appendNewline()
																		.append(Component.text("Click here to message ")
																				.color(NamedTextColor.GREEN).append(player.name()).color(NamedTextColor.GREEN))))
										)
										.clickEvent(ClickEvent.suggestCommand("/message " + player.getName()))
										;
							}
						}
				)
		);
	}
}