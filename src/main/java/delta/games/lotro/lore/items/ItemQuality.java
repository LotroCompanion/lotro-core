package delta.games.lotro.lore.items;

import java.util.Collection;
import java.util.HashMap;

/**
 * Item quality.
 * @author DAM
 */
public class ItemQuality
{
  private static HashMap<String,ItemQuality> _mapFromCode=new HashMap<String,ItemQuality>();
  private static HashMap<String,ItemQuality> _mapFromColor=new HashMap<String,ItemQuality>();
  private static HashMap<String,ItemQuality> _mapFromMeaning=new HashMap<String,ItemQuality>();

  /**
   * Common.
   */
  public static final ItemQuality COMMON=new ItemQuality(0,"COMMON","Common","White");
  /**
   * Uncommon.
   */
  public static final ItemQuality UNCOMMON=new ItemQuality(1,"UNCOMMON","Uncommon","Yellow");
  /**
   * Rare.
   */
  public static final ItemQuality RARE=new ItemQuality(2,"RARE","Rare","Purple");
  /**
   * Incomparable.
   */
  public static final ItemQuality INCOMPARABLE=new ItemQuality(3,"INCOMPARABLE","Incomparable","Teal");
  /**
   * Epic.
   */
  public static final ItemQuality LEGENDARY=new ItemQuality(4,"LEGENDARY","Epic","Orange");

  private int _code;
  private String _key;
  private String _meaning;
  private String _color;

  private ItemQuality(int code, String key, String meaning, String color)
  {
    _code=code;
    _key=key;
    _meaning=meaning;
    _color=color;
    _mapFromCode.put(key,this);
    _mapFromMeaning.put(meaning,this);
    _mapFromColor.put(color,this);
  }

  /**
   * Get sort code.
   * @return a integer value.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the code for this object.
   * @return an internal identifying code.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the quality label.
   * @return the quality label.
   */
  public String getMeaning()
  {
    return _meaning;
  }

  /**
   * Get the associated color.
   * @return a color name.
   */
  public String getColor()
  {
    return _color;
  }

  /**
   * Get an item quality from its code.
   * @param code Code to use.
   * @return An item quality or <code>null</code> if not found.
   */
  public static ItemQuality fromCode(String code)
  {
    return _mapFromCode.get(code);
  }

  /**
   * Get an item quality from its color.
   * @param color Color to use.
   * @return An item quality or <code>null</code> if not found.
   */
  public static ItemQuality fromColor(String color)
  {
    return _mapFromColor.get(color);
  }

  /**
   * Get all instances of this class.
   * @return an array of all instances of this class.
   */
  public static ItemQuality[] getAll()
  {
    Collection<ItemQuality> values=_mapFromCode.values();
    return values.toArray(new ItemQuality[values.size()]);
  }

  @Override
  public String toString()
  {
    return _meaning;
  }
}
