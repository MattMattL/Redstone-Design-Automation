package net.muonalpha.caverse.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.muonalpha.caverse.CaversMode;
import net.muonalpha.caverse.items.AllCaversItems;

import java.util.function.Supplier;

public class AllCaversBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CaversMode.MODID);

	public static final RegistryObject<Block> COMMAND_CENTRE_BLOCK = registerBlock("command_centre_block", () -> new CommandCentreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
	{
		RegistryObject<T> registry = BLOCKS.register(name, block);
		registerBlockItem(name, registry);

		return registry;
	}

	private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
	{
		return AllCaversItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus)
	{
		BLOCKS.register(eventBus);
	}
}
