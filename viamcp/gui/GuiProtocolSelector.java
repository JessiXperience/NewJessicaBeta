package viamcp.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import viamcp.ViaMCP;
import viamcp.protocols.ProtocolCollection;

import java.io.IOException;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

public class GuiProtocolSelector extends GuiScreen
{
    private GuiScreen parent;
    public SlotList list;

    public GuiProtocolSelector(GuiScreen parent)
    {
        this.parent = parent;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(new GuiButton(1, width / 2 - 100, height - 25, 200, 20, "Back"));
        list = new SlotList(mc, width, height, 32, height - 32, 10);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException
    {
        list.actionPerformed(guiButton);

        if (guiButton.id == 1)
        {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        list.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        list.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0, 2.0, 2.0);
        String title = TextFormatting.BOLD + "ViaMCP";
        drawString(this.fontRendererObj, title, (this.width - (this.fontRendererObj.getStringWidth(title) * 2)) / 4, 5, -1);
        GlStateManager.popMatrix();

        String versionName = ProtocolCollection.getProtocolById(ViaMCP.getInstance().getVersion()).getName();
        String versionCodeName = ProtocolCollection.getProtocolInfoById(ViaMCP.getInstance().getVersion()).getName();
        String versionReleaseDate = ProtocolCollection.getProtocolInfoById(ViaMCP.getInstance().getVersion()).getReleaseDate();
        String versionTitle = "Version: " + versionName + " - " + versionCodeName;
        String versionReleased = "Released: " + versionReleaseDate;

        int fixedHeight = ((5 + this.fontRendererObj.FONT_HEIGHT) * 2) + 2;

        drawString(this.fontRendererObj, TextFormatting.GRAY + (TextFormatting.BOLD + "Version Information"), (width - this.fontRendererObj.getStringWidth("Version Information")) / 2, fixedHeight, -1);
        drawString(this.fontRendererObj, versionTitle, (width - this.fontRendererObj.getStringWidth(versionTitle)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT, -1);
        drawString(this.fontRendererObj, versionReleased, (width - this.fontRendererObj.getStringWidth(versionReleased)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 2, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    class SlotList extends GuiSlot
    {
        public SlotList(Minecraft mc, int width, int height, int top, int bottom, int slotHeight)
        {
            super(mc, width, height, top + 30, bottom, 18);
        }

        @Override
        protected int getSize()
        {
            return ProtocolCollection.values().length;
        }

        @Override
        protected void elementClicked(int i, boolean b, int i1, int i2)
        {
            int protocolVersion = ProtocolCollection.values()[i].getVersion().getVersion();
            ViaMCP.getInstance().setVersion(protocolVersion);
            //ViaMCP.getInstance().asyncSlider.setVersion(protocolVersion);
        }

        @Override
        protected boolean isSelected(int i)
        {
            return false;
        }

        @Override
        protected void drawBackground()
        {
            drawDefaultBackground();
        }

        @Override
        protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_)
        {
            final ProtocolVersion version = ProtocolCollection.getProtocolById(ProtocolCollection.values()[p_192637_1_].getVersion().getVersion());
            final int versionID = version.getVersion();
            final String text = version.getName() + " §7[" + versionID + "]";
            
            /*drawCenteredString(mc.fontRendererObj,(ViaMCP.getInstance().getVersion() == ProtocolCollection.values()[p_192637_1_].getVersion().getVersion() ? TextFormatting.GREEN.toString() + TextFormatting.BOLD : TextFormatting.GRAY.toString()) + ProtocolCollection.getProtocolById(ProtocolCollection.values()[p_192637_1_].getVersion().getVersion()).getName(), width / 2, p_192637_3_ + 2, -1);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            drawCenteredString(mc.fontRendererObj, "PVN: " + ProtocolCollection.getProtocolById(ProtocolCollection.values()[p_192637_1_].getVersion().getVersion()).getVersion(), width, (p_192637_3_ + 2) * 2 + 20, -1);
            GlStateManager.popMatrix();*/
            
            if (ViaMCP.getInstance().getVersion() == versionID) {
                GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, "§a§l" + text, width / 2, p_192637_3_ + 2, -1);
            }
            else {
                
                GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, "§c" + text, width / 2, p_192637_3_ + 2, -1);
            }
            //GlStateManager.popMatrix();
        }
    }
}
