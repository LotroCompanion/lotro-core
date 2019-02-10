package delta.games.lotro.lore.items.legendary;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Effect;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;

/**
 * Legendary item description.
 * @author DAM
 */
public class LegendaryAttrs
{
  // Name
  private String _legendaryName;
  // Title
  private LegendaryTitle _title;
  // Relics
  private Relic _setting;
  private Relic _gem;
  private Relic _rune;
  private Relic _crafted;
  // Passives
  private List<Effect> _passives;
  // Imbued attributes
  private ImbuedLegendaryAttrs _imbuedAttrs;
  // Non-imbued attributes
  private NonImbuedLegendaryAttrs _nonImbuedAttrs;

  /**
   * Constructor.
   */
  public LegendaryAttrs()
  {
    _legendaryName="";
    _setting=null;
    _gem=null;
    _rune=null;
    _crafted=null;
    _passives=new ArrayList<Effect>();
    _nonImbuedAttrs=new NonImbuedLegendaryAttrs();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryAttrs(LegendaryAttrs source)
  {
    _legendaryName=source._legendaryName;
    _title=source._title;
    _setting=source._setting;
    _gem=source._gem;
    _rune=source._rune;
    _crafted=source._crafted;
    _passives.clear();
    _passives.addAll(source._passives);
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
   * Get a list of all passives.
   * @return a list of all passives.
   */
  public List<Effect> getPassives()
  {
    return new ArrayList<Effect>(_passives);
  }

  /**
   * Get the non-imbued attributes.
   * @return the non-imbued attributes.
   */
  public NonImbuedLegendaryAttrs getNonImbuedAttrs()
  {
    return _nonImbuedAttrs;
  }

  /**
   * Set the non-imbued attributes.
   * @param nonImbuedAttrs Attributes to set.
   */
  public void setNonImbuedAttrs(NonImbuedLegendaryAttrs nonImbuedAttrs)
  {
    _nonImbuedAttrs=nonImbuedAttrs;
  }

  /**
   * Get the imbued attributes.
   * @return the imbued attributes.
   */
  public ImbuedLegendaryAttrs getImbuedAttrs()
  {
    return _imbuedAttrs;
  }

  /**
   * Set the imbued attributes.
   * @param imbuedAttrs Attributes to set.
   */
  public void setImbuedAttrs(ImbuedLegendaryAttrs imbuedAttrs)
  {
    _imbuedAttrs=imbuedAttrs;
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
    List<Relic> relics=getRelics();
    for(Relic relic : relics)
    {
      if (relic!=null)
      {
        BasicStatsSet relicStats=relic.getStats();
        ret.addStats(relicStats);
      }
    }
    // Passives
    for(Effect passive : _passives)
    {
      StatsProvider statsProvider=passive.getStatsProvider();
      BasicStatsSet stats=statsProvider.getStats(1,itemLevel);
      ret.addStats(stats);
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
    if (_legendaryName.length()>0)
    {
      sb.append("Name: ").append(_legendaryName).append(EndOfLine.NATIVE_EOL);
    }
    if (_title!=null)
    {
      sb.append("Title: ").append(_title.getName()).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Relics:").append(EndOfLine.NATIVE_EOL);
    for(Relic relic : getRelics())
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
    if (_nonImbuedAttrs!=null)
    {
      sb.append(_nonImbuedAttrs).append(EndOfLine.NATIVE_EOL);
    }
    if (_imbuedAttrs!=null)
    {
      sb.append(_imbuedAttrs).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString();
  }
}
