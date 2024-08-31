package delta.games.lotro.lore.xrefs.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.common.comparators.NamedComparator;
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
import delta.games.lotro.lore.items.Container;
import delta.games.lotro.lore.items.ContainersManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.containers.ItemsContainer;
import delta.games.lotro.lore.items.containers.LootTables;
import delta.games.lotro.lore.items.cosmetics.ItemCosmetics;
import delta.games.lotro.lore.items.cosmetics.ItemCosmeticsManager;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.lore.items.sets.ItemsSetsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.quests.objectives.ItemCondition;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.relics.melding.MeldingOutput;
import delta.games.lotro.lore.relics.melding.RelicMeldingRecipe;
import delta.games.lotro.lore.relics.melding.RelicMeldingRecipesManager;
import delta.games.lotro.lore.rewardsTrack.RewardsTrack;
import delta.games.lotro.lore.rewardsTrack.RewardsTrackStep;
import delta.games.lotro.lore.rewardsTrack.RewardsTracksManager;
import delta.games.lotro.lore.tasks.Task;
import delta.games.lotro.lore.tasks.TasksRegistry;
import delta.games.lotro.lore.trade.barter.BarterEntry;
import delta.games.lotro.lore.trade.barter.BarterEntryElement;
import delta.games.lotro.lore.trade.barter.BarterNpc;
import delta.games.lotro.lore.trade.barter.BarterProfile;
import delta.games.lotro.lore.trade.barter.BarterersManager;
import delta.games.lotro.lore.trade.barter.ItemBarterEntryElement;
import delta.games.lotro.lore.trade.vendor.SellList;
import delta.games.lotro.lore.trade.vendor.VendorNpc;
import delta.games.lotro.lore.trade.vendor.VendorsManager;
import delta.games.lotro.lore.webStore.WebStoreItem;
import delta.games.lotro.lore.webStore.WebStoreItemsManager;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to items.
 * @author DAM
 */
public class ItemReferencesBuilder
{
  private static final Logger LOGGER=Logger.getLogger(ItemReferencesBuilder.class);

  private List<Reference<?,ItemRole>> _storage;

