package delta.games.lotro.lore.quests;

import java.util.HashMap;
import java.util.List;

import delta.games.lotro.utils.Proxy;

/**
 * Resolver for quest proxies.
 * @author DAM
 */
public class QuestProxiesResolver
{
  private List<QuestDescription> _quests;
  private HashMap<Integer,QuestDescription> _mapByKey;

  /**
   * Constructor.
   * @param quests Quests to process.
   */
  public QuestProxiesResolver(List<QuestDescription> quests)
  {
    _quests=quests;
  }

  private void loadMapByKey()
  {
    _mapByKey=new HashMap<Integer,QuestDescription>();
    for(QuestDescription quest : _quests)
    {
      int id=quest.getIdentifier();
      _mapByKey.put(Integer.valueOf(id),quest);
    }
  }

  /**
   * Do resolve links.
   */
  public void doIt()
  {
    loadMapByKey();
    // Resolve quest links
    for(QuestDescription quest : _quests)
    {
      resolveQuest(quest);
    }
  }

  private void resolveQuest(QuestDescription quest)
  {
    String name=quest.getName();
    for(Proxy<QuestDescription> prerequisiteProxy : quest.getPrerequisiteQuests())
    {
      resolveProxy(name,prerequisiteProxy);
    }
    resolveProxy(name,quest.getNextQuest());
  }

  private void resolveProxy(String name, Proxy<QuestDescription> proxy)
  {
    if (proxy==null) return;
    int id=proxy.getId();
    QuestDescription proxiedQuest=_mapByKey.get(Integer.valueOf(id));
    if (proxiedQuest!=null)
    {
      proxy.setName(proxiedQuest.getName());
      proxy.setObject(proxiedQuest);
    }
    else
    {
      System.out.println("Quest ["+name+"]: reference not found to id: "+id+", name: "+proxy.getName());
    }
  }
}
