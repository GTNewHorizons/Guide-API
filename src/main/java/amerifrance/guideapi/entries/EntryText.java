package amerifrance.guideapi.entries;

import java.util.List;

import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.EntryBase;

/**
 * Use {@link EntryBase} instead. It's the same thing
 */
@Deprecated
public class EntryText extends EntryBase {

    public EntryText(List<IPage> pageList, String unlocEntryName, boolean unicode) {
        super(pageList, unlocEntryName, unicode);
    }

    public EntryText(List<IPage> pageList, String unlocEntryName) {
        this(pageList, unlocEntryName, false);
    }
}
