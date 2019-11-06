package delta.games.lotro.lore.crafting;

/**
 * Facade for the crafting system.
 * @author DAM
 */
public class CraftingData
{
  private Vocations _vocations;
  private Professions _professions;

  /**
   * Constructor.
   */
  public CraftingData()
  {
    _vocations=new Vocations();
    _professions=new Professions();
  }

  /**
   * Get the vocations registry.
   * @return the vocations registry.
   */
  public Vocations getVocationsRegistry()
  {
    return _vocations;
  }

  /**
   * Get the professions registry.
   * @return the professions registry.
   */
  public Professions getProfessionsRegistry()
  {
    return _professions;
  }
}
