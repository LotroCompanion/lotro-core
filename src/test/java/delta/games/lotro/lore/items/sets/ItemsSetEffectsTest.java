package delta.games.lotro.lore.items.sets;

import java.util.List;

import junit.framework.TestCase;

/**
 * Test display of item effects.
 * @author DAM
 */
public class ItemsSetEffectsTest extends TestCase
{
  static int[] TEST_SET_IDS={
      1879150692, // Protector's Reproach (Max Level: 69)
      1879301149, // Eighth Captain's Jewellery of Deeping Coomb&#10;(Max Level: 68)
  };

  /**
   * Test effects display.
   */
  public void testEffectsDisplay()
  {
    //for(int sedId : TEST_SET_IDS)
    for(ItemsSet set : ItemsSetsManager.getInstance().getAll())
    {
      //ItemsSet set=ItemsSetsManager.getInstance().getSetById(sedId);
      showSet(set);
    }
  }

  private void showSet(ItemsSet set)
  {
    boolean hasEffects=hasEffects(set);
    if (!hasEffects)
    {
      return;
    }
    SetEffectsDisplay display=new SetEffectsDisplay();
    System.out.println("Set: "+set);
    for(SetBonus bonus : set.getBonuses())
    {
      List<String> text=display.buildSetEffectsDisplay(set,bonus);
      if (!text.isEmpty())
      {
        System.out.println("Nb pieces: "+bonus.getPiecesCount());
        for(String line : text)
        {
          System.out.println(line);
        }
      }
    }
  }

  private boolean hasEffects(ItemsSet set)
  {
    boolean doIt=false;
    List<SetBonus> bonuses=set.getBonuses();
    for(SetBonus bonus : bonuses)
    {
      ItemSetEffectsManager effectsMgr=bonus.getEffects();
      if (effectsMgr!=null)
      {
        doIt=true;
        break;
      }
    }
    return doIt;
  }
}
