package net.muonalpha.caverse.tools.timing;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;

public class TimingTool
{
	private static final ArrayList<Probe> inputProbes = new ArrayList<>();
	private static final ArrayList<Probe> outputProbes = new ArrayList<>();

	public static boolean isRunning = false;

	public static int registerProbe(boolean isInput, CommandSourceStack source, BlockPos blockPos, String uniqueName)
	{
		BlockState blockState = source.getLevel().getBlockState(blockPos);

		if(!blockState.hasProperty(BlockStateProperties.POWER)
				&& !blockState.hasProperty(BlockStateProperties.POWERED)
				&& !blockState.hasProperty(BlockStateProperties.LIT))
		{
			System.out.printf("[Cavers] <TimingTool#registerProbe> Block has no redstone property\n");
			return -1;
		}

		if(isInput)
		{
			inputProbes.add(new Probe(source, blockPos, uniqueName));
		}
		else
		{
			outputProbes.add(new Probe(source, blockPos, uniqueName));
		}

		return 0;
	}

	public static int onRunCommand(CommandSourceStack source)
	{
		isRunning = true;

		return 0;
	}

	public static int onStopCommand(CommandSourceStack source)
	{
		isRunning = false;

		// test ->
		for(Probe probe : inputProbes)
		{
			System.out.printf("%10s ", probe.name);

			for(boolean signal : probe.loggedSignals)
				System.out.printf("%d ", signal? 1 : 0);

			System.out.printf("\n");
		}

		System.out.printf("\n");

		for(Probe probe : outputProbes)
		{
			System.out.printf("%10s ", probe.name);

			for(boolean signal : probe.loggedSignals)
				System.out.printf("%d ", signal? 1 : 0);

			System.out.printf("\n");
		}

		System.out.printf("\n");
		// <- test

		NetworkHooks.openScreen(source.getPlayer(), new SimpleMenuProvider(
				(pContainerId, pPlayerInventory, pPlayer) -> new WaveformMenu(pContainerId, pPlayer.getInventory()), Component.translatable("menu.title.cavers.waveform_menu")));

		return 0;
	}

	public static void tick()
	{
		System.out.printf("tick\n");

		for(Probe probe : inputProbes)
		{
			probe.saveCurrentSignal();
		}

		for(Probe probe : outputProbes)
		{
			probe.saveCurrentSignal();
		}
	}

	public static int runManualAnalysis()
	{
		return -1;
	}

	public static int runAutomaticAnalysis()
	{
		return -1;
	}

	public static int onClearCommand(CommandSourceStack source)
	{
		inputProbes.clear();
		outputProbes.clear();

		return 0;
	}

}
