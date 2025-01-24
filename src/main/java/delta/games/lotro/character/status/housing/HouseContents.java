package delta.games.lotro.character.status.housing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.common.id.InternalGameIdComparator;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Contents of a house.
 * @author DAM
 */
public class HouseContents
{
  // Housing items
  private List<HousingItem> _items;

  /**
   * Constructor.
   */
  public HouseContents()
  {
    _items=new ArrayList<HousingItem>();
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(HousingItem item)
  {
    _items.add(item);
  }

  /**
   * Get the managed items.
   * @return A list of managed items, sorted by entity ID.
   */
  public List<HousingItem> getItems()
  {
    List<HousingItem> ret=new ArrayList<HousingItem>(_items);
    DataProvider<HousingItem,InternalGameId> provider=new DataProvider<HousingItem,InternalGameId>()
    {
      @Override
      public InternalGameId getData(HousingItem item)
      {
        return item.getEntityID();
      }
    };
    DelegatingComparator<HousingItem,InternalGameId> delegatingComparator=new DelegatingComparator<HousingItem,InternalGameId>(provider,new InternalGameIdComparator());
    Collections.sort(ret,delegatingComparator);
    return ret;
  }
}
