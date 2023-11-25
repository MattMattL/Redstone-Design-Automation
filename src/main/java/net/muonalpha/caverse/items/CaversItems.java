package net.muonalpha.caverse.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.muonalpha.caverse.CaversMode;

public class CaversItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CaversMode.MODID);

	public static final RegistryObject<Item> COMMAND_CENTRE_BLOCK_ITEM = ITEMS.register("command_centre_block_item", () -> new Item(new Item.Properties()));

	public static void register(IEventBus eventBus)
	{
		ITEMS.register(eventBus);
	}
}
