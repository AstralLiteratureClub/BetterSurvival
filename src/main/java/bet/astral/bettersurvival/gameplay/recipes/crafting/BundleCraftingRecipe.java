package bet.astral.bettersurvival.gameplay.recipes.crafting;

import bet.astral.bettersurvival.BetterSurvival;
import bet.astral.bettersurvival.gameplay.recipes.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BundleCraftingRecipe implements Recipe {
	private final NamespacedKey key;
	private final String group;
	private final CraftingBookCategory craftingBookCategory;
	private final ItemStack result;

	public BundleCraftingRecipe() {
		this.result = new ItemStack(Material.BUNDLE);
		this.key = BetterSurvival.instance().key("bundle");
		this.group = "bundle";
		this.craftingBookCategory = CraftingBookCategory.MISC;
		this.result.setAmount(1);
		this.result.editMeta(meta->{
			meta.setRarity(ItemRarity.COMMON);
			meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
		});
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
		ShapedRecipe recipe = new ShapedRecipe(key, result);
		recipe.shape("xyx", "y y", "yyy");
		recipe.setIngredient('x', Material.STRING);
		recipe.setIngredient('y', Material.RABBIT_HIDE);
		recipe.setGroup(group);
		recipe.setCategory(craftingBookCategory);
		return recipe;
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
