package delta.games.lotro.character.stats;

import delta.games.lotro.character.Character;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivatedStatsContributionsMgr;
import delta.games.lotro.character.stats.tomes.TomesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.essences.Essence;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Character stats computer.
 * @author DAM
 */
public class CharacterStatsComputer
{
  private BaseStatsManager _baseStatsMgr;
  private VirtuesContributionsMgr _virtuesMgr;
  private TomesContributionsMgr _tomesMgr;

  /**
   * Constructor.
   */
  public CharacterStatsComputer()
  {
    _baseStatsMgr=new BaseStatsManager();
    _virtuesMgr=new VirtuesContributionsMgr();
    _tomesMgr=new TomesContributionsMgr();
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

  /**
   * Compute the stats of a character.
   * @param c Character to use.
   * @return A set of stats.
   */
  public BasicStatsSet getStats(Character c)
  {
    // Base stats (from character class, race and level)
    BasicStatsSet baseStats=_baseStatsMgr.getBaseStats(c.getCharacterClass(),c.getRace(),c.getLevel());
    // Virtues
    BasicStatsSet virtuesStats=_virtuesMgr.getContribution(c.getVirtues());
    // Tomes
    BasicStatsSet tomesStats=_tomesMgr.getContribution(c.getTomes());
    // Equipment
    BasicStatsSet equipmentStats=getEquipmentStats(c.getEquipment());

    // Total
    BasicStatsSet raw=new BasicStatsSet();
    raw.addStats(baseStats);
    raw.addStats(virtuesStats);
    raw.addStats(tomesStats);
    raw.addStats(equipmentStats);

    // Misc
    BasicStatsSet additionalStats=c.getAdditionalStats();
    raw.addStats(additionalStats);

    // Derivated contributions
    DerivatedStatsContributionsMgr derivedStatsMgr=new DerivatedStatsContributionsMgr();
    BasicStatsSet derivedContrib=derivedStatsMgr.getContribution(c.getCharacterClass(),raw);
    raw.addStats(derivedContrib);
    return raw;
  }
}
