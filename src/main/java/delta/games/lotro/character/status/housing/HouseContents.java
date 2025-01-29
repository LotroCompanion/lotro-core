package delta.games.lotro.character.status.housing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.enums.HousingHookID;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.common.status.StatusMetadata;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Content of a house (interior or exterior).
 * @author DAM
 */
public class HouseContents
{
  // Type
  private HouseContentsType _contentsType;
  // Status
  private StatusMetadata _statusMetadata;
  // Housing items
  private List<HousingItem> _items;

  /**
   * Constructor.
   * @param contentsType Contents type.
   */
  public HouseContents(HouseContentsType contentsType)
  {
    _contentsType=contentsType;
    _statusMetadata=new StatusMetadata();
    _items=new ArrayList<HousingItem>();
  }

  /**
   * Get the contents type.
   * @return the contents type.
   */
  public HouseContentsType getContentsType()
  {
    return _contentsType;
  }

  /**
   * Get the status metadata.
   * @return the status metadata.
   */
  public StatusMetadata getStatusMetadata()
  {
    return _statusMetadata;
  }

  /**
   * Remove all items.
   */
  public void clear()
  {
    _items.clear();
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
   * @return A list of managed items, sorted by hook ID.
   */
  public List<HousingItem> getItems()
  {
    List<HousingItem> ret=new ArrayList<HousingItem>(_items);
    DataProvider<HousingItem,HousingHookID> provider=new DataProvider<HousingItem,HousingHookID>()
    {
      @Override
      public HousingHookID getData(HousingItem item)
      {
        return item.getHookID();
      }
    };
    DelegatingComparator<HousingItem,HousingHookID> delegatingComparator=new DelegatingComparator<HousingItem,HousingHookID>(provider,new LotroEnumEntryCodeComparator<HousingHookID>());
    Collections.sort(ret,delegatingComparator);
    return ret;
  }
}
