package bet.astral.bettersurvival.gameplay.recipes.crafting;

import bet.astral.bettersurvival.BetterSurvival;
import io.papermc.paper.event.block.BlockLockCheckEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataType;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LockableBarrelCraftingRecipe implements bet.astral.bettersurvival.gameplay.recipes.Recipe, Listener {
	private final Map<UUID, Barrel> addPlayerMap = new HashMap<>();
	public final NamespacedKey key;
	private final NamespacedKey key2;
	private final String group;
	private final CraftingBookCategory craftingBookCategory;
	private final ItemStack result;

	public LockableBarrelCraftingRecipe() {
		this.result = new ItemStack(Material.BARREL);
		this.key = BetterSurvival.instance().key("locked_chest");
		this.key2 = BetterSurvival.instance().key("locked_chest_2");
		this.group = "locked_chest";
		this.craftingBookCategory = CraftingBookCategory.MISC;
		this.result.setAmount(1);
		this.result.editMeta(meta -> {
			meta.setRarity(ItemRarity.COMMON);
			meta.setCustomModelData(10003);
			meta.setEnchantmentGlintOverride(true);
			meta.displayName(Component.text("Locked Barrel").color(ItemRarity.COMMON.color()).decoration(TextDecoration.ITALIC, false));
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
	public @NotNull bet.astral.bettersurvival.gameplay.recipes.Recipe register() {
		Bukkit.addRecipe(create(), true);
		Bukkit.addRecipe(create2(), true);
		return this;
	}


	org.bukkit.inventory.Recipe create2(){
		ShapelessRecipe recipe = new ShapelessRecipe(key2, result);
		recipe.addIngredient(Material.BARREL);
		recipe.addIngredient(Material.IRON_BLOCK);
		recipe.setCategory(craftingBookCategory);
		recipe.setGroup(group);
		return recipe;
	}
	org.bukkit.inventory.Recipe create() {
		ShapedRecipe recipe = new ShapedRecipe(key, result);
		recipe.shape("xyx", "xzx", "xyx");
		RecipeChoice planks = new RecipeChoice.MaterialChoice(Material.OAK_PLANKS,
				Material.SPRUCE_PLANKS,
				Material.BIRCH_PLANKS,
				Material.JUNGLE_PLANKS,
				Material.ACACIA_PLANKS,
				Material.CHERRY_PLANKS,
				Material.DARK_OAK_PLANKS,
				Material.MANGROVE_PLANKS,
				Material.BAMBOO_PLANKS,
				Material.CRIMSON_PLANKS,
				Material.WARPED_PLANKS
		);
		RecipeChoice slabs = new RecipeChoice.MaterialChoice(Material.OAK_SLAB,
				Material.SPRUCE_SLAB,
				Material.BIRCH_SLAB,
				Material.JUNGLE_SLAB,
				Material.ACACIA_SLAB,
				Material.CHERRY_SLAB,
				Material.DARK_OAK_SLAB,
				Material.MANGROVE_SLAB,
				Material.BAMBOO_SLAB,
				Material.CRIMSON_SLAB,
				Material.WARPED_SLAB
		);
		recipe.setIngredient('x', planks);
		recipe.setIngredient('y', slabs);
		recipe.setIngredient('z', Material.IRON_BLOCK);

		recipe.setGroup(group);
		recipe.setCategory(craftingBookCategory);
		return recipe;
	}

	@Override
	public void unregister() {
		Bukkit.removeRecipe(key, true);
		Bukkit.removeRecipe(key2, true);
	}

	public boolean isResultType(@NotNull ItemStack itemStack) {
		if (itemStack.getItemMeta() == null) {
			return false;
		}
		return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false);
	}

	public boolean canEdit(@NotNull Location location, @NotNull Player player){
		Barrel barrel = (Barrel) location.getBlock().getState();
		String lock = barrel.getLock();
		if (lock.contains(",")){
			String[] split = lock.split(",");
			for (String val : split) {
				if (val.equalsIgnoreCase(player.getUniqueId().toString())){
					return true;
				}
			}
		} else {
			return lock.equalsIgnoreCase(player.getUniqueId().toString());
		}
		return false;
	}

	@EventHandler(ignoreCancelled = true)
	private void onOpen(BlockLockCheckEvent event) {
		BlockState blockState = event.getBlockState();
		if (blockState instanceof Barrel barrel) {
			if (barrel.getLock().isEmpty()){
				return;
			}
			if (!canEdit(barrel.getLocation(), event.getPlayer())) {
				event.setResult(Event.Result.DENY);
				event.setLockedMessage(Component.text("You cannot open this container!").color(NamedTextColor.RED));
			} else {
				event.setResult(Event.Result.ALLOW);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onMine(@NotNull BlockBreakEvent event) {
		if (event.getBlock().getType() != Material.BARREL) {
			return;
		}
		Barrel barrel = (Barrel) event.getBlock().getState();
		if (barrel.getLock().isEmpty()){
			return;
		}
		if (!canEdit(event.getBlock().getLocation(), event.getPlayer())){
			event.setCancelled(true);
			return;
		}
		if (!Arrays.stream(barrel.getInventory().getStorageContents()).filter(item -> item != null && !item.isEmpty()).toList().isEmpty()){
			for (ItemStack itemStack : Arrays.stream(barrel.getInventory().getStorageContents()).filter(item->item != null && !item.isEmpty()).toList()){
				drop(event.getBlock().getLocation(), itemStack);
			}
		}
		barrel.getInventory().close();
		event.setDropItems(false);
		drop(event.getBlock().getLocation(), result);
		event.getBlock().setType(Material.AIR);
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlace(BlockPlaceEvent event) {
		ItemStack itemStack = event.getItemInHand();
		if (itemStack.getType() != Material.BARREL) {
			return;
		}
		if (!itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) {
			return;
		}
		Location location = event.getBlock().getLocation();
		Block block = location.getBlock();
		Barrel barrel = (Barrel) block.getState();
		barrel.setLock(event.getPlayer().getUniqueId().toString());
		barrel.update(true, true);
	}

	@EventHandler(ignoreCancelled = true)
	private void onNewPlayerAddBegin(PlayerInteractEvent event){
		if (event.getHand() != EquipmentSlot.HAND){
			return;
		}
		if (event.getClickedBlock() != null){
			if (!event.getPlayer().isSneaking()){
				return;
			}
			if (event.getItem() != null && !event.getItem().isEmpty()){
				return;
			}
			Block block = event.getClickedBlock();
			Material material = block.getType();
			if (material == Material.BARREL){
				Barrel barrel = (Barrel) block.getState();
				if (!barrel.isLocked()){
					return;
				}
				String lock = barrel.getLock();
				Player player = event.getPlayer();
				if (!canEdit(barrel.getLocation(), player)){
					return;
				}
				if (lock.contains(",")){
					if (!lock.split(",")[0].equalsIgnoreCase(player.getUniqueId().toString())){
						return;
					}
				}
				if (addPlayerMap.get(player.getUniqueId()) != null && addPlayerMap.get(player.getUniqueId()).getLocation().distanceSquared(block.getLocation())==0){
					return;
				}
				addPlayerMap.put(player.getUniqueId(), barrel);
				player.sendRichMessage("<gray>Send the name of the player you want to add to this container. <red>To cancel send <bold>CANCEL<!bold>.");
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	private void onNewPlayerAddClose(AsyncChatEvent event){
		if (!addPlayerMap.containsKey(event.getPlayer().getUniqueId())){
			return;
		}
		Bukkit.getScheduler().runTask(BetterSurvival.instance(), ()->{
			Barrel barrel = addPlayerMap.get(event.getPlayer().getUniqueId());
			Block block = barrel.getLocation().getBlock();
			if (block.getType()==Material.BARREL){
				barrel = (Barrel) block.getState();
			} else {
				addPlayerMap.remove(event.getPlayer().getUniqueId());
				return;
			}
			if (!barrel.isLocked()){
				addPlayerMap.remove(event.getPlayer().getUniqueId());
				return;
			}

			Barrel finalBarrel = barrel;
			Bukkit.getAsyncScheduler().runNow(
					BetterSurvival.instance(),
					(task)->{
						String contents = PlainTextComponentSerializer.plainText().serialize(event.message());
						if (contents.equalsIgnoreCase("cancel")){
							addPlayerMap.remove(event.getPlayer().getUniqueId());
							event.getPlayer().sendRichMessage("<dark_green>Canceled a new player from being added to the container.");
							event.setCancelled(true);
							return;
						}
						OfflinePlayer player = Bukkit.getOfflinePlayer(contents);
						if (!player.hasPlayedBefore() && !player.isOnline()){
							event.setCancelled(true);
							event.getPlayer().sendRichMessage("<red>Couldn't add <white>"+contents+"<red> to locked container as they have never played before!");
							return;
						}
						if (player.getUniqueId().toString().equalsIgnoreCase(event.getPlayer().getUniqueId().toString())){
							event.setCancelled(true);
							event.getPlayer().sendRichMessage("<red>You cannot add yourself to the container.");
							return;
						}
						if (finalBarrel.getLock().contains(player.getUniqueId().toString())){
							event.setCancelled(true);
							event.getPlayer().sendRichMessage("<red>You cannot add a player which is already added to this container.");
							return;
						}
						Bukkit.getScheduler().runTask(BetterSurvival.instance(), ()->{
							addPlayerMap.remove(player.getUniqueId());
							finalBarrel.setLock(finalBarrel.getLock()+","+ player.getUniqueId());
							finalBarrel.update(true, true);
							event.getPlayer().sendRichMessage("<green>Added <white>"+player.getName()+"<green> to the container!");
							event.setCancelled(true);
						});
					}
			);
		});
	}
	@EventHandler
	private void onJoin(PlayerQuitEvent event){
		addPlayerMap.remove(event.getPlayer().getUniqueId());
	}
}

















