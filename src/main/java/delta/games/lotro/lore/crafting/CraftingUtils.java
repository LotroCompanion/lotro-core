package delta.games.lotro.lore.crafting;

/**
 * Utility methods related to crafting.
 * @author DAM
 */
public class CraftingUtils
{
  /**
   * Get a profession using its name.
   * @param professionName Profession name.
   * @return A profession or <code>null</code> if not found.
   */
  public static Profession getProfessionByName(String professionName)
  {
    CraftingData crafting=CraftingSystem.getInstance().getData();
    Professions professions=crafting.getProfessionsRegistry();
    Profession profession=professions.getProfessionByName(professionName);
    return profession;
  }
}
