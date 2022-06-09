package delta.games.lotro.lore.xrefs.races;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

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
  public List<RaceReference<?>> inspectRace(Race race)
  {
    _storage.clear();
    findInClasses(race);
    List<RaceReference<?>> ret=new ArrayList<RaceReference<?>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInClasses(Race race)
  {
    RaceDescription description=RacesManager.getInstance().getRaceDescription(race);
    if (description!=null)
    {
      for(CharacterClass characterClass : description.getAllowedClasses())
      {
        _storage.add(new RaceReference<CharacterClass>(characterClass,RaceRole.CLASS_RACE));
      }
    }
  }
}
