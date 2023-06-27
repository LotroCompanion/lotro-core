package delta.games.lotro.lore.xrefs.races;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to races.
 * @author DAM
 */
public class RaceReferencesBuilder
{
  private List<Reference<?,RaceRole>> _storage;

  /**
   * Constructor.
   */
  public RaceReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,RaceRole>>();
  }

  /**
   * Search for a race.
   * @param race Race.
   * @return the found references.
   */
  public List<Reference<?,RaceRole>> inspectRace(RaceDescription race)
  {
    _storage.clear();
    findInRaces(race);
    List<Reference<?,RaceRole>> ret=new ArrayList<Reference<?,RaceRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInRaces(RaceDescription race)
  {
    for(String classKey : race.getAllowedClasses())
    {
      ClassDescription characterClass=ClassesManager.getInstance().getCharacterClassByKey(classKey);
      _storage.add(new Reference<ClassDescription,RaceRole>(characterClass,RaceRole.CLASS_RACE));
    }
  }
}
