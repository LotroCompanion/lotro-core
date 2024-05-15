package delta.games.lotro.character.storage.currencies;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author DAM
 */
public class TestCurrencyStorage extends TestCase
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
  public void testEmptyStorage()
  {
    CurrencyStorage storage=new CurrencyStorage();
    Assert.assertNull(storage.getValueAtTime(0));
  }

  /**
   * Test addition of the first item.
   */
  public void testFirstItemAddition()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    Assert.assertNull(storage.getValueAtTime(START_TIME1-1));
    Assert.assertNotNull(storage.getValueAtTime(START_TIME1));
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    Assert.assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of the second item.
   */
  public void testSecondItemAdditionAfter()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    storage.setValueAt(START_TIME2,VALUE2);
    Assert.assertNull(storage.getValueAtTime(START_TIME1-1));
    Assert.assertNotNull(storage.getValueAtTime(START_TIME1));
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    Assert.assertNotNull(storage.getValueAtTime(START_TIME1+1));
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
    Assert.assertNotNull(storage.getValueAtTime(START_TIME2));
    Assert.assertEquals(VALUE2,storage.getValueAtTime(START_TIME2).intValue());
    Assert.assertNull(storage.getValueAtTime(START_TIME2+1));
  }

   /**
   * Test addition of several points with the same value.
   */
  public void testAdditionOfPointsWithSameValue()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    Assert.assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1,VALUE1);
    Assert.assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME2,VALUE1);
    Assert.assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME2,VALUE1);
    Assert.assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME2+1,VALUE1);
    Assert.assertEquals(2,storage.getPoints());
  }

  /**
   * Test addition of point before the beginning: dd identical value.
   */
  public void testAdditionBeforeBeginning_addIdenticalValue()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    Assert.assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE1);
    Assert.assertEquals(2,storage.getPoints());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-1).intValue());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    Assert.assertNull(storage.getValueAtTime(START_TIME1-2));
    Assert.assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of point before the beginning: add identical value several times.
   */
  public void testAdditionBeforeBeginning_addIdenticalValueSeveralTimes()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    Assert.assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE1);
    Assert.assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME1-2,VALUE1);
    Assert.assertEquals(2,storage.getPoints());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-2).intValue());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-1).intValue());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    Assert.assertNull(storage.getValueAtTime(START_TIME1-3));
    Assert.assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of point before the beginning: add a different value.
   */
  public void testAdditionBeforeBeginning_addDifferentValue()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    Assert.assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE2);
    Assert.assertEquals(2,storage.getPoints());
    Assert.assertEquals(VALUE2,storage.getValueAtTime(START_TIME1-1).intValue());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    Assert.assertNull(storage.getValueAtTime(START_TIME1-2));
    Assert.assertNull(storage.getValueAtTime(START_TIME1+1));
  }

  /**
   * Test addition of point before the beginning: add an identical value that changes later.
   */
  public void testAdditionBeforeBeginning_addIdenticalValueThatChangesLater()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    storage.setValueAt(START_TIME1+1,VALUE2);
    Assert.assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME1-1,VALUE1);
    Assert.assertEquals(2,storage.getPoints());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1-1).intValue());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    Assert.assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+1).intValue());
    Assert.assertNull(storage.getValueAtTime(START_TIME1-2));
    Assert.assertNull(storage.getValueAtTime(START_TIME1+2));
  }

  /**
   * Test addition of point after beginning.
   */
  public void testAdditionAfterBeginning()
  {
    // Add identical value
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      Assert.assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME1+1,VALUE1);
      Assert.assertEquals(2,storage.getPoints());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      Assert.assertNull(storage.getValueAtTime(START_TIME1+2));
      Assert.assertNull(storage.getValueAtTime(START_TIME1-1));
    }
    // Add identical value several times
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      Assert.assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME1+1,VALUE1);
      Assert.assertEquals(2,storage.getPoints());
      storage.setValueAt(START_TIME1+2,VALUE1);
      Assert.assertEquals(2,storage.getPoints());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+2).intValue());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      Assert.assertNull(storage.getValueAtTime(START_TIME1+3));
      Assert.assertNull(storage.getValueAtTime(START_TIME1-1));
    }
    // Add different value
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      Assert.assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME1+1,VALUE2);
      Assert.assertEquals(2,storage.getPoints());
      Assert.assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+1).intValue());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      Assert.assertNull(storage.getValueAtTime(START_TIME1+2));
      Assert.assertNull(storage.getValueAtTime(START_TIME1-1));
    }
  }

  /**
   * Test addition of point after the beginning: add an identical value that changes before.
   */
  public void testAdditionBeforeBeginning_addIdenticalValueThatChangesBefore()
  {
    CurrencyStorage storage=new CurrencyStorage();
    storage.setValueAt(START_TIME1,VALUE1);
    Assert.assertEquals(1,storage.getPoints());
    storage.setValueAt(START_TIME1+1,VALUE2);
    Assert.assertEquals(2,storage.getPoints());
    storage.setValueAt(START_TIME1+2,VALUE2);
    Assert.assertEquals(3,storage.getPoints());
    Assert.assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+2).intValue());
    Assert.assertEquals(VALUE2,storage.getValueAtTime(START_TIME1+1).intValue());
    Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
    Assert.assertNull(storage.getValueAtTime(START_TIME1+3));
    Assert.assertNull(storage.getValueAtTime(START_TIME1-1));
  }

  /**
   * Test addition of point after beginning.
   */
  public void testInsertions()
  {
    // Add identical value
    {
      CurrencyStorage storage=new CurrencyStorage();
      storage.setValueAt(START_TIME1,VALUE1);
      Assert.assertEquals(1,storage.getPoints());
      storage.setValueAt(START_TIME2,VALUE2);
      Assert.assertEquals(2,storage.getPoints());
      storage.setValueAt(START_TIME3,VALUE3);
      Assert.assertEquals(3,storage.getPoints());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1+1).intValue());
      Assert.assertEquals(VALUE1,storage.getValueAtTime(START_TIME1).intValue());
      Assert.assertEquals(VALUE2,storage.getValueAtTime(START_TIME2).intValue());
      Assert.assertEquals(VALUE3,storage.getValueAtTime(START_TIME3).intValue());
      Assert.assertNull(storage.getValueAtTime(START_TIME1-1));
      Assert.assertNull(storage.getValueAtTime(START_TIME2+1));
    }
  }
}
