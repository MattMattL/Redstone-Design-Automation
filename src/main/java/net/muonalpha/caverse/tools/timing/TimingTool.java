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
	public static final ArrayList<Probe> inputProbes = new ArrayList<>();
	public static final ArrayList<Probe> outputProbes = new ArrayList<>();

	public static boolean isRunning = false;

	public static int onAddCommand(boolean isInput, CommandSourceStack source, BlockPos blockPos, String uniqueName)
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

	private static ArrayList<Boolean> getRandomSignalsForDebugging()
	{
		ArrayList<Boolean> randomSignals = new ArrayList<>();

		for(int signal=0; signal<100; signal++)
		{
			randomSignals.add(Math.random() < 0.5);
		}

		return randomSignals;
	}

	public static int onDebugCommand()
	{
		for(int i=0; i<5; i++)
		{
			inputProbes.add(new Probe(null, null, "input"));
			inputProbes.get(inputProbes.size() - 1).loggedSignals = getRandomSignalsForDebugging();
		}

		for(int o=0; o<5; o++)
		{
			outputProbes.add(new Probe(null, null, "output"));
			outputProbes.get(outputProbes.size() - 1).loggedSignals = getRandomSignalsForDebugging();
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
