package bet.astral.bettersurvival.commands;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.bukkit.parser.OfflinePlayerParser;
import org.incendo.cloud.paper.PaperCommandManager;

public class AdminPlayerLocationLookupCommand extends AdminLockChestBreakCommand{
	public AdminPlayerLocationLookupCommand(BetterSurvival survival, PaperCommandManager<CommandSender> commandManager) {
		super(survival, commandManager);
		commandManager.command(
				commandManager
						.commandBuilder("player-location")
						.senderType(Player.class)
						.permission(new OpPermission())
						.required(OfflinePlayerParser.offlinePlayerComponent()
								.name("who"))
						.handler(context->{
							Player player = context.sender();
							OfflinePlayer offlinePlayer = context.get("who");
							Location l = offlinePlayer.getLocation();
							player.sendRichMessage("<yellow>Last known location: x"+l.getX() +", y"+l.getY()+", z"+l.getZ()+", "+ l.getWorld().getName());
						})
		);
	}
}
