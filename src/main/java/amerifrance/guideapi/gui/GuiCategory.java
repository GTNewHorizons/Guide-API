package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.buttons.ButtonBack;
import amerifrance.guideapi.buttons.ButtonNext;
import amerifrance.guideapi.buttons.ButtonPrev;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.wrappers.EntryWrapper;
import com.google.common.collect.HashMultimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class GuiCategory extends GuiBase {

    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png");
    public Book book;
    public AbstractCategory category;
    public HashMultimap<Integer, EntryWrapper> entryWrapperList;
    private int entryPage;
    public ButtonBack buttonBack, buttonFirstPage;
    public ButtonNext buttonNext;
    public ButtonPrev buttonPrev;

    public GuiCategory(Book book, AbstractCategory category, EntityPlayer player, ItemStack bookStack) {
        super(player, bookStack);
        this.outlineTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_greyscale.png");
        this.category = category;
        this.book = book;
        this.entryWrapperList = this.entryWrapperList.create();
        this.entryPage = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.entryWrapperList.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        this.buttonList.add(buttonBack = new ButtonBack(0, guiLeft, guiTop, this, false));
        this.buttonList.add(buttonFirstPage = new ButtonBack(1, guiLeft + xSize / 6, guiTop, this, true));
        this.buttonList.add(buttonNext = new ButtonNext(2, guiLeft + 5 * xSize / 6, guiTop + 5 * ySize / 6, this));
        this.buttonList.add(buttonPrev = new ButtonPrev(3, guiLeft + xSize / 6, guiTop + 5 * ySize / 6, this));

        int eX = guiLeft + 37;
        int eY = guiTop + 15;
        int i = 0;
        int pageNumber = 0;
        for (AbstractEntry entry : category.entries()) {
            entryWrapperList.put(pageNumber, new EntryWrapper(this, book, category, entry, eX, eY, 4 * xSize / 6, 10, player, this.fontRendererObj, bookStack));
            eY += 13;
            i++;

            if (i >= 11) {
                i = 0;
                eY = guiTop + 15;
                pageNumber++;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.color());

        for (EntryWrapper wrapper : this.entryWrapperList.get(entryPage)) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(mouseX, mouseY, this);
                wrapper.drawExtras(mouseX, mouseY, this);
            }
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                wrapper.onHoverOver(mouseX, mouseY);
            }
        }

        drawCenteredString(fontRendererObj, String.valueOf(entryPage + 1) + "/" + String.valueOf(entryWrapperList.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeofClick) {
        super.mouseClicked(mouseX, mouseY, typeofClick);

        for (EntryWrapper wrapper : this.entryWrapperList.get(entryPage)) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) wrapper.entry.onLeftClicked(book, category, mouseX, mouseY, player, this);
                else if (typeofClick == 1) wrapper.entry.onRightClicked(book, category, mouseX, mouseY, player, this);
            }
        }

        if (typeofClick == 1) {
            this.mc.displayGuiScreen(new GuiHome(book, player, bookStack));
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_BACK || keyCode == this.mc.gameSettings.keyBindUseItem.getKeyCode()) {
            this.mc.displayGuiScreen(new GuiHome(book, player, bookStack));
        }
        if ((keyCode == Keyboard.KEY_UP || keyCode == Keyboard.KEY_RIGHT) && entryPage + 1 < entryWrapperList.asMap().size()) {
            this.entryPage++;
        }
        if ((keyCode == Keyboard.KEY_DOWN || keyCode == Keyboard.KEY_LEFT) && entryPage > 0) {
            this.entryPage--;
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiHome(book, player, bookStack));
        } else if (button.id == 1) {
            this.entryPage = 0;
        } else if (button.id == 2 && entryPage + 1 < entryWrapperList.asMap().size()) {
            this.entryPage++;
        } else if (button.id == 3 && entryPage > 0) {
            this.entryPage--;
        }
    }
}
