package bet.astral.bettersurvival.gameplay.recipes;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class InvisibleGlowItemFrameCraftingRecipe implements Recipe, Listener {
	private final NamespacedKey key;
	private final String group;
	private final CraftingBookCategory craftingBookCategory;
	private final ItemStack result;

	public InvisibleGlowItemFrameCraftingRecipe(BetterSurvival survival) {
		this.result = new ItemStack(Material.ITEM_FRAME);
		this.key = survival.key("invisible_glow_item_frame");
		this.group = "invisible_glow_item_frame";
		this.craftingBookCategory = CraftingBookCategory.MISC;
		this.result.setAmount(8);
		this.result.editMeta(meta->{
			meta.setRarity(ItemRarity.EPIC);
			meta.setEnchantmentGlintOverride(true);
			meta.setCustomModelData(1500);
			meta.displayName(Component.text("Invisible Glow Item Frame").color(ItemRarity.EPIC.color()).decoration(TextDecoration.ITALIC, false));
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
		ShapelessRecipe recipe = new ShapelessRecipe(key, result);
		recipe.setGroup(group);
		recipe.setCategory(craftingBookCategory);
		recipe.addIngredient(Material.GLOW_ITEM_FRAME);
		recipe.addIngredient(Material.DIAMOND);
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
	public void onHang(@NotNull HangingPlaceEvent event){
		if (event.getItemStack() == null){
			return;
		}
		if (!isResultType(event.getItemStack())){
			return;
		}
		if (event.getEntity() instanceof GlowItemFrame glowItemFrame){
			glowItemFrame.setVisible(false);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onDestroy(@NotNull HangingBreakEvent event){
		if (event.getEntity() instanceof GlowItemFrame itemFrame){
			if (!itemFrame.isValid()){
				return;
			}
			if (itemFrame.isVisible()){
				event.setCancelled(true);
				event.getEntity().remove();
				event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), result);
			}

		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onDestroy(@NotNull HangingBreakByEntityEvent event){
		if (event.getEntity() instanceof GlowItemFrame itemFrame){
			if (!itemFrame.isValid()){
				return;
			}
			if (itemFrame.isVisible()){
				event.setCancelled(true);
				event.getEntity().remove();
				event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), result);
			}
		}
	}
}
