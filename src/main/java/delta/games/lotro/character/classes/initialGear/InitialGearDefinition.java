package delta.games.lotro.character.classes.initialGear;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Definition of the initial gear for a character class.
 * @author DAM
 */
public class InitialGearDefinition
{
  private String _classKey;
  private List<InitialGearElement> _elements;

  /**
   * Constructor.
   * @param classKey Key of the managed class.
   */
  public InitialGearDefinition(String classKey)
  {
    _classKey=classKey;
    _elements=new ArrayList<InitialGearElement>();
  }

  /**
   * Get the key of the managed class.
   * @return a character class key.
   */
  public String getClassKey()
  {
    return _classKey;
  }

  /**
   * Add a gear element.
   * @param element Element to add.
   */
  public void addGearElement(InitialGearElement element)
  {
    _elements.add(element);
  }

  /**
   * Get a list of all gear elements.
   * @return a list of gear elements.
   */
  public List<InitialGearElement> getElements()
  {
    return _elements;
  }

  /**
   * Get the items to use for a given race.
   * @param race Race to use.
   * @return A list of item identifiers.
   */
  public List<Item> getItems(RaceDescription race)
  {
    List<Item> itemIds=new ArrayList<Item>();
    for(InitialGearElement element : _elements)
    {
      RaceDescription requiredRace=element.getRequiredRace();
      if ((requiredRace==null) || (requiredRace==race))
      {
        itemIds.add(element.getItem());
      }
    }
    return itemIds;
  }
}
