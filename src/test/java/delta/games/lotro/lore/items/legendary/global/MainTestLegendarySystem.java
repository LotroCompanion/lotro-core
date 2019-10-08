package delta.games.lotro.lore.items.legendary.global;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacy;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Test class for the legendary system.
 * @author DAM
 */
public class MainTestLegendarySystem
{
  private void doIt()
  {
    //doItem(1879219224); // Reshaped Hunter's Crossbow of the First Age (75)
    //doItem(1879311761); // Reshaped Hunter's Axe of the First Age (100)
    doAllItems();
  }

  private void doAllItems()
  {
    ItemsManager itemsMgr=ItemsManager.getInstance();
    List<Item> items=itemsMgr.getAllItems();
    for(Item item : items)
    {
      if (item instanceof Legendary)
      {
        doItem(item.getIdentifier());
      }
    }
  }

  private Set<String> done=new HashSet<String>();

  private void doItem(int itemId)
  {
    // Get reference item
    Item item=ItemsManager.getInstance().getItem(itemId);
    String name=item.getName();
    System.out.println("Item ID: "+itemId+": "+name);

    // Build an instance of it
    ItemInstance<? extends Item> itemInstance=ItemFactory.buildInstance(item);

    // Load legendary system
    LegendarySystem legendarySystem=LegendarySystem.getInstance();

    // 1) Main legacy
    int[] mainLegacyRanks=legendarySystem.getRanksForMainLegacy(itemInstance);
    if (mainLegacyRanks!=null)
    {
      LegendaryInstance legendaryInstance=(LegendaryInstance)itemInstance;
      NonImbuedLegendaryInstanceAttrs nonImbuedAttrs=legendaryInstance.getLegendaryAttributes().getNonImbuedAttrs();
      DefaultNonImbuedLegacyInstance defaultLegacyInstance=nonImbuedAttrs.getDefaultLegacy();
      DefaultNonImbuedLegacy defaultLegacy=defaultLegacyInstance.getLegacy();
      StatsProvider statsProvider=defaultLegacy.getEffect().getStatsProvider();
      for(int i=0;i<mainLegacyRanks.length;i++)
      {
        BasicStatsSet stats=statsProvider.getStats(1,mainLegacyRanks[i]);
        FixedDecimalsInteger dpsValue=stats.getStat(WellKnownStat.DPS);
        if (dpsValue!=null)
        {
          float dpsFactor=getDpsFactor(item.getQuality());
          float dps=dpsValue.floatValue()*dpsFactor;
          stats.setStat(WellKnownStat.DPS,new FixedDecimalsInteger(dps));
        }
        System.out.println("Main Rank "+(i+1)+": "+stats);
      }
    }

    CharacterClass characterClass=item.getRequiredClass();
    if (characterClass==null) return; // Skip bridles
    EquipmentLocation slot=item.getEquipmentLocation();
    ItemQuality quality=item.getQuality();
    Integer itemLevel=item.getItemLevel();
    String key=characterClass.getKey()+"#"+slot+"#"+quality.getKey()+"#"+itemLevel;
    if (done.contains(key))
    {
      return;
    }
    done.add(key);
    // 2) Other legacies
    NonImbuedLegaciesManager legaciesMgr=NonImbuedLegaciesManager.getInstance();
    List<TieredNonImbuedLegacy> legacies=legaciesMgr.getTieredLegacies(characterClass,slot);
    for(TieredNonImbuedLegacy legacy : legacies)
    {
      System.out.println("Legacy: "+legacy.getStat().getName());
      List<NonImbuedLegacyTier> tiers=legacy.getTiers();
      NonImbuedLegacyTier tier=tiers.get(0);
      {
        System.out.println("\tTier "+tier.getTier()+": ");
        int[] ranks=legendarySystem.getRanksForLegacyTier(itemInstance,tier);
        if (ranks!=null)
        {
          for(int i=0;i<ranks.length;i++)
          {
            BasicStatsSet stats=tier.getEffect().getStatsProvider().getStats(1,ranks[i]);
            System.out.println("Rank "+(i+1)+": "+stats);
          }
        }
      }
    }
  }

  private float getDpsFactor(ItemQuality quality)
  {
    if (quality==ItemQuality.RARE) return 1.04f;
    if (quality==ItemQuality.INCOMPARABLE) return 1.08f;
    if (quality==ItemQuality.LEGENDARY) return 1.12f;
    return 0;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestLegendarySystem().doIt();
  }
}
