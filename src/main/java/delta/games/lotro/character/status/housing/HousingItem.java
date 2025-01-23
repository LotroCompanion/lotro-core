package delta.games.lotro.character.status.housing;

import delta.common.utils.math.geometry.Vector3D;
import delta.games.lotro.common.enums.HousingHookID;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Item in a house.
 * @author DAM
 */
public class HousingItem
{
  private int _itemID;
  private InternalGameId _entityID;
  private HousingHookID _hookID;
  private Vector3D _positionOffset;
  private float _rotationOffset;
  private float _hookRotation;
  private InternalGameId _boundTo;

  /**
   * Constructor.
   * @param itemID Item identifier.
   * @param entityID Entity ID.
   * @param hookID Hook ID.
   */
  public HousingItem(int itemID, InternalGameId entityID, HousingHookID hookID)
  {
    _itemID=itemID;
    _entityID=entityID;
    _hookID=hookID;
    _positionOffset=new Vector3D();
  }

  /**
   * Get the item identifier.
   * @return the item identifier.
   */
  public int getItemID()
  {
    return _itemID;
  }

  /**
   * Get the entity identifier.
   * @return the entity identifier.
   */
  public InternalGameId getEntityID()
  {
    return _entityID;
  }

  /**
   * Get the hook identifier.
   * @return the hook identifier.
   */
  public HousingHookID getHookID()
  {
    return _hookID;
  }

  /**
   * Get the position offset.
   * @return the position offset.
   */
  public Vector3D getPositionOffset()
  {
    return _positionOffset;
  }

  /**
   * Set the position offset.
   * @param positionOffset the position offset to set.
   */
  public void setPositionOffset(Vector3D positionOffset)
  {
    if (positionOffset!=null)
    {
      _positionOffset=positionOffset;
    }
  }

  /**
   * Get the rotation offset.
   * @return the rotation offset.
   */
  public float getRotationOffset()
  {
    return _rotationOffset;
  }

  /**
   * Set the rotation offset.
   * @param rotationOffset the rotation offset to set.
   */
  public void setRotationOffset(float rotationOffset)
  {
    _rotationOffset=rotationOffset;
  }

  /**
   * Get the hook rotation.
   * @return the hook rotation.
   */
  public float getHookRotation()
  {
    return _hookRotation;
  }

  /**
   * Set the hook rotation.
   * @param hookRotation the hook rotation to set.
   */
  public void setHookRotation(float hookRotation)
  {
    _hookRotation=hookRotation;
  }

  /**
   * Get the item binding.
   * @return a owner identifier or <code>null</code> if not bound.
   */
  public InternalGameId getBoundTo()
  {
    return _boundTo;
  }

  /**
   * Set the item binding.
   * @param boundTo the owner identifier to set (may be <code>null</code>).
   */
  public void setBoundTo(InternalGameId boundTo)
  {
    _boundTo=boundTo;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Housing item:");
    sb.append(" item ID=").append(_itemID);
    sb.append(", entity ID=").append(_entityID.asDisplayableString());
    sb.append(", hook ID=").append(_hookID);
    sb.append(", position offset=").append(_positionOffset);
    if (Math.abs(_rotationOffset)>0.001)
    {
      sb.append(", rotation offset=").append(_rotationOffset);
    }
    if (Math.abs(_hookRotation)>0.001)
    {
      sb.append(", hook offset=").append(_hookRotation);
    }
    if (_boundTo!=null)
    {
      sb.append(", bound to=").append(_boundTo.asDisplayableString());
    }
    return sb.toString();
  }
}
