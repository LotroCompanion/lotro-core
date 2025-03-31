package delta.games.lotro.lore.items.sets;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test display of item effects.
 * @author DAM
 */
class ItemsSetEffectsTest
{
  private static final int[] TEST_SET_IDS={
      1879150692, // Protector's Reproach (Max Level: 69)
      1879301149, // Eighth Captain's Jewellery of Deeping Coomb&#10;(Max Level: 68)
  };

  /**
   * Test effects display.
   */
  @Test
  void testEffectsDisplay()
  {
    for(ItemsSet set : ItemsSetsManager.getInstance().getAll())
    {
      showSet(set);
    }
  }

  /**
   * Test specific effects display.
   */
  @Test
  void testSpecificEffectsDisplay()
  {
    for(int sedId : TEST_SET_IDS)
    {
      ItemsSet set=ItemsSetsManager.getInstance().getSetById(sedId);
      assertNotNull(set);
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
    int level=set.getSetLevel();
    for(SetBonus bonus : set.getBonuses())
    {
      List<String> text=display.buildSetEffectsDisplay(set,bonus,level);
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
