package delta.games.lotro.character.storage.wardrobe;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Item of the wardrobe.
 * @author DAM
 */
public class WardrobeItem
{
  private Item _item;
  private List<ColorDescription> _colors;

  /**
   * Constructor.
   * @param item Item.
   */
  public WardrobeItem(Item item)
  {
    _item=item;
    _colors=new ArrayList<ColorDescription>();
  }

  /**
   * Get the managed item.
   * @return an item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Add a color.
   * @param color Color to add.
   */
  public void addColor(ColorDescription color)
  {
    _colors.add(color);
  }

  /**
   * Get the registered colors.
   * @return A list of colors.
   */
  public List<ColorDescription> getColors()
  {
    return _colors;
  }

  /**
   * Get a label to display the managed colors.
   * @return A displayable label.
   */
  public String getColorsLabel()
  {
    if (_colors.isEmpty())
    {
      return "";
    }
    StringBuilder sb=new StringBuilder();
    for(ColorDescription color : _colors)
    {
      String colorName=color.getName();
      if (colorName!=null)
      {
        if (sb.length()>0)
        {
          sb.append(',');
        }
        sb.append(colorName);
      }
    }
    return sb.toString();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_item!=null)
    {
      sb.append("Item: ").append(_item);
    }
    if (_colors.size()>0)
    {
      if (sb.length()>0)
      {
        sb.append(", ");
      }
      sb.append("colors: ");
      for(ColorDescription color : _colors)
      {
        sb.append(color.getName());
      }
    }
    return sb.toString();
  }
}
