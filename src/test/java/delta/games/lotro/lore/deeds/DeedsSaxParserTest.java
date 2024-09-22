package delta.games.lotro.lore.deeds;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.deeds.io.xml.DeedXMLWriter;
import delta.games.lotro.lore.quests.AchievableProxiesResolver;

/**
 * Test class for the deeds SAX parser.
 * @author DAM
 */
class DeedsSaxParserTest
{
  /**
   * Test quests loading: loads quests and write them to a file.
   * Compare the produced file with the initial one.
   */
  @Test
  void testDeedsLoading()
  {
    List<DeedDescription> deeds=DeedsManager.getInstance().getAll();
    for(DeedDescription deed : deeds)
    {
      AchievableProxiesResolver.resolve(deed);
    }
    DeedXMLWriter writer=new DeedXMLWriter();
    File to=new File("deeds.xml");
    writer.writeDeeds(to,deeds,EncodingNames.UTF_8);
    // Compare file deeds.xml with the one loaded by the managed
  }
}
