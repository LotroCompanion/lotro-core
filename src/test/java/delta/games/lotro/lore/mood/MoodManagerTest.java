package delta.games.lotro.lore.mood;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Test for the mood data access. 
 * @author DAM
 */
class MoodManagerTest
{
  /**
   * Test morale modifiers.
   */
  @Test
  void testMoraleModifiers()
  {
    MoodManager moodManager=MoodManager.getInstance();
    assertNotNull(moodManager);
    for(int i=-15;i<=15;i++)
    {
      float factor=moodManager.getMoraleModifier(i);
      System.out.println(i+" =>" +factor);
    }
  }
}
