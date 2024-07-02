package bet.astral.bettersurvival.format;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChatStylingListener implements Listener {
	private static final Pattern pattern_all_item = Pattern.compile("((?i)\\[allitem]|\\[ai])");
	private static final Pattern pattern_item = Pattern.compile("((?i)\\[item]|\\[i])");
	private static final Map<String, Pattern> patternMap = new HashMap<>();
	private final BetterSurvival survival;

	public ChatStylingListener(BetterSurvival survival) {
		this.survival = survival;
	}

	public static Component formatAllItem(Player player, @NotNull Component component, @NotNull BetterSurvival survival) {
		if (player == null) {
			return component;
		}
		String plain = PlainTextComponentSerializer.plainText().serialize(component);
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
				if (meta.hasRarity()) {
					itemComponent = survival.miniMessage
							.deserialize(survival.itemFormat)
							.replaceText(builder -> builder.match("%displayname%").replacement(displayname.color(meta.getRarity().color())))
							.hoverEvent(itemStack);
				} else {
					itemComponent = survival.miniMessage
							.deserialize(survival.itemFormat)
							.replaceText(builder -> builder.match("%displayname%").replacement(displayname))
							.hoverEvent(itemStack);
				}

				int amount = 0;
				for (ItemStack item : player.getInventory()) {
					if (item == null) {
						continue;
					}
					if (item.isSimilar(itemStack)) {
						amount += item.getAmount();
					}
				}
				final int finalAmount = amount;
				itemComponent = itemComponent.replaceText(builder -> builder.match("%amount%").replacement("" + finalAmount));
			}
			Component finalItemComponent = itemComponent;
			return component.replaceText(builder ->
					builder.match(pattern_all_item).replacement(finalItemComponent));
		}
		return component;
	}

	public static Component formatItem(Player player, @NotNull Component component, @NotNull BetterSurvival survival) {
		if (player == null) {
			return component;
		}
		String plain = PlainTextComponentSerializer.plainText().serialize(component);
		if (plain.toLowerCase().contains("[item]") || plain.toLowerCase().contains("[i]")) {
			ItemStack itemStack = player.getInventory().getItemInMainHand();
			Component itemComponent;
			if (itemStack.isEmpty()) {
				itemComponent = survival.miniMessage.deserialize(survival.itemFormatEmpty)
						.replaceText(builder -> builder.match("%player%").replacement(player.getName()))
				;
			} else {
				ItemMeta meta = itemStack.getItemMeta();
				Component displayname = meta.hasDisplayName() ? meta.displayName() : Component.translatable(itemStack.translationKey());
				if (meta.hasRarity()) {
					itemComponent = survival.miniMessage
							.deserialize(survival.itemFormat)
							.replaceText(builder -> builder.match("%amount%").replacement("" + itemStack.getAmount()))
							.replaceText(builder -> builder.match("%displayname%").replacement(displayname.color(meta.getRarity().color())))
							.hoverEvent(itemStack);
				} else {
					itemComponent = survival.miniMessage
							.deserialize(survival.itemFormat)
							.replaceText(builder -> builder.match("%amount%").replacement("" + itemStack.getAmount()))
							.replaceText(builder -> builder.match("%displayname%").replacement(displayname))
							.hoverEvent(itemStack);
				}
			}
			Component finalItemComponent = itemComponent;
			return component.replaceText(builder ->
					builder.match(pattern_item).replacement(finalItemComponent));
		}
		return component;
	}

	private static Component tryFixLinks(Component component, @RegExp String value, @NotNull BetterSurvival survival) {
		for (String v : survival.links) {
			if (value.toLowerCase().startsWith(v.toLowerCase())) {
				final String found = value.replaceFirst(v, "");
				if (!found.isEmpty()) {
					if (!found.contains(".")) {
						continue;
					}
					if (found.charAt(0) == '.') {
						continue;
					}
					if (patternMap.get(value) == null) {
						patternMap.put(value, Pattern.compile(Pattern.quote(value)));
					}
					component = component.replaceText(builder ->
							builder.match(patternMap.get(value)).replacement(Component.text(value)
									.clickEvent(ClickEvent.openUrl(value))
									.color(NamedTextColor.AQUA)
									.decorate(TextDecoration.UNDERLINED)));
				}
			}
		}
		return component;
	}

	public static Component formatLinks(Player player, @NotNull Component component, @NotNull BetterSurvival survival) {
		@RegExp String msg = survival.miniMessage.stripTags(survival.miniMessage.serialize(component));
		if (msg.contains(" ")) {
			String[] split = msg.split(" ");
			for (@RegExp String s : split) {
				component = tryFixLinks(component, s, survival);
			}
		} else {
			component = tryFixLinks(component, msg, survival);
		}
		return component;
	}


	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChatDecorate(AsyncChatDecorateEvent event) {
		event.result(formatAllItem(event.player(), event.result(), survival));
		event.result(formatItem(event.player(), event.result(), survival));
		event.result(formatLinks(event.player(), event.result(), survival));
	}
}