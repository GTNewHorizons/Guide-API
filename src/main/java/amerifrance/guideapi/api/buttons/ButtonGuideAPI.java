package amerifrance.guideapi.api.buttons;

import net.minecraft.client.gui.GuiButton;

import amerifrance.guideapi.gui.GuiBase;

public class ButtonGuideAPI extends GuiButton {

    public GuiBase guiBase;

    public ButtonGuideAPI(int id, int x, int y, GuiBase guiBase) {
        super(id, x, y, "");
        this.guiBase = guiBase;
    }
}
