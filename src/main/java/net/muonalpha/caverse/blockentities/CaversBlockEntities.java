package net.muonalpha.caverse.blockentities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.muonalpha.caverse.CaversMode;
import net.muonalpha.caverse.blocks.CaversBlocks;

public class CaversBlockEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CaversMode.MODID);

	public static final RegistryObject<BlockEntityType<CommandCentreBlockEntity>> COMMAND_CENTRE_BLOCK_ENTITY = BLOCK_ENTITIES.register("command_centre_block_entity", () -> BlockEntityType.Builder.of(CommandCentreBlockEntity::new, CaversBlocks.COMMAND_CENTRE_BLOCK.get()).build(null));

	public static void register(IEventBus eventBus)
	{
		BLOCK_ENTITIES.register(eventBus);
	}
}
