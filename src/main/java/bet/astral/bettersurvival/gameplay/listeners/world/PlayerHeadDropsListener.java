package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class PlayerHeadDropsListener implements Listener {
	private final BetterSurvival survival;

	@Contract(pure = true)
	public PlayerHeadDropsListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	private void onDeath(@NotNull PlayerDeathEvent event){
		EntityDamageEvent entityDamageEvent = event.getPlayer().getLastDamageCause();
		if (entityDamageEvent instanceof EntityDamageByEntityEvent entityDamageByEntityEvent){
			Entity entity = entityDamageByEntityEvent.getDamager();
			if (entity instanceof Projectile projectile && ((Projectile) entity).getShooter() instanceof Entity){
				entity = (Entity) projectile.getShooter();
			}
			if (entity instanceof Player attacker) {
				Player victim = event.getPlayer();
				ItemStack head = new ItemStack(Material.PLAYER_HEAD);
				SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

				PlayerProfile playerProfile = victim.getPlayerProfile();
				skullMeta.setPlayerProfile(playerProfile);
				skullMeta.displayName(victim.name().decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD).append(Component.text("'s head").color(NamedTextColor.YELLOW)));
				Location location = victim.getLocation();
				skullMeta.lore(List.of(
						Component.text()
								.decoration(TextDecoration.ITALIC, false).build()
								.append(Component.text("Killed by ").color(NamedTextColor.RED))
								.append(attacker.name().color(NamedTextColor.WHITE)),
						Component.text()
								.decoration(TextDecoration.ITALIC, false).build()
								.append(Component.text("Date: ").color(NamedTextColor.YELLOW)
								.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.WHITE).decoration(TextDecoration.UNDERLINED, true)))
						)
				);

				head.setItemMeta(skullMeta);
				location.getWorld().dropItemNaturally(location.add(0, 2, 0), head, (item)->{
					item.setGlowing(true);
				});
			}
		}
	}
}
