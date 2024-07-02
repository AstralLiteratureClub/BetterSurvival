package bet.astral.bettersurvival.commands;

import bet.astral.bettersurvival.BetterSurvival;
import bet.astral.bettersurvival.format.ChatStylingListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.parser.standard.StringParser;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommandMessage extends Command<CommandSender> {
	private final Map<UUID, UUID> last = new HashMap<>();
	public CommandMessage(BetterSurvival survival, PaperCommandManager<CommandSender> commandManager) {
		super(survival, commandManager);
	}

	@Override
	public void register() {
		commandManager
				.command(
						commandManager
								.commandBuilder(
										"message",
										"msg",
										"whisper",
										"w"
								)
								.argument(PlayerParser.playerComponent().name("player"))
								.argument(StringParser.stringComponent(StringParser.StringMode.GREEDY).name("message"))
								.senderType(Player.class)
								.handler(context->{
									Player player = context.sender();
									Player to = context.get("player");
									String msg = context.get("message");
									last.put(player.getUniqueId(), to.getUniqueId());
									Component message = Component.text(msg).color(NamedTextColor.WHITE);
									message = ChatStylingListener.formatItem(to, message, survival);
									message = ChatStylingListener.formatAllItem(to, message, survival);
									message = ChatStylingListener.formatLinks(to, message, survival);
									player.sendMessage(
											Component.text().append(
													Component.text("To").color(NamedTextColor.YELLOW)
															.appendSpace()
															.append(to.name().color(NamedTextColor.YELLOW))
															.append(Component.text(":")).color(NamedTextColor.YELLOW)
															.appendSpace()
															.append(message.hoverEvent(
																	HoverEvent.showText(Component.text("Click here to copy this message."))
															).clickEvent(ClickEvent.copyToClipboard(PlainTextComponentSerializer.plainText().serialize(message))))
															.hoverEvent(
																	HoverEvent.showText(
																			Component.text().decoration(TextDecoration.ITALIC, false).
																					append(Component.text("Sent ").color(NamedTextColor.YELLOW)
																							.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true))
																							.appendNewline()
																							.append(Component.text("Click here to message ")
																									.color(NamedTextColor.GREEN).append(to.name()).color(NamedTextColor.GREEN))))
															)
															.clickEvent(ClickEvent.suggestCommand("/message " + to.getName()))));
									to.sendMessage(
											Component.text().append(
											Component.text("From").color(NamedTextColor.YELLOW)
													.appendSpace()
													.append(player.name().color(NamedTextColor.YELLOW))
													.append(Component.text(":")).color(NamedTextColor.YELLOW)
													.appendSpace()
													.append(message.hoverEvent(
																	HoverEvent.showText(Component.text("Click here to copy this message."))
															).clickEvent(ClickEvent.copyToClipboard(PlainTextComponentSerializer.plainText().serialize(message))))
													.hoverEvent(
															HoverEvent.showText(
																	Component.text().decoration(TextDecoration.ITALIC, false).
																			append(Component.text("Sent ").color(NamedTextColor.YELLOW)
																					.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true))
																					.appendNewline()
																					.append(Component.text("Click here to message ")
																							.color(NamedTextColor.GREEN).append(player.name()).color(NamedTextColor.GREEN))))
													)
													.clickEvent(ClickEvent.suggestCommand("/message " + player.getName()))));
								})
				);
		commandManager
				.command(
						commandManager
								.commandBuilder(
										"reply",
										"r"
								)
								.argument(StringParser.stringComponent(StringParser.StringMode.GREEDY).name("message"))
								.senderType(Player.class)
								.handler(context->{
									Player player = context.sender();
									if (last.get(player.getUniqueId())==null){
										player.sendMessage(Component.text("You do not have anyone to reply to!").color(NamedTextColor.RED));
										return;
									} else if (Bukkit.getPlayer(last.get(player.getUniqueId()))==null){
										player.sendMessage(Component.text(Bukkit.getOfflinePlayer(last.get(player.getUniqueId())).getName()+" is not online currently!").color(NamedTextColor.RED));
										return;
									}
									Player to = Bukkit.getPlayer(last.get(player.getUniqueId()));
									String msg = context.get("message");
									last.put(player.getUniqueId(), to.getUniqueId());
									Component message = Component.text(msg).color(NamedTextColor.WHITE);
									message = ChatStylingListener.formatItem(to, message, survival);
									message = ChatStylingListener.formatAllItem(to, message, survival);
									message = ChatStylingListener.formatLinks(to, message, survival);
									player.sendMessage(
											Component.text().append(
													Component.text("To").color(NamedTextColor.YELLOW)
															.appendSpace()
															.append(to.name().color(NamedTextColor.YELLOW))
															.append(Component.text(":")).color(NamedTextColor.YELLOW)
															.appendSpace()
															.append(message.hoverEvent(
																	HoverEvent.showText(Component.text("Click here to copy this message."))
															).clickEvent(ClickEvent.copyToClipboard(PlainTextComponentSerializer.plainText().serialize(message))))
															.hoverEvent(
																	HoverEvent.showText(
																			Component.text().decoration(TextDecoration.ITALIC, false).
																					append(Component.text("Sent ").color(NamedTextColor.YELLOW)
																							.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true))
																							.appendNewline()
																							.append(Component.text("Click here to message ")
																									.color(NamedTextColor.GREEN).append(to.name()).color(NamedTextColor.GREEN))))
															)
															.clickEvent(ClickEvent.suggestCommand("/message " + to.getName()))));
									to.sendMessage(
											Component.text().append(
													Component.text("From").color(NamedTextColor.YELLOW)
															.appendSpace()
															.append(player.name().color(NamedTextColor.YELLOW))
															.append(Component.text(":")).color(NamedTextColor.YELLOW)
															.appendSpace()
															.append(message.hoverEvent(
																	HoverEvent.showText(Component.text("Click here to copy this message."))
															).clickEvent(ClickEvent.copyToClipboard(PlainTextComponentSerializer.plainText().serialize(message))))
															.hoverEvent(
																	HoverEvent.showText(
																			Component.text().decoration(TextDecoration.ITALIC, false).
																					append(Component.text("Sent ").color(NamedTextColor.YELLOW)
																							.append(Component.text(survival.dateFormat.format(Date.from(Instant.now()))).color(NamedTextColor.YELLOW).decoration(TextDecoration.UNDERLINED, true))
																							.appendNewline()
																							.append(Component.text("Click here to message ")
																									.color(NamedTextColor.GREEN).append(player.name()).color(NamedTextColor.GREEN))))
															)
															.clickEvent(ClickEvent.suggestCommand("/message " + player.getName()))));
								})
				);
	}
}