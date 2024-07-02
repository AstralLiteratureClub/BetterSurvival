package bet.astral.bettersurvival.gameplay.recipes;

import bet.astral.bettersurvival.gameplay.recipes.crafting.*;
import bet.astral.bettersurvival.gameplay.recipes.furnace.RottenfleshLeatherFurnaceRecipe;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public interface Recipe {
	BundleCraftingRecipe BUNDLE_CR = new BundleCraftingRecipe();
	InvisibleGlowItemFrameCraftingRecipe INVISIBLE_GLOWING_ITEM_FRAME = new InvisibleGlowItemFrameCraftingRecipe();
	InvisibleItemFrameCraftingRecipe INVISIBLE_ITEM_FRAME = new InvisibleItemFrameCraftingRecipe();
	LockableBarrelCraftingRecipe LOCKABLE_BARREL = new LockableBarrelCraftingRecipe();
	KeypadCraftingRecipe KEYPAD = new KeypadCraftingRecipe();
//	BundleCraftingRecipe KEYPAD_LOCKABLE_BARREL = new BundleCraftingRecipe();
	RottenfleshLeatherFurnaceRecipe LEATHER = new RottenfleshLeatherFurnaceRecipe();

	NamespacedKey[] getRecipes();
	ItemStack getResult();
	@NotNull Recipe register();
	void unregister();
	default boolean alwaysGrant(){
		return true;
	}

	default void drop(@NotNull Location location, @NotNull ItemStack itemStack){
		location = location.toCenterLocation();
		location.getWorld().dropItem(location, itemStack);
	}

	static <P, C> void set(@NotNull Location location, @NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type, C value){
		Block block = location.getBlock();
		BlockState blockState = block.getState();
		if (blockState instanceof TileState tileState){
			tileState.getPersistentDataContainer().set(key, type, value);
			tileState.update(true, true);
		}
	}
	@NotNull
	static <P, C> C get(@NotNull Location location, @NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type, C defaultValue){
		Block block = location.getBlock();
		BlockState blockState = block.getState();
		if (blockState instanceof TileState tileState){
			return tileState.getPersistentDataContainer().getOrDefault(key, type, defaultValue);
		}
		return defaultValue;
	}
	static <P, C> void unset(@NotNull Location location, @NotNull NamespacedKey key){
		Block block = location.getBlock();
		BlockState blockState = block.getState();
		if (blockState instanceof TileState tileState){
			tileState.getPersistentDataContainer().remove(key);
			tileState.update(true, true);
		}
	}
}
