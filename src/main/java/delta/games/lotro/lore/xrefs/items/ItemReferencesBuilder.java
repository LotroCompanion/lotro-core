package delta.games.lotro.lore.xrefs.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import delta.games.lotro.lore.relics.melding.MeldingOutput;
import delta.games.lotro.lore.relics.melding.RelicMeldingRecipe;
import delta.games.lotro.lore.relics.melding.RelicMeldingRecipesManager;
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
    findInTaskQuests(itemId);
    findInDeedRewards(itemId);
    findInBarterers(itemId);
    findInVendors(itemId);
    findInSets(itemId);
    findInContainers(itemId);
    findInMeldingRecipes(itemId);
    findSameCosmetics(itemId);
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
        Item ingredientItem=ingredient.getItem();
        if ((ingredientItem!=null) && (ingredientItem.getIdentifier()==itemId))
        {
          ItemRole role=ingredient.isOptional()?ItemRole.RECIPE_CRITICAL_INGREDIENT:ItemRole.RECIPE_INGREDIENT;
          roles.add(role);
          break;
        }
      }
      // Result
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
      _storage.add(new ItemReference<Task>(task,ItemRole.TASK_ITEM));
    }
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
        int itemRewardId=itemReward.getItem().getIdentifier();
        if (itemRewardId==itemId)
        {
          ItemRole role=(context instanceof QuestDescription)?ItemRole.QUEST_REWARD:ItemRole.DEED_REWARD;
          _storage.add(new ItemReference<Achievable>(context,role));
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
          }
        }
        for(ItemBarterEntryElement toGive : entry.getElementsToGive())
        {
          int itemToGiveId=toGive.getItemProxy().getId();
          if (itemToGiveId==itemId)
          {
            roles.add(ItemRole.BARTERER_RECEIVED);
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
          _storage.add(new ItemReference<ItemsContainer>(itemsContainer,ItemRole.CONTAINED_IN));
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
        _storage.add(new ItemReference<RelicMeldingRecipe>(recipe,ItemRole.RECIPE_RESULT));
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
      if (sameCosmeticItems.size()>0)
      {
        for(Item sameCosmeticItem : sameCosmeticItems)
        {
          _storage.add(new ItemReference<Item>(sameCosmeticItem,ItemRole.SAME_COSMETICS));
        }
      }
    }
  }
}
