package delta.games.lotro.crafting;

import java.util.ArrayList;
import java.util.List;

/**
 * Vocation.
 * @author DAM
 */
public class Vocation
{
  private String _name;
  private List<Profession> _professions;

  /**
   * Constructor.
   * @param name
   * @param professions
   */
  public Vocation(String name, Profession... professions)
  {
    _name=name;
    _professions=new ArrayList<Profession>();
    for(Profession profession : professions)
    {
      _professions.add(profession);
    }
  }

  /**
   * Get the name of this vocation. 
   * @return the name of this vocation.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the professions of this vocation. 
   * @return An array of professions.
   */
  public Profession[] getProfessions()
  {
    return _professions.toArray(new Profession[_professions.size()]);
  }
}
