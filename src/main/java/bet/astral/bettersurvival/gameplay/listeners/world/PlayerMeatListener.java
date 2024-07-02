package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerMeatListener implements Listener {
	private final BetterSurvival survival;
	private final NamespacedKey key;

	public PlayerMeatListener(BetterSurvival survival) {
		this.survival = survival;
		key = survival.key("player_meat");
	}

	@EventHandler(ignoreCancelled = true)
	private void onDeath(PlayerDeathEvent event){
		if (survival.random.nextInt(0, 100)>96){
			ItemStack itemStack = new ItemStack(Material.MUTTON);
			itemStack.setAmount(survival.random.nextInt(1, 3));
			itemStack.editMeta(meta->{
				meta.displayName(event.getPlayer().name().color(ItemRarity.EPIC.color()).append(Component.text("'s meat").color(ItemRarity.EPIC.color())));
				meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
			});
			event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), itemStack);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		if (event.getItem().getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) {
			Player player = event.getPlayer();
			player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 70, 3));
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 70, 5));
		}
	}
}
