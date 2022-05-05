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
  private String _description;
  private String _progress;
  private AbstractWorldEventCondition _valueComputer;

  /**
   * Constructor.
   * @param worldEventID Identifier.
   * @param propertyID Identifier of the associated property.
   */
  public WorldEvent(int worldEventID, int propertyID)
  {
    _identifier=worldEventID;
    _propertyID=propertyID;
  }

  /**
   * Get the identifier of this allegiance.
   * @return the identifier of this allegiance.
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
   * Set the value computer for this world event.
   * @return a value computer or <code>null</code> if none.
   */
  public AbstractWorldEventCondition getValueComputer()
  {
    return _valueComputer;
  }

  /**
   * Set the value computer for this world event.
   * @param valueComputer the value computer to set.
   */
  public void setValueComputer(AbstractWorldEventCondition valueComputer)
  {
    _valueComputer=valueComputer;
  }
}
