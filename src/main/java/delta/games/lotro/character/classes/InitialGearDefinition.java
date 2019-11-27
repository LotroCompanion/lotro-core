package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Race;

/**
 * Definition of the initial gear for a character class.
 * @author DAM
 */
public class InitialGearDefinition
{
  private List<InitialGearElement> _elements;

  /**
   * Constructor.
   */
  public InitialGearDefinition()
  {
    _elements=new ArrayList<InitialGearElement>();
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
  public List<Integer> getItems(Race race)
  {
    List<Integer> itemIds=new ArrayList<Integer>();
    for(InitialGearElement element : _elements)
    {
      Race requiredRace=element.getRequiredRace();
      if ((requiredRace==null) || (requiredRace==race))
      {
        itemIds.add(Integer.valueOf(element.getItemId()));
      }
    }
    return itemIds;
  }
}
