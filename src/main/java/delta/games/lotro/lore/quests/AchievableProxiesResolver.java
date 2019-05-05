package delta.games.lotro.lore.quests;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
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

  private static final AchievableProxiesResolver _instance=new AchievableProxiesResolver();
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
    for(Proxy<Achievable> prerequisite : quest.getPrerequisites())
    {
      resolveProxy(prerequisite);
    }
    // Next quest
    resolveProxy(quest.getNextQuest());
    // Objectives
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
          resolveProxy(completeCondition.getProxy());
        }
      }
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
