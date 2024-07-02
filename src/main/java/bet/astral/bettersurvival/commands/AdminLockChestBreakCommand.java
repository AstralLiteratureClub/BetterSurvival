package bet.astral.bettersurvival.commands;

import bet.astral.bettersurvival.BetterSurvival;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.paper.PaperCommandManager;

public class AdminLockChestBreakCommand extends Command<CommandSender> {
	public AdminLockChestBreakCommand(BetterSurvival survival, PaperCommandManager<CommandSender> commandManager) {
		super(survival, commandManager);
	}

	@Override
	public void register() {
		commandManager.command(
				commandManager
						.commandBuilder("see-locked")
						.senderType(Player.class)
						.permission(new OpPermission())
						.handler(context->{
							Bukkit.getScheduler().runTask(survival, ()->{
								Player player = context.sender();
								Block block = player.getTargetBlockExact(6, FluidCollisionMode.NEVER);
								if (block == null){
									return;
								}
								player.sendMessage(block.getType().name());
								if (block.getType()== Material.BARREL){
									Barrel barrel = (Barrel) block.getState();
									if (barrel.isLocked()){
										player.sendMessage("Locked barrel");
										player.sendRichMessage("Password: <yellow>%password%".replace("%password%", barrel.getLock()));
									} else {
										player.sendMessage("Not Locked");
									}
								}
							});
						})
		);
		commandManager.command(
				commandManager
						.commandBuilder("break-locked")
						.senderType(Player.class)
						.permission(new OpPermission())
						.handler(context->{
							Bukkit.getScheduler().runTask(survival, ()-> {
								Player player = context.sender();
								Block block = player.getTargetBlockExact(6, FluidCollisionMode.NEVER);
								if (block == null) {
									return;
								}
								if (block.getType() == Material.BARREL) {
									Barrel barrel = (Barrel) block.getState();
									for (ItemStack itemStack : barrel.getDrops(player.getInventory().getItemInMainHand(), player)) {
										if (itemStack == null){
											return;
										}
										barrel.getLocation().getWorld().dropItemNaturally(barrel.getLocation(), itemStack);
									}
									block.setType(Material.AIR);
								}
							});
						})
		);
	}
}
