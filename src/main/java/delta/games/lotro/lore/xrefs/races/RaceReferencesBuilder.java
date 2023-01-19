package delta.games.lotro.lore.xrefs.races;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.CharacterClass;

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
    for(CharacterClass characterClass : race.getAllowedClasses())
    {
      _storage.add(new RaceReference<CharacterClass>(characterClass,RaceRole.CLASS_RACE));
    }
  }
}
