package amerifrance.guideapi.wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;

public class PageWrapper extends AbstractWrapper {

    public GuiEntry guiEntry;
    public Book book;
    public CategoryAbstract category;
    public EntryAbstract entry;
    public IPage page;
    public int guiLeft, guiTop;
    public EntityPlayer player;
    public FontRenderer renderer;
    public ItemStack bookStack;

    public PageWrapper(GuiEntry guiEntry, Book book, CategoryAbstract category, EntryAbstract entry, IPage page,
            int guiLeft, int guiTop, EntityPlayer player, FontRenderer renderer, ItemStack bookStack) {
        this.guiEntry = guiEntry;
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.page = page;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
        this.player = player;
        this.renderer = renderer;
        this.bookStack = bookStack;
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {}

    @Override
    public boolean canPlayerSee() {
        return page.canSee(book, category, entry, player, bookStack, guiEntry);
    }

    @Override
    public void draw(int mouseX, int mouseY, GuiBase gui) {
        page.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, gui, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public void drawExtras(int mouseX, int mouseY, GuiBase gui) {
        page.drawExtras(
                book,
                category,
                entry,
                guiLeft,
                guiTop,
                mouseX,
                mouseY,
                gui,
                Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return true;
    }
}
