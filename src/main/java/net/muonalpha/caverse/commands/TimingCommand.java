package net.muonalpha.caverse.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.muonalpha.caverse.tools.timing.TimingTool;

public class TimingCommand
{
	public static LiteralArgumentBuilder<CommandSourceStack> wrapper()
	{
		return Commands.literal("timing").requires((source) -> { return source.hasPermission(2); })
				.then(Commands.literal("debug").executes((source) -> { return debugCommandCallback(); }))
				.then(Commands.literal("clear").executes((source) -> { return clearCommandCallback(source.getSource()); }))
				.then(Commands.literal("run").executes((source) -> { return runCommandCallback(source.getSource()); }))
				.then(Commands.literal("end").executes((source) -> { return stopCommandCallback(source.getSource()); }))
				.then(Commands.literal("add")
					.then(Commands.literal("input")
						.then(Commands.argument("signal_source", Vec3Argument.vec3())
							.then(Commands.argument("str_input_name", ComponentArgument.textComponent()).executes((source) ->
							{
								return addCommandCallback(true, source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "str_input_name"));
							}))
						)
					)
					.then(Commands.literal("output")
						.then(Commands.argument("signal_source", Vec3Argument.vec3())
							.then(Commands.argument("str_output_name", ComponentArgument.textComponent()).executes((source) ->
							{
								return addCommandCallback(false, source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "str_output_name"));
							}))
						)
					)
				);

		// cavers timing add [input|output] <Vec3> <str_probe_name>
		// cavers timing clear
		// cavers timing run <str_run_name> [auto|manual]
		// cavers timing end
		// cavers timing history
		// cavers timing restore <str_run_name>
		// cavers timing help
	}

	private static int debugCommandCallback()
	{
		return TimingTool.onDebugCommand();
	}

	private static int addCommandCallback(boolean isInput, CommandSourceStack source, Coordinates coordinates, Component component)
	{
		return TimingTool.onAddCommand(isInput, source, coordinates.getBlockPos(source), component.getString());
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
