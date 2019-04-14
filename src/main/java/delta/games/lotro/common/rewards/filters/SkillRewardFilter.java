package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.SkillReward;

/**
 * Filter for rewards that contain a skill.
 * @author DAM
 */
public class SkillRewardFilter implements Filter<Rewards>
{
  private String _skill;

  /**
   * Constructor.
   * @param skill Skill to select (may be <code>null</code>).
   */
  public SkillRewardFilter(String skill)
  {
    _skill=skill;
  }

  /**
   * Get the skill to use.
   * @return A skill or <code>null</code>.
   */
  public String getSkill()
  {
    return _skill;
  }

  /**
   * Set the skill to select.
   * @param skill Skill to use, may be <code>null</code>.
   */
  public void setSkill(String skill)
  {
    _skill=skill;
  }

  public boolean accept(Rewards rewards)
  {
    if (_skill==null)
    {
      return true;
    }
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof SkillReward)
      {
        SkillReward skillReward=(SkillReward)rewardElement;
        if (accept(skillReward))
        {
          return true;
        }
      }
      else if (rewardElement instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectable=(SelectableRewardElement)rewardElement;
        if (accept(selectable.getElements()))
        {
          return true;
        }
      }
    }
    return false;
  }

  private boolean accept(SkillReward skillReward)
  {
    return (_skill.equals(skillReward.getName()));
  }
}
