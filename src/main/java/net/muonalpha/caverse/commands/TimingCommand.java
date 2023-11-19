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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TimingCommand
{
	public static LiteralArgumentBuilder<CommandSourceStack> wrapper()
	{
		return Commands.literal("timing").requires((source) -> { return source.hasPermission(2); })
				.then(Commands.literal("reset_io").executes((source) -> { return 0; }))
				.then(Commands.literal("run").executes((source) -> { return 0; }))
				.then(Commands.literal("stop").executes((source) -> { return 0; }))
				.then(Commands.literal("set_io")
					.then(Commands.argument("signal_source", Vec3Argument.vec3())
						.then(Commands.literal("input")
							.then(Commands.argument("input_name", ComponentArgument.textComponent()).executes((source) -> { return printArgument(source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "input_name")); }))
						)
						.then(Commands.literal("output")
							.then(Commands.argument("output_name", ComponentArgument.textComponent()).executes((source) -> { return printArgument(source.getSource(), Vec3Argument.getCoordinates(source, "signal_source"), ComponentArgument.getComponent(source, "output_name")); }))
						)
					)
				);

		// cavers timing [run|stop]
		// cavers timing set [input|output] <string_name>
	}

	private static int printArgument(CommandSourceStack source, Coordinates coordinates, Component component)
	{
		String textArgument = component.getString();
		System.out.println(textArgument);

		BlockPos blockPos = coordinates.getBlockPos(source);
		System.out.printf("(%d, %d, %d)\n", blockPos.getX(), blockPos.getY(), blockPos.getZ());

		BlockState blockState = source.getLevel().getBlockState(blockPos);

		int signalStrength = 0;
		boolean isPowered = false;
		boolean isLit = false;

		if(blockState.hasProperty(BlockStateProperties.POWER))
		{
			signalStrength = blockState.getValue(BlockStateProperties.POWER);
		}

		if(!blockState.hasProperty(BlockStateProperties.POWERED))
		{
			isPowered = blockState.getValue(BlockStateProperties.POWERED);
		}

		if(!blockState.hasProperty(BlockStateProperties.LIT))
		{
			isLit = blockState.getValue(BlockStateProperties.LIT);
		}

		boolean isSignalHigh = (signalStrength > 0) && isPowered && isLit;

		return 0;
	}
}
