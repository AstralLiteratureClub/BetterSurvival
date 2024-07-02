package bet.astral.bettersurvival.gameplay.recipes.crafting;

import bet.astral.bettersurvival.BetterSurvival;
import bet.astral.bettersurvival.gameplay.recipes.Recipe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class KeypadCraftingRecipe implements Recipe, Listener {
	private final NamespacedKey key;
	private final String group;
	private final CraftingBookCategory craftingBookCategory;
	private final ItemStack result;

	public KeypadCraftingRecipe() {
		this.result = new ItemStack(Material.IRON_INGOT);
		this.key = BetterSurvival.instance().key("keypad");
		this.group = "keypad";
		this.craftingBookCategory = CraftingBookCategory.MISC;
		this.result.setAmount(1);
		this.result.editMeta(meta->{
			meta.setRarity(ItemRarity.COMMON);
			meta.setCustomModelData(10004);
			meta.setEnchantmentGlintOverride(true);
			meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
			meta.displayName(Component.text("Keypad").decoration(TextDecoration.ITALIC, false).color(ItemRarity.COMMON.color()));
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
		ShapelessRecipe recipe = new ShapelessRecipe(key, result);
		recipe.addIngredient(Material.IRON_BLOCK);
		recipe.addIngredient(Material.REDSTONE_BLOCK);
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

	@EventHandler
	private void onPlace(BlockPlaceEvent event){
		if (isResultType(event.getItemInHand())) {
			event.setCancelled(true);
		}
	}
}
