package delta.games.lotro.lore.mood;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test for the mood data access. 
 * @author DAM
 */
public class MoodManagerTest extends TestCase
{
  /**
   * Test morale modifiers.
   */
  public void testMoraleModifiers()
  {
    MoodManager moodManager=MoodManager.getInstance();
    Assert.assertNotNull(moodManager);
    for(int i=-15;i<=15;i++)
    {
      float factor=moodManager.getMoraleModifier(i);
      System.out.println(i+" =>" +factor);
    }
  }
}
