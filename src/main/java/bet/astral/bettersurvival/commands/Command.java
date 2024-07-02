package bet.astral.bettersurvival.commands;

import bet.astral.bettersurvival.BetterSurvival;
import org.incendo.cloud.paper.PaperCommandManager;

public abstract class Command<C> {
	protected final BetterSurvival survival;
	protected final PaperCommandManager<C> commandManager;
	public Command(BetterSurvival survival, PaperCommandManager<C> commandManager) {
		this.survival = survival;
		this.commandManager = commandManager;
	}

	public abstract void register();

}
