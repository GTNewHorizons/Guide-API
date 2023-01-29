package amerifrance.guideapi.pages;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Use {@link PageItemStack}
 */
@Deprecated
public class PageUnlocItemStack extends PageUnlocText {

    public ItemStack stack;

    /**
     * @param unlocText - Unlocalized text to draw
     * @param stack     - ItemStack to render
     */
    public PageUnlocItemStack(String unlocText, ItemStack stack) {
        super(unlocText, 60);
        this.stack = stack;
    }

    /**
     * @param unlocText - Unlocalized text to draw
     * @param item      - Item to render
     */
    public PageUnlocItemStack(String unlocText, Item item) {
        super(unlocText, 60);
        this.stack = new ItemStack(item);
    }

    /**
     * @param unlocText - Unlocalized text to draw
     * @param block     - Block to render
     */
    public PageUnlocItemStack(String unlocText, Block block) {
        super(unlocText, 60);
        this.stack = new ItemStack(block);
    }

    /**
     * @param unlocText - Unlocalized text to draw
     * @param entry     - OreDict entry to render
     */
    public PageUnlocItemStack(String unlocText, String entry) {
        super(unlocText, 60);

        this.stack = new ItemStack(Blocks.fire);

        if (!OreDictionary.getOres(entry).isEmpty()) for (int i = 0; i < OreDictionary.getOres(entry).size(); i++) {
            ItemStack stack = OreDictionary.getOres(entry).get(i);

            this.stack = stack;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop,
            int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        GuiHelper.drawScaledItemStack(stack, guiLeft + 75, guiTop + 20, 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageUnlocItemStack that = (PageUnlocItemStack) o;
        if (stack != null ? !stack.isItemEqual(that.stack) : that.stack != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return stack != null ? stack.hashCode() : 0;
    }
}
