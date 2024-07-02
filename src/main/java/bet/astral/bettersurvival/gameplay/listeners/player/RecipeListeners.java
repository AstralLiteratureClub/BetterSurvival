package bet.astral.bettersurvival.gameplay.listeners.player;

import bet.astral.bettersurvival.BetterSurvival;
import bet.astral.bettersurvival.gameplay.recipes.Recipe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class RecipeListeners implements Listener {
	private final BetterSurvival survival;

	public RecipeListeners(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().undiscoverRecipes(survival.removedRecipes);
		for (Recipe recipe : survival.recipes) {
			if (recipe == null || !recipe.alwaysGrant()){
				continue;
			}
			for (NamespacedKey key : recipe.getRecipes()) {
				if (!event.getPlayer().hasDiscoveredRecipe(key)) {
					event.getPlayer().discoverRecipe(key);
					event.getPlayer().sendMessage(Component.text("You have discovered new recipe for ").
							color(NamedTextColor.YELLOW).append(recipe.getResult().getItemMeta().hasDisplayName() ?
									Objects.requireNonNull(recipe.getResult().getItemMeta().displayName()) :
									Component.translatable(recipe.getResult())).color(NamedTextColor.WHITE));
				}
			}
		}
	}
}
