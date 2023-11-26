package net.muonalpha.caverse.tools.timing;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;

public class WaveformScreen extends AbstractContainerScreen<WaveformMenu>
{
	public WaveformScreen(WaveformMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
		System.out.printf("WaveformScreen#WaveformScreen\n");
	}

	public static void renderGraphFrom(ArrayList<Probe> inputProbes, ArrayList<Probe> outputProbes)
	{

	}

	@Override
	public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
	{
		System.out.printf("WaveformScreen#render\n");
		this.renderBackground(pGuiGraphics);

		super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

//		pGuiGraphics.fill(RenderType.LINE_STRIP, 10, 10, 20, 20, 0x888888);
	}

	@Override
	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY)
	{
		System.out.printf("WaveformScreen#renderBg\n");
	}
}
