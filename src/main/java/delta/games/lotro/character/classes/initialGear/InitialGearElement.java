package delta.games.lotro.character.classes.initialGear;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Definition of a piece of the initial gear of a class.
 * @author DAM
 */
public class InitialGearElement
{
  private Item _item;
  private ClassDescription _class;
  private RaceDescription _requiredRace;

  /**
   * Constructor.
   * @param item Item.
   * @param classDecription Class. 
   * @param race Race (may be <code>null</code>).
   */
  public InitialGearElement(Item item, ClassDescription classDecription, RaceDescription race)
  {
    _item=item;
    _class=classDecription;
    _requiredRace=race;
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
   * Get the description of the managed class.
   * @return A class description.
   */
  public ClassDescription getClassDescription()
  {
    return _class;
  }

  /**
   * Get the race requirement for this element.
   * @return a race or <code>null</code> if applicable for all races.
   */
  public RaceDescription getRequiredRace()
  {
    return _requiredRace;
  }
}
