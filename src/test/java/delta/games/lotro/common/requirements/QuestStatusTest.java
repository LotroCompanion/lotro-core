package delta.games.lotro.common.requirements;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Tests for class QuestStatus.
 * @author DAM
 */
public class QuestStatusTest extends TestCase
{
  /**
   * Test persistence keys.
   */
  public void testPersistenceKeys()
  {
    testIOForSingleValue(QuestStatus.COMPLETED);
    testIOForSingleValue(QuestStatus.UNDERWAY);
    testIOForSingleValue(QuestStatus.FAILED);
    for(int i=1;i<=QuestStatus.MIN_UNDERWAY_OBJECTIVE;i++)
    {
      testIOForSingleValue(QuestStatus.getUnderwayObjective(i));
    }
  }

  private void testIOForSingleValue(QuestStatus status)
  {
    String key=status.getKey();
    QuestStatus newStatus=QuestStatus.getByKey(key);
    Assert.assertSame(status,newStatus);
  }
}
