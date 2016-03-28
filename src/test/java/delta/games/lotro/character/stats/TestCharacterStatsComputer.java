package delta.games.lotro.character.stats;

import delta.games.lotro.character.Character;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivatedStatsContributionsMgr;
import delta.games.lotro.character.stats.tomes.TomesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.essences.Essence;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Test for the character stats computer.
 * @author DAM
 */
public class TestCharacterStatsComputer
{
  private BasicStatsSet getRawVirtuesContribution(VirtuesSet virtues)
  {
    BasicStatsSet ret=new BasicStatsSet();
    VirtuesContributionsMgr virtuesMgr=new VirtuesContributionsMgr();
    for(int i=0;i<VirtuesSet.MAX_VIRTUES;i++)
    {
      VirtueId virtue=virtues.getSelectedVirtue(i);
      if (virtue!=null)
      {
        int rank=virtues.getVirtueRank(virtue);
        BasicStatsSet virtueContrib=virtuesMgr.getContribution(virtue,rank);
        ret.addStats(virtueContrib);
      }
    }
    return ret;
  }

  private BasicStatsSet getEquipmentStats(CharacterEquipment equipment)
  {
    BasicStatsSet ret=new BasicStatsSet();
    for(EQUIMENT_SLOT slot : EQUIMENT_SLOT.values())
    {
      SlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents!=null)
      {
        Item item=slotContents.getItem();
        if (item!=null)
        {
          BasicStatsSet itemStats=getItemStats(item);
          ret.addStats(itemStats);
        }
      }
    }
    return ret;
  }

  private BasicStatsSet getItemStats(Item item)
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStats(item.getTotalStats());
    // If the item has essences, use them
    EssencesSet essences=item.getEssences();
    if (essences!=null)
    {
      int nbSlots=essences.getSize();
      for(int i=0;i<nbSlots;i++)
      {
        Essence essence=essences.getEssence(i);
        if (essence!=null)
        {
          BasicStatsSet essenceStats=essence.getStats();
          ret.addStats(essenceStats);
        }
      }
    }
    // If its an armour, add Armour value
    if (item instanceof Armour)
    {
      Armour armour=(Armour)item;
      int armourValue=armour.getArmourValue();
      ret.addStat(STAT.ARMOUR,new FixedDecimalsInteger(armourValue));
    }
    return ret;
  }

  private void doIt()
  {
    CharacterGenerator generator=new CharacterGenerator();
    Character c=generator.buildCharacter();
    BaseStatsManager baseStatsMgr=new BaseStatsManager();
    BasicStatsSet baseStats=baseStatsMgr.getBaseStats(c.getCharacterClass(),c.getRace(),c.getLevel());
    BasicStatsSet virtuesStats=getRawVirtuesContribution(c.getVirtues());
    BasicStatsSet tomesStats=new TomesContributionsMgr().getContribution(c.getTomes());
    BasicStatsSet raw=new BasicStatsSet();
    raw.addStats(baseStats);
    raw.addStats(virtuesStats);
    raw.addStats(tomesStats);
    // Equipment
    BasicStatsSet equipmentStats=getEquipmentStats(c.getEquipment());
    raw.addStats(equipmentStats);
    // Misc
    // - yellow line buffs
    raw.addStat(STAT.MIGHT, new FixedDecimalsInteger(158));
    raw.addStat(STAT.FINESSE, new FixedDecimalsInteger(2486));
    // - Balance of Man
    raw.addStat(STAT.EVADE, new FixedDecimalsInteger(808));
    raw.addStat(STAT.PARRY, new FixedDecimalsInteger(808));
    raw.addStat(STAT.BLOCK, new FixedDecimalsInteger(808));

    DerivatedStatsContributionsMgr derivedStatsMgr=new DerivatedStatsContributionsMgr();
    BasicStatsSet derivedContrib=derivedStatsMgr.getContribution(c.getCharacterClass(),raw);
    raw.addStats(derivedContrib);
    System.out.println(raw.dump());
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new TestCharacterStatsComputer().doIt();
  }
}
