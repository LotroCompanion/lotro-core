package delta.games.lotro.lore.deeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.enums.DeedCategory;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;

/**
 * Deed-related utility methods.
 * @author DAM
 */
public class DeedUtils
{
  /**
   * Load available categories from deeds manager.
   * @return A sorted list of deed categories.
   */
  public static List<DeedCategory> getCategories()
  {
    Set<DeedCategory> categories=new HashSet<DeedCategory>();
    List<DeedDescription> deeds=DeedsManager.getInstance().getAll();
    for(DeedDescription deed : deeds)
    {
      DeedCategory deedCategory=deed.getCategory();
      categories.add(deedCategory);
    }
    List<DeedCategory> ret=new ArrayList<DeedCategory>(categories);
    ret.remove(null);
    Collections.sort(ret,new LotroEnumEntryNameComparator<DeedCategory>());
    return ret;
  }

  /**
   * Get the icon path for a deed type.
   * @param type Deed type.
   * @return A path.
   */
  public static String getDeedTypeIconPath(DeedType type)
  {
    String filename=type.getKey().toLowerCase();
    String path="/resources/gui/deeds/"+filename+".png";
    return path;
  }
}
