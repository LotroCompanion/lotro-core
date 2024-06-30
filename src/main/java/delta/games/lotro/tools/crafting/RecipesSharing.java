package delta.games.lotro.tools.crafting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.StoragesIO;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.vaults.Chest;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Tool to assess recipes that shall be shared.
 * @author DAM
 */
public class RecipesSharing
{
  /**
   * Characters to ignore.
   */
  private Set<String> _ignoredToons;

  /**
   * Constructor.
   */
  public RecipesSharing()
  {
    _ignoredToons=new HashSet<String>();
    _ignoredToons.add("Testlc");
    _ignoredToons.add("Fimbathil");
    _ignoredToons.add("Nelthorielle");
  }

  private RecipesToShare findRecipeItems(CharacterFile file)
  {
    RecipesToShare ret=new RecipesToShare();
    CharacterStorage storage=StoragesIO.loadCharacterStorage(file);
    analyzeVault(storage.getSharedVault(),ret);
    analyzeBags(storage.getBags(),ret);
    return ret;
  }

  private void analyzeVault(Vault vault, RecipesToShare recipesToShare)
  {
    for(Integer chestId : vault.getChestIds())
    {
      Chest chest=vault.getChest(chestId.intValue());
      for(CountedItem<ItemInstance<? extends Item>> chestElement : chest.getAllItemInstances())
      {
        Item item=chestElement.getItem();
        recipesToShare.analyze(item);
      }
    }
  }

  private void analyzeBags(BagsManager bags, RecipesToShare recipesToShare)
  {
    for(CountedItem<ItemInstance<? extends Item>> element : bags.getAllItemInstancesWithCount())
    {
      Item item=element.getItem();
      recipesToShare.analyze(item);
    }
  }

  private List<CharacterFile> getUsers(CharacterFile ref)
  {
    // Find character files with the same account/server
    List<CharacterFile> ret=new ArrayList<CharacterFile>();
    String refServerName=ref.getServerName();
    String refAccountName=ref.getAccountName();
    for(CharacterFile file : CharactersManager.getInstance().getAllToons())
    {
      if (file==ref)
      {
        continue;
      }
      if (_ignoredToons.contains(file.getName()))
      {
        continue;
      }
      String accountName=file.getAccountName();
      String serverName=file.getServerName();
      if ((refAccountName.equals(accountName)) && (refServerName.equals(serverName)))
      {
        ret.add(file);
      }
    }
    return ret;
  }

  private void doIt(CharacterFile file)
  {
    RecipesToShare recipesToShare=findRecipeItems(file);
    List<CharacterFile> users=getUsers(file);
    for(CharacterFile user : users)
    {
      CrafterKnowledge ck=new CrafterKnowledge(user);
      for(Profession profession : ck.getProfessions())
      {
        int maxTier=ck.getMaxTier(profession);
        for(RecipeSharingElement recipeToShare : recipesToShare.getRecipes(profession))
        {
          Recipe recipe=recipeToShare.getRecipe();
          int recipeId=recipe.getIdentifier();
          int recipeTier=recipe.getTier();
          if (recipeTier>maxTier)
          {
            continue;
          }
          boolean known=ck.isKnown(profession,recipeId);
          if (!known)
          {
            Item recipeItem=recipeToShare.getItem();
            System.out.println("User "+user.getName()+" would use Item "+recipeItem+" for recipe: "+recipe.getName());
          }
        }
      }
    }
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    CharacterFile file=CharactersManager.getInstance().getToonById("Landroval","Utharr");
    new RecipesSharing().doIt(file);
  }
}
