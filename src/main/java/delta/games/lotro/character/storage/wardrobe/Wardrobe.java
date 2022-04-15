package delta.games.lotro.character.storage.wardrobe;

import java.util.ArrayList;
import java.util.List;

/**
 * Wardrobe.
 * @author DAM
 */
public class Wardrobe
{
  private List<WardrobeItem> _items;

  /**
   * Constructor.
   */
  public Wardrobe()
  {
    _items=new ArrayList<WardrobeItem>();
  }

  /**
   * Add an item in the wardrobe.
   * @param item Item to add.
   */
  public void addItem(WardrobeItem item)
  {
    _items.add(item);
  }

  /**
   * Get all the items in the wardrobe.
   * @return A list of wardrobe items.
   */
  public List<WardrobeItem> getAll()
  {
    return new ArrayList<WardrobeItem>(_items);
  }

  @Override
  public String toString()
  {
    return "Wardrobe: "+_items;
  }
}
