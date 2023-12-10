package net.muonalpha.caverse.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.muonalpha.caverse.tools.timing.TimingTool;
import net.muonalpha.caverse.tools.timing.WaveformMenu;
import org.jetbrains.annotations.Nullable;

public class CommandCentreBlockEntity extends BlockEntity
{
	private int gameTick = 0;

	public CommandCentreBlockEntity(BlockPos pPos, BlockState pBlockState)
	{
		super(AllCaversBlockEntities.COMMAND_CENTRE_BLOCK_ENTITY.get(), pPos, pBlockState);
	}

	public void tick(Level pLevel, BlockPos pPos, BlockState pState)
	{
		if(TimingTool.isRunning)
		{
			if(this.gameTick % 2 == 0) // run every redstone tick
			{
				TimingTool.tick();

				if(this.gameTick > 12000) // reset every 10 minutes
				{
					this.gameTick = 0;
				}
			}

			this.gameTick++;
		}
	}
}
