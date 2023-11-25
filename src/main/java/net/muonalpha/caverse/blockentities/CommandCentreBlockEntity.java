package net.muonalpha.caverse.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.muonalpha.caverse.tools.timing.TimingTool;
import org.jetbrains.annotations.Nullable;

public class CommandCentreBlockEntity extends BlockEntity implements MenuProvider
{
	public CommandCentreBlockEntity(BlockPos pPos, BlockState pBlockState)
	{
		super(AllCaversBlockEntities.COMMAND_CENTRE_BLOCK_ENTITY.get(), pPos, pBlockState);
	}

	@Override
	public Component getDisplayName()
	{
		return Component.translatable("command_centre_block_entity");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
	{
		return null;
	}

	public void tick(Level pLevel, BlockPos pPos, BlockState pState)
	{
		if(TimingTool.isRunning)
		{
			TimingTool.tick();
		}
	}
}
