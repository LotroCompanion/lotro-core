package delta.games.lotro.lore.items.legendary.global;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemQualities;
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

/**
 * Test class for the legendary system.
 * @author DAM
 */
public class MainTestLegendarySystem
{
  private static final Logger LOGGER=LoggerFactory.getLogger(MainTestLegendarySystem.class);

  private Set<String> _done=new HashSet<String>();

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
      StatsProvider statsProvider=defaultLegacy.getStatsProvider();
      for(int i=0;i<mainLegacyRanks.length;i++)
      {
        BasicStatsSet stats=statsProvider.getStats(1,mainLegacyRanks[i]);
        Number dpsValue=stats.getStat(WellKnownStat.DPS);
        if (dpsValue!=null)
        {
          float dpsFactor=getDpsFactor(item.getQuality());
          float dps=dpsValue.floatValue()*dpsFactor;
          stats.setStat(WellKnownStat.DPS,Float.valueOf(dps));
        }
        System.out.println("Main Rank "+(i+1)+": "+stats);
      }
    }

    ClassDescription characterClass=(ClassDescription)item.getRequiredClass();
    if (characterClass==null) return; // Skip bridles
    EquipmentLocation slot=item.getEquipmentLocation();
    ItemQuality quality=item.getQuality();
    Integer itemLevel=item.getItemLevel();
    String key=characterClass.getKey()+"#"+slot+"#"+quality.getKey()+"#"+itemLevel;
    if (_done.contains(key))
    {
      return;
    }
    _done.add(key);
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
            BasicStatsSet stats=tier.getStatsProvider().getStats(1,ranks[i]);
            System.out.println("Rank "+(i+1)+": "+stats);
          }
        }
        else
        {
          LOGGER.warn("No ranks defined for item instance: {}, tier: {}",itemInstance,tier);
        }
      }
    }
  }

  private float getDpsFactor(ItemQuality quality)
  {
    if (quality==ItemQualities.RARE) return 1.04f;
    if (quality==ItemQualities.INCOMPARABLE) return 1.08f;
    if (quality==ItemQualities.LEGENDARY) return 1.12f;
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
