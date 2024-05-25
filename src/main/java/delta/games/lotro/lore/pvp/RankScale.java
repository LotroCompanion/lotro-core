package delta.games.lotro.lore.pvp;

import java.util.ArrayList;
import java.util.List;

/**
 * Rank scale.
 * @author DAM
 */
public class RankScale
{
  private String _key;
  private List<RankScaleEntry> _entries;

  /**
   * Constructor.
   *  @param key Key.
   */
  public RankScale(String key)
  {
    _key=key;
    _entries=new ArrayList<RankScaleEntry>();
  }

  /**
   * Get the identifying key for this scale.
   * @return a key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the entries.
   * @return the entries.
   */
  public List<RankScaleEntry> getEntries()
  {
    return _entries;
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(RankScaleEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get an entry using its code.
   * @param code Rank code to use.
   * @return An entry or <code>null</code>.
   */
  public RankScaleEntry getRankByCode(int code)
  {
    for(RankScaleEntry entry : _entries)
    {
      if (entry.getRank().getCode()==code)
      {
        return entry;
      }
    }
    return null;
  }

  /**
   * Get the rank for the given value.
   * @param value Input value.
   * @return the associated rank.
   */
  public Rank getRank(int value)
  {
    Rank ret=null;
    for(RankScaleEntry entry : _entries)
    {
      if (value<entry.getValue())
      {
        break;
      }
      ret=entry.getRank();
    }
    return ret;
  }
}
