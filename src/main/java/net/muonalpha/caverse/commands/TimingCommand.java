package net.muonalpha.caverse.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.muonalpha.caverse.tools.TimingTool;

public class TimingCommand
{
	public static LiteralArgumentBuilder<CommandSourceStack> wrapper()
	{
		return Commands.literal("timing").requires((source) -> { return source.hasPermission(2); })
				.then(Commands.literal("clear_io").executes((source) -> { return 0; }))
				.then(Commands.literal("run").executes((source) -> { return runCommandCallback(source.getSource()); }))
				.then(Commands.literal("stop").executes((source) -> { return stopCommandCallback(source.getSource()); }))
				.then(Commands.literal("set_io")
					.then(Commands.argument("signal_source", Vec3Argument.vec3())
						.then(Commands.literal("input")
							.then(Commands.argument("input_name", ComponentArgument.textComponent()).executes((source) ->
							{
								return registerProbeCallback(true, source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "input_name"));
							}))
						)
						.then(Commands.literal("output")
							.then(Commands.argument("output_name", ComponentArgument.textComponent()).executes((source) ->
							{
								return registerProbeCallback(false, source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "output_name"));
							}))
						)
					)
				);

		// cavers timing run [auto|manual]
		// cavers timing stop
		// cavers timing set_io [input|output] <string_name>
		// cavers timing clear_io
	}

	private static int registerProbeCallback(boolean isInput, CommandSourceStack source, Coordinates coordinates, Component component)
	{
		BlockPos blockPos = coordinates.getBlockPos(source);
		BlockState blockState = source.getLevel().getBlockState(blockPos);

		return TimingTool.registerProbe(isInput, source, blockState, component.getString());
	}

	private static int runCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onRunCommand(source);
	}

	private static int stopCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onStopCommand(source);
	}
}
