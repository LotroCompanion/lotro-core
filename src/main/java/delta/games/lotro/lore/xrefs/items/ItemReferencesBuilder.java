package delta.games.lotro.lore.xrefs.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import delta.games.lotro.lore.trade.vendor.SellList;
import delta.games.lotro.lore.trade.vendor.VendorNpc;
import delta.games.lotro.lore.trade.vendor.VendorsManager;
import delta.games.lotro.utils.Proxy;

/**
 * Finds references to items.
 * @author DAM
 */
public class ItemReferencesBuilder
{
  private List<ItemReference<?>> _storage;

  /**
   * Constructor.
   */
  public ItemReferencesBuilder()
  {
    _storage=new ArrayList<ItemReference<?>>();
  }

  /**
   * Search for an item.
   * @param itemId Item identifier.
   * @return the found references.
   */
  public List<ItemReference<?>> inspectItem(int itemId)
  {
    _storage.clear();
    findInRecipes(itemId);
    findInQuestRewards(itemId);
    findInDeedRewards(itemId);
    findInBarterers(itemId);
    findInVendors(itemId);
    findInSets(itemId);
    List<ItemReference<?>> ret=new ArrayList<ItemReference<?>>(_storage);
    _storage.clear();
    return ret;
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
    Set<ItemRole> roles=new HashSet<ItemRole>();
    for(RecipeVersion version : recipe.getVersions())
    {
      // Ingredient?
      List<Ingredient> ingredients=version.getIngredients();
      for(Ingredient ingredient : ingredients)
      {
        int ingredientId=ingredient.getItem().getId();
        if (ingredientId==itemId)
        {
          ItemRole role=ingredient.isOptional()?ItemRole.RECIPE_CRITICAL_INGREDIENT:ItemRole.RECIPE_INGREDIENT;
          roles.add(role);
          //logFinding(itemId,"ingredient in recipe: "+recipe.getName());
          break;
        }
      }
      // Result
      CraftingResult regularResult=version.getRegular();
      int regularResultId=regularResult.getItem().getId();
      if (regularResultId==itemId)
      {
        roles.add(ItemRole.RECIPE_RESULT);
        //logFinding(itemId,"result in recipe: "+recipe.getName());
      }
      CraftingResult criticalResult=version.getCritical();
      if (criticalResult!=null)
      {
        int criticalResultId=criticalResult.getItem().getId();
        if (criticalResultId==itemId)
        {
          roles.add(ItemRole.RECIPE_CRITICAL_RESULT);
          //logFinding(itemId,"critical result in recipe: "+recipe.getName());
        }
      }
      // Recipe item
      ItemProxy recipeItem=recipe.getRecipeScroll();
      if (recipeItem!=null)
      {
        int recipeItemId=recipeItem.getId();
        if (recipeItemId==itemId)
        {
          roles.add(ItemRole.RECIPE_PROVIDES_RECIPE);
          //logFinding(itemId,"provides recipe: "+recipe.getName());
        }
      }
    }
    if (roles.size()>0)
    {
      _storage.add(new ItemReference<Recipe>(recipe,roles));
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
          ItemRole role=(context instanceof QuestDescription)?ItemRole.QUEST_REWARD:ItemRole.DEED_REWARD;
          _storage.add(new ItemReference<Achievable>(context,role));
          //logFinding(itemId,"reward in: "+context.getName());
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
    Set<ItemRole> roles=new HashSet<ItemRole>();
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
            roles.add(ItemRole.BARTERER_GIVEN);
            //logFinding(itemId,"barter from "+barterer.getNpc().getName()+" with profile "+profile.getName());
          }
        }
        for(ItemBarterEntryElement toGive : entry.getElementsToGive())
        {
          int itemToGiveId=toGive.getItemProxy().getId();
          if (itemToGiveId==itemId)
          {
            roles.add(ItemRole.BARTERER_RECEIVED);
            //logFinding(itemId,"barter from "+barterer.getNpc().getName()+" with profile "+profile.getName());
          }
        }
      }
    }
    if (roles.size()>0)
    {
      _storage.add(new ItemReference<BarterNpc>(barterer,roles));
    }
  }

  private void findInVendors(int itemId)
  {
    VendorsManager vendorsManager=VendorsManager.getInstance();
    List<VendorNpc> vendors=vendorsManager.getAll();
    for(VendorNpc vendor : vendors)
    {
      findInVendor(vendor, itemId);
    }
  }

  private void findInVendor(VendorNpc vendor, int itemId)
  {
    List<SellList> sellLists=vendor.getSellLists();
    for(SellList sellList : sellLists)
    {
      List<Proxy<Item>> entries=sellList.getItems();
      for(Proxy<Item> entry : entries)
      {
        int itemToGetId=entry.getId();
        if (itemToGetId==itemId)
        {
          _storage.add(new ItemReference<VendorNpc>(vendor,ItemRole.VENDOR_SOLD_BY));
          //logFinding(itemId,"sold by "+vendor.getNpc().getName());
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
        _storage.add(new ItemReference<ItemsSet>(itemsSet,ItemRole.SET_MEMBER_OF_SET));
        //logFinding(itemId,"member of set "+itemsSet.getName());
      }
    }
  }

  /*
  private void logFinding(int itemId, String log)
  {
    Item item=ItemsManager.getInstance().getItem(itemId);
    System.out.println("Found item: "+item.getName()+" as "+log);
  }
  */
}
