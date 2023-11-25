package net.muonalpha.caverse.tools;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;

public class TimingTool
{
	final private static ArrayList<IOPoint> inputProbes = new ArrayList<>();
	final private static  ArrayList<IOPoint> outputProbes = new ArrayList<>();

	private static class IOPoint
	{
		public BlockState blockState;
		public String name;

		public IOPoint(BlockState blockStateIn, String nameIn)
		{
			blockState = blockStateIn;
			name = nameIn;
		}

		private static boolean isSignalHigh(BlockState blockState)
		{
			int signalStrength = 0;
			boolean isPowered = false;
			boolean isLit = false;

			if(blockState.hasProperty(BlockStateProperties.POWER))
			{
				signalStrength = blockState.getValue(BlockStateProperties.POWER);
			}

			if(blockState.hasProperty(BlockStateProperties.POWERED))
			{
				isPowered = blockState.getValue(BlockStateProperties.POWERED);
			}

			if(blockState.hasProperty(BlockStateProperties.LIT))
			{
				isLit = blockState.getValue(BlockStateProperties.LIT);
			}

			return (signalStrength > 0) && isPowered && isLit;
		}
	}

	public static int registerProbe(boolean isInput, CommandSourceStack source, BlockState blockState, String uniqueName)
	{
		if(!blockState.hasProperty(BlockStateProperties.POWER)
				&& !blockState.hasProperty(BlockStateProperties.POWERED)
				&& !blockState.hasProperty(BlockStateProperties.LIT))
		{
			System.out.printf("[Cavers] <TimingTool#setProbePpoint> Block has no redstone property\n");
			return -1;
		}

		IOPoint newIOPoint = new IOPoint(blockState, uniqueName);

		if(isInput)
		{
			inputProbes.add(newIOPoint);
		}
		else
		{
			outputProbes.add(newIOPoint);
		}

		return 0;
	}

	public static int onRunCommand(CommandSourceStack source)
	{
		return 0;
	}

	public static int onStopCommand(CommandSourceStack source)
	{
		return 0;
	}

	public static int runManualAnalysis()
	{
		return -1;
	}

	public static int runAutomaticAnalysis()
	{
		return -1;
	}

}
