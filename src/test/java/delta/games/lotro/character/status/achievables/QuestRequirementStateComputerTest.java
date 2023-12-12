package delta.games.lotro.character.status.achievables;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.status.achievables.io.DeedsStatusIo;
import delta.games.lotro.character.status.achievables.io.QuestsStatusIo;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.lore.quests.AchievableProxiesResolver;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for the {@link QuestRequirementStateComputer}.
 * @author DAM
 */
public class QuestRequirementStateComputerTest extends TestCase
{
  /**
   * Test quest requirements state computer.
   */
  public void testQuestRequirementsStat()
  {
    CharactersManager mgr=CharactersManager.getInstance();
    CharacterFile character=mgr.getToonById("Landroval","Giswald");
    AchievablesStatusManager questsStatusMgr=QuestsStatusIo.load(character);
    AchievablesStatusManager deedsStatusMgr=DeedsStatusIo.load(character);
    QuestRequirementStateComputer computer=new QuestRequirementStateComputer(questsStatusMgr,deedsStatusMgr);
    for(QuestDescription quest : QuestsManager.getInstance().getAll())
    {
      AbstractAchievableRequirement requirement=quest.getQuestRequirements();
      if (requirement!=null)
      {
        AchievableProxiesResolver.getInstance().resolveQuestRequirement(requirement);
        System.out.println("ID="+quest.getIdentifier()+" => "+quest.getName());
        boolean ok=computer.assess(requirement);
        System.out.println("\t=>"+(ok?"OK":"KO"));
      }
    }
    Assert.assertTrue(true);
  }
}
