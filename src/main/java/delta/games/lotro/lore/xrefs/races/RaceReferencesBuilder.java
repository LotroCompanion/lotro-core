package delta.games.lotro.lore.xrefs.races;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.races.RaceDescription;

/**
 * Finds references to races.
 * @author DAM
 */
public class RaceReferencesBuilder
{
  private List<RaceReference<?>> _storage;

  /**
   * Constructor.
   */
  public RaceReferencesBuilder()
  {
    _storage=new ArrayList<RaceReference<?>>();
  }

  /**
   * Search for a race.
   * @param race Race.
   * @return the found references.
   */
  public List<RaceReference<?>> inspectRace(RaceDescription race)
  {
    _storage.clear();
    findInRaces(race);
    List<RaceReference<?>> ret=new ArrayList<RaceReference<?>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInRaces(RaceDescription race)
  {
    for(String classKey : race.getAllowedClasses())
    {
      ClassDescription characterClass=ClassesManager.getInstance().getCharacterClassByKey(classKey);
      _storage.add(new RaceReference<ClassDescription>(characterClass,RaceRole.CLASS_RACE));
    }
  }
}
