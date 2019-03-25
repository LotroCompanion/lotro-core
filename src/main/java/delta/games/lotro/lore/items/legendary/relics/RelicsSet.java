package delta.games.lotro.lore.items.legendary.relics;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage for a set of relics.
 * @author DAM
 */
public class RelicsSet
{
  private Relic _setting;
  private Relic _gem;
  private Relic _rune;
  private Relic _crafted;

  /**
   * Constructor.
   */
  public RelicsSet()
  {
    _setting=null;
    _gem=null;
    _rune=null;
    _crafted=null;
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public RelicsSet(RelicsSet source)
  {
    _setting=source._setting;
    _gem=source._gem;
    _rune=source._rune;
    _crafted=source._crafted;
  }

  /**
   * Get the list of all relics put on this legendary item.
   * @return A possibly empty list of relics.
   */
  public List<Relic> getRelics()
  {
    List<Relic> relics=new ArrayList<Relic>();
    relics.add(_setting);
    relics.add(_gem);
    relics.add(_rune);
    relics.add(_crafted);
    return relics;
  }

  /**
   * Slot a relic.
   * @param relic Relic to set.
   */
  public void slotRelic(Relic relic)
  {
    if (relic!=null)
    {
      if (relic.getType()==RelicType.SETTING) _setting=relic;
      else if (relic.getType()==RelicType.GEM) _gem=relic;
      else if (relic.getType()==RelicType.RUNE) _rune=relic;
      else if (relic.getType()==RelicType.CRAFTED_RELIC) _crafted=relic;
    }
  }

  /**
   * Get the 'setting' relic.
   * @return A 'setting' relic or <code>null</code>.
   */
  public Relic getSetting()
  {
    return _setting;
  }

  /**
   * Set 'setting' relic.
   * @param relic Relic to set.
   */
  public void setSetting(Relic relic)
  {
    if (relic!=null)
    {
      if (relic.getType()==RelicType.SETTING)
      {
        _setting=relic;
      }
    }
    else
    {
      _setting=null;
    }
  }

  /**
   * Get the 'gem' relic.
   * @return A 'gem' relic or <code>null</code>.
   */
  public Relic getGem()
  {
    return _gem;
  }

  /**
   * Set 'gem' relic.
   * @param relic Relic to set.
   */
  public void setGem(Relic relic)
  {
    if (relic!=null)
    {
      if (relic.getType()==RelicType.GEM)
      {
        _gem=relic;
      }
    }
    else
    {
      _gem=null;
    }
  }

  /**
   * Get the 'rune' relic.
   * @return A 'rune' relic or <code>null</code>.
   */
  public Relic getRune()
  {
    return _rune;
  }

  /**
   * Set 'rune' relic.
   * @param relic Relic to set.
   */
  public void setRune(Relic relic)
  {
    if (relic!=null)
    {
      if (relic.getType()==RelicType.RUNE)
      {
        _rune=relic;
      }
    }
    else
    {
      _rune=null;
    }
  }

  /**
   * Get the 'crafted' relic.
   * @return A 'crafted' relic or <code>null</code>.
   */
  public Relic getCraftedRelic()
  {
    return _crafted;
  }

  /**
   * Set 'crafted' relic.
   * @param relic Relic to set.
   */
  public void setCraftedRelic(Relic relic)
  {
    if (relic!=null)
    {
      if (relic.getType()==RelicType.CRAFTED_RELIC)
      {
        _crafted=relic;
      }
    }
    else
    {
      _crafted=null;
    }
  }
}
