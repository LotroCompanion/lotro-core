package delta.games.lotro.lore.items.legendary2.comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import delta.common.utils.collections.CompoundComparator;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.comparators.ItemNameComparator;
import delta.games.lotro.lore.items.comparators.ItemQualityComparator;
import delta.games.lotro.lore.items.comparators.ItemTierComparator;
import delta.games.lotro.lore.items.legendary2.Tracery;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Factory for tracery comparators.
 * @author DAM
 */
public class TraceryComparators
{
  /**
   * Build a comparator for traceries.
   * @return A traceries comparator using name/tier/quality.
   */
  public static Comparator<Tracery> buildTraceriesComparator()
  {
    Comparator<Item> itemsComparator=buildTraceryItemsComparator();
    DataProvider<Tracery,Item> itemProvider=new DataProvider<Tracery,Item>()
    {
      public Item getData(Tracery p)
      {
        return p.getItem();
      }
    };
    DelegatingComparator<Tracery,Item> ret=new DelegatingComparator<Tracery,Item>(itemProvider,itemsComparator);
    return ret;
  }

  /**
   * Build a comparator for tracery items.
   * @return A comparator using name/tier/quality.
   */
  public static Comparator<Item> buildTraceryItemsComparator()
  {
    // Item comparators
    List<Comparator<Item>> itemComparators=new ArrayList<Comparator<Item>>();
    // - name
    ItemNameComparator nameComparator=new ItemNameComparator();
    itemComparators.add(nameComparator);
    // - tier
    ItemTierComparator tierComparator=new ItemTierComparator();
    itemComparators.add(tierComparator);
    // - quality
    DataProvider<Item,ItemQuality> qualityProvider=new DataProvider<Item,ItemQuality>()
    {
      public ItemQuality getData(Item item)
      {
        return item.getQuality();
      }
    };
    DelegatingComparator<Item,ItemQuality> qualityComparator=new DelegatingComparator<Item,ItemQuality>(qualityProvider,new ItemQualityComparator());
    itemComparators.add(qualityComparator);
    Comparator<Item> ret=new CompoundComparator<Item>(itemComparators);
    return ret;
  }
}
