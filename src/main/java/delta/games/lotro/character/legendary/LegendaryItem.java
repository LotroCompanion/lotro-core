package delta.games.lotro.character.legendary;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.legendary.relics.Relic;
import delta.games.lotro.character.legendary.relics.RelicType;
import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Legendary item description.
 * @author DAM
 */
public class LegendaryItem
{
  private String _type;
  /*
  public enum ITEM_TYPE
  {
    WEAPON,
    CLASS
  }
  private boolean _imbued;
  private int _requiredLevel;
  private CharacterClass _requiredClass;
  private ITEM_TYPE _type;
  */
  private LegendaryTitle _title;
  private String _name;
  private String _crafterName;
  private BasicStatsSet _passives;
  // Legacies
  // TODO
  // Relics
  private Relic _setting;
  private Relic _gem;
  private Relic _rune;
  private Relic _crafted;

  // Subclass
  //private WeaponType _weaponType;

  /**
   * Constructor.
   */
  public LegendaryItem()
  {
    _passives=new BasicStatsSet();
  }

  /**
   * Get the item type.
   * @return a type.
   */
  public String getItemType()
  {
    return _type;
  }

  /**
   * Set the item type.
   * @param type the type to set.
   */
  public void setItemType(String type)
  {
    _type=type;
  }

  /**
   * Get the item name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the item name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the crafter name (if any).
   * @return A crafter name or <code>null</code>.
   */
  public String getCrafterName()
  {
    return _crafterName;
  }

  /**
   * Set the crafter name.
   * @param crafterName Name to set.
   */
  public void setCrafterName(String crafterName)
  {
    _crafterName=crafterName;
  }

  /**
   * Get passive stats.
   * @return a set of stats.
   */
  public BasicStatsSet getPassives()
  {
    return _passives;
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
   * Get the total raw stats for this item, including:
   * <ul>
   * <li>passives,
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
    // Passives
    ret.addStats(_passives);
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
