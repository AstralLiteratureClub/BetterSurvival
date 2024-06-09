package bet.astral.bettersurvival.format;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.regex.Pattern;

public class AllItemDisplayListener implements Listener {
	private final Pattern pattern = Pattern.compile("((?i)\\[allitem]|\\[ai])");
	private final BetterSurvival survival;

	public AllItemDisplayListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	public void onItemDisplay(AsyncChatDecorateEvent event) {
		Player player = event.player();
		assert player != null;
		String plain = PlainTextComponentSerializer.plainText().serialize(event.result());
		if (plain.toLowerCase().contains("[allitem]") || plain.toLowerCase().contains("[ai]")) {
			ItemStack itemStack = player.getInventory().getItemInMainHand();
			Component itemComponent;
			if (itemStack.isEmpty()) {
				itemComponent = survival.miniMessage.deserialize(survival.itemFormatEmpty)
						.replaceText(builder -> builder.match("%player%").replacement(player.getName()))
				;
			} else {
				ItemMeta meta = itemStack.getItemMeta();
				Component displayname = meta.hasDisplayName() ? meta.displayName() : Component.translatable(itemStack.translationKey());
				itemComponent = survival.miniMessage
						.deserialize(survival.itemFormat)
						.replaceText(builder->builder.match("%displayname%").replacement(displayname))
						.hoverEvent(itemStack);
				int amount = 0;
				for (ItemStack item : player.getInventory()){
					if (item.isSimilar(itemStack)){
						amount+=item.getAmount();
					}
				}
				final int finalAmount = amount;
				itemComponent = itemComponent.replaceText(builder->builder.match("%amount%").replacement(""+ finalAmount));
			}
			Component finalItemComponent = itemComponent;
			event.result(event.result().replaceText(builder ->
					builder.match(pattern).replacement(finalItemComponent)));
		}
	}
}
