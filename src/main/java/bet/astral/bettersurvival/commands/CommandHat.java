package bet.astral.bettersurvival.commands;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.incendo.cloud.paper.PaperCommandManager;

public class CommandHat extends Command<CommandSender> {

	public CommandHat(BetterSurvival survival, PaperCommandManager<CommandSender> commandManager) {
		super(survival, commandManager);
	}

	@Override
	public void register() {
		commandManager.command(
				commandManager.commandBuilder(
								"hat"
						)
						.senderType(Player.class)
						.handler(context -> {
							Player player = context.sender();
							PlayerInventory inventory = player.getInventory();
							if (inventory.getHelmet() != null && inventory.getHelmet().getEnchantmentLevel(Enchantment.BINDING_CURSE)>0){
								player.sendRichMessage("<red>Your hat is cursed! Oh no you can't use this to remove it!");
								return;
							}
							if (inventory.getItemInMainHand().isEmpty()) {
								player.sendRichMessage("<red>You cannot wear air on your head!");
								return;
							}
							ItemStack current = inventory.getHelmet();
							inventory.setHelmet(inventory.getItemInMainHand());
							inventory.setItemInMainHand(current);
							player.sendRichMessage("<yellow>Enjoy your new hat!");
						})
		);
	}
}