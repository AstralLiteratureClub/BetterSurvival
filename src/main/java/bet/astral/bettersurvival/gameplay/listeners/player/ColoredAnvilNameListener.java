package bet.astral.bettersurvival.gameplay.listeners.player;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.meta.ItemMeta;
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
	@EventHandler
	public void onSmith(SmithItemEvent event){
		ItemStack itemStack = event.getCurrentItem();
		if (itemStack == null){
			return;
		}
		SmithingInventory inventory = event.getInventory();
		if (inventory.getInputEquipment() != null) {
			ItemMeta meta = inventory.getInputEquipment().getItemMeta();
			if (meta.hasDisplayName()) {
				itemStack.editMeta(m -> m.displayName(m.displayName()));
			}
		}
	}
}
