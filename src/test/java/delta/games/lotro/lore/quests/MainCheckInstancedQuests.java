package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncounterQuests;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.utils.Proxy;

/**
 * Tool to check data on quests/private encounters.
 * @author DAM
 */
public class MainCheckInstancedQuests
{
  private void doIt()
  {
    List<QuestDescription> peQuests=getPeQuests();
    System.out.println("Found "+peQuests.size()+" private encounter quests");
    Set<QuestDescription> peQuestsSet=new HashSet<QuestDescription>(peQuests);
    List<QuestDescription> instancedQuests=getInstancedQuests();
    Set<QuestDescription> instancedQuestsSet=new HashSet<QuestDescription>(instancedQuests);
    {
      System.out.println("Instanced quests that do not have a private encounter:");
      Set<QuestDescription> instancedNotPe=new HashSet<QuestDescription>(instancedQuestsSet);
      instancedNotPe.removeAll(peQuestsSet);
      List<QuestDescription> toShow=new ArrayList<QuestDescription>(instancedNotPe);
      for(QuestDescription quest : toShow)
      {
        System.out.println(quest);
      }
      // => only 7
    }
    {
      System.out.println("Private encounter quest that are not instanced:");
      Set<QuestDescription> peNotInstanced=new HashSet<QuestDescription>(peQuestsSet);
      peNotInstanced.removeAll(instancedQuestsSet);
      List<QuestDescription> toShow=new ArrayList<QuestDescription>(peNotInstanced);
      for(QuestDescription quest : toShow)
      {
        System.out.println(quest);
      }
      // => none!
    }
  }

  private List<QuestDescription> getInstancedQuests()
  {
    List<QuestDescription> ret=new ArrayList<QuestDescription>();
    for(QuestDescription quest : QuestsManager.getInstance().getAll())
    {
      if (quest.isInstanced())
      {
        ret.add(quest);
      }
    }
    Collections.sort(ret,new IdentifiableComparator<>());
    return ret;
  }

  private List<QuestDescription> getPeQuests()
  {
    PrivateEncountersManager mgr=PrivateEncountersManager.getInstance();
    Set<QuestDescription> peQuests=new HashSet<QuestDescription>();
    for(PrivateEncounter pe : mgr.getPrivateEncounters())
    {
      PrivateEncounterQuests peQuestsData=pe.getQuests();
      Proxy<QuestDescription> parentProxy=peQuestsData.getParentQuest();
      if (parentProxy!=null)
      {
        int questId=parentProxy.getId();
        QuestDescription quest=QuestsManager.getInstance().getQuest(questId);
        if (quest!=null)
        {
          peQuests.add(quest);
        }
      }
    }
    List<QuestDescription> quests=new ArrayList<QuestDescription>(peQuests);
    Collections.sort(quests,new IdentifiableComparator<>());
    return quests;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainCheckInstancedQuests().doIt();
  }
}
