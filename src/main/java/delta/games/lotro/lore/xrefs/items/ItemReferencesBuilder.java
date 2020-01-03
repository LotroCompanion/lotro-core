package delta.games.lotro.lore.xrefs.items;

import java.util.List;

import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.lore.crafting.recipes.CraftingResult;
import delta.games.lotro.lore.crafting.recipes.Ingredient;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.lore.crafting.recipes.RecipesManager;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemProxy;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.lore.items.sets.ItemsSetsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.trade.barter.BarterEntry;
import delta.games.lotro.lore.trade.barter.BarterEntryElement;
import delta.games.lotro.lore.trade.barter.BarterNpc;
import delta.games.lotro.lore.trade.barter.BarterProfile;
import delta.games.lotro.lore.trade.barter.BarterersManager;
import delta.games.lotro.lore.trade.barter.ItemBarterEntryElement;

/**
 * Finds references to items.
 * @author DAM
 */
public class ItemReferencesBuilder
{
  /**
   * Search for an item.
   * @param itemId Item identifier.
   */
  public void inspectItem(int itemId)
  {
    findInRecipes(itemId);
    findInQuestRewards(itemId);
    findInDeedRewards(itemId);
    findInBarterers(itemId);
    findInSets(itemId);
  }

  private void findInRecipes(int itemId)
  {
    RecipesManager recipesManager=RecipesManager.getInstance();
    List<Recipe> recipes=recipesManager.getAll();
    for(Recipe recipe : recipes)
    {
      findInRecipe(recipe, itemId);
    }
  }

  private void findInRecipe(Recipe recipe, int itemId)
  {
    for(RecipeVersion version : recipe.getVersions())
    {
      // Ingredient?
      List<Ingredient> ingredients=version.getIngredients();
      for(Ingredient ingredient : ingredients)
      {
        int ingredientId=ingredient.getItem().getId();
        if (ingredientId==itemId)
        {
          logFinding(itemId,"ingredient in recipe: "+recipe.getName());
          break;
        }
      }
      // Result
      CraftingResult regularResult=version.getRegular();
      int regularResultId=regularResult.getItem().getId();
      if (regularResultId==itemId)
      {
        logFinding(itemId,"result in recipe: "+recipe.getName());
      }
      CraftingResult criticalResult=version.getCritical();
      if (criticalResult!=null)
      {
        int criticalResultId=criticalResult.getItem().getId();
        if (criticalResultId==itemId)
        {
          logFinding(itemId,"critical result in recipe: "+recipe.getName());
        }
      }
      // Recipe item
      ItemProxy recipeItem=recipe.getRecipeScroll();
      if (recipeItem!=null)
      {
        int recipeItemId=recipeItem.getId();
        if (recipeItemId==itemId)
        {
          logFinding(itemId,"provides recipe: "+recipe.getName());
        }
      }
    }
  }

  private void findInQuestRewards(int itemId)
  {
    QuestsManager questsManager=QuestsManager.getInstance();
    List<QuestDescription> quests=questsManager.getAll();
    for(QuestDescription quest : quests)
    {
      findInQuest(quest, itemId);
    }
  }

  private void findInQuest(QuestDescription quest, int itemId)
  {
    findInRewards(quest,quest.getRewards(),itemId);
  }

  private void findInDeedRewards(int itemId)
  {
    DeedsManager deedsManager=DeedsManager.getInstance();
    List<DeedDescription> deeds=deedsManager.getAll();
    for(DeedDescription deed : deeds)
    {
      findInDeed(deed, itemId);
    }
  }

  private void findInDeed(DeedDescription deed, int itemId)
  {
    findInRewards(deed,deed.getRewards(),itemId);
  }

  private void findInRewards(Achievable context, Rewards rewards, int itemId)
  {
    List<RewardElement> elements=rewards.getRewardElements();
    findInRewardsElements(context,elements,itemId);
  }

  private void findInRewardsElements(Achievable context, List<RewardElement> elements, int itemId)
  {
    for(RewardElement element : elements)
    {
      if (element instanceof ItemReward)
      {
        ItemReward itemReward=(ItemReward)element;
        int itemRewardId=itemReward.getItemProxy().getId();
        if (itemRewardId==itemId)
        {
          logFinding(itemId,"reward in: "+context.getName());
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
       findInRewardsElements(context,selectableReward.getElements(),itemId);
      }
    }
  }

  private void findInBarterers(int itemId)
  {
    BarterersManager barterersManager=BarterersManager.getInstance();
    List<BarterNpc> barterers=barterersManager.getAll();
    for(BarterNpc barterer : barterers)
    {
      findInBarter(barterer, itemId);
    }
  }

  private void findInBarter(BarterNpc barterer, int itemId)
  {
    List<BarterProfile> profiles=barterer.getBarterProfiles();
    for(BarterProfile profile : profiles)
    {
      List<BarterEntry> entries=profile.getEntries();
      for(BarterEntry entry : entries)
      {
        BarterEntryElement toReceive=entry.getElementToReceive();
        if (toReceive instanceof ItemBarterEntryElement)
        {
          ItemBarterEntryElement itemToReceive=(ItemBarterEntryElement)toReceive;
          int itemToReceiveId=itemToReceive.getItemProxy().getId();
          if (itemToReceiveId==itemId)
          {
            logFinding(itemId,"barter from "+barterer.getNpc().getName()+" with profile "+profile.getName());
          }
        }
      }
    }
  }

  private void findInSets(int itemId)
  {
    ItemsSetsManager itemsSetsManager=ItemsSetsManager.getInstance();
    for(ItemsSet itemsSet : itemsSetsManager.getAll())
    {
      if (itemsSet.hasMember(itemId))
      {
        logFinding(itemId,"member of set "+itemsSet.getName());
      }
    }
  }

  private void logFinding(int itemId, String log)
  {
    Item item=ItemsManager.getInstance().getItem(itemId);
    System.out.println("Found item: "+item.getName()+" as "+log);
  }
}
