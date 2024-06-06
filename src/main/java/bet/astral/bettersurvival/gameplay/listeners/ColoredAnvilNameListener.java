package bet.astral.bettersurvival.gameplay.listeners;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;

public class ColoredAnvilNameListener implements Listener {
	private final BetterSurvival survival;

	@Contract(pure = true)
	public ColoredAnvilNameListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	private void onAnvil(PrepareAnvilEvent event){
		AnvilInventory anvilInventory = event.getInventory();
		if (anvilInventory.getRenameText() != null
				&& !anvilInventory.getRenameText().isEmpty()
				&& !anvilInventory.getRenameText().replaceAll(" ", "").isEmpty()){
			ItemStack result = event.getResult();
			if (result == null){
				return;
			}
			result.editMeta(meta->{
				meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(anvilInventory.getRenameText()).decoration(TextDecoration.ITALIC, false));
			});
			event.setResult(result);
		}
	}
}
