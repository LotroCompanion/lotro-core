package delta.games.lotro.lore.deeds;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.Rewards;

/**
 * LOTRO deed description.
 * @author DAM
 */
public class DeedDescription
{
  private int _identifier;
  private String _key;
  private String _name;
  private DeedType _type;
  private String _category;
  private String _description;
  private String _objectives;
  // Requirements
  private Race _race;
  private CharacterClass _class;
  private Integer _minLevel;
  // Rewards
  private Rewards _rewards;
  // Links
  private DeedProxy _previous;
  private DeedProxy _next;
  private DeedProxy _parent;
  private List<DeedProxy> _children;

  /**
   * Constructor.
   */
  public DeedDescription()
  {
    _identifier=0;
    _key=null;
    _name="";
    _type=DeedType.SLAYER;
    _category=null;
    _description="";
    _objectives="";
    _race=null;
    _class=null;
    _minLevel=null;
    _rewards=new Rewards();
    _previous=null;
    _next=null;
    _parent=null;
    _children=new ArrayList<DeedProxy>();
  }

  /**
   * Get the identifier of this deed.
   * @return the identifier of this deed.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this deed.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the key of this deed.
   * @return the key of this deed.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the key of this deed.
   * @param key the key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get the name of this deed.
   * @return the name of this deed.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this deed.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the type of this deed.
   * @return the type of this deed.
   */
  public DeedType getType()
  {
    return _type;
  }

  /**
   * Set the type of this deed. 
   * @param type the type to set.
   */
  public void setType(DeedType type)
  {
    _type=type;
  }

  /**
   * Get the category of this deed.
   * @return the category of this deed.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this deed.
   * @param category the category to set.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  /**
   * Get the required race for this deed.
   * @return a race or <code>null</code>.
   */
  public Race getRequiredRace()
  {
    return _race;
  }

  /**
   * Set the required race for this deed.
   * @param race the race to set (or <code>null</code>).
   */
  public void setRequiredRace(Race race)
  {
    _race=race;
  }

  /**
   * Get the required class for this deed.
   * @return a character class or <code>null</code>.
   */
  public CharacterClass getRequiredClass()
  {
    return _class;
  }

  /**
   * Set the required class for this deed.
   * @param characterClass the class to set (or <code>null</code>).
   */
  public void setRequiredClass(CharacterClass characterClass)
  {
    _class=characterClass;
  }

  /**
   * Get the minimum level of this deed.
   * @return the minimum level of this deed.
   */
  public Integer getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level of this deed. 
   * @param minLevel the minimum level to set.
   */
  public void setMinLevel(Integer minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the description of this deed.
   * @return the description of this deed.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this deed.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the objectives of this deed.
   * @return the objectives of this deed.
   */
  public String getObjectives()
  {
    return _objectives;
  }

  /**
   * Set the objectives of this deed.
   * @param objectives the objectives to set.
   */
  public void setObjectives(String objectives)
  {
    _objectives=objectives;
  }

  /**
   * Get the rewards for this deed.
   * @return the rewards.
   */
  public Rewards getRewards()
  {
    return _rewards;
  }

  /**
   * Get a proxy for the previous deed, if any.
   * @return a deed proxy or <code>null</code>.
   */
  public DeedProxy getPreviousDeedProxy()
  {
    return _previous;
  }

  /**
   * Set a proxy for the previous deed.
   * @param previous A proxy or <code>null</code>.
   */
  public void setPreviousDeedProxy(DeedProxy previous)
  {
    _previous=previous;
  }

  /**
   * Get a proxy for the next deed, if any.
   * @return a deed proxy or <code>null</code>.
   */
  public DeedProxy getNextDeedProxy()
  {
    return _next;
  }

  /**
   * Set a proxy for the next deed.
   * @param next A proxy or <code>null</code>.
   */
  public void setNextDeedProxy(DeedProxy next)
  {
    _next=next;
  }

  /**
   * Get a proxy for the parent deed, if any.
   * @return a deed proxy or <code>null</code>.
   */
  public DeedProxy getParentDeedProxy()
  {
    return _parent;
  }

  /**
   * Set a proxy for the parent deed.
   * @param parent A proxy or <code>null</code>.
   */
  public void setParentDeedProxy(DeedProxy parent)
  {
    _parent=parent;
  }

  /**
   * Get the child deeds.
   * @return a possibly empty, but not <code>null</code> list of deed proxies.
   */
  public List<DeedProxy> getChildDeeds()
  {
    return _children;
  }

  /**
   * Dump the contents of this deed as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Name: ").append(_name);
    if (_identifier!=0)
    {
      sb.append(" (");
      sb.append(_identifier);
      sb.append(')');
    }
    if (_key!=null)
    {
      sb.append(" (");
      sb.append(_key);
      sb.append(')');
    }
    if (_type!=null)
    {
      sb.append(" (");
      sb.append(_type);
      sb.append(')');
    }
    if (_category!=null)
    {
      sb.append(" (");
      sb.append(_category);
      sb.append(')');
    }
    if (_race!=null)
    {
      sb.append(" race=");
      sb.append(_race);
    }
    if (_class!=null)
    {
      sb.append(" class=");
      sb.append(_class);
    }
    if (_minLevel!=null)
    {
      sb.append(" level=");
      sb.append(_minLevel);
    }
    if ((_description!=null) && (_description.length()>0))
    {
      sb.append(EndOfLine.NATIVE_EOL);
      sb.append("Description: ").append(_description);
    }
    if (_objectives.length()>0)
    {
      sb.append(EndOfLine.NATIVE_EOL);
      sb.append("Objectives: ").append(_objectives);
    }
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append("Rewards: ").append(_rewards);
    return sb.toString();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Deed id=").append(_identifier);
    if (_key!=null)
    {
      sb.append(", key=").append(_key);
    }
    sb.append(", name=").append(_name);
    sb.append(", type=").append(_type);
    if (_category!=null)
    {
      sb.append(", category=").append(_category);
    }
    if (_class!=null)
    {
      sb.append(", class=").append(_class);
    }
    if (_minLevel!=null)
    {
      sb.append(", min level=").append(_minLevel);
    }
    sb.append(", description=").append(_description);
    sb.append(", objectives=").append(_objectives);
    sb.append(", rewards=").append(_rewards);
    return sb.toString();
  }
}
