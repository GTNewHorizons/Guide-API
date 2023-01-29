package amerifrance.guideapi.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import amerifrance.guideapi.api.GuideAPIItems;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.items.ItemLostPage;

public class LootGenerator {

    public static void registerLoot() {
        for (Book book : GuideRegistry.getBookList()) {
            if (book.isLostBook) {
                for (CategoryAbstract categoryAbstract : book.categoryList) {
                    for (EntryAbstract entryAbstract : categoryAbstract.entryList) {
                        for (int i = 0; i < entryAbstract.pageList.size(); i++) {
                            ItemStack page = new ItemStack(GuideAPIItems.lostPage, 1, 0);
                            ItemLostPage.setPage(
                                    page,
                                    GuideRegistry.getIndexOf(book),
                                    book.categoryList.indexOf(categoryAbstract),
                                    categoryAbstract.entryList.indexOf(entryAbstract),
                                    i);
                            for (String hook : book.chestHooks) ChestGenHooks
                                    .addItem(hook, new WeightedRandomChestContent(page, 1, 1, book.lootChance));
                        }
                    }
                }
            }
        }
    }
}
