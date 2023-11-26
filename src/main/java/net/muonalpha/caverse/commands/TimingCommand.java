package net.muonalpha.caverse.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.muonalpha.caverse.tools.timing.TimingTool;

public class TimingCommand
{
	public static LiteralArgumentBuilder<CommandSourceStack> wrapper()
	{
		return Commands.literal("timing").requires((source) -> { return source.hasPermission(2); })
				.then(Commands.literal("clear").executes((source) -> { return clearCommandCallback(source.getSource()); }))
				.then(Commands.literal("run").executes((source) -> { return runCommandCallback(source.getSource()); }))
				.then(Commands.literal("stop").executes((source) -> { return stopCommandCallback(source.getSource()); }))
				.then(Commands.literal("set")
					.then(Commands.argument("signal_source", Vec3Argument.vec3())
						.then(Commands.literal("input")
							.then(Commands.argument("input_name", ComponentArgument.textComponent()).executes((source) ->
							{
								return registerProbeCallback(true, source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "str_input_name"));
							}))
						)
						.then(Commands.literal("output")
							.then(Commands.argument("output_name", ComponentArgument.textComponent()).executes((source) ->
							{
								return registerProbeCallback(false, source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "str_output_name"));
							}))
						)
					)
				);

		// cavers timing set <Vec3f> [input|output] <str_probe_name>
		// cavers timing run <str_run_name> [auto|manual]
		// cavers timing stop
		// cavers timing clear
		// cavers timing history
		// cavers timing restore <str_run_name>
		// cavers timing help
	}

	private static int registerProbeCallback(boolean isInput, CommandSourceStack source, Coordinates coordinates, Component component)
	{
		BlockPos blockPos = coordinates.getBlockPos(source);

		return TimingTool.registerProbe(isInput, source, blockPos, component.getString());
	}

	private static int runCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onRunCommand(source);
	}

	private static int stopCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onStopCommand(source);
	}

	private static int clearCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onClearCommand(source);
	}
}
