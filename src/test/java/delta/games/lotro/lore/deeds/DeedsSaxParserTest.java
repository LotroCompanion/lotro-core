package delta.games.lotro.lore.deeds;

import java.io.File;
import java.util.List;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.deeds.io.xml.DeedXMLWriter;
import delta.games.lotro.lore.quests.AchievableProxiesResolver;
import junit.framework.TestCase;

/**
 * Test class for the deeds SAX parser.
 * @author DAM
 */
public class DeedsSaxParserTest extends TestCase
{
  /**
   * Test quests loading: loads quests and write them to a file.
   * Compare the produced file with the initial one.
   */
  public void testDeedsLoading()
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
