package delta.games.lotro.character.stats;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.Character;
import delta.games.lotro.character.io.xml.CharacterXMLWriter;

/**
 * Test for the character stats computer.
 * @author DAM
 */
public class TestCharacterStatsComputer
{
  private void doIt()
  {
    CharacterStatsComputer statsComputer=new CharacterStatsComputer();
    // Giswald
    {
      CharacterGenerator generator=new CharacterGenerator();
      Character c=generator.buildCharacter();
      BasicStatsSet stats=statsComputer.getStats(c);
      c.getStats().setStats(stats);
      CharacterXMLWriter w=new CharacterXMLWriter();
      w.write(new File("giswald.xml"),c,EncodingNames.UTF_8);
      System.out.println(stats.dump());
    }
    // Meva
    {
      CharacterGeneratorMeva generator=new CharacterGeneratorMeva();
      Character c=generator.buildCharacter();
      BasicStatsSet stats=statsComputer.getStats(c);
      c.getStats().setStats(stats);
      CharacterXMLWriter w=new CharacterXMLWriter();
      w.write(new File("meva.xml"),c,EncodingNames.UTF_8);
      System.out.println(stats.dump());
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
