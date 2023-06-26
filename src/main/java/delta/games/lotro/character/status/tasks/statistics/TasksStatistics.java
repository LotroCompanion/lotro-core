package delta.games.lotro.character.status.tasks.statistics;

import java.util.List;

import delta.games.lotro.character.status.achievables.AchievableElementState;
import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.character.status.achievables.statistics.reputation.AchievablesFactionStats;
import delta.games.lotro.character.status.achievables.statistics.reputation.AchievablesReputationStats;
import delta.games.lotro.character.status.tasks.TaskStatus;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.ReputationReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.statistics.items.ItemsStats;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.tasks.Task;

/**
 * Gather statistics about a collection of tasks for a single character.
 * @author DAM
 */
public class TasksStatistics
{
  private int _distinctCompletedTasks;
  private int _taskCompletions;
  private AchievablesReputationStats _reputation;
  private ItemsStats _consumedItems;
  private Money _consumedItemsPrice;
  private ItemsStats _earnedItems;
  private int _totalXP;
  private int _totalItemXP;
  private int _totalMountXP;

  /**
   * Constructor.
   */
  public TasksStatistics()
  {
    _reputation=new AchievablesReputationStats();
    _consumedItems=new ItemsStats();
    _consumedItemsPrice=new Money();
    _earnedItems=new ItemsStats();
    reset();
  }

  /**
   * Reset contents.
   */
  public void reset()
  {
    _distinctCompletedTasks=0;
    _taskCompletions=0;
    _reputation.reset();
    _consumedItems.reset();
    _consumedItemsPrice.setRawValue(0);
    _earnedItems.reset();
    _totalXP=0;
    _totalItemXP=0;
    _totalMountXP=0;
  }

  /**
   * Compute statistics using the given tasks status.
   * @param selectedTaskStatuses selectedTaskStatuses Tasks statuses to use.
   */
  public void useTasks(List<TaskStatus> selectedTaskStatuses)
  {
    reset();
    for(TaskStatus taskStatus : selectedTaskStatuses)
    {
      AchievableStatus achievableStatus=taskStatus.getStatus();
      if (achievableStatus!=null)
      {
        useTask(taskStatus);
      }
    }
  }

  private void useTask(TaskStatus status)
  {
    AchievableStatus achievableStatus=status.getStatus();
    AchievableElementState state=achievableStatus.getState();
    if (state==AchievableElementState.COMPLETED)
    {
      Achievable quest=achievableStatus.getAchievable();
      // Count
      _distinctCompletedTasks++;
      // Completions count
      Integer completionCount=achievableStatus.getCompletionCount();
      int completionCountInt=(completionCount!=null)?completionCount.intValue():1;
      _taskCompletions+=completionCountInt;
      Rewards rewards=quest.getRewards();
      // XP
      _totalXP+=(rewards.getXp()*completionCountInt);
      _totalItemXP+=(rewards.getItemXp()*completionCountInt);
      _totalMountXP+=(rewards.getMountXp()*completionCountInt);
      // Other rewards
      for(RewardElement rewardElement : rewards.getRewardElements())
      {
        // Earned items
        if (rewardElement instanceof ItemReward)
        {
          ItemReward itemReward=(ItemReward)rewardElement;
          Item item=itemReward.getItem();
          int itemId=item.getIdentifier();
          int itemsCount=itemReward.getQuantity();
          _earnedItems.add(itemId,itemsCount*completionCountInt);
        }
        // Reputation
        else if (rewardElement instanceof ReputationReward)
        {
          ReputationReward reputationReward=(ReputationReward)rewardElement;
          Faction faction=reputationReward.getFaction();
          AchievablesFactionStats factionStats=_reputation.get(faction,true);
          int amount=reputationReward.getAmount();
          factionStats.addCompletions(completionCountInt,amount);
        }
      }
      // Consumed items
      Task task=status.getTask();
      Item item=task.getItem();
      int count=task.getItemCount();
      if ((item!=null) && (count>0))
      {
        _consumedItems.add(item.getIdentifier(),count*completionCountInt);
        // Price of consumed items
        Money itemCost=item.getValueAsMoney();
        int copperValue=itemCost.getInternalValue();
        int newCoppers=completionCountInt*count*copperValue;
        _consumedItemsPrice.setRawValue(_consumedItemsPrice.getInternalValue()+newCoppers);
      }
    }
  }

  /**
   * Get the completions count.
   * @return A number of achievable completions (including repeatables).
   */
  public int getTaskCompletionsCount()
  {
    return _taskCompletions;
  }

  /**
   * Get the total number of achievables.
   * @return A number of achievables.
   */
  public int getDistinctCompletedTasksCount()
  {
    return _distinctCompletedTasks;
  }

  /**
   * Get the statistics about the acquired reputation.
   * @return Reputation statistics.
   */
  public AchievablesReputationStats getReputationStats()
  {
    return _reputation;
  }

  /**
   * Get the statistics about the consumed items.
   * @return Items statistics.
   */
  public ItemsStats getConsumedItemsStats()
  {
    return _consumedItems;
  }

  /**
   * Get the price of consumed items.
   * @return a money amount.
   */
  public Money getConsumedItemsPrice()
  {
    return _consumedItemsPrice;
  }

  /**
   * Get the statistics about the earned items.
   * @return Items statistics.
   */
  public ItemsStats getEarnedItemsStats()
  {
    return _earnedItems;
  }

  /**
   * Get the total XP.
   * @return an XP amount.
   */
  public int getTotalXP()
  {
    return _totalXP;
  }

  /**
   * Get the total item XP.
   * @return an item XP amount.
   */
  public int getTotalItemXP()
  {
    return _totalItemXP;
  }

  /**
   * Get the total mount XP.
   * @return a mount XP amount.
   */
  public int getTotalMountXP()
  {
    return _totalMountXP;
  }
}
