package delta.games.lotro.character.classes.initialGear;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Definition of a piece of the initial gear of a class.
 * @author DAM
 */
public class InitialGearElement
{
  private Item _item;
  private RaceDescription _requiredRace;

  /**
   * Constructor.
   */
  public InitialGearElement()
  {
    _item=null;
    _requiredRace=null;
  }

  /**
   * Get the targeted item.
   * @return an item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Set the item.
   * @param item Item to set.
   */
  public void setItem(Item item)
  {
    _item=item;
  }

  /**
   * Get the race requirement for this element.
   * @return a race or <code>null</code> if applicable for all races.
   */
  public RaceDescription getRequiredRace()
  {
    return _requiredRace;
  }

  /**
   * Set the required race for this element.
   * @param race A race or <code>null</code> if no race requirement.
   */
  public void setRequiredRace(RaceDescription race)
  {
    _requiredRace=race;
  }
}
