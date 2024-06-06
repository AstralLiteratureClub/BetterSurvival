package bet.astral.bettersurvival.format;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ChatFormatListener implements Listener {
	private final BetterSurvival survival;

	public ChatFormatListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		event.renderer(
				ChatRenderer.viewerUnaware(
						new ChatRenderer.ViewerUnaware() {
							@Override
							public @NotNull Component render(@NotNull Player player, @NotNull Component displayname, @NotNull Component message) {
								return survival.miniMessage.deserialize(survival.chatFormat)
										.replaceText(builder -> builder.match("%player%").replacement(Component.text(player.getName())))
										.replaceText(builder -> builder.match("%message%").replacement(message))
										;
							}
						}
				)
		);
	}
}
