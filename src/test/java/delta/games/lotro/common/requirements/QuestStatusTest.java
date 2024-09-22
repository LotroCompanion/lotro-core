package delta.games.lotro.common.requirements;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests for class QuestStatus.
 * @author DAM
 */
class QuestStatusTest
{
  /**
   * Test persistence keys.
   */
  @Test
  void testPersistenceKeys()
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
    assertSame(status,newStatus);
  }
}
