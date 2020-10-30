package delta.games.lotro.lore.xrefs.relics;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.rewards.RelicReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.items.Container;
import delta.games.lotro.lore.items.ContainersManager;
import delta.games.lotro.lore.items.legendary.relics.RelicsContainer;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;

/**
 * Finds references to relics.
 * @author DAM
 */
public class RelicReferencesBuilder
{
  private List<RelicReference<?>> _storage;

  /**
   * Constructor.
   */
  public RelicReferencesBuilder()
  {
    _storage=new ArrayList<RelicReference<?>>();
  }

  /**
   * Search for a relic.
   * @param relicId Relic identifier.
   * @return the found references.
   */
  public List<RelicReference<?>> inspectItem(int relicId)
  {
    _storage.clear();
    findInQuestRewards(relicId);
    findInDeedRewards(relicId);
    findInContainers(relicId);
    List<RelicReference<?>> ret=new ArrayList<RelicReference<?>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInContainers(int relicId)
  {
    ContainersManager containersMgr=ContainersManager.getInstance();
    List<Container> containers=containersMgr.getContainers();
    for(Container container : containers)
    {
      if (container instanceof RelicsContainer)
      {
        RelicsContainer relicsContainer=(RelicsContainer)container;
        boolean found=relicsContainer.contains(relicId);
        if (found)
        {
          _storage.add(new RelicReference<RelicsContainer>(relicsContainer,RelicRole.CONTAINED_IN));
        }
      }
    }
  }

  private void findInQuestRewards(int relicId)
  {
    QuestsManager questsManager=QuestsManager.getInstance();
    List<QuestDescription> quests=questsManager.getAll();
    for(QuestDescription quest : quests)
    {
      findInQuest(quest,relicId);
    }
  }

  private void findInQuest(QuestDescription quest, int relicId)
  {
    findInRewards(quest,quest.getRewards(),relicId);
  }

  private void findInDeedRewards(int relicId)
  {
    DeedsManager deedsManager=DeedsManager.getInstance();
    List<DeedDescription> deeds=deedsManager.getAll();
    for(DeedDescription deed : deeds)
    {
      findInDeed(deed,relicId);
    }
  }

  private void findInDeed(DeedDescription deed, int itemId)
  {
    findInRewards(deed,deed.getRewards(),itemId);
  }

  private void findInRewards(Achievable context, Rewards rewards, int relicId)
  {
    List<RewardElement> elements=rewards.getRewardElements();
    findInRewardsElements(context,elements,relicId);
  }

  private void findInRewardsElements(Achievable context, List<RewardElement> elements, int relicId)
  {
    for(RewardElement element : elements)
    {
      if (element instanceof RelicReward)
      {
        RelicReward itemReward=(RelicReward)element;
        int relicRewardId=itemReward.getRelicProxy().getId();
        if (relicRewardId==relicId)
        {
          RelicRole role=(context instanceof QuestDescription)?RelicRole.QUEST_REWARD:RelicRole.DEED_REWARD;
          _storage.add(new RelicReference<Achievable>(context,role));
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
        findInRewardsElements(context,selectableReward.getElements(),relicId);
      }
    }
  }
}
