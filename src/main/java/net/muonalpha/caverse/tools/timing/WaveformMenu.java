package net.muonalpha.caverse.tools.timing;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.muonalpha.caverse.menus.AllCaversMenuTypes;

public class WaveformMenu extends AbstractContainerMenu
{
	public WaveformMenu(int pContainerId, Inventory inventory)
	{
		super(AllCaversMenuTypes.WAVEFORM_MENU.get(), pContainerId);
	}

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex)
	{
		return new ItemStack(ItemStack.EMPTY.getItem());
	}

	@Override
	public boolean stillValid(Player pPlayer)
	{
		return true;
	}
}
