package bet.astral.bettersurvival.commands;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Command extends org.bukkit.command.Command {
	public Command(@NotNull String name) {
		super(name);
	}

	public Command(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}

	public void register(JavaPlugin javaPlugin){
		javaPlugin.getServer().getCommandMap().registerAll(javaPlugin.getName(), List.of(this));
	}
}
