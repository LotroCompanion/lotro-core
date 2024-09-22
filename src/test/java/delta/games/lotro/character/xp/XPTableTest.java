package delta.games.lotro.character.xp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the XP table.
 * @author DAM
 */
class XPTableTest
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
  @Test
  void testGetLevel()
  {
    XPTable toTest=buildTable();
    assertEquals(1,toTest.getLevel(0));
    assertEquals(1,toTest.getLevel(1));
    assertEquals(1,toTest.getLevel(LEVEL2-1));
    assertEquals(2,toTest.getLevel(LEVEL2));
    assertEquals(2,toTest.getLevel(LEVEL2+1));
    assertEquals(2,toTest.getLevel(LEVEL3-1));
    assertEquals(3,toTest.getLevel(LEVEL3));
    assertEquals(4,toTest.getLevel(LEVEL4));
    assertEquals(4,toTest.getLevel(LEVEL4+1));
    assertEquals(4,toTest.getLevel(1000));
  }
}
