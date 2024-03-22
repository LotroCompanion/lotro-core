package delta.games.lotro.business.crafting;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.status.crafting.CraftingStatus;
import delta.games.lotro.character.status.crafting.CraftingStatusManager;
import delta.games.lotro.character.status.crafting.KnownRecipes;
import delta.games.lotro.character.status.crafting.ProfessionStatus;
import delta.games.lotro.lore.crafting.Profession;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for the {@link KnownRecipesComputer}.
 * @author DAM
 */
public class KnownRecipesComputerTest extends TestCase
{
  /**
   * Load a crafting status and show the recipes status for it.
   */
  public void test()
  {
    CraftingStatus craftingStatus=load();
    for(Profession profession : craftingStatus.getKnownProfessions())
    {
      ProfessionStatus professionStatus=craftingStatus.getProfessionStatus(profession);
      Assert.assertNotNull(professionStatus);
      KnownRecipes knownRecipes=professionStatus.getKnownRecipes();
      Assert.assertNotNull(knownRecipes);
      KnownRecipesComputer.showRecipesStatusForProfession(profession,knownRecipes);
    }
  }

  private CraftingStatus load()
  {
    CharacterFile file=CharactersManager.getInstance().getToonById("Landroval","Lorewyne");
    CraftingStatusManager craftingStatusMgr=file.getCraftingMgr();
    CraftingStatus craftingStatus=craftingStatusMgr.getCraftingStatus();
    return craftingStatus;
  }
}
