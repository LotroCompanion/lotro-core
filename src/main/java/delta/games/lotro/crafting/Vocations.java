package delta.games.lotro.crafting;

import java.util.HashMap;

/**
 * Vocations registry.
 * @author DAM
 */
public class Vocations
{
  private static Vocations _instance=new Vocations();

  private HashMap<String,Vocation> _vocations;

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
    _vocations=new HashMap<String,Vocation>();
    addVocation("Armourer",Profession.PROSPECTOR,Profession.METALSMITH,Profession.TAILOR);
    addVocation("Armsman",Profession.PROSPECTOR,Profession.WEAPONSMITH,Profession.WOODWORKER);
    addVocation("Explorer",Profession.FORESTER,Profession.PROSPECTOR,Profession.TAILOR);
    addVocation("Historian",Profession.FARMER,Profession.SCHOLAR,Profession.WEAPONSMITH);
    addVocation("Tinker",Profession.PROSPECTOR,Profession.COOK,Profession.JEWELLER);
    addVocation("Woodsman",Profession.FARMER,Profession.FORESTER,Profession.WOODWORKER);
    addVocation("Yeoman",Profession.FARMER,Profession.COOK,Profession.TAILOR);
  }

  private void addVocation(String name, Profession... professions)
  {
    Vocation v=new Vocation(name,professions);
    _vocations.put(name,v);
  }

  /**
   * Get a vocation by its name. 
   * @param name Name of vocation to get.
   * @return A vocation or <code>null</code> if not found.
   */
  public Vocation getVocationByName(String name)
  {
    return _vocations.get(name);
  }
}
