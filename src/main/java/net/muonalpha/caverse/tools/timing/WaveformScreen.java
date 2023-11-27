package net.muonalpha.caverse.tools.timing;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.muonalpha.caverse.CaversMode;

import java.awt.*;
import java.util.ArrayList;

public class WaveformScreen extends AbstractContainerScreen<WaveformMenu>
{
	private static final ResourceLocation WAVEFORM_TEXTURE = new ResourceLocation(CaversMode.MODID, "textures/gui/waveform_2.png");

	public WaveformScreen(WaveformMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);

		this.titleLabelX = 10000;
		this.inventoryLabelY = 10000;
	}

	public static void renderGraphFrom(ArrayList<Probe> inputProbes, ArrayList<Probe> outputProbes)
	{

	}

	@Override
	public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
	{
		this.renderBackground(pGuiGraphics);

		super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
	}

	@Override
	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY)
	{
		int screenX = (this.width - this.imageWidth) / 2;
        int screenY = (this.height - this.imageHeight) / 2;

		pGuiGraphics.blit(WAVEFORM_TEXTURE, screenX, screenY, 0, 0, this.imageWidth, this.imageHeight);


		int tickCount = 0;
		int channelCount = 0;
		int baseX, baseY;

		final int channelHeight = 10;
		final int signalHeight = 5;

		for(Probe probe : TimingTool.inputProbes)
		{
			for(int i=0; i<probe.loggedSignals.size(); i++)
			{
				boolean isPowered = probe.loggedSignals.get(i);
				baseX = screenX + tickCount + 5;
				baseY = screenY + channelHeight * channelCount + 5;

				if(isPowered)
				{
					pGuiGraphics.blit(WAVEFORM_TEXTURE, baseX, baseY-signalHeight+1, 180, 0, 1, signalHeight);
				}
				else
				{
					pGuiGraphics.blit(WAVEFORM_TEXTURE, baseX, baseY, 180, 0, 1, 1);
				}

				tickCount++;
			}

			tickCount = 0;
			channelCount++;
		}

		for(Probe probe : TimingTool.outputProbes)
		{
			for(int i=0; i<probe.loggedSignals.size(); i++)
			{
				boolean isPowered = probe.loggedSignals.get(i);
				baseX = screenX + tickCount + 5;
				baseY = screenY + channelHeight * channelCount + 5;

				if(isPowered)
				{
					pGuiGraphics.blit(WAVEFORM_TEXTURE, baseX, baseY-signalHeight+1, 180, 0, 1, signalHeight);
				}
				else
				{
					pGuiGraphics.blit(WAVEFORM_TEXTURE, baseX, baseY, 180, 0, 1, 1);
				}

				tickCount++;
			}

			tickCount = 0;
			channelCount++;
		}
	}
}
