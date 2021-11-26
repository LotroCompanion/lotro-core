package delta.games.lotro.lore.items.paper;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Paper item description.
 * @author DAM
 */
public class PaperItem implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private String _itemClass;
  private String _category;
  private boolean _accountShared;
  private boolean _free;
  private Integer _iconId;
  private Integer _cap;
  private boolean _old;
  // LATER OR NEVER: conversion data (if old)

  /**
   * Constructor.
   * @param id Paper item identifier.
   */
  public PaperItem(int id)
  {
    _identifier=id;
    _name="";
    _itemClass="";
    _category="";
    _accountShared=false;
    _free=true;
    _iconId=null;
    _cap=null;
    _old=false;
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the paper item name.
   * @return a paper item name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the paper item name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null) name="";
    _name=name;
  }

  /**
   * Get the item class.
   * @return an item class.
   */
  public String getItemClass()
  {
    return _itemClass;
  }

  /**
   * Set the item class.
   * @param itemClass Item class to set.
   */
  public void setItemClass(String itemClass)
  {
    if (itemClass==null) itemClass="";
    _itemClass=itemClass;
  }

  /**
   * Get the category.
   * @return a category.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category.
   * @param category Category to set.
   */
  public void setCategory(String category)
  {
    if (category==null) category="";
    _category=category;
  }

  /**
   * Indicates if this item is shared or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isShared()
  {
    return _accountShared;
  }

  /**
   * Set the 'shared' flag for this item.
   * @param shared Value to set.
   */
  public void setShared(boolean shared)
  {
    _accountShared=shared;
  }

  /**
   * Indicates if this item is free or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isFree()
  {
    return _free;
  }

  /**
   * Set the 'free' flag for this item.
   * @param free Value to set.
   */
  public void setFree(boolean free)
  {
    _free=free;
  }

  /**
   * Get the icon ID for this paper item.
   * @return an icon ID or <code>null</code>.
   */
  public Integer getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID for this paper item.
   * @param iconId Icon ID to set.
   */
  public void setIconId(Integer iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the cap for this paper item.
   * @return a cap or <code>null</code>.
   */
  public Integer getCap()
  {
    return _cap;
  }

  /**
   * Set the cap for this paper item.
   * @param cap Cap to set.
   */
  public void setCap(Integer cap)
  {
    _cap=cap;
  }

  /**
   * Indicates if this item is old/obsolete or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isOld()
  {
    return _old;
  }

  /**
   * Set the 'old' flag for this item.
   * @param old Value to set.
   */
  public void setOld(boolean old)
  {
    _old=old;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Paper item: ID=").append(_identifier);
    sb.append(", name=").append(_name);
    sb.append(", class=").append(_category);
    sb.append(", category=").append(_category);
    if (_accountShared)
    {
      sb.append(", shared=").append(_accountShared);
    }
    if (_free)
    {
      sb.append(", free=").append(_free);
    }
    if (_iconId!=null)
    {
      sb.append(", iconID=").append(_iconId);
    }
    if (_cap!=null)
    {
      sb.append(", cap=").append(_cap);
    }
    if (_old)
    {
      sb.append(", old=").append(_old);
    }
    return sb.toString();
  }
}
