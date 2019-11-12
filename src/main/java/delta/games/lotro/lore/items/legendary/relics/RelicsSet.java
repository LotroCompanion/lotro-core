package delta.games.lotro.lore.items.legendary.relics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Storage for a set of relics.
 * @author DAM
 */
public class RelicsSet
{
  private Map<RelicType,Relic> _relics;

  /**
   * Constructor.
   */
  public RelicsSet()
  {
    _relics=new HashMap<RelicType,Relic>();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public RelicsSet(RelicsSet source)
  {
    _relics=new HashMap<RelicType,Relic>(source._relics);
  }

  /**
   * Get the list of all relics put on this legendary item.
   * @return A possibly empty list of relics.
   */
  public List<Relic> getRelics()
  {
    List<Relic> relics=new ArrayList<Relic>();
    for(RelicType type : RelicType.values())
    {
      Relic relic=_relics.get(type);
      relics.add(relic);
    }
    return relics;
  }

  /**
   * Slot a relic.
   * @param relic Relic to set.
   * @param type Type to use.
   */
  public void slotRelic(Relic relic, RelicType type)
  {
    _relics.put(type,relic);
  }

  /**
   * Get the 'setting' relic.
   * @return A 'setting' relic or <code>null</code>.
   */
  public Relic getSetting()
  {
    return _relics.get(RelicType.SETTING);
  }

  /**
   * Set 'setting' relic.
   * @param relic Relic to set.
   */
  public void setSetting(Relic relic)
  {
    _relics.put(RelicType.SETTING,relic);
  }

  /**
   * Get the 'gem' relic.
   * @return A 'gem' relic or <code>null</code>.
   */
  public Relic getGem()
  {
    return _relics.get(RelicType.GEM);
  }

  /**
   * Set 'gem' relic.
   * @param relic Relic to set.
   */
  public void setGem(Relic relic)
  {
    _relics.put(RelicType.GEM,relic);
  }

  /**
   * Get the 'rune' relic.
   * @return A 'rune' relic or <code>null</code>.
   */
  public Relic getRune()
  {
    return _relics.get(RelicType.RUNE);
  }

  /**
   * Set 'rune' relic.
   * @param relic Relic to set.
   */
  public void setRune(Relic relic)
  {
    _relics.put(RelicType.RUNE,relic);
  }

  /**
   * Get the 'crafted' relic.
   * @return A 'crafted' relic or <code>null</code>.
   */
  public Relic getCraftedRelic()
  {
    return _relics.get(RelicType.CRAFTED_RELIC);
  }

  /**
   * Set 'crafted' relic.
   * @param relic Relic to set.
   */
  public void setCraftedRelic(Relic relic)
  {
    _relics.put(RelicType.CRAFTED_RELIC,relic);
  }

  /**
   * Get a list of all managed relics.
   * @return A list of 4 relics. Each relic may be <code>null</code>.
   */
  public List<Relic> getAll()
  {
    List<Relic> ret=new ArrayList<Relic>();
    ret.add(getSetting());
    ret.add(getGem());
    ret.add(getRune());
    ret.add(getCraftedRelic());
    return ret;
  }
}
