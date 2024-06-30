package delta.games.lotro.tools.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.crafting.CraftingStatus;
import delta.games.lotro.character.status.crafting.CraftingStatusManager;
import delta.games.lotro.character.status.crafting.KnownRecipes;
import delta.games.lotro.character.status.crafting.ProfessionStatus;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.Profession;

/**
 * Gather knowledge about a single crafter:
 * <ul>
 * <li>known recipes,
 * <li>crafting capabilities: known profession and associated proficiency.
 * </ul>
 * @author DAM
 */
public class CrafterKnowledge
{
  private Map<Profession,KnownRecipes> _knownRecipes;
  private Map<Profession,Integer> _maxTier;

  /**
   * Constructor.
   * @param file Character file to use.
   */
  public CrafterKnowledge(CharacterFile file)
  {
    _knownRecipes=new HashMap<Profession,KnownRecipes>();
    _maxTier=new HashMap<Profession,Integer>();
    init(file);
  }

  private void init(CharacterFile file)
  {
    CraftingStatusManager statusMgr=file.getCraftingMgr();
    CraftingStatus status=statusMgr.getCraftingStatus();
    for(Profession profession : status.getActiveProfessions())
    {
      ProfessionStatus professionStatus=status.getProfessionStatus(profession);
      KnownRecipes recipes=professionStatus.getKnownRecipes();
      _knownRecipes.put(profession,recipes);
      CraftingLevel level=professionStatus.getProficiencyLevel();
      _maxTier.put(profession,Integer.valueOf(level.getTier()));
    }
  }

  /**
   * Get the known professions.
   * @return A list of professions.
   */
  public List<Profession> getProfessions()
  {
    return new ArrayList<Profession>(_knownRecipes.keySet());
  }

  /**
   * Indicates if this crafter knows the given recipe.
   * @param profession Profession for the recipe.
   * @param recipeId Recipe identifier.
   * @return <code>true</code> if it is known, <code>false</code> otherwise.
   */
  public boolean isKnown(Profession profession, int recipeId)
  {
    KnownRecipes knownRecipes=_knownRecipes.get(profession);
    if (knownRecipes==null)
    {
      return false;
    }
    return knownRecipes.getKnownRecipes().contains(Integer.valueOf(recipeId));
  }

  /**
   * Get the maximum proficiency tier for this crafter.
   * @param profession Profession to use.
   * @return A tier value (NPE if profession is not managed).
   */
  public int getMaxTier(Profession profession)
  {
    return _maxTier.get(profession).intValue();
  }
}
