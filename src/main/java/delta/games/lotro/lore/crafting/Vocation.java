package delta.games.lotro.lore.crafting;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Vocation.
 * @author DAM
 */
public class Vocation implements Identifiable,Named
{
  private int _id;
  private String _key;
  private String _name;
  private String _description;
  private List<Profession> _professions;

  /**
   * Constructor.
   */
  public Vocation()
  {
    _id=0;
    _key=null;
    _name="";
    _description="";
    _professions=new ArrayList<Profession>();
  }

  /**
   * Get the identifier of this object.
   * @return an object identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this object.
   * @param id Identifier to set.
   */
  public void setIdentifier(int id)
  {
    _id=id;
  }

  /**
   * Get the identifying key for this vocation.
   * @return the identifying key for this vocation.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the identifying key for this vocation. 
   * @param key Key to set.
   */
  public void setKey(String key)
  {
    _key=key;
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
   * Set the name of this vocation.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the description of this vocation. 
   * @return the description of this vocation.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this vocation.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Add a profession.
   * @param profession Profession to add.
   */
  public void addProfession(Profession profession)
  {
    _professions.add(profession);
  }

  /**
   * Get the professions of this vocation. 
   * @return A list of professions.
   */
  public List<Profession> getProfessions()
  {
    return new ArrayList<Profession>(_professions);
  }

  /**
   * Get a list of available guilds.
   * @return A list of available guilds (1 or 2).
   */
  public List<Profession> getAvailableGuilds()
  {
    List<Profession> ret=new ArrayList<Profession>();
    for(Profession profession : _professions)
    {
      if (profession.hasGuild())
      {
        ret.add(profession);
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
