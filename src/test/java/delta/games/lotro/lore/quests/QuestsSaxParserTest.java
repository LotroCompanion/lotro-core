package delta.games.lotro.lore.quests;

import java.io.File;
import java.util.List;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.quests.io.xml.QuestXMLWriter;
import junit.framework.TestCase;

/**
 * Test class for the quests SAX parser.
 * @author DAM
 */
public class QuestsSaxParserTest extends TestCase
{
  /**
   * Test quests loading: loads quests and write them to a file.
   * Compare the produced file with the initial one.
   */
  public void testQuestsLoading()
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
