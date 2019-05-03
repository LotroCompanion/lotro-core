package delta.games.lotro.lore.deeds;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.deeds.geo.DeedGeoData;
import delta.games.lotro.lore.quests.Achievable;

/**
 * LOTRO deed description.
 * @author DAM
 */
public class DeedDescription extends Achievable
{
  /**
   * Deed legacy key (lotro-wiki key).
   */
  private String _key;
  /**
   * Deed type.
   */
  private DeedType _type;
  private String _objectives;
  // Links
  private DeedProxy _previous;
  private DeedProxy _next;
  private DeedProxies _parents;
  private DeedProxies _children;
  // Geographic data
  private DeedGeoData _geo;

  /**
   * Constructor.
   */
  public DeedDescription()
  {
    super();
    _key=null;
    _type=DeedType.SLAYER;
    _objectives="";
    _previous=null;
    _next=null;
    _parents=new DeedProxies();
    _children=new DeedProxies();
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
   * Get the objectives of this deed.
   * @return the objectives of this deed.
   */
  public String getObjectivesString()
  {
    return _objectives;
  }

  /**
   * Set the objectives of this deed.
   * @param objectives the objectives to set.
   */
  public void setObjectivesString(String objectives)
  {
    _objectives=objectives;
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
   * Get the parent deed proxies, if any.
   * @return a deed proxies collection.
   */
  public DeedProxies getParentDeedProxies()
  {
    return _parents;
  }

  /**
   * Get the child deeds.
   * @return a deed proxies collection.
   */
  public DeedProxies getChildDeedProxies()
  {
    return _children;
  }

  /**
   * Get the geographic data for this deed.
   * @return Some data, or <code>null</code> if none.
   */
  public DeedGeoData getGeoData()
  {
    return _geo;
  }

  /**
   * Set the geographic data for this deed.
   * @param geo Data to set.
   */
  public void setGeoData(DeedGeoData geo)
  {
    _geo=geo;
  }

  /**
   * Dump the contents of this deed as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    super.dumpFirstLine(sb);
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
    sb.append(EndOfLine.NATIVE_EOL);
    if (_objectives.length()>0)
    {
      sb.append("Objectives: ").append(_objectives).append(EndOfLine.NATIVE_EOL);
    }
    // TODO Previous, next, parents, children
    return sb.toString();
  }
}
