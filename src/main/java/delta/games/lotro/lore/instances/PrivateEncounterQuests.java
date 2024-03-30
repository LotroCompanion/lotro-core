package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Storage for quests related to a private encounter.
 * @author DAM
 */
public class PrivateEncounterQuests
{
  private Proxy<QuestDescription> _parentQuest;
  private List<Proxy<QuestDescription>> _always;
  private int _randomQuestsCount;
  private List<Proxy<QuestDescription>> _random;

  /**
   * Constructor.
   */
  public PrivateEncounterQuests()
  {
    _parentQuest=null;
    _always=new ArrayList<Proxy<QuestDescription>>();
    _randomQuestsCount=0;
    _random=new ArrayList<Proxy<QuestDescription>>();
  }

  /**
   * Get the parent quest.
   * @return the parent quest.
   */
  public Proxy<QuestDescription> getParentQuest()
  {
    return _parentQuest;
  }

  /**
   * Set the parent quest.
   * @param quest Quest to set.
   */
  public void setParentQuest(Proxy<QuestDescription> quest)
  {
    _parentQuest=quest;
  }

  /**
   * Add a bestowed quest.
   * @param quest Quest to add.
   */
  public void addQuest(Proxy<QuestDescription> quest)
  {
    _always.add(quest);
  }

  /**
   * Get the bestowed quest.
   * @return A list of bestowed quests.
   */
  public List<Proxy<QuestDescription>> getQuests()
  {
    return _always;
  }

  /**
   * Get the number of random quests.
   * @return A random quests count.
   */
  public int getRandomQuestsCount()
  {
    return _randomQuestsCount;
  }

  /**
   * Set the random quests count.
   * @param randomQuestsCount Count to set.
   */
  public void setRandomQuestsCount(int randomQuestsCount)
  {
    _randomQuestsCount=randomQuestsCount;
  }

  /**
   * Add a random quest.
   * @param quest Quest to add.
   */
  public void addRandomQuest(Proxy<QuestDescription> quest)
  {
    _random.add(quest);
  }

  /**
   * Get the random quests.
   * @return A list of random quests.
   */
  public List<Proxy<QuestDescription>> getRandomQuests()
  {
    return _random;
  }

  /**
   * Dump the contents of this object to the given stream.
   * @param sb Output stream.
   */
  public void dump(StringBuilder sb)
  {
    if (_parentQuest!=null)
    {
      String q=_parentQuest.getId()+" - "+_parentQuest.getName();
      sb.append("Quest=").append(q).append(EndOfLine.NATIVE_EOL);
    }
    if (!_always.isEmpty())
    {
      sb.append("Bestowed quests: ");
      List<String> quests=new ArrayList<String>();
      for(Proxy<QuestDescription> q : _always)
      {
        quests.add(q.getId()+" - "+q.getName());
      }
      sb.append(String.join(", ",quests)).append(EndOfLine.NATIVE_EOL);
    }
    if ((_randomQuestsCount>0) && (!_random.isEmpty()))
    {
      sb.append("Random quests (").append(_randomQuestsCount).append("): ");
      List<String> quests=new ArrayList<String>();
      for(Proxy<QuestDescription> q : _random)
      {
        quests.add(q.getId()+" - "+q.getName());
      }
      sb.append(String.join(", ",quests)).append(EndOfLine.NATIVE_EOL);
    }
  }
}
