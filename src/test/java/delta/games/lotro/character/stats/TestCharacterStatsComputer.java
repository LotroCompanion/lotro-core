package delta.games.lotro.character.stats;

import delta.games.lotro.character.Character;

/**
 * Test for the character stats computer.
 * @author DAM
 */
public class TestCharacterStatsComputer
{
  private void doIt()
  {
    CharacterGenerator generator=new CharacterGenerator();
    Character c=generator.buildCharacter();
    CharacterStatsComputer statsComputer=new CharacterStatsComputer();
    BasicStatsSet stats=statsComputer.getStats(c);
    System.out.println(stats.dump());
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
