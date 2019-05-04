package delta.games.lotro.lore.quests;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;
import delta.games.lotro.utils.Proxy;

/**
 * Resolver for achievable proxies.
 * @author DAM
 */
public class AchievableProxiesResolver
{
  private static final Logger LOGGER=Logger.getLogger(AchievableProxiesResolver.class);

  private HashMap<Integer,Achievable> _mapByKey;

  /**
   * Constructor.
   * @param quests Known quests.
   * @param deeds Known deeds.
   */
  public AchievableProxiesResolver(Collection<QuestDescription> quests, Collection<DeedDescription> deeds)
  {
    _mapByKey=new HashMap<Integer,Achievable>();
    for(DeedDescription deed : deeds)
    {
      _mapByKey.put(Integer.valueOf(deed.getIdentifier()),deed);
    }
    for(QuestDescription quest : quests)
    {
      _mapByKey.put(Integer.valueOf(quest.getIdentifier()),quest);
    }
  }

  /**
   * Resolve the proxies of a quest.
   * @param quest Quest to update.
   */
  public void resolveQuest(QuestDescription quest)
  {
    resolveObjectives(quest.getObjectives());
  }

  private void resolveObjectives(ObjectivesManager objectivesMgr)
  {
    for(Objective objective : objectivesMgr.getObjectives())
    {
      for(ObjectiveCondition condition : objective.getConditions())
      {
        if (condition instanceof QuestCompleteCondition)
        {
          QuestCompleteCondition completeCondition=(QuestCompleteCondition)condition;
          Proxy<Achievable> proxy=completeCondition.getProxy();
          if (proxy!=null)
          {
            int id=proxy.getId();
            Achievable target=findAchievable(id);
            if (target!=null)
            {
              proxy.setObject(target);
            }
            else
            {
              LOGGER.warn("Could not find achievable with ID="+id);
            }
          }
        }
      }
    }
  }

  private Achievable findAchievable(int id)
  {
    return _mapByKey.get(Integer.valueOf(id));
  }
}
