package delta.games.lotro.character.stats.buffs.io.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Raw storage for buffs, as loaded from XML. 
 * @author DAM
 */
public class RawBuffStorage
{
  private List<String> _buffIDs;
  private List<Integer> _tiers;

  /**
   * Constructor.
   */
  public RawBuffStorage() 
  {
    _buffIDs=new ArrayList<String>();
    _tiers=new ArrayList<Integer>();
  }

  /**
   * Indicates if this storage contains the given buff.
   * @param buffID Buff identifier.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(String buffID)
  {
    for(String currentBuffID : _buffIDs)
    {
      if (currentBuffID.equals(buffID))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the number of buffs.
   * @return A buffs count.
   */
  public int getSize()
  {
    return _buffIDs.size();
  }

  /**
   * Add a buff.
   * @param id Buff identifier.
   * @param tier Buff tier.
   */
  public void add(String id, Integer tier)
  {
    _buffIDs.add(id);
    _tiers.add(tier);
  }

  /**
   * Get a buff identifier.
   * @param index Index of buff.
   * @return A buff identifier.
   */
  public String getBuffID(int index)
  {
    return _buffIDs.get(index);
  }

  /**
   * Get a buff tier.
   * @param index Index of buff.
   * @return A buff tier.
   */
  public Integer getTier(int index)
  {
    return _tiers.get(index);
  }

  private int findBuffIndex(String buffID)
  {
    int nb=getSize();
    for(int i=0;i<nb;i++)
    {
      if (_buffIDs.get(i).equals(buffID))
      {
        return i;
      }
    }
    return -1;
  }

  /**
   * Remove a buff.
   * @param buffID Buff identifier.
   */
  public void removeBuff(String buffID)
  {
    int index=findBuffIndex(buffID);
    if (index!=-1)
    {
      _buffIDs.remove(index);
      _tiers.remove(index);
    }
  }

  @Override
  public String toString()
  {
    return "Buffs: "+_buffIDs+", tiers: "+_tiers;
  }
}
