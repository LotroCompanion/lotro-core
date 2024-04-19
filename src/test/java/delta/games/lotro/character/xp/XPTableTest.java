package delta.games.lotro.character.xp;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for the XP table.
 * @author DAM
 */
public class XPTableTest extends TestCase
{
  private static final long LEVEL2=100;
  private static final long LEVEL3=200;
  private static final long LEVEL4=300;

  private XPTable buildTable()
  {
    long[] values=new long[] {0, 0, LEVEL2, LEVEL3, LEVEL4};
    XPTable ret=new XPTable();
    ret.setXpTable(values);
    return ret;
  }

  /**
   * Test the 'get level' method.
   */
  public void testGetLevel()
  {
    XPTable toTest=buildTable();
    Assert.assertEquals(1,toTest.getLevel(0));
    Assert.assertEquals(1,toTest.getLevel(1));
    Assert.assertEquals(1,toTest.getLevel(LEVEL2-1));
    Assert.assertEquals(2,toTest.getLevel(LEVEL2));
    Assert.assertEquals(2,toTest.getLevel(LEVEL2+1));
    Assert.assertEquals(2,toTest.getLevel(LEVEL3-1));
    Assert.assertEquals(3,toTest.getLevel(LEVEL3));
    Assert.assertEquals(4,toTest.getLevel(LEVEL4));
    Assert.assertEquals(4,toTest.getLevel(LEVEL4+1));
    Assert.assertEquals(4,toTest.getLevel(1000));
  }
}
