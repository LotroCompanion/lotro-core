package delta.games.lotro.character.storage.currencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Test currency storage.
 * @author DAM
 */
class TestCurrencyStorage
{
  private static final long START_TIME1=10000;
  private static final int VALUE1=100;
  private static final long START_TIME2=20000;
  private static final int VALUE2=200;
  private static final long START_TIME3=15000;
  private static final int VALUE3=150;

  /**
   * Test empty storage.
   */
  @Test
  void testEmptyStorage()
  {
    CurrencyStorage storage=new CurrencyStorage();
    assertNull(storage.getValueAtTime(0));
  }

  /**
   * Test addition of the first item.
   */
  @Test
  void testFirstItemAddition()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    assertNull(storage.getValueAtTime(START_TIME1-1));
    assertNotNull(storage.getValueAtTime(START_TIME1));
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of the second item.
   */
  @Test
  void testSecondItemAdditionAfter()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    storage.setValueAt(START_TIME2,VALUE2);
    assertNull(storage.getValueAtTime(START_TIME1-1));
    assertNotNull(storage.getValueAtTime(START_TIME1));
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    assertNotNull(storage.getValueAtTime(START_TIME1+1));
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
    assertNotNull(storage.getValueAtTime(START_TIME2));
    assertEquals(VALUE2,storage.getValueAtTime(START_TIME2).intValue());
    assertNull(storage.getValueAtTime(START_TIME2+1));
  }

   /**
   * Test addition of several points with the same value.
   */
  @Test
  void testAdditionOfPointsWithSameValue()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1,VALUE1);
    assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME2,VALUE1);
    assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME2,VALUE1);
    assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME2+1,VALUE1);
    assertEquals(2,storage.getPoints());
  }

  /**
   * Test addition of point before the beginning: dd identical value.
   */
  @Test
  void testAdditionBeforeBeginning_addIdenticalValue()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE1);
    assertEquals(2,storage.getPoints());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-1).intValue());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    assertNull(storage.getValueAtTime(START_TIME1-2));
    assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of point before the beginning: add identical value several times.
   */
  @Test
  void testAdditionBeforeBeginning_addIdenticalValueSeveralTimes()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE1);
    assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME1-2,VALUE1);
    assertEquals(2,storage.getPoints());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-2).intValue());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-1).intValue());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    assertNull(storage.getValueAtTime(START_TIME1-3));
    assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of point before the beginning: add a different value.
   */
  @Test
  void testAdditionBeforeBeginning_addDifferentValue()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE2);
    assertEquals(2,storage.getPoints());
    assertEquals(VALUE2,storage.getValueAtTime(START_TIME1-1).intValue());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    assertNull(storage.getValueAtTime(START_TIME1-2));
    assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of point before the beginning: add an identical value that changes later.
   */
  @Test
  void testAdditionBeforeBeginning_addIdenticalValueThatChangesLater()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    storage.setValueAt(START_TIME1+1,VALUE2);
    assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE1);
    assertEquals(2,storage.getPoints());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-1).intValue());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+1).intValue());
    assertNull(storage.getValueAtTime(START_TIME1-2));
    assertNull(storage.getValueAtTime(START_TIME1+2));
  }

  /**
   * Test addition of point after beginning.
   */
  @Test
  void testAdditionAfterBeginning()
  {
    // Add identical value
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME1+1,VALUE1);
      assertEquals(2,storage.getPoints());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      assertNull(storage.getValueAtTime(START_TIME1+2));
      assertNull(storage.getValueAtTime(START_TIME1-1));
    }
    // Add identical value several times
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME1+1,VALUE1);
      assertEquals(2,storage.getPoints());
      storage.setValueAt(START_TIME1+2,VALUE1);
      assertEquals(2,storage.getPoints());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+2).intValue());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      assertNull(storage.getValueAtTime(START_TIME1+3));
      assertNull(storage.getValueAtTime(START_TIME1-1));
    }
    // Add different value
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME1+1,VALUE2);
      assertEquals(2,storage.getPoints());
      assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+1).intValue());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      assertNull(storage.getValueAtTime(START_TIME1+2));
      assertNull(storage.getValueAtTime(START_TIME1-1));
    }
  }

  /**
   * Test addition of point after the beginning: add an identical value that changes before.
   */
  @Test
  void testAdditionBeforeBeginning_addIdenticalValueThatChangesBefore()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1+1,VALUE2);
    assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME1+2,VALUE2);
    assertEquals(3,storage.getPoints());
    assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+2).intValue());
    assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+1).intValue());
    assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    assertNull(storage.getValueAtTime(START_TIME1+3));
    assertNull(storage.getValueAtTime(START_TIME1-1));
  }

  /**
   * Test addition of point after beginning.
   */
  @Test
  void testInsertions()
  {
    // Add identical value
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME2,VALUE2);
      assertEquals(2,storage.getPoints());
      storage.setValueAt(START_TIME3,VALUE3);
      assertEquals(3,storage.getPoints());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
      assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      assertEquals(VALUE2,storage.getValueAtTime(START_TIME2).intValue());
      assertEquals(VALUE3,storage.getValueAtTime(START_TIME3).intValue());
      assertNull(storage.getValueAtTime(START_TIME1-1));
      assertNull(storage.getValueAtTime(START_TIME2+1));
    }
  }
}
