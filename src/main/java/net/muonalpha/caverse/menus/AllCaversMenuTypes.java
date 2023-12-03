package net.muonalpha.caverse.menus;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.muonalpha.caverse.CaversMode;
import net.muonalpha.caverse.tools.timing.WaveformMenu;

public class AllCaversMenuTypes
{
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, CaversMode.MODID);

//    public static final RegistryObject<MenuType<WaveformMenu>> WAVEFORM_MENU = registerMenuType("waveform_menu", WaveformMenu::new);
	public static final RegistryObject<MenuType<WaveformMenu>> WAVEFORM_MENU = MENUS.register("waveform_menu", () -> new MenuType(WaveformMenu::new, FeatureFlags.DEFAULT_FLAGS));

//    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory)
//	{
//        return MENUS.register(name, () -> IForgeMenuType.create(factory));
//    }

    public static void register(IEventBus eventBus)
	{
        MENUS.register(eventBus);
		System.out.printf("AlLCaversMenuTypes#register\n");
    }
}
