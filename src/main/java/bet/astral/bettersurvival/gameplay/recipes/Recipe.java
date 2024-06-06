package bet.astral.bettersurvival.gameplay.recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Recipe {
	NamespacedKey[] getRecipes();
	ItemStack getResult();
	@NotNull Recipe register();
	void unregister();
	default boolean alwaysGrant(){
		return true;
	}
}
