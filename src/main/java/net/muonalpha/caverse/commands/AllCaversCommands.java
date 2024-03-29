package net.muonalpha.caverse.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.muonalpha.caverse.CaversMode;

@Mod.EventBusSubscriber(modid = CaversMode.MODID)
public class AllCaversCommands
{
	private static CommandDispatcher dispatcher;

	@SubscribeEvent
	public static void onCommandsRegister(RegisterCommandsEvent event)
	{
		dispatcher = event.getDispatcher();

		dispatcher.register(
				Commands.literal("cavers").requires((player) -> { return player.hasPermission(2); })
				.then(TimingCommand.wrapper())
		);

		ConfigCommand.register(dispatcher);
	}
}