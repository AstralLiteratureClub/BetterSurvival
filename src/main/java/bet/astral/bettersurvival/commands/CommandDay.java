package bet.astral.bettersurvival.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class CommandDay extends Command{
	private final DecimalFormat decimalFormat = new DecimalFormat(".x");
	public CommandDay() {
		super("day");
	}

	@Override
	public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
		if (commandSender instanceof Player player) {
			commandSender.sendMessage(Component.text("Current day is ").color(NamedTextColor.YELLOW).append(Component.text(decimalFormat.format((double) player.getWorld().getFullTime()/24000))));
		}
		return true;
	}
}
