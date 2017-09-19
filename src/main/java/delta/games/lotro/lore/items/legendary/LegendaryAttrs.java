package delta.games.lotro.lore.items.legendary;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;

/**
 * Legendary item description.
 * @author DAM
 */
public class LegendaryAttrs
{
  /*
  private boolean _imbued;
  private int _stars;
  */
  private LegendaryTitle _title;
  // Legacies
  // TODO
  // Relics
  private Relic _setting;
  private Relic _gem;
  private Relic _rune;
  private Relic _crafted;

  /**
   * Constructor.
   */
  public LegendaryAttrs()
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
  public LegendaryAttrs(LegendaryAttrs source)
  {
    _setting=source._setting;
    _gem=source._gem;
    _rune=source._rune;
    _crafted=source._crafted;
  }

  /**
   * Get the legendary title.
   * @return a title or <code>null</code> if not set.
   */
  public LegendaryTitle getTitle()
  {
    return _title;
  }

  /**
   * Set the legendary title.
   * @param title Title to set.
   */
  public void setTitle(LegendaryTitle title)
  {
    _title=title;
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

  /**
   * Get the total stats for these legendary attributes, including:
   * <ul>
   * <li>title stats,
   * <li>relics stats
   * </ul>
   * @return a set of stats.
   */
  public BasicStatsSet getRawStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    // Title
    if (_title!=null)
    {
      BasicStatsSet titleStats=_title.getStats();
      ret.addStats(titleStats);
    }
    // Relics
    List<Relic> relics=getRelics();
    for(Relic relic : relics)
    {
      if (relic!=null)
      {
        BasicStatsSet relicStats=relic.getStats();
        ret.addStats(relicStats);
      }
    }
    return ret;
  }

  /**
   * Dump the contents of this quest as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Relics:").append(EndOfLine.NATIVE_EOL);
    for(Relic relic : getRelics())
    {
      if (relic!=null)
      {
        sb.append('\t').append(relic).append(EndOfLine.NATIVE_EOL);
      }
    }
    return sb.toString();
  }
}
