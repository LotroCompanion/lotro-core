package delta.games.lotro.lore.collections.mounts;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.enums.MountType;
import delta.games.lotro.common.enums.SkillCharacteristicSubCategory;
import delta.games.lotro.lore.collections.Collectable;

/**
 * Mount.
 * @author DAM
 */
public class MountDescription extends SkillDescription implements Collectable
{
  private String _initialName;
  private SkillCharacteristicSubCategory _category;
  private MountType _mountType;
  private String _sourceDescription;
  private int _morale;
  private float _speed;
  private boolean _tall;
  private int _peerMountId;

  /**
   * Constructor.
   */
  public MountDescription()
  {
    super();
    _initialName="";
    _category=null;
    _mountType=null;
    _sourceDescription="";
    _morale=0;
    _speed=0;
    _tall=false;
    _peerMountId=0;
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
  public MountType getMountType()
  {
    return _mountType;
  }

  /**
   * Set the mount type.
   * @param mountType Type to set.
   */
  public void setMountType(MountType mountType)
  {
    _mountType=mountType;
  }

  /**
   * Get the mount category.
   * @return a mount category.
   */
  public SkillCharacteristicSubCategory getMountCategory()
  {
    return _category;
  }

  /**
   * Set the mount category.
   * @param category Category to set.
   */
  public void setMountCategory(SkillCharacteristicSubCategory category)
  {
    _category=category;
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

  /**
   * Get the ID of the peer mount.
   * @return An ID or 9 if no such mount.
   */
  public int getPeerMountId()
  {
    return _peerMountId;
  }

  /**
   * Set the peer mount ID.
   * @param peerMountId Mount ID to set.
   */
  public void setPeerMountId(int peerMountId)
  {
    _peerMountId=peerMountId;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Mount: ID=").append(getIdentifier());
    sb.append(", name=").append(getName());
    sb.append(", initial name=").append(_initialName);
    sb.append(", category=").append(_category);
    sb.append(", mount type=").append(_mountType);
    sb.append(", description=").append(getDescription());
    sb.append(", source description=").append(_sourceDescription);
    sb.append(", iconID=").append(getIconId());
    sb.append(", morale=").append(_morale);
    sb.append(", speed=").append(_speed);
    sb.append(", tall=").append(_tall);
    return sb.toString();
  }
}
