package net.muonalpha.caverse.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;
import net.muonalpha.caverse.tools.timing.TimingTool;

public class TimingCommand
{
	public static LiteralArgumentBuilder<CommandSourceStack> wrapper()
	{
		return Commands.literal("timing").requires((source) -> { return source.hasPermission(2); })
				.then(Commands.literal("help").executes((source) -> { return helpCommandCallback(source.getSource()); }))
				.then(Commands.literal("debug").executes((source) -> { return debugCommandCallback(source.getSource()); }))
				.then(Commands.literal("clear").executes((source) -> { return clearCommandCallback(source.getSource()); }))
				.then(Commands.literal("run").executes((source) -> { return runCommandCallback(source.getSource()); }))
				.then(Commands.literal("end").executes((source) -> { return stopCommandCallback(source.getSource()); }))
				.then(Commands.literal("probes").executes((source) -> { return probesCommandCallback(source.getSource()); }))
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
				)
				.then(Commands.literal("save")
					.then(Commands.argument("str_run_id", ComponentArgument.textComponent()).executes((source) ->
					{
						return saveCommandCallback(source.getSource(), ComponentArgument.getComponent(source, "str_run_id"));
					}))
				)
				.then(Commands.literal("restore")
					.then(Commands.argument("str_run_id", ComponentArgument.textComponent()).executes((source) ->
					{
						return restoreCommandCallback(source.getSource(), ComponentArgument.getComponent(source, "str_run_id"));
					}))
				)
				.then(Commands.literal("history").executes((source) -> { return historyCommandCallback(source.getSource()); }));
	}

	private static int helpCommandCallback(CommandSourceStack source)
	{
		if(source.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK))
		{
			String helpPrompt =
				"/cavers timing help\n" +
				"/cavers timing add [ input | output ] <Vec3> <str_probe_name>\n" +
				"/cavers timing [ run | end ]\n" +
				"/cavers timing [ save | restore ] <str_run_name>\n" +
				"/cavers timing clear\n" +
				"/cavers timing history";

			// cavers timing save [ history | bundle ] <str_name>
			// cavers timing restore [ history | bundle ] <str_name>

			source.getPlayer().sendSystemMessage(Component.literal(helpPrompt));
		}

		return 1;
	}

	private static int debugCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onDebugCommand(source);
	}

	private static int addCommandCallback(boolean isInput, CommandSourceStack source, Coordinates coordinates, Component component)
	{
		return TimingTool.onAddCommand(isInput, source, coordinates.getBlockPos(source), component.getString());
	}

	private static int probesCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onProbesCommand(source);
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

	private static int saveCommandCallback(CommandSourceStack source, Component component)
	{
		return TimingTool.onSaveCommand(source, component.getString());
	}

	private static int historyCommandCallback(CommandSourceStack source)
	{
		return TimingTool.onHistoryCommand(source);
	}

	private static int restoreCommandCallback(CommandSourceStack source, Component component)
	{
		return TimingTool.onRestoreCommand(source, component.getString());
	}
}
