package bet.astral.bettersurvival.gameplay.recipes.furnace;

import bet.astral.bettersurvival.BetterSurvival;
import bet.astral.bettersurvival.gameplay.recipes.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class RottenfleshLeatherFurnaceRecipe implements Recipe{
	private final NamespacedKey key;
	private final String group;
	private final CraftingBookCategory craftingBookCategory;
	private final ItemStack result;

	public RottenfleshLeatherFurnaceRecipe() {
		this.result = new ItemStack(Material.LEATHER);
		this.key = BetterSurvival.instance().key("leather");
		this.group = "leather";
		this.craftingBookCategory = CraftingBookCategory.MISC;
		this.result.setAmount(1);
	}

	@Override
	public NamespacedKey[] getRecipes() {
		return new NamespacedKey[]{key};
	}

	@Override
	public ItemStack getResult() {
		return result;
	}

	@Override
	public @NotNull Recipe register() {
		Bukkit.addRecipe(create(), true);
		return this;
	}
	org.bukkit.inventory.Recipe create(){
		return new FurnaceRecipe(key, result, Material.ROTTEN_FLESH, 0.01f, 200);
	}

	@Override
	public void unregister() {
		Bukkit.removeRecipe(key, true);
	}

	public boolean isResultType(@NotNull ItemStack itemStack){
		if (itemStack.getItemMeta() == null){
			return false;
		}
		return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false);
	}

}