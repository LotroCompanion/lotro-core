package delta.games.lotro.lore.crafting;

import java.util.ArrayList;
import java.util.List;

/**
 * Vocation.
 * @author DAM
 */
public class Vocation
{
  private String _id;
  private String _name;
  private List<Profession> _professions;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Label.
   * @param professions Involved professions.
   */
  public Vocation(String id, String name, Profession... professions)
  {
    _id=id;
    _name=name;
    _professions=new ArrayList<Profession>();
    for(Profession profession : professions)
    {
      _professions.add(profession);
    }
  }

  /**
   * Get the identifier of this vocation. 
   * @return the identifier of this vocation.
   */
  public String getIdentifier()
  {
    return _id;
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
   * @return A list of professions.
   */
  public List<Profession> getProfessions()
  {
    return new ArrayList<Profession>(_professions);
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
