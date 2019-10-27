package delta.games.lotro.lore.items.legendary;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsSet;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;

/**
 * Legendary item instance description.
 * @author DAM
 */
public class LegendaryInstanceAttrs
{
  // Name
  private String _legendaryName;
  // Title
  private LegendaryTitle _title;
  // Relics
  private RelicsSet _relics;
  // Passives
  private List<Effect> _passives;
  // Imbued or not?
  private boolean _imbued;
  // Imbued attributes
  private ImbuedLegendaryInstanceAttrs _imbuedAttrs;
  // Non-imbued attributes
  private NonImbuedLegendaryInstanceAttrs _nonImbuedAttrs;

  /**
   * Constructor.
   */
  public LegendaryInstanceAttrs()
  {
    _legendaryName="";
    _relics=new RelicsSet();
    _passives=new ArrayList<Effect>();
    _imbued=false;
    _imbuedAttrs=new ImbuedLegendaryInstanceAttrs();
    _nonImbuedAttrs=new NonImbuedLegendaryInstanceAttrs();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryInstanceAttrs(LegendaryInstanceAttrs source)
  {
    _legendaryName=source._legendaryName;
    _title=source._title;
    _relics=new RelicsSet(source._relics);
    _passives=new ArrayList<Effect>();
    _passives.addAll(source._passives);
    _imbued=source._imbued;
    _imbuedAttrs=new ImbuedLegendaryInstanceAttrs(source._imbuedAttrs);
    _nonImbuedAttrs=new NonImbuedLegendaryInstanceAttrs(source._nonImbuedAttrs);
  }

  /**
   * Get the name of this legendary item.
   * @return a name (never <code>null</code>).
   */
  public String getLegendaryName()
  {
    return _legendaryName;
  }

  /**
   * Set the name of this legendary item.
   * @param legendaryName the name to set.
   */
  public void setLegendaryName(String legendaryName)
  {
    if (legendaryName==null) legendaryName="";
    _legendaryName=legendaryName;
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
   * Get the relics set.
   * @return the relics set.
   */
  public RelicsSet getRelicsSet()
  {
    return _relics;
  }

  /**
   * Add a passive.
   * @param effect Passive to add.
   */
  public void addPassive(Effect effect)
  {
    _passives.add(effect);
  }

  /**
   * Remove a passive.
   * @param effect Legacy to remove.
   */
  public void removePassive(Effect effect)
  {
    _passives.remove(effect);
  }

  /**
   * Remove all passives.
   */
  public void removeAllPassvies()
  {
    _passives.clear();
  }

  /**
   * Get a list of all passives.
   * @return a list of all passives.
   */
  public List<Effect> getPassives()
  {
    return new ArrayList<Effect>(_passives);
  }

  /**
   * Indicates if this legendary instance is imbued or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isImbued()
  {
    return _imbued;
  }

  /**
   * Set the imbued state for this legendary instance.
   * @param imbued <code>true</code> if imbued, <code>false</code> otherwise.
   */
  public void setImbued(boolean imbued)
  {
    _imbued=imbued;
  }

  /**
   * Get the non-imbued attributes.
   * @return the non-imbued attributes.
   */
  public NonImbuedLegendaryInstanceAttrs getNonImbuedAttrs()
  {
    return _nonImbuedAttrs;
  }

  /**
   * Get the imbued attributes.
   * @return the imbued attributes.
   */
  public ImbuedLegendaryInstanceAttrs getImbuedAttrs()
  {
    return _imbuedAttrs;
  }

  /**
   * Get the total stats for these legendary attributes, including:
   * @param itemLevel Item level.
   * <ul>
   * <li>title stats,
   * <li>passive stats,
   * <li>relics stats.
   * </ul>
   * @return a set of stats.
   */
  public BasicStatsSet getRawStats(int itemLevel)
  {
    BasicStatsSet ret=new BasicStatsSet();
    // Title
    if (_title!=null)
    {
      BasicStatsSet titleStats=_title.getStats();
      ret.addStats(titleStats);
    }
    // Relics
    List<Relic> relics=_relics.getRelics();
    for(Relic relic : relics)
    {
      if (relic!=null)
      {
        BasicStatsSet relicStats=relic.getStats();
        ret.addStats(relicStats);
      }
    }
    // Passives
    BasicStatsSet passivesStats=getPassivesStats(itemLevel);
    ret.addStats(passivesStats);
    return ret;
  }

  /**
   * Get the stats provided by the passives.
   * @param itemLevel Item level to use.
   * @return some stats.
   */
  public BasicStatsSet getPassivesStats(int itemLevel)
  {
    BasicStatsSet ret=new BasicStatsSet();
    int passivesLevel=findLevelForPassives(itemLevel);
    for(Effect passive : _passives)
    {
      StatsProvider statsProvider=passive.getStatsProvider();
      BasicStatsSet stats=statsProvider.getStats(1,passivesLevel);
      ret.addStats(stats);
    }
    return ret;
  }

  /**
   * Find the level to use for passive stats.
   * @param itemLevel Item level.
   * @return a level.
   */
  public int findLevelForPassives(int itemLevel)
  {
    int ret=itemLevel;
    if (_imbued)
    {
      ImbuedLegacyInstance mainLegacyInstance=_imbuedAttrs.getMainLegacy();
      if (mainLegacyInstance!=null)
      {
        int mainLegacyTier=mainLegacyInstance.getCurrentLevel();
        ret+=mainLegacyTier;
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
    sb.append("Name: ").append(_legendaryName).append(EndOfLine.NATIVE_EOL);
    if (_title!=null)
    {
      sb.append("Title: ").append(_title.getName()).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Relics:").append(EndOfLine.NATIVE_EOL);
    for(Relic relic : _relics.getRelics())
    {
      if (relic!=null)
      {
        sb.append('\t').append(relic).append(EndOfLine.NATIVE_EOL);
      }
    }
    int nbPassives=_passives.size();
    if (nbPassives>0)
    {
      sb.append("Passives:").append(EndOfLine.NATIVE_EOL);
      for(Effect passive : _passives)
      {
        sb.append('\t').append(passive).append(EndOfLine.NATIVE_EOL);
      }
    }
    sb.append("Imbued: ").append(_imbued).append(EndOfLine.NATIVE_EOL);
    if (_nonImbuedAttrs!=null)
    {
      sb.append(_nonImbuedAttrs).append(EndOfLine.NATIVE_EOL);
    }
    if (_imbuedAttrs!=null)
    {
      sb.append(_imbuedAttrs).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return dump();
  }
}
