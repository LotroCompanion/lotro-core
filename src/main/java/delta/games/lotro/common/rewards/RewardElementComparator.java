package delta.games.lotro.common.rewards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Comparator for reward elements.
 * @author DAM
 */
public class RewardElementComparator implements Comparator<RewardElement>
{
  private List<Class<? extends RewardElement>> _classes;

  /**
   * Constructor.
   */
  public RewardElementComparator()
  {
    _classes=new ArrayList<Class<? extends RewardElement>>();
    _classes.add(ReputationReward.class);
    _classes.add(TraitReward.class);
    _classes.add(SkillReward.class);
    _classes.add(TitleReward.class);
    _classes.add(VirtueReward.class);
    _classes.add(EmoteReward.class);
    _classes.add(ItemReward.class);
    _classes.add(SelectableRewardElement.class);
  }

  @Override
  public int compare(RewardElement o1, RewardElement o2)
  {
    int index1=_classes.indexOf(o1.getClass());
    int index2=_classes.indexOf(o2.getClass());
    return Integer.compare(index1,index2);
  }
}
