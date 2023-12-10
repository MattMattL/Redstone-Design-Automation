package net.muonalpha.caverse.tools.timing;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.muonalpha.caverse.CaversMode;

import java.util.ArrayList;

public class WaveformScreen extends AbstractContainerScreen<WaveformMenu>
{
	private static final ResourceLocation WAVEFORM_TEXTURE = new ResourceLocation(CaversMode.MODID, "textures/gui/waveform_gui_texture.png");
	private static final ArrayList<Probe> allProbes = new ArrayList<>();

	private int MARGIN;

	private int CHANNEL_HEIGHT;
	private int CHANNEL_WIDTH;
	private int CHANNELS_LEFT;
	private int CHANNELS_RIGHT;
	private int CHANNELS_TOP;
	private int CHANNELS_BOTTOM;

	private int HIGH_WAVE_HEIGHT;
	private int LOW_WAVE_HEIGHT;
	private int WAVE_BOTTOM_MARGIN;
	private int WIDTH_PER_SIGNAL;

	private final float DRAG_Y_MULTIPLIER = 1;
	private final float SCROLL_MULTIPLIER = 2;
	private int BLANK_SCROLL_AREA_LIMIT = 0;

	private double scrollAmountX = 0;
	private double dragAmountX = 0;
	private double dragAmountY = 0;

	public WaveformScreen(WaveformMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);

		this.imageWidth = 240;
		this.imageHeight = 166;
	}

	@Override
	protected void init()
	{
		this.leftPos = (this.width - this.imageWidth) / 2;
		this.topPos = (this.height - this.imageHeight) / 2;

		this.MARGIN = 6;
		this.CHANNELS_TOP = this.topPos + this.MARGIN;
		this.CHANNELS_BOTTOM = this.CHANNELS_TOP + 6 * this.CHANNEL_HEIGHT;
		this.CHANNEL_HEIGHT = 26;
		this.CHANNEL_WIDTH = this.imageWidth - 2 * this.MARGIN;
		this.CHANNELS_LEFT = this.leftPos + this.MARGIN;
		this.CHANNELS_RIGHT = this.CHANNELS_LEFT + CHANNEL_WIDTH;

		this.HIGH_WAVE_HEIGHT = 10;
		this.LOW_WAVE_HEIGHT = 2;
		this.WAVE_BOTTOM_MARGIN = 5;
		this.WIDTH_PER_SIGNAL = 5;

		this.BLANK_SCROLL_AREA_LIMIT = CHANNEL_WIDTH / 2;

		this.titleLabelX = 10000; // TODO: change to rendering blank strings " "
		this.inventoryLabelY = 10000;

		this.allProbes.clear();
		this.allProbes.addAll(TimingTool.inputProbes);
		this.allProbes.addAll(TimingTool.outputProbes);
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
		pGuiGraphics.blit(WAVEFORM_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		if(this.allProbes.isEmpty())
		{
			return;
		}

		int columnNumber = 0;
		int firstChannelNumber = (int)(this.dragAmountY / CHANNEL_HEIGHT);

		for(int i=firstChannelNumber; i<this.allProbes.size() && columnNumber < 6; i++)
		{
			Probe channel = this.allProbes.get(i);

			this.renderMousePosition(pGuiGraphics, pMouseX, pMouseY, columnNumber);
			this.renderChannelSignals(pGuiGraphics, channel.loggedSignals, columnNumber);
			this.renderChannelName(pGuiGraphics, channel.name, columnNumber);

			columnNumber++;
		}
	}

	private void renderMousePosition(GuiGraphics gui, int mouseX, int mouseY, int column)
	{
		if(mouseX < CHANNELS_LEFT || mouseX > CHANNELS_RIGHT)
		{
			return;
		}

		// draw vertical bar
		int scrollCorrection = (int)(this.scrollAmountX % WIDTH_PER_SIGNAL);

		int baseX = mouseX - CHANNELS_LEFT - scrollCorrection;
		baseX -= baseX % WIDTH_PER_SIGNAL;
		baseX += CHANNELS_LEFT + scrollCorrection;

		int baseY = CHANNELS_TOP + CHANNEL_HEIGHT * column;

		gui.blit(WAVEFORM_TEXTURE, baseX, baseY, 245, 0, WIDTH_PER_SIGNAL, CHANNEL_HEIGHT-2);

		// display redstone tick count
		int redstoneTick = mouseX - CHANNELS_LEFT - (int)(this.scrollAmountX);
		redstoneTick /= WIDTH_PER_SIGNAL;

		if(redstoneTick > 0)
		{
			String infoToDisplay = String.format("%d", redstoneTick);
			gui.drawString(this.font, infoToDisplay, baseX + WIDTH_PER_SIGNAL/2 - this.font.width(infoToDisplay)/2, this.topPos - 16, 0xf0f0f0);
		}
	}

	private void renderChannelSignals(GuiGraphics gui, ArrayList<Boolean> signals, int column)
	{
		int baseX = CHANNELS_LEFT + (int)(this.scrollAmountX);
		int baseY = CHANNELS_TOP + CHANNEL_HEIGHT * (column + 1) - WAVE_BOTTOM_MARGIN;

		for(boolean isPowered : signals)
		{
			baseX += WIDTH_PER_SIGNAL;
			baseY += 0;

			if(baseX < CHANNELS_LEFT)
			{
				continue;
			}

			if(baseX >= CHANNELS_RIGHT - 3)
			{
				break;
			}

			if(isPowered)
			{
				gui.blit(WAVEFORM_TEXTURE, baseX, baseY - HIGH_WAVE_HEIGHT + 1, 240, 0, WIDTH_PER_SIGNAL, HIGH_WAVE_HEIGHT);
			}
			else
			{
				gui.blit(WAVEFORM_TEXTURE, baseX, baseY - LOW_WAVE_HEIGHT + 1, 240, 0, WIDTH_PER_SIGNAL, LOW_WAVE_HEIGHT);
			}
		}
	}

	private void renderChannelName(GuiGraphics gui, String name, int column)
	{
		int baseX = CHANNELS_LEFT + 1;
		int baseY = CHANNELS_TOP + (CHANNEL_HEIGHT * column) + 1;

		gui.drawString(this.font, name, baseX, baseY, 0xf0f0f0);
	}

	@Override
	public boolean mouseDragged(double pMouseX,double pMouseY, int pButton, double pDragX, double pDragY)
	{
		this.dragAmountY -= pDragY * DRAG_Y_MULTIPLIER;

		if(this.dragAmountY < 0)
		{
			this.dragAmountY = 0;
		}
		else if(this.dragAmountY >= CHANNEL_HEIGHT * (this.allProbes.size() - 1))
		{
			this.dragAmountY = CHANNEL_HEIGHT * (this.allProbes.size() - 1);
		}

		return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
	}

	@Override
	public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta)
	{
		if(!this.allProbes.isEmpty())
		{
			this.scrollAmountX += pDelta * SCROLL_MULTIPLIER;

			if(this.scrollAmountX > BLANK_SCROLL_AREA_LIMIT)
			{
				this.scrollAmountX = BLANK_SCROLL_AREA_LIMIT;
			}
			else if(this.scrollAmountX < BLANK_SCROLL_AREA_LIMIT- this.allProbes.get(0).loggedSignals.size() * WIDTH_PER_SIGNAL)
			{
				this.scrollAmountX = BLANK_SCROLL_AREA_LIMIT- this.allProbes.get(0).loggedSignals.size() * WIDTH_PER_SIGNAL;
			}
		}

		return super.mouseScrolled(pMouseX, pMouseY, pDelta);
	}
}
