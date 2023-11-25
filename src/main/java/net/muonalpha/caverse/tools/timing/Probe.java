package net.muonalpha.caverse.tools.timing;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;

public class Probe
{
	private final CommandSourceStack source;
	private final BlockPos blockPos;
	public final String name;
	public ArrayList<Boolean> loggedSignals = new ArrayList<>();

	public Probe(CommandSourceStack sourceIn, BlockPos blockPosIn, String nameIn)
	{
		source = sourceIn;
		blockPos = blockPosIn;
		name = nameIn;
	}

	public void saveCurrentSignal()
	{
		BlockState blockState = source.getLevel().getBlockState(blockPos);
		boolean result = false;

		if(blockState.hasProperty(BlockStateProperties.POWER))
		{
			result = (0 < blockState.getValue(BlockStateProperties.POWER));
		}
		else if(blockState.hasProperty(BlockStateProperties.POWERED))
		{
			result = blockState.getValue(BlockStateProperties.POWERED);
		}
		else if(blockState.hasProperty(BlockStateProperties.LIT))
		{
			result = blockState.getValue(BlockStateProperties.LIT);
		}

		this.loggedSignals.add(result);
	}
}