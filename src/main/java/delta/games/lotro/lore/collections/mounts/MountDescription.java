package delta.games.lotro.lore.collections.mounts;

import delta.games.lotro.lore.collections.Collectable;

/**
 * Mount.
 * @author DAM
 */
public class MountDescription implements Collectable
{
  private int _identifier;
  private String _name;
  private String _initialName;
  private String _category;
  private String _mountType;
  private String _description;
  private String _sourceDescription;
  private int _iconId;
  private int _morale;
  private float _speed;
  private boolean _tall;

  /**
   * Constructor.
   * @param id Mount identifier.
   */
  public MountDescription(int id)
  {
    _identifier=id;
    _name="";
    _initialName="";
    _category="";
    _mountType="";
    _description="";
    _sourceDescription="";
    _iconId=0;
    _morale=0;
    _speed=0;
    _tall=false;
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
   * Get the mount name.
   * @return a mount name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the mount name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null) name="";
    _name=name;
  }

  /**
   * Get the mount initial name.
   * @return a mount name.
   */
  public String getInitialName()
  {
    return _initialName;
  }

  /**
   * Set the mount initial name.
   * @param initialName Name to set.
   */
  public void setInitialName(String initialName)
  {
    if (initialName==null) initialName="";
    _initialName=initialName;
  }

  /**
   * Get the mount type.
   * @return a mount type.
   */
  public String getMountType()
  {
    return _mountType;
  }

  /**
   * Set the mount type.
   * @param mountType Type to set.
   */
  public void setMountType(String mountType)
  {
    if (mountType==null) mountType="";
    _mountType=mountType;
  }

  /**
   * Get the mount category.
   * @return a mount category.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the mount category.
   * @param category Category to set.
   */
  public void setCategory(String category)
  {
    if (category==null) category="";
    _category=category;
  }

  /**
   * Get the mount description.
   * @return a mount description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the mount description.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Get the description of the mount source.
   * @return a description.
   */
  public String getSourceDescription()
  {
    return _sourceDescription;
  }

  /**
   * Set the description of the mount source.
   * @param sourceDescription Description to set.
   */
  public void setSourceDescription(String sourceDescription)
  {
    if (sourceDescription==null) sourceDescription="";
    _sourceDescription=sourceDescription;
  }

  /**
   * Get the icon ID for this mount.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID for this mount.
   * @param iconId Icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the morale of this mount.
   * @return a morale value.
   */
  public int getMorale()
  {
    return _morale;
  }

  /**
   * Set the morale of this mount.
   * @param morale Morale to set.
   */
  public void setMorale(int morale)
  {
    _morale=morale;
  }

  /**
   * Get the speed of this mount.
   * @return a speed value.
   */
  public float getSpeed()
  {
    return _speed;
  }

  /**
   * Set the speed of this mount.
   * @param speed Speed to set.
   */
  public void setSpeed(float speed)
  {
    _speed=speed;
  }

  /**
   * Indicates if this mount is tall (horse) or short (pony).
   * @return <code>true</code> if tall, <code>false</code> if short.
   */
  public boolean isTall()
  {
    return _tall;
  }

  /**
   * Set the 'tall' flag.
   * @param tall Value to set.
   */
  public void setTall(boolean tall)
  {
    _tall=tall;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Mount: ID=").append(_identifier);
    sb.append(", name=").append(_name);
    sb.append(", initial name=").append(_initialName);
    sb.append(", category=").append(_category);
    sb.append(", mount type=").append(_mountType);
    sb.append(", description=").append(_description);
    sb.append(", source description=").append(_sourceDescription);
    sb.append(", iconID=").append(_iconId);
    sb.append(", morale=").append(_morale);
    sb.append(", speed=").append(_speed);
    sb.append(", tall=").append(_tall);
    return sb.toString();
  }
}
