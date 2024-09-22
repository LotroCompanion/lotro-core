package delta.games.lotro.lore.quests;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.quests.io.xml.QuestXMLWriter;

/**
 * Test class for the quests SAX parser.
 * @author DAM
 */
class QuestsSaxParserTest
{
  /**
   * Test quests loading: loads quests and write them to a file.
   * Compare the produced file with the initial one.
   */
  @Test
  void testQuestsLoading()
  {
    List<QuestDescription> quests=QuestsManager.getInstance().getAll();
    for(QuestDescription quest : quests)
    {
      AchievableProxiesResolver.resolve(quest);
    }
    QuestXMLWriter writer=new QuestXMLWriter();
    File to=new File("quests.xml");
    writer.writeQuests(to,quests,EncodingNames.UTF_8);
    // Compare file quests.xml with the one loaded by the managed
  }
}
