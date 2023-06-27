package delta.games.lotro.lore.xrefs.titles;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.TitleReward;
import delta.games.lotro.lore.billingGroups.BillingGroupDescription;
import delta.games.lotro.lore.billingGroups.BillingGroupsManager;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to titles.
 * @author DAM
 */
public class TitleReferencesBuilder
{
  private List<Reference<?,TitleRole>> _storage;

  /**
   * Constructor.
   */
  public TitleReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,TitleRole>>();
  }

  /**
   * Search for a title.
   * @param titleID Title identifier.
   * @return the found references.
   */
  public List<Reference<?,TitleRole>> inspectTitle(int titleID)
  {
    _storage.clear();
    findInRewards(titleID);
    findInBillingGroups(titleID);
    List<Reference<?,TitleRole>> ret=new ArrayList<Reference<?,TitleRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInBillingGroups(int titleID)
  {
    for(BillingGroupDescription billingGroup : BillingGroupsManager.getInstance().getAll())
    {
      findInBillingGroup(billingGroup, titleID);
    }
  }

  private void findInBillingGroup(BillingGroupDescription billingGroup, int titleID)
  {
    for(TitleDescription title : billingGroup.getAccountTitles())
    {
      if (title.getIdentifier()==titleID)
      {
        _storage.add(new Reference<BillingGroupDescription,TitleRole>(billingGroup,TitleRole.BILLING_GROUP_REWARD));
      }
    }
  }

  private void findInRewards(int titleID)
  {
    // Quests
    QuestsManager questsManager=QuestsManager.getInstance();
    List<QuestDescription> quests=questsManager.getAll();
    for(QuestDescription quest : quests)
    {
      findInRewards(quest,quest.getRewards(),titleID);
    }
    // Deeds
    DeedsManager deedsManager=DeedsManager.getInstance();
    List<DeedDescription> deeds=deedsManager.getAll();
    for(DeedDescription deed : deeds)
    {
      findInRewards(deed,deed.getRewards(),titleID);
    }
  }

  private void findInRewards(Achievable context, Rewards rewards, int titleID)
  {
    List<RewardElement> elements=rewards.getRewardElements();
    findInRewardsElements(context,elements,titleID);
  }

  private void findInRewardsElements(Achievable context, List<RewardElement> elements, int titleID)
  {
    for(RewardElement element : elements)
    {
      if (element instanceof TitleReward)
      {
        TitleReward titleReward=(TitleReward)element;
        int titleRewardID=titleReward.getTitle().getIdentifier();
        if (titleRewardID==titleID)
        {
          TitleRole role=TitleRole.REGULAR_REWARD;
          _storage.add(new Reference<Achievable,TitleRole>(context,role));
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
        findInRewardsElements(context,selectableReward.getElements(),titleID);
      }
    }
  }
}