  /**
   * Constructor.
   */
  public ItemReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,ItemRole>>();
  }

  /**
   * Search for an item.
   * @param itemId Item identifier.
   * @return the found references.
   */
  public List<Reference<?,ItemRole>> inspectItem(int itemId)
  {
    _storage.clear();
    findInRecipes(itemId);
    findInQuests(itemId);
    findInTaskQuests(itemId);
    findInDeeds(itemId);
    findInRewardTracks(itemId);
    findInBarterers(itemId);
    findInVendors(itemId);
    findInSets(itemId);
    findInContainers(itemId);
    findInMeldingRecipes(itemId);
    findSameCosmetics(itemId);
    findInWebStoreItems(itemId);
    List<Reference<?,ItemRole>> ret=new ArrayList<Reference<?,ItemRole>>(_storage);
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
        Item ingredientItem=ingredient.getItem();
        if ((ingredientItem!=null) && (ingredientItem.getIdentifier()==itemId))
        {
          ItemRole role=ingredient.isOptional()?ItemRole.RECIPE_CRITICAL_INGREDIENT:ItemRole.RECIPE_INGREDIENT;
          roles.add(role);
          break;
        }
      }
      // Result
      findInRecipeResult(version,itemId,roles);
      // Recipe item
      Item recipeItem=recipe.getRecipeScroll();
      if (recipeItem!=null)
      {
        int recipeItemId=recipeItem.getIdentifier();
        if (recipeItemId==itemId)
        {
          roles.add(ItemRole.RECIPE_PROVIDES_RECIPE);
        }
      }
    }
    if (!roles.isEmpty())
    {
      _storage.add(new Reference<Recipe,ItemRole>(recipe,roles));
    }
  }

  private void findInRecipeResult(RecipeVersion version, int itemId, Set<ItemRole> roles)
  {
    CraftingResult regularResult=version.getRegular();
    Item regularResultItem=regularResult.getItem();
    if (regularResultItem!=null)
    {
      int regularResultId=regularResultItem.getIdentifier();
      if (regularResultId==itemId)
      {
        roles.add(ItemRole.RECIPE_RESULT);
      }
    }
    CraftingResult criticalResult=version.getCritical();
    if (criticalResult!=null)
    {
      Item criticalResultItem=criticalResult.getItem();
      if (criticalResultItem!=null)
      {
        int criticalResultId=criticalResultItem.getIdentifier();
        if (criticalResultId==itemId)
        {
          roles.add(ItemRole.RECIPE_CRITICAL_RESULT);
        }
      }
    }
  }

  private void findInQuests(int itemId)
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
    findInAchievable(quest,itemId);
  }

  private void findInTaskQuests(int itemId)
  {
    TasksRegistry tasksRegistry=TasksRegistry.getInstance();
    List<Task> tasks=tasksRegistry.getTasks();
    for(Task task : tasks)
    {
      findInTask(task, itemId);
    }
  }

  private void findInTask(Task task, int itemId)
  {
    Item item=task.getItem();
    if ((item!=null) && (item.getIdentifier()==itemId))
    {
      _storage.add(new Reference<Task,ItemRole>(task,ItemRole.TASK_ITEM));
    }
  }

  private void findInDeeds(int itemId)
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
    findInAchievable(deed,itemId);
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
        int itemRewardId=itemReward.getItem().getIdentifier();
        if (itemRewardId==itemId)
        {
          _storage.add(new Reference<Achievable,ItemRole>(context,ItemRole.ACHIEVABLE_REWARD));
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
        findInRewardsElements(context,selectableReward.getElements(),itemId);
      }
    }
  }

  private void findInAchievable(Achievable context, int itemId)
  {
    ObjectivesManager objectives=context.getObjectives();
    for(Objective objective : objectives.getObjectives())
    {
      for(ObjectiveCondition condition : objective.getConditions())
      {
        if (findInCondition(context,condition,itemId))
        {
          return;
        }
      }
      for(ObjectiveCondition condition : objective.getFailureConditions())
      {
        if (findInCondition(context,condition,itemId))
        {
          return;
        }
      }
    }
    for(ObjectiveCondition condition : objectives.getFailureConditions())
    {
      if (findInCondition(context,condition,itemId))
      {
        return;
      }
    }
  }

  private boolean findInCondition(Achievable context, ObjectiveCondition condition, int itemId)
  {
    if (condition instanceof ItemCondition)
    {
      ItemCondition itemCondition=(ItemCondition)condition;
      Item item=itemCondition.getItem();
      if (item!=null)
      {
        int conditionItemID=item.getIdentifier();
        if (conditionItemID==itemId)
        {
          _storage.add(new Reference<Achievable,ItemRole>(context,ItemRole.ACHIEVABLE_INVOLVED));
          return true;
        }
      }
      else
      {
        LOGGER.debug("Item is null in condition "+condition+" of achievable "+context);
      }
    }
    return false;
  }

  private void findInRewardTracks(int itemId)
  {
    RewardsTracksManager mgr=RewardsTracksManager.getInstance();
    for(RewardsTrack rewardTrack : mgr.getAllRewardsTracks())
    {
      findInRewardTrack(rewardTrack,itemId);
    }
  }

  private void findInRewardTrack(RewardsTrack rewardTrack, int itemId)
  {
    for(RewardsTrackStep step : rewardTrack.getSteps())
    {
      Item rewardItem=step.getReward();
      if ((rewardItem!=null) && (rewardItem.getIdentifier()==itemId))
      {
        _storage.add(new Reference<RewardsTrack,ItemRole>(rewardTrack,ItemRole.REWARDS_TRACK_REWARD));
        break;
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
          int itemToReceiveId=itemToReceive.getItem().getIdentifier();
          if (itemToReceiveId==itemId)
          {
            roles.add(ItemRole.BARTERER_GIVEN);
          }
        }
        for(ItemBarterEntryElement toGive : entry.getElementsToGive())
        {
          int itemToGiveId=toGive.getItem().getIdentifier();
          if (itemToGiveId==itemId)
          {
            roles.add(ItemRole.BARTERER_RECEIVED);
          }
        }
      }
    }
    if (!roles.isEmpty())
    {
      _storage.add(new Reference<BarterNpc,ItemRole>(barterer,roles));
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
      List<Item> entries=sellList.getItems();
      for(Item entry : entries)
      {
        int itemToGetId=entry.getIdentifier();
        if (itemToGetId==itemId)
        {
          _storage.add(new Reference<VendorNpc,ItemRole>(vendor,ItemRole.VENDOR_SOLD_BY));
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
        _storage.add(new Reference<ItemsSet,ItemRole>(itemsSet,ItemRole.SET_MEMBER_OF_SET));
      }
    }
  }

  private void findInContainers(int itemId)
  {
    ContainersManager containersMgr=ContainersManager.getInstance();
    List<Container> containers=containersMgr.getContainers();
    for(Container container : containers)
    {
      if (container instanceof ItemsContainer)
      {
        ItemsContainer itemsContainer=(ItemsContainer)container;
        LootTables lootTables=itemsContainer.getLootTables();
        boolean found=lootTables.contains(itemId);
        if (found)
        {
          _storage.add(new Reference<ItemsContainer,ItemRole>(itemsContainer,ItemRole.CONTAINED_IN));
        }
      }
    }
  }

  private void findInMeldingRecipes(int itemId)
  {
    RelicMeldingRecipesManager recipesMgr=RelicMeldingRecipesManager.getInstance();
    List<RelicMeldingRecipe> recipes=recipesMgr.getMeldingRecipes().getItems();
    for(RelicMeldingRecipe recipe : recipes)
    {
      MeldingOutput output=recipe.getOutput();
      if (output.isResultItem(itemId))
      {
        _storage.add(new Reference<RelicMeldingRecipe,ItemRole>(recipe,ItemRole.RECIPE_RESULT));
      }
    }
  }

  private void findSameCosmetics(int itemID)
  {
    ItemCosmeticsManager mgr=ItemCosmeticsManager.getInstance();
    ItemCosmetics cosmeticsMgr=mgr.getData();
    Integer cosmeticID=cosmeticsMgr.findCosmeticID(itemID);
    if (cosmeticID==null)
    {
      return;
    }
    int[] itemIDs=cosmeticsMgr.findItemIDs(cosmeticID.intValue());
    if (itemIDs!=null)
    {
      List<Item> sameCosmeticItems=new ArrayList<Item>();
      for(int sameCosmeticItemID : itemIDs)
      {
        if (sameCosmeticItemID!=itemID)
        {
          Item sameCosmeticItem=ItemsManager.getInstance().getItem(sameCosmeticItemID);
          sameCosmeticItems.add(sameCosmeticItem);
        }
      }
      Collections.sort(sameCosmeticItems,new NamedComparator());
      if (!sameCosmeticItems.isEmpty())
      {
        for(Item sameCosmeticItem : sameCosmeticItems)
        {
          _storage.add(new Reference<Item,ItemRole>(sameCosmeticItem,ItemRole.SAME_COSMETICS));
        }
      }
    }
  }

  private void findInWebStoreItems(int itemId)
  {
    WebStoreItemsManager webStoreItemsMgr=WebStoreItemsManager.getInstance();
    for(WebStoreItem webStoreItem : webStoreItemsMgr.getAll())
    {
      int webStoreItemId=webStoreItem.getItemID();
      if (webStoreItemId==itemId)
      {
        _storage.add(new Reference<WebStoreItem,ItemRole>(webStoreItem,ItemRole.WEB_STORE_ITEM));
      }
    }
  }
}
