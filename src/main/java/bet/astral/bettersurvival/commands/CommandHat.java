package bet.astral.bettersurvival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class CommandHat extends Command {
	public CommandHat() {
		super("hat");
	}

	@Override
	public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
		if (commandSender instanceof Player player) {
			PlayerInventory inventory = player.getInventory();
			if (inventory.getItemInMainHand().isEmpty()){
				player.sendRichMessage("<red>You cannot wear air on your head!");
				return true;
			}
			ItemStack current = inventory.getHelmet();
			inventory.setHelmet(inventory.getItemInMainHand());
			inventory.setItemInMainHand(current);
			player.sendRichMessage("<yellow>Enjoy your new hat!");
		} else {
			commandSender.sendRichMessage("<red>You cannot use this command!");
		}
		return true;
	}
}
