package delta.games.lotro.lore.worldEvents;

import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;

/**
 * @author dm
 */
public class MainTestWorldEventConditionsRenderer
{
  private WorldEventConditionsRenderer _renderer=new WorldEventConditionsRenderer();

  private void doIt()
  {
    /*
    for(DeedDescription deed : DeedsManager.getInstance().getAll())
    {
      doAchievable(deed);
    }
    */
    for(QuestDescription quest : QuestsManager.getInstance().getAll())
    {
      doAchievable(quest);
    }
  }

  private void doAchievable(Achievable achievable)
  {
    AbstractWorldEventCondition condition=achievable.getWorldEventsRequirement();
    _renderer.renderWorldEventCondition(achievable,condition);
  }

  public static void main(String[] args)
  {
    new MainTestWorldEventConditionsRenderer().doIt();
  }
}
