package delta.games.lotro.common.requirements;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;

/**
 * Race requirement.
 * @author DAM
 */
public class RaceRequirement
{
  private static final String SEPARATOR=";";
  private List<RaceDescription> _allowedRaces;

  /**
   * Constructor.
   */
  public RaceRequirement()
  {
    _allowedRaces=new ArrayList<RaceDescription>();
  }

  /**
   * Add an allowed race.
   * @param race Race to add.
   */
  public void addAllowedRace(RaceDescription race)
  {
    _allowedRaces.add(race);
  }

  /**
   * Get the allowed races.
   * @return A possibly empty, but not <code>null</code> list of races.
   */
  public List<RaceDescription> getAllowedRaces()
  {
    return _allowedRaces;
  }

  /**
   * Indicates if this requirement accepts the given race or not.
   * @param race Race to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean accept(RaceDescription race)
  {
    return _allowedRaces.contains(race);
  }

  /**
   * Get a string representation of this requirement.
   * @return A persistable string.
   */
  public String asString()
  {
    StringBuilder sb=new StringBuilder();
    for(RaceDescription race : _allowedRaces)
    {
      if (sb.length()>0)
      {
        sb.append(SEPARATOR);
      }
      sb.append(race.getKey());
    }
    return sb.toString();
  }

  /**
   * Build a race requirement from a string.
   * @param input Input string (";" separated list of race keys).
   * @return A race requirement or <code>null</code> if none.
   */
  public static RaceRequirement fromString(String input)
  {
    RaceRequirement ret=null;
    if ((input!=null) && (input.length()>0))
    {
      ret=new RaceRequirement();
      String[] raceStrs=input.split(SEPARATOR);
      RacesManager mgr=RacesManager.getInstance();
      for(String raceStr : raceStrs)
      {
        RaceDescription race=mgr.getByKey(raceStr);
        if (race!=null)
        {
          ret.addAllowedRace(race);
        }
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return _allowedRaces.toString();
  }
}
