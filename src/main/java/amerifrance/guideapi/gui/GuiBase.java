package amerifrance.guideapi.gui;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiBase extends GuiScreen {

    public int guiLeft, guiTop;
    public int xSize = 192;
    public int ySize = 192;
    public EntityPlayer player;
    public ItemStack bookStack;
    public float publicZLevel;

    public GuiBase(EntityPlayer player, ItemStack bookStack) {
        this.player = player;
        this.bookStack = bookStack;
        this.publicZLevel = zLevel;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen((GuiScreen) null);
            this.mc.setIngameFocus();
        }
    }

    public void drawTexturedModalRectWithColor(int x, int y, int textureX, int textureY, int width, int height,
            Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        GL11.glColor3f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(
                (double) (x + 0),
                (double) (y + height),
                (double) this.zLevel,
                (double) ((float) (textureX + 0) * f),
                (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
                (double) (x + width),
                (double) (y + height),
                (double) this.zLevel,
                (double) ((float) (textureX + width) * f),
                (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
                (double) (x + width),
                (double) (y + 0),
                (double) this.zLevel,
                (double) ((float) (textureX + width) * f),
                (double) ((float) (textureY + 0) * f1));
        tessellator.addVertexWithUV(
                (double) (x + 0),
                (double) (y + 0),
                (double) this.zLevel,
                (double) ((float) (textureX + 0) * f),
                (double) ((float) (textureY + 0) * f1));
        tessellator.draw();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void drawHoveringText(List list, int x, int y, FontRenderer font) {
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        super.drawHoveringText(list, x, y, font);
        GL11.glPopAttrib();
    }

    @Override
    public void drawCenteredString(FontRenderer fontRenderer, String string, int x, int y, int color) {
        fontRenderer.drawString(string, x - fontRenderer.getStringWidth(string) / 2, y, color);
    }

    public void drawCenteredStringWithShadow(FontRenderer fontRenderer, String string, int x, int y, int color) {
        super.drawCenteredString(fontRenderer, string, x, y, color);
    }

    public void drawSplitString(String string, int x, int y, int maxLength, int color) {
        fontRendererObj.drawSplitString(string, x, y, maxLength, color);
    }

    @Override
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        super.drawTexturedModalRect(x, y, textureX, textureY, width, height);
        GL11.glPopMatrix();
    }

    @Override
    public void func_146283_a(List p_146283_1_, int p_146283_2_, int p_146283_3_) {
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        super.func_146283_a(p_146283_1_, p_146283_2_, p_146283_3_);
        GL11.glPopAttrib();
    }

    @Override
    public void renderToolTip(ItemStack stack, int x, int y) {
        super.renderToolTip(stack, x, y);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
