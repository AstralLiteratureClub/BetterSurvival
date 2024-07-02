package bet.astral.bettersurvival.commands;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.permission.PredicatePermission;

public class ArmorStandEditCommand extends Command<CommandSender> {
	public ArmorStandEditCommand(BetterSurvival survival, PaperCommandManager<CommandSender> commandManager) {
		super(survival, commandManager);
	}

	@Override
	public void register() {
		org.incendo.cloud.Command.Builder<Player> builder = commandManager
				.commandBuilder("armorstand")
				.senderType(Player.class)
				;
		commandManager.command(
				builder.literal("SHOW_ARMS")
						.handler(context->{
							Player player = context.sender();
							Entity entity = player.getTargetEntity(6);
							if (entity == null){
								player.sendRichMessage("<red>Please select armor stand which to change arm visibility of.");
								return;
							}
							if (!(entity instanceof ArmorStand armorStand)){
								player.sendRichMessage("<red>Please select armor stand which to change arm visibility of.");
								return;
							}
							armorStand.setArms(true);
						})
		);
		commandManager.command(
				builder.literal("HIDE_ARMS")
						.handler(context->{
							Player player = context.sender();
							Entity entity = player.getTargetEntity(6);
							if (entity == null){
								player.sendRichMessage("<red>Please select armor stand which to change arm visibility of.");
								return;
							}
							if (!(entity instanceof ArmorStand armorStand)){
								player.sendRichMessage("<red>Please select armor stand which to change arm visibility of.");
								return;
							}
							armorStand.setArms(false);
						})
		);
		commandManager.command(
				builder.literal("SHOW_BASE_PLATE")
						.handler(context->{
							Player player = context.sender();
							Entity entity = player.getTargetEntity(6);
							if (entity == null){
								player.sendRichMessage("<red>Please select armor stand which to change base plate visibility of.");
								return;
							}
							if (!(entity instanceof ArmorStand armorStand)){
								player.sendRichMessage("<red>Please select armor stand which to change base plate visibility of.");
								return;
							}
							armorStand.setBasePlate(true);
						})
		);
		commandManager.command(
				builder.literal("HIDE_BASE_PLATE")
						.handler(context->{
							Player player = context.sender();
							Entity entity = player.getTargetEntity(6);
							if (entity == null){
								player.sendRichMessage("<red>Please select armor stand which to change base plate visibility of.");
								return;
							}
							if (!(entity instanceof ArmorStand armorStand)){
								player.sendRichMessage("<red>Please select armor stand which to change base plate visibility of.");
								return;
							}
							armorStand.setBasePlate(false);
						})
		);
		commandManager.command(
				builder.literal("MAKE_ADULT_SIZE")
						.handler(context->{
							Player player = context.sender();
							Entity entity = player.getTargetEntity(6);
							if (entity == null){
								player.sendRichMessage("<red>Please select armor stand which to change size of.");
								return;
							}
							if (!(entity instanceof ArmorStand armorStand)){
								player.sendRichMessage("<red>Please select armor stand which to change size of.");
								return;
							}
							armorStand.setSmall(false);
						})
		);
		commandManager.command(
				builder.literal("MAKE_CHILD_SIZE", "MAKE_SONIA_SIZE")
						.handler(context->{
							Player player = context.sender();
							Entity entity = player.getTargetEntity(6);
							if (entity == null){
								player.sendRichMessage("<red>Please select armor stand which to change size of.");
								return;
							}
							if (!(entity instanceof ArmorStand armorStand)){
								player.sendRichMessage("<red>Please select armor stand which to change size of.");
								return;
							}
							armorStand.setSmall(true);
						})
		);
	}
}
