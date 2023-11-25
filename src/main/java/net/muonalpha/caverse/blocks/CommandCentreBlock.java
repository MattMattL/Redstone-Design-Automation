package net.muonalpha.caverse.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.muonalpha.caverse.blockentities.AllCaversBlockEntities;
import net.muonalpha.caverse.blockentities.CommandCentreBlockEntity;
import org.jetbrains.annotations.Nullable;

public class CommandCentreBlock extends BaseEntityBlock
{
	public CommandCentreBlock(Properties pProperties)
	{
		super(pProperties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
	{
		return new CommandCentreBlockEntity(pPos, pState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
	{
		if(pLevel.isClientSide())
		{
			return null;
		}

		return createTickerHelper(pBlockEntityType, AllCaversBlockEntities.COMMAND_CENTRE_BLOCK_ENTITY.get(), (level, blockPos, blockState, blockEntity) -> blockEntity.tick(level, blockPos, blockState));
	}
}
