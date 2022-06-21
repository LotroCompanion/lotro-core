package delta.games.lotro.lore.titles;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * LOTRO title description.
 * @author DAM
 */
public class TitleDescription implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private int _iconId;
  private String _category;
  private String _description;
  private String _exclusionGroup;
  private Integer _priority;

  /**
   * Constructor.
   */
  public TitleDescription()
  {
    _identifier=0;
    _name="";
    _category="";
    _description="";
    _iconId=0;
    _exclusionGroup=null;
    _priority=null;
  }

  /**
   * Get the identifier of this title.
   * @return the identifier of this title.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this title.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this title.
   * @return the name of this title.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this title.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null) name="";
    _name=name;
  }

  /**
   * Get the icon ID of this title.
   * @return the icon ID of this title.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID of this title.
   * @param iconId the icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the category of this title.
   * @return the category of this title.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this title.
   * @param category the category to set.
   */
  public void setCategory(String category)
  {
    if (category==null) category="";
    _category=category;
  }

  /**
   * Get the description of this title.
   * @return the description of this title.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this title.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Get the exclusion group.
   * @return An exclusion group or <code>null</code>.
   */
  public String getExclusionGroup()
  {
    return _exclusionGroup;
  }

  /**
   * Set the exclusion group.
   * @param exclusionGroup Exclusion group to set.
   */
  public void setExclusionGroup(String exclusionGroup)
  {
    _exclusionGroup=exclusionGroup;
  }

  /**
   * Get the priority.
   * @return A priority value or <code>null</code>.
   */
  public Integer getPriority()
  {
    return _priority;
  }

  /**
   * Set the priority.
   * @param priority Priority to set.
   */
  public void setPriority(Integer priority)
  {
    _priority=priority;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Title id=").append(_identifier);
    sb.append(", name=").append(_name);
    if (_iconId!=0)
    {
      sb.append(", icon=").append(_iconId);
    }
    sb.append(", category=").append(_category);
    sb.append(", description=").append(_description);
    if (_exclusionGroup!=null)
    {
      sb.append(", exclusion group=").append(_exclusionGroup);
    }
    if (_priority!=null)
    {
      sb.append(", priority=").append(_priority);
    }
    return sb.toString();
  }
}
