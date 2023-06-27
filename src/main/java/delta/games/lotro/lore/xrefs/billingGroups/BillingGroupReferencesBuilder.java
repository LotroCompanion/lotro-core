package delta.games.lotro.lore.xrefs.billingGroups;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.common.rewards.BillingTokenReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to billing groups.
 * @author DAM
 */
public class BillingGroupReferencesBuilder
{
  private List<Reference<?,BillingGroupRole>> _storage;

  /**
   * Constructor.
   */
  public BillingGroupReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,BillingGroupRole>>();
  }

  /**
   * Search for a billing group.
   * @param billingGroupID Billing group identifier.
   * @return the found references.
   */
  public List<Reference<?,BillingGroupRole>> inspectBillingGroup(int billingGroupID)
  {
    _storage.clear();
    findInRewards(billingGroupID);
    List<Reference<?,BillingGroupRole>> ret=new ArrayList<Reference<?,BillingGroupRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInRewards(int billingGroupID)
  {
    // Quests
    QuestsManager questsManager=QuestsManager.getInstance();
    List<QuestDescription> quests=questsManager.getAll();
    for(QuestDescription quest : quests)
    {
      findInRewards(quest,quest.getRewards(),billingGroupID);
    }
    // Deeds
    DeedsManager deedsManager=DeedsManager.getInstance();
    List<DeedDescription> deeds=deedsManager.getAll();
    for(DeedDescription deed : deeds)
    {
      findInRewards(deed,deed.getRewards(),billingGroupID);
    }
  }

  private void findInRewards(Achievable context, Rewards rewards, int billingGroupID)
  {
    List<RewardElement> elements=rewards.getRewardElements();
    findInRewardsElements(context,elements,billingGroupID);
  }

  private void findInRewardsElements(Achievable context, List<RewardElement> elements, int billingGroupID)
  {
    for(RewardElement element : elements)
    {
      if (element instanceof BillingTokenReward)
      {
        BillingTokenReward billingTokenReward=(BillingTokenReward)element;
        BillingGroup billingGroup=billingTokenReward.getBillingGroup();
        if (billingGroup.getCode()==billingGroupID)
        {
          BillingGroupRole role=BillingGroupRole.REGULAR_REWARD;
          _storage.add(new Reference<Achievable,BillingGroupRole>(context,role));
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
        findInRewardsElements(context,selectableReward.getElements(),billingGroupID);
      }
    }
  }
}
