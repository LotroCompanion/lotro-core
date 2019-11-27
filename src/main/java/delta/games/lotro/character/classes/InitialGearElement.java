package delta.games.lotro.character.classes;

import delta.games.lotro.common.Race;

/**
 * Definition of a piece of the initial gear of a class.
 * @author DAM
 */
public class InitialGearElement
{
  private int _itemId;
  private Race _requiredRace;

  /**
   * Constructor.
   */
  public InitialGearElement()
  {
    _itemId=0;
    _requiredRace=null;
  }

  /**
   * Get the targeted item identifier.
   * @return an item identifier.
   */
  public int getItemId()
  {
    return _itemId;
  }

  /**
   * Set the item identifier.
   * @param itemId Item identifier to set.
   */
  public void setItemId(int itemId)
  {
    _itemId=itemId;
  }

  /**
   * Get the race requirement for this element.
   * @return a race or <code>null</code> if applicable for all races.
   */
  public Race getRequiredRace()
  {
    return _requiredRace;
  }

  /**
   * Set the required race for this element.
   * @param race A race or <code>null</code> if no race requirement.
   */
  public void setRequiredRace(Race race)
  {
    _requiredRace=race;
  }
}
