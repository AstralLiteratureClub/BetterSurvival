package bet.astral.bettersurvival.commands;

import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import org.incendo.cloud.permission.PermissionResult;
import org.incendo.cloud.permission.PredicatePermission;

public class OpPermission implements PredicatePermission<CommandSender> {
	@Override
	public @NonNull PermissionResult testPermission(@NonNull CommandSender sender) {
		return PermissionResult.of(sender.isOp(), this);
	}
}
