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
	public static final ArrayList<WaveformHistory> allHistories = new ArrayList<>();
	public static WaveformHistory ioProbes = new WaveformHistory();
	public static WaveformHistory waveformResult = new WaveformHistory();
	public static boolean isWaveformRunning = false;

	public static int onAddCommand(boolean isInput, CommandSourceStack source, BlockPos blockPos, String uniqueName)
	{
		if(ioProbes == null)
		{
			ioProbes = new WaveformHistory();
		}

		BlockState blockState = source.getLevel().getBlockState(blockPos);
		String blockName = blockState.getBlock().getName().getString();

		if(!blockState.hasProperty(BlockStateProperties.POWER)
				&& !blockState.hasProperty(BlockStateProperties.POWERED)
				&& !blockState.hasProperty(BlockStateProperties.LIT))
		{
			source.getPlayer().sendSystemMessage(Component.literal("Probe " + blockName + " has no redstone property"));
			return -1;
		}

		if(isInput)
		{
			source.getPlayer().sendSystemMessage(Component.literal("Input probe " + blockName + " added"));
			ioProbes.inputProbes.add(new Probe(source, blockPos, uniqueName));
		}
		else
		{
			source.getPlayer().sendSystemMessage(Component.literal("Output probe " + blockName + " added"));
			ioProbes.outputProbes.add(new Probe(source, blockPos, uniqueName));
		}

		return 1;
	}

	public static int onDebugCommand(CommandSourceStack source)
	{
		waveformResult = new WaveformHistory();
		waveformResult.runId = "debug_mode";

		for(int i=0; i<5; i++)
		{
			waveformResult.inputProbes.add(new Probe(null, null, "input"));
			waveformResult.inputProbes.get(waveformResult.inputProbes.size() - 1).loggedSignals = getRandomSignalsForDebugging();
		}

		for(int o=0; o<5; o++)
		{
			waveformResult.outputProbes.add(new Probe(null, null, "output"));
			waveformResult.outputProbes.get(waveformResult.outputProbes.size() - 1).loggedSignals = getRandomSignalsForDebugging();
		}

		promptWaveformMenu(source);

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

	public static int onRunCommand(CommandSourceStack source)
	{
		isWaveformRunning = true;
		source.getPlayer().sendSystemMessage(Component.literal("Running timing tool..."));

		return 0;
	}

	public static int onStopCommand(CommandSourceStack source)
	{
		isWaveformRunning = false;
		waveformResult = ioProbes;

		promptWaveformMenu(source);

		return 0;
	}

	private static void promptWaveformMenu(CommandSourceStack source)
	{
		NetworkHooks.openScreen(source.getPlayer(), new SimpleMenuProvider(
				(pContainerId, pPlayerInventory, pPlayer) -> new WaveformMenu(pContainerId, pPlayer.getInventory()), Component.translatable("menu.title.cavers.waveform_menu")));
	}

	public static void tick()
	{
		for(Probe probe : ioProbes.inputProbes)
		{
			probe.saveCurrentSignal();
		}

		for(Probe probe : ioProbes.outputProbes)
		{
			probe.saveCurrentSignal();
		}
	}

	public static int onClearCommand(CommandSourceStack source)
	{
		String clearMessage = String.format("%d input and %d output probes removed", ioProbes.inputProbes.size(), ioProbes.outputProbes.size());
		source.getPlayer().sendSystemMessage(Component.literal(clearMessage));

		ioProbes = null;
		waveformResult = null;

		return 0;
	}

	public static int onSaveCommand(CommandSourceStack source, String runId)
	{
		if(waveformResult == null)
		{
			source.getPlayer().sendSystemMessage(Component.literal("No logged redstone signals found"));
		}
		else
		{
			waveformResult.runId = runId;
			allHistories.add(waveformResult);

			waveformResult = null;
		}

		return 0;
	}

	public static int onHistoryCommand(CommandSourceStack source)
	{
		for(WaveformHistory history : allHistories)
		{
			source.getPlayer().sendSystemMessage(Component.literal(history.runId));
		}

		return 0;
	}

	public static int onRestoreCommand(CommandSourceStack source, String runId)
	{
		for(WaveformHistory history : allHistories)
		{
			if(history.runId.equals(runId))
			{
				waveformResult = history;
				break;
			}
		}

		promptWaveformMenu(source);

		return 0;
	}

	public static int onProbesCommand(CommandSourceStack source)
	{
		if(waveformResult == null)
		{
			source.getPlayer().sendSystemMessage(Component.literal("No probes found"));
			return 0;
		}

		for(Probe input : waveformResult.inputProbes)
		{
			source.getPlayer().sendSystemMessage(Component.literal("i: " + input.name));
		}

		for(Probe output : waveformResult.outputProbes)
		{
			source.getPlayer().sendSystemMessage(Component.literal("o: " + output.name));
		}

		return 0;
	}
}
