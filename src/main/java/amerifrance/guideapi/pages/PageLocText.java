package amerifrance.guideapi.pages;

import net.minecraft.client.gui.FontRenderer;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Use {@link PageText}
 */
@Deprecated
public class PageLocText extends PageBase {

    public String locText;
    public boolean unicode;
    private int yOffset;

    /**
     * @param locText - Pre-localized text to draw.
     * @param yOffset - How many pixels to offset the text on the Y value
     * @param unicode - Whether to enable the unicode flag or not
     */
    public PageLocText(String locText, int yOffset, boolean unicode) {
        this.locText = locText;
        this.yOffset = yOffset;
        this.unicode = unicode;
    }

    public PageLocText(String locText, int yOffset) {
        this(locText, yOffset, false);
    }

    public PageLocText(String locText, boolean unicode) {
        this(locText, 60, unicode);
    }

    public PageLocText(String locText) {
        this(locText, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX,
            int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        boolean startFlag = fontRenderer.getUnicodeFlag();

        if (unicode) fontRenderer.setUnicodeFlag(true);

        fontRenderer.drawSplitString(locText, guiLeft + 39, guiTop + 12 + yOffset, 3 * guiBase.xSize / 5, 0);

        if (unicode && !startFlag) fontRenderer.setUnicodeFlag(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageLocText that = (PageLocText) o;
        if (locText != null ? !locText.equals(that.locText) : that.locText != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return locText != null ? locText.hashCode() : 0;
    }
}
