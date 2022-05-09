package delta.games.lotro.lore.worldEvents;

import delta.games.lotro.common.Identifiable;

/**
 * World event definition.
 * @author DAM
 */
public class WorldEvent implements Identifiable
{
  private int _identifier;
  private int _propertyID;
  private String _propertyName;
  private String _description;
  private String _progress;

  /**
   * Constructor.
   */
  public WorldEvent()
  {
    // Nothing!
  }

  /**
   * Set the world identifier.
   * @param identifier Identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the identifier of this world event.
   * @return the identifier of this world event.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the identifier of the associated property.
   * @return a property identifier.
   */
  public int getPropertyID()
  {
    return _propertyID;
  }

  /**
   * Set the property ID.
   * @param propertyID Property ID to set.
   */
  public void setPropertyID(int propertyID)
  {
    _propertyID=propertyID;
  }

  /**
   * Get the description of this world event.
   * @return a description or <code>null</code>.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description for this world event.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the progress label for this world event.
   * @return A progress label or <code>null</code> if none.
   */
  public String getProgress()
  {
    return _progress;
  }

  /**
   * Set the progress label for this world event.
   * @param progress the progress label to set.
   */
  public void setProgress(String progress)
  {
    _progress=progress;
  }

  /**
   * Get the property name of this world event.
   * @return a property name or <code>null</code>.
   */
  public String getPropertyName()
  {
    return _propertyName;
  }

  /**
   * Set the property name for this world event.
   * @param propertyName the property name to set.
   */
  public void setPropertyName(String propertyName)
  {
    _propertyName=propertyName;
  }

  /**
   * Get a short label for this world event. 
   * @return A short label.
   */
  public String getShortLabel()
  {
    StringBuilder sb=new StringBuilder("WE ID=");
    sb.append(_identifier);
    if (_propertyName!=null)
    {
      sb.append(" (");
      sb.append(_propertyName);
      sb.append(')');
    }
    return sb.toString();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("WE ID=");
    sb.append(_identifier);
    if (_propertyName!=null)
    {
      sb.append(", property=");
      sb.append(_propertyName);
    }
    if (_description!=null)
    {
      sb.append(", description=");
      sb.append(_description);
    }
    return sb.toString();
  }
}
