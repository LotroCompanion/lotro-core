package delta.games.lotro.character.status.traitPoints;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for trait points utilities.
 * @author DAM
 */
public class TestTraitPoints extends TestCase
{
  /**
   * Test the computation of the number of trait points given from the level.
   */
  public void testTraitPointsFromLevel()
  {
    int[] levels={6, 7, 8, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 120, 124, 125, 129, 130};
    int[] expected={5, 6, 7, 74, 75, 76, 76, 77, 77, 78, 78, 79, 79, 80, 81, 86, 88, 89, 91, 92};
    Assert.assertEquals(levels.length,expected.length);
    int nbItems=Math.min(levels.length,expected.length);
    for(int i=0;i<nbItems;i++)
    {
      int nbPoints=TraitPoints.getTraitPointsFromLevel(levels[i]);
      Assert.assertEquals("Item #"+i,expected[i],nbPoints);
    }
  }
}
