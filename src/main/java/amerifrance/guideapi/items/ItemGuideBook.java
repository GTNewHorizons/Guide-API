package amerifrance.guideapi.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IGuideLinked;
import amerifrance.guideapi.api.base.Book;

import com.google.common.base.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ItemGuideBook extends Item {

    public IIcon pagesIcon;
    public TIntObjectMap<IIcon> customIcons = new TIntObjectHashMap<IIcon>();

    public ItemGuideBook() {
        setCreativeTab(GuideAPI.tabGuide);
        setUnlocalizedName("GuideBook");
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage()) {
            if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            player.openGui(
                    GuideAPI.instance,
                    stack.getItemDamage(),
                    world,
                    (int) player.posX,
                    (int) player.posY,
                    (int) player.posZ);
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
            float hitX, float hitY, float hitZ) {

        if (!player.isSneaking()) return false;

        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage()
                && world.getBlock(x, y, z) instanceof IGuideLinked) {
            IGuideLinked guideLinked = (IGuideLinked) world.getBlock(x, y, z);
            Book book = GuideRegistry.getBook(stack.getItemDamage());
            String entryName = guideLinked.getLinkedEntryUnlocName(world, x, y, z, player, stack);
            for (CategoryAbstract category : book.categoryList) {
                for (EntryAbstract entry : category.entryList) {
                    if (entry.unlocEntryName.equals(entryName)) {
                        GuideAPI.proxy.openEntry(book, category, entry, player, stack);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage()) {
            String name = String.valueOf(stack.getItemDamage());
            return getUnlocalizedName() + "." + name;
        } else {
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    public int getRenderPasses(int metadata) {
        return requiresMultipleRenderPasses() ? 2 : 1;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        IIcon icon = customIcons.get(stack.getItemDamage());
        if (icon == null) icon = pass == 0 ? itemIcon : pagesIcon;
        return icon;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return GuideRegistry.isValidBook(stack) ? GuideRegistry.getBook(stack.getItemDamage()).getLocalizedDisplayName()
                : super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        itemIcon = ir.registerIcon(ModInformation.TEXLOC + "book_cover");
        pagesIcon = ir.registerIcon(ModInformation.TEXLOC + "book_pages");

        for (int i = 0; i < GuideRegistry.getBookList().size(); i++) {
            Book book = GuideRegistry.getBook(i);
            if (book.itemTexture != null) {
                customIcons.put(i, ir.registerIcon(book.itemTexture));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (GuideRegistry.isValidBook(stack) && GuideRegistry.getBook(stack.getItemDamage()).itemTexture == null) {
            if (pass == 0) return GuideRegistry.getBook(stack.getItemDamage()).bookColor.getRGB();
            else return super.getColorFromItemStack(stack, pass);
        } else {
            return super.getColorFromItemStack(stack, pass);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        if (!GuideRegistry.isEmpty()) {
            for (Book book : GuideRegistry.getBookList()) {
                ItemStack stack = new ItemStack(this, 1, GuideRegistry.getIndexOf(book));

                if (stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound();

                stack.stackTagCompound.setBoolean("CreativeBook", book.isLostBook);
                list.add(stack);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

        if (!GuideRegistry.isValidBook(stack))
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("text.book.warning"));

        if (GuideRegistry.isValidBook(stack)
                && !Strings.isNullOrEmpty(GuideRegistry.getBook(stack.getItemDamage()).author))
            list.add(StatCollector.translateToLocal(GuideRegistry.getBook(stack.getItemDamage()).author));

        if (stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound();

        if (stack.stackTagCompound.getBoolean("CreativeBook"))
            list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("text.book.creative"));
    }
}
