package net.muonalpha.caverse.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.muonalpha.caverse.CaversMode;
import net.muonalpha.caverse.blocks.CaversBlocks;

public class CaversCreativeModeTab
{
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CaversMode.MODID);

	public static final RegistryObject<CreativeModeTab> CAVERS_TAB = CREATIVE_MODE_TABS.register("cavers_tab", () -> CreativeModeTab.builder()
			.icon(() -> Items.COMMAND_BLOCK.getDefaultInstance())
			.title(Component.translatable("creativetab.cavers_tab"))
			.displayItems
			(
				(param, event) ->
				{
					event.accept(CaversBlocks.COMMAND_CENTRE_BLOCK.get());
				}
			)
			.build());

	public static void register(IEventBus eventBus)
	{
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
