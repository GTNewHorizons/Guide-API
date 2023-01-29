package amerifrance.guideapi.buttons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.buttons.ButtonGuideAPI;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;

public class ButtonNext extends ButtonGuideAPI {

    public ButtonNext(int id, int x, int y, GuiBase guiBase) {
        super(id, x, y, guiBase);
        width = 18;
        height = 10;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.visible) {
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            minecraft.getTextureManager()
                    .bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png"));
            if (GuiHelper.isMouseBetween(mouseX, mouseY, xPosition, yPosition, width, height)) {
                this.drawTexturedModalRect(xPosition, yPosition + 1, 47, 201, 18, 10);
                guiBase.drawHoveringText(getHoveringText(), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
            } else {
                this.drawTexturedModalRect(xPosition, yPosition, 24, 201, 18, 10);
            }
            GL11.glDisable(GL11.GL_BLEND);
            RenderHelper.disableStandardItemLighting();
        }
    }

    public List<String> getHoveringText() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(StatCollector.translateToLocal("button.next.name"));
        return list;
    }
}
