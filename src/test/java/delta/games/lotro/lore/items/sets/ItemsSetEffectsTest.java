package delta.games.lotro.lore.items.sets;

import junit.framework.TestCase;

/**
 * Test display of item effects.
 * @author DAM
 */
public class ItemsSetEffectsTest extends TestCase
{
  private static int[] TEST_SET_IDS={
      1879150692, // Protector's Reproach (Max Level: 69)
  };

  /**
   * Test effects display.
   */
  public void testEffectsDisplay()
  {
    for(int sedId : TEST_SET_IDS)
    {
      ItemsSet set=ItemsSetsManager.getInstance().getSetById(sedId);
      showSet(set);
    }
  }

  private void showSet(ItemsSet set)
  {
    SetEffectsDisplay display=new SetEffectsDisplay();
    System.out.println("Set: "+set);
    for(SetBonus bonus : set.getBonuses())
    {
      String text=display.buildSetEffectsDisplay(set,bonus).trim();
      if (text.length()>0)
      {
        System.out.println("Nb pieces: "+bonus.getPiecesCount());
        System.out.println(text);
      }
    }
  }
}
