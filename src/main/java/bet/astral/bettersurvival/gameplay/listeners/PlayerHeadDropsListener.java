package bet.astral.bettersurvival.gameplay.listeners;

import bet.astral.bettersurvival.BetterSurvival;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class PlayerHeadDropsListener implements Listener {
	private final BetterSurvival survival;

	public PlayerHeadDropsListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	private void onDeath(PlayerDeathEvent event){
		EntityDamageEvent entityDamageEvent = event.getPlayer().getLastDamageCause();
		if (entityDamageEvent instanceof EntityDamageByEntityEvent entityDamageByEntityEvent){
			Entity entity = entityDamageByEntityEvent.getDamager();
			if (event instanceof Player attacker) {
				Player victim = event.getPlayer();
				ItemStack head = new ItemStack(Material.PLAYER_HEAD);
				SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

				PlayerProfile playerProfile = victim.getPlayerProfile();
				skullMeta.setPlayerProfile(playerProfile);
				skullMeta.displayName(victim.name().color(NamedTextColor.GOLD).append(Component.text("'s head").color(NamedTextColor.YELLOW)));
				Location location = ((Player) event).getLocation();
				skullMeta.lore(List.of(
						Component.text()
								.decoration(TextDecoration.ITALIC, false).build()
								.append(Component.text("Killed by ").color(NamedTextColor.RED))
								.append(attacker.name().color(NamedTextColor.RED)),

						Component.text()
								.decoration(TextDecoration.ITALIC, false).build()
								.append(Component.text("At: ").color(NamedTextColor.GOLD))
								.append(Component.text("x", NamedTextColor.WHITE).append(Component.text(location.getX()).color(NamedTextColor.YELLOW).append(Component.text(",").color(NamedTextColor.GRAY)))).appendSpace()
								.append(Component.text("y", NamedTextColor.WHITE).append(Component.text(location.getY()).color(NamedTextColor.YELLOW).append(Component.text(",").color(NamedTextColor.GRAY)))).appendSpace()
								.append(Component.text("z", NamedTextColor.WHITE).append(Component.text(location.getZ()).color(NamedTextColor.YELLOW).append(Component.text(",").color(NamedTextColor.GRAY)))).appendSpace()
								.append(Component.text(location.getWorld().getName()).color(NamedTextColor.WHITE)),

						Component.text()
								.decoration(TextDecoration.ITALIC, false).build()
								.append(Component.text("Date: ").color(NamedTextColor.YELLOW)
								.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true)))
						)
				);


				head.setItemMeta(skullMeta);
				location.getWorld().dropItemNaturally(location.add(0, 2, 0), head, (item)->{
					item.setGlowing(true);
					item.setCanPlayerPickup(true);
					item.setCanMobPickup(false);
					item.setPickupDelay(5);
					item.setWillAge(false);
					item.setThrower(victim.getUniqueId());
					item.setOwner(victim.getUniqueId());
				});
			}
		}
	}
}
