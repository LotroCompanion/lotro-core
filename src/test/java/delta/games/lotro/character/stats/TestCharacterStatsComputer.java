package delta.games.lotro.character.stats;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.io.xml.CharacterXMLWriter;
import delta.games.lotro.character.stats.contribs.StatsContributionsManager;

/**
 * Test for the character stats computer.
 * @author DAM
 */
public class TestCharacterStatsComputer
{
  private void doIt()
  {
    CharacterGenerationTools tools=new CharacterGenerationTools();
    // Giswald
    {
      CharacterStatsComputer statsComputer=new CharacterStatsComputer();
      CharacterGeneratorGiswald generator=new CharacterGeneratorGiswald(tools);
      CharacterData c=generator.buildCharacter();
      BasicStatsSet stats=statsComputer.getStats(c);
      c.getStats().setStats(stats);
      CharacterXMLWriter w=new CharacterXMLWriter();
      w.write(new File("giswald.xml"),c,EncodingNames.UTF_8);
      System.out.println(stats.dump());
    }
    // Meva
    {
      StatsContributionsManager contribs=new StatsContributionsManager();
      CharacterStatsComputer statsComputer=new CharacterStatsComputer(contribs);
      CharacterGeneratorMeva generator=new CharacterGeneratorMeva(tools);
      CharacterData c=generator.buildCharacter();
      BasicStatsSet stats=statsComputer.getStats(c);
      c.getStats().setStats(stats);
      CharacterXMLWriter w=new CharacterXMLWriter();
      w.write(new File("meva.xml"),c,EncodingNames.UTF_8);
      System.out.println(stats.dump());
      System.out.println(contribs);
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new TestCharacterStatsComputer().doIt();
  }
}
