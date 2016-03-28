package delta.games.lotro.lore.items.legendary;

import java.util.ArrayList;
import java.util.List;

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
    if (_setting!=null)
    {
      relics.add(_setting);
    }
    if (_gem!=null)
    {
      relics.add(_gem);
    }
    if (_rune!=null)
    {
      relics.add(_rune);
    }
    if (_crafted!=null)
    {
      relics.add(_crafted);
    }
    return relics;
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
      BasicStatsSet relicStats=relic.getStats();
      ret.addStats(relicStats);
    }
    return ret;
  }
}
