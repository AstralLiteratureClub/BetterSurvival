package bet.astral.bettersurvival.format;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.intellij.lang.annotations.RegExp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LinkFixListener implements Listener {
	private final Map<String, Pattern> patternMap = new HashMap<>();
	private final BetterSurvival survival;

	public LinkFixListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	public void onChatFormat(AsyncChatDecorateEvent event){
		String msg = survival.miniMessage.stripTags(survival.miniMessage.serialize(event.result()));
		if (msg.contains(" ")){
			String[] split = msg.split(" ");
			for (String s : split){
				event.result(tryFixLinks(event.result(), s));
			}
		} else {
			event.result(tryFixLinks(event.result(), msg));
		}
	}

	private Component tryFixLinks(Component component, @RegExp String value){
		for (String v : survival.links){
			if (value.toLowerCase().startsWith(v.toLowerCase())){
				final String found = value.replaceFirst(v, "");
				if (!found.isEmpty()){
					if (!found.contains(".")){
						continue;
					}
					if (found.charAt(0) == '.'){
						continue;
					}
					if (patternMap.get(value) == null){
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
}
