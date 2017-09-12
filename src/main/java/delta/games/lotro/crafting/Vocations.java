package delta.games.lotro.crafting;

import java.util.HashMap;

/**
 * Vocations registry.
 * @author DAM
 */
public class Vocations
{
  private static Vocations _instance=new Vocations();

  private HashMap<String,Vocation> _vocationsByName;
  private HashMap<String,Vocation> _vocationsById;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static Vocations getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private Vocations()
  {
    _vocationsByName=new HashMap<String,Vocation>();
    _vocationsById=new HashMap<String,Vocation>();
    addVocation("ARMOURER","Armourer",Profession.PROSPECTOR,Profession.METALSMITH,Profession.TAILOR);
    addVocation("ARMSMAN","Armsman",Profession.PROSPECTOR,Profession.WEAPONSMITH,Profession.WOODWORKER);
    addVocation("EXPLORER","Explorer",Profession.FORESTER,Profession.PROSPECTOR,Profession.TAILOR);
    addVocation("HISTORIAN","Historian",Profession.FARMER,Profession.SCHOLAR,Profession.WEAPONSMITH);
    addVocation("TINKER","Tinker",Profession.PROSPECTOR,Profession.COOK,Profession.JEWELLER);
    addVocation("WOODSMAN","Woodsman",Profession.FARMER,Profession.FORESTER,Profession.WOODWORKER);
    addVocation("YEAOMAN","Yeoman",Profession.FARMER,Profession.COOK,Profession.TAILOR);
  }

  private void addVocation(String id, String name, Profession... professions)
  {
    Vocation v=new Vocation(id,name,professions);
    _vocationsById.put(id,v);
    _vocationsByName.put(name,v);
  }

  /**
   * Get a vocation by its identifier. 
   * @param id Identifier of vocation to get.
   * @return A vocation or <code>null</code> if not found.
   */
  public Vocation getVocationById(String id)
  {
    return _vocationsById.get(id);
  }

  /**
   * Get a vocation by its name. 
   * @param name Name of vocation to get.
   * @return A vocation or <code>null</code> if not found.
   */
  public Vocation getVocationByName(String name)
  {
    return _vocationsByName.get(name);
  }
}
