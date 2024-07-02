package bet.astral.bettersurvival.gameplay.listeners.world;

import bet.astral.bettersurvival.BetterSurvival;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Chicken;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ChickenVillagerListener implements Listener {
	private final BetterSurvival survival;

	public ChickenVillagerListener(BetterSurvival survival) {
		this.survival = survival;
	}

	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent event){
		if (!(event.getRightClicked() instanceof Chicken chicken)){
			return;
		}
		if (event.getRightClicked().customName() != null){
			if (PlainTextComponentSerializer.plainText().serialize(event.getRightClicked().customName()).equalsIgnoreCase("villager")){
				event.getPlayer().sendRichMessage("<rainbow:"+survival.random.nextInt(0, 10)+">Hewwo it's me totawwie wegit viwwagew");
			}
		}
	}
}
