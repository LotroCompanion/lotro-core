package delta.games.lotro.common.rewards;

import java.util.List;

/**
 * Utility methods related to rewards.
 * @author DAM
 */
public class RewardsUtils
{
  /**
   * Build a rewards object as the total of the given rewards.
   * @param rewards Rewards to use.
   * @return A rewards object.
   */
  public static Rewards buildTotalRewards(List<Rewards> rewards)
  {
    Rewards ret=new Rewards();
    for(Rewards r : rewards)
    {
      ret.getMoney().add(r.getMoney());
      ret.setXp(ret.getXp()+r.getXp());
      ret.setItemXp(ret.getItemXp()+r.getItemXp());
      ret.setMountXp(ret.getMountXp()+r.getMountXp());
      ret.setVirtueXp(ret.getVirtueXp()+r.getVirtueXp());
      ret.setGlory(ret.getGlory()+r.getGlory());
      ret.setLotroPoints(ret.getLotroPoints()+r.getLotroPoints());
      ret.setClassPoints(ret.getClassPoints()+r.getClassPoints());
      for(RewardElement element : r.getRewardElements())
      {
        mergeRewardElement(ret,element);
      }
    }
    return ret;
  }

  private static void mergeRewardElement(Rewards target, RewardElement element)
  {
    if (element instanceof CraftingXpReward)
    {
      mergeCraftingXpReward(target,(CraftingXpReward)element);
    }
    else if (element instanceof ItemReward)
    {
      mergeItemReward(target,(ItemReward)element);
    }
    else if (element instanceof RelicReward)
    {
      mergeRelicReward(target,(RelicReward)element);
    }
    else if (element instanceof ReputationReward)
    {
      mergeReputationReward(target,(ReputationReward)element);
    }
    else if (element instanceof VirtueReward)
    {
      mergeVirtueReward(target,(VirtueReward)element);
    }
    else if ((element instanceof EmoteReward) || (element instanceof SelectableRewardElement) ||
        (element instanceof TitleReward) || (element instanceof TraitReward))
    {
      target.addRewardElement(element);
    }
  }

  private static void mergeCraftingXpReward(Rewards target, CraftingXpReward element)
  {
    List<CraftingXpReward> existing=target.getRewardElementsOfClass(CraftingXpReward.class);
    boolean found=false;
    for(CraftingXpReward reward : existing)
    {
      if ((reward.getProfession()==element.getProfession()) &&
          (reward.getTier()==element.getTier()))
      {
        found=true;
        reward.addXp(element.getXp());
        break;
      }
    }
    if (!found)
    {
      CraftingXpReward newOne=new CraftingXpReward(element.getProfession(),element.getTier(),element.getXp());
      target.addRewardElement(newOne);
    }
  }

  private static void mergeItemReward(Rewards target, ItemReward element)
  {
    List<ItemReward> existing=target.getRewardElementsOfClass(ItemReward.class);
    boolean found=false;
    for(ItemReward reward : existing)
    {
      if (reward.getItem().getIdentifier()==element.getItem().getIdentifier())
      {
        found=true;
        reward.addQuantity(element.getQuantity());
        break;
      }
    }
    if (!found)
    {
      ItemReward newOne=new ItemReward(element.getItem(),element.getQuantity());
      target.addRewardElement(newOne);
    }
  }

  private static void mergeRelicReward(Rewards target, RelicReward element)
  {
    List<RelicReward> existing=target.getRewardElementsOfClass(RelicReward.class);
    boolean found=false;
    for(RelicReward reward : existing)
    {
      if (reward.getRelic().getIdentifier()==element.getRelic().getIdentifier())
      {
        found=true;
        reward.addQuantity(element.getQuantity());
        break;
      }
    }
    if (!found)
    {
      RelicReward newOne=new RelicReward(element.getRelic(),element.getQuantity());
      target.addRewardElement(newOne);
    }
  }

  private static void mergeReputationReward(Rewards target, ReputationReward element)
  {
    List<ReputationReward> existing=target.getRewardElementsOfClass(ReputationReward.class);
    boolean found=false;
    for(ReputationReward reward : existing)
    {
      if (reward.getFaction()==element.getFaction())
      {
        found=true;
        reward.setAmount(reward.getAmount()+element.getAmount());
        break;
      }
    }
    if (!found)
    {
      ReputationReward newOne=new ReputationReward(element.getFaction());
      newOne.setAmount(element.getAmount());
      target.addRewardElement(newOne);
    }
  }


  private static void mergeVirtueReward(Rewards target, VirtueReward element)
  {
    List<VirtueReward> existing=target.getRewardElementsOfClass(VirtueReward.class);
    boolean found=false;
    for(VirtueReward reward : existing)
    {
      if (reward.getVirtue()==element.getVirtue())
      {
        found=true;
        reward.addPoints(element.getCount());
        break;
      }
    }
    if (!found)
    {
      VirtueReward newOne=new VirtueReward(element.getVirtue(),element.getCount());
      target.addRewardElement(newOne);
    }
  }
}
