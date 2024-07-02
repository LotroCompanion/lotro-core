package delta.games.lotro.lore.items.effects;

import java.util.List;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.SkillToExecute;
import delta.games.lotro.lore.items.effects.ItemEffectsManager.Type;
import junit.framework.TestCase;

/**
 * Test display of item effects.
 * @author DAM
 */
public class ItemEffectsTest extends TestCase
{
  private static int[] TEST_ITEM_IDS= {
      1879150044, // Lothlórien Protector's Locket
      1879049652, // Lesser Celebrant Salve
      1879049653, // Roast Pork
      1879049715, // Mushroom Pie
      1879050268, // Garth Agarwen Gate Key
      1879050465, // Flask of Lhinestad
      1879050706, // Dwarf Padded Waistcoat of Absorption
      1879052430, // Fire-oil
      1879054882, // Rust Dye Recipe
      1879055045, // Scroll of Battle Lore
      1879066712, // Rowan Camp-fire Kit
      1879070441, // Dwarf-iron Caltrops
      1879087990, // King's Leggings
      1879088738, // Stonehelm Shield
      1879090863, // Cloak of the Cluck
      1879097386, // Threkrand
      1879102287, // Strange Chicken Nest  (ambiguous)
      1879112236, // Trickster's Boots
      1879162588, // Race Horse
      1879163962, // Emblem of Wisdom
      1879408989, // Grant the Wine Tasting Emote
      1879401700, // Champion's Silk-steel Helm of the Endless Duel
      1879264476, // Greater Helm of the Erebor Gambler
      1879387359, // Purple Dwarf-candle
      1879285840, // Potent Enduring Bow of Evasion
      1879459939, // Pristine Carn Dûm Etched Necklace
  };

  /**
   * test effects display.
   */
  public void testEffectsDisplay()
  {
    for(int itemId : TEST_ITEM_IDS)
    {
      Item item=ItemsManager.getInstance().getItem(itemId);
      handleItem(item);
    }
  }

  /**
   * test effects display.
   */
  public void testEffectsDisplay2()
  {
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      showItem(item);
    }
  }

  private void handleItem(Item item)
  {
    ItemEffectsManager mgr=item.getEffects();
    if (mgr==null)
    {
      return;
    }
    EffectGenerator[] onEquip=mgr.getEffects(Type.ON_EQUIP);
    if (onEquip.length>0)
    {
      for(EffectGenerator effectGenerator : onEquip)
      {
        Effect effect=effectGenerator.getEffect();
        if (effect.getClass()==PropertyModificationEffect.class)
        {
          Float spellcraft=effectGenerator.getSpellcraft();
          System.out.println(item+" => "+effect.getIdentifier()+" - "+spellcraft);
        }
      }
    }
  }

  private void showItem(Item item)
  {
    if (hasEffects(item))
    {
      ItemEffectsDisplay display=new ItemEffectsDisplay();
      System.out.println("Item: "+item);
      List<String> text=display.buildItemEffectsDisplay(item);
      for(String line : text)
      {
        System.out.println(line);
      }
    }
  }

  private boolean hasEffects(Item item)
  {
    ItemEffectsManager effectsMgr=item.getEffects();
    if (effectsMgr!=null)
    {
      return true;
    }
    ItemDetailsManager detailsMgr=item.getDetails();
    if (detailsMgr!=null)
    {
      List<SkillToExecute> skills=item.getDetails().getItemDetails(SkillToExecute.class);
      if (skills.size()>0)
      {
        return true;
      }
    }
    return false;
  }
}
