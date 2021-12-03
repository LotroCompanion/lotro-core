package delta.games.lotro.lore.items;

import java.util.HashMap;

/**
 * Item quality.
 * @author DAM
 */
public final class ItemQuality
{
  private static HashMap<String,ItemQuality> _mapFromCode=new HashMap<String,ItemQuality>();
  private static HashMap<String,ItemQuality> _mapFromMeaning=new HashMap<String,ItemQuality>();

  /**
   * Common.
   */
  public static final ItemQuality COMMON=new ItemQuality(0,"COMMON","Common");
  /**
   * Uncommon.
   */
  public static final ItemQuality UNCOMMON=new ItemQuality(1,"UNCOMMON","Uncommon");
  /**
   * Rare.
   */
  public static final ItemQuality RARE=new ItemQuality(2,"RARE","Rare");
  /**
   * Incomparable.
   */
  public static final ItemQuality INCOMPARABLE=new ItemQuality(3,"INCOMPARABLE","Incomparable");
  /**
   * Epic.
   */
  public static final ItemQuality LEGENDARY=new ItemQuality(4,"LEGENDARY","Epic");

  /**
   * All qualities.
   */
  public static final ItemQuality[] ALL={ COMMON, UNCOMMON, RARE, INCOMPARABLE, LEGENDARY };

  private int _code;
  private String _key;
  private String _meaning;

  private ItemQuality(int code, String key, String meaning)
  {
    _code=code;
    _key=key;
    _meaning=meaning;
    _mapFromCode.put(key,this);
    _mapFromMeaning.put(meaning,this);
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
   * Get an item quality from its code.
   * @param code Code to use.
   * @return An item quality or <code>null</code> if not found.
   */
  public static ItemQuality fromCode(String code)
  {
    return _mapFromCode.get(code);
  }

  @Override
  public String toString()
  {
    return _meaning;
  }
}
