package amerifrance.guideapi.entries;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.EntryBase;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntryItemStack extends EntryBase {

    public ItemStack stack;

    public EntryItemStack(List<IPage> pageList, String unlocEntryName, ItemStack stack, boolean unicode) {
        super(pageList, unlocEntryName, unicode);

        this.stack = stack;
    }

    public EntryItemStack(List<IPage> pageList, String unlocEntryName, ItemStack stack) {
        this(pageList, unlocEntryName, stack, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth,
            int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        if (stack != null) GuiHelper.drawScaledItemStack(stack, entryX + 2, entryY, 0.5F);
    }
}
