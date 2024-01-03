package delta.games.lotro.lore.quests;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.CompoundQuestRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestBestowedCondition;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;
import delta.games.lotro.utils.Proxy;

/**
 * Resolver for achievable proxies.
 * @author DAM
 */
public class AchievableProxiesResolver
{
  private static final Logger LOGGER=Logger.getLogger(AchievableProxiesResolver.class);

  private static AchievableProxiesResolver _instance=null;
  private HashMap<Integer,Achievable> _mapByKey;

  private AchievableProxiesResolver()
  {
    List<QuestDescription> quests=QuestsManager.getInstance().getAll();
    List<DeedDescription> deeds=DeedsManager.getInstance().getAll();
    init(quests,deeds);
  }

  /**
   * Get the main instance of this class.
   * @return an instance.
   */
  public static AchievableProxiesResolver getInstance()
  {
    if (_instance==null)
    {
      _instance=new AchievableProxiesResolver();
    }
    return _instance;
  }

  /**
   * Static method to resolve a quest.
   * @param quest Quest to resolve.
   */
  public static void resolve(QuestDescription quest)
  {
    getInstance().resolveQuest(quest);
  }

  /**
   * Static method to resolve a deed.
   * @param deed Deed to resolve.
   */
  public static void resolve(DeedDescription deed)
  {
    getInstance().resolveDeed(deed);
  }

  /**
   * Constructor.
   * @param quests Known quests.
   * @param deeds Known deeds.
   */
  public AchievableProxiesResolver(Collection<QuestDescription> quests, Collection<DeedDescription> deeds)
  {
    init(quests,deeds);
  }

  private void init(Collection<QuestDescription> quests, Collection<DeedDescription> deeds)
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
   * Resolve the proxies of a deed.
   * @param deed Deed to update.
   */
  public void resolveDeed(DeedDescription deed)
  {
    // Pre-requisites
    AbstractAchievableRequirement questRequirement=deed.getQuestRequirements();
    resolveQuestRequirement(questRequirement);
    // Objectives
    resolveObjectives(deed.getObjectives());
  }

  /**
   * Resolve the proxies of a quest.
   * @param quest Quest to update.
   */
  public void resolveQuest(QuestDescription quest)
  {
    // Pre-requisites
    AbstractAchievableRequirement questRequirement=quest.getQuestRequirements();
    resolveQuestRequirement(questRequirement);
    // Next quest
    resolveProxy(quest.getNextQuest());
    // Objectives
    resolveObjectives(quest.getObjectives());
  }

  /**
   * Resolve a quest requirement.
   * @param requirement Requirement to resolve.
   */
  public void resolveQuestRequirement(AbstractAchievableRequirement requirement)
  {
    if (requirement instanceof CompoundQuestRequirement)
    {
      CompoundQuestRequirement compoundRequirement=(CompoundQuestRequirement)requirement;
      for(AbstractAchievableRequirement childRequirement : compoundRequirement.getRequirements())
      {
        resolveQuestRequirement(childRequirement);
      }
    }
    else if (requirement instanceof QuestRequirement)
    {
      QuestRequirement questRequirement=(QuestRequirement)requirement;
      resolveProxy(questRequirement.getRequiredAchievable());
    }
  }

  private void resolveObjectives(ObjectivesManager objectivesMgr)
  {
    for(ObjectiveCondition condition : objectivesMgr.getFailureConditions())
    {
      resolveCondition(condition);
    }
    for(Objective objective : objectivesMgr.getObjectives())
    {
      for(ObjectiveCondition condition : objective.getConditions())
      {
        resolveCondition(condition);
      }
      for(ObjectiveCondition condition : objective.getFailureConditions())
      {
        resolveCondition(condition);
      }
    }
  }

  private void resolveCondition(ObjectiveCondition condition)
  {
    if (condition instanceof QuestCompleteCondition)
    {
      QuestCompleteCondition completeCondition=(QuestCompleteCondition)condition;
      resolveProxy(completeCondition.getProxy());
    }
    else if (condition instanceof QuestBestowedCondition)
    {
      QuestBestowedCondition completeCondition=(QuestBestowedCondition)condition;
      resolveProxy(completeCondition.getProxy());
    }
  }

  private void resolveProxy(Proxy<Achievable> proxy)
  {
    if (proxy!=null)
    {
      int id=proxy.getId();
      Achievable target=findAchievable(id);
      if (target!=null)
      {
        proxy.setObject(target);
        proxy.setName(target.getName());
      }
      else
      {
        LOGGER.warn("Could not find achievable with ID="+id);
      }
    }
  }

  /**
   * Find an achievable using its identifier.
   * @param id Achievable identifier.
   * @return An achievable or <code>null</code> if not found.
   */
  public Achievable findAchievable(int id)
  {
    return _mapByKey.get(Integer.valueOf(id));
  }
}
