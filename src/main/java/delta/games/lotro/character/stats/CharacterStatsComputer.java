package delta.games.lotro.character.stats;

import delta.games.lotro.character.Character;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.CharacterProficiencies;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivatedStatsContributionsMgr;
import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.character.stats.tomes.TomesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourType;
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
  private RatingsMgr _ratingsMgr;
  private CharacterProficiencies _proficiencies;

  /**
   * Constructor.
   */
  public CharacterStatsComputer()
  {
    _baseStatsMgr=new BaseStatsManager();
    _virtuesMgr=new VirtuesContributionsMgr();
    _tomesMgr=new TomesContributionsMgr();
    _ratingsMgr=new RatingsMgr();
    _proficiencies=new CharacterProficiencies();
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

    // Ratings
    BasicStatsSet ratings=computeRatings(c,raw);
    raw.addStats(ratings);
    return raw;
  }

  private BasicStatsSet computeRatings(Character c, BasicStatsSet stats)
  {
    int level=c.getLevel();
    BasicStatsSet ret=new BasicStatsSet();
    // Crit %
    FixedDecimalsInteger crit=stats.getStat(STAT.CRITICAL_RATING);
    FixedDecimalsInteger critPercentage=computeRating(_ratingsMgr.getCriticalHitCurve(),crit,level);
    // TODO missing 5% in melee for champ
    ret.setStat(STAT.CRITICAL_MELEE_PERCENTAGE,critPercentage);
    ret.setStat(STAT.CRITICAL_RANGED_PERCENTAGE,critPercentage);
    ret.setStat(STAT.CRITICAL_TACTICAL_PERCENTAGE,critPercentage);
    // Devastate %
    FixedDecimalsInteger devPercentage=computeRating(_ratingsMgr.getDevastateHitCurve(),crit,level);
    ret.setStat(STAT.DEVASTATE_MELEE_PERCENTAGE,devPercentage);
    ret.setStat(STAT.DEVASTATE_RANGED_PERCENTAGE,devPercentage);
    ret.setStat(STAT.DEVASTATE_TACTICAL_PERCENTAGE,devPercentage);
    // Crit&Dev Magnitude %
    FixedDecimalsInteger critDevMagnitudePercentage=computeRating(_ratingsMgr.getCritAndDevastateHitMagnitudeCurve(),crit,level);
    ret.setStat(STAT.CRIT_DEVASTATE_MAGNITUDE_MELEE_PERCENTAGE,critDevMagnitudePercentage);
    ret.setStat(STAT.CRIT_DEVASTATE_MAGNITUDE_RANGED_PERCENTAGE,critDevMagnitudePercentage);
    ret.setStat(STAT.CRIT_DEVASTATE_MAGNITUDE_TACTICAL_PERCENTAGE,critDevMagnitudePercentage);
    // Finesse %
    FixedDecimalsInteger finesse=stats.getStat(STAT.FINESSE);
    FixedDecimalsInteger finessePercentage=computeRating(_ratingsMgr.getFinesse(),finesse,level);
    ret.setStat(STAT.FINESSE_PERCENTAGE,finessePercentage);
    // Physical Damage %
    FixedDecimalsInteger physicalMastery=stats.getStat(STAT.PHYSICAL_MASTERY);
    FixedDecimalsInteger damagePercentage=computeRating(_ratingsMgr.getDamage(),physicalMastery,level);
    ret.setStat(STAT.MELEE_DAMAGE_PERCENTAGE,damagePercentage);
    ret.setStat(STAT.RANGED_DAMAGE_PERCENTAGE,damagePercentage);
    // Tactical Damage / Outgoing healing %
    FixedDecimalsInteger tacticalMastery=stats.getStat(STAT.TACTICAL_MASTERY);
    FixedDecimalsInteger tacticalDamagePercentage=computeRating(_ratingsMgr.getDamage(),tacticalMastery,level);
    FixedDecimalsInteger outgoingHealingPercentage=computeRating(_ratingsMgr.getHealing(),tacticalMastery,level);
    ret.setStat(STAT.TACTICAL_DAMAGE_PERCENTAGE,tacticalDamagePercentage);
    ret.setStat(STAT.OUTGOING_HEALING_PERCENTAGE,outgoingHealingPercentage);
    // Resistance %
    FixedDecimalsInteger resistance=stats.getStat(STAT.RESISTANCE);
    FixedDecimalsInteger resistancePercentage=computeRating(_ratingsMgr.getResistance(),resistance,level);
    ret.setStat(STAT.RESISTANCE_PERCENTAGE,resistancePercentage);
    // Critical Defence %
    FixedDecimalsInteger critDefence=stats.getStat(STAT.CRITICAL_DEFENCE);
    FixedDecimalsInteger critDefencePercentage=computeRating(_ratingsMgr.getCriticalDefence(),critDefence,level);
    ret.setStat(STAT.MELEE_CRITICAL_DEFENCE,critDefencePercentage);
    ret.setStat(STAT.RANGED_CRITICAL_DEFENCE,critDefencePercentage);
    ret.setStat(STAT.TACTICAL_CRITICAL_DEFENCE,critDefencePercentage);
    // Incoming healing %
    FixedDecimalsInteger incomingHealing=stats.getStat(STAT.INCOMING_HEALING);
    FixedDecimalsInteger incomingHealingPercentage=computeRating(_ratingsMgr.getIncomingHealing(),incomingHealing,level);
    ret.setStat(STAT.INCOMING_HEALING_PERCENTAGE,incomingHealingPercentage);
    // TODO Block
    // Parry %, Partial Parry %, Parry Mitigation %
    FixedDecimalsInteger parry=stats.getStat(STAT.PARRY);
    FixedDecimalsInteger parryPercentage=computeRating(_ratingsMgr.getAvoidance(),parry,level);
    ret.setStat(STAT.PARRY_PERCENTAGE,parryPercentage);
    FixedDecimalsInteger partialParryPercentage=computeRating(_ratingsMgr.getPartialAvoidance(),parry,level);
    ret.setStat(STAT.PARTIAL_PARRY_PERCENTAGE,partialParryPercentage);
    FixedDecimalsInteger partialParryMitigationPercentage=computeRating(_ratingsMgr.getPartialMitigation(),parry,level);
    partialParryMitigationPercentage.add(10);
    ret.setStat(STAT.PARTIAL_PARRY_MITIGATION_PERCENTAGE,partialParryMitigationPercentage);
    // Evade %, Partial Evade %, Evade Mitigation %
    FixedDecimalsInteger evade=stats.getStat(STAT.EVADE);
    FixedDecimalsInteger evadePercentage=computeRating(_ratingsMgr.getAvoidance(),evade,level);
    ret.setStat(STAT.EVADE_PERCENTAGE,evadePercentage);
    FixedDecimalsInteger partialEvadePercentage=computeRating(_ratingsMgr.getPartialAvoidance(),evade,level);
    ret.setStat(STAT.PARTIAL_EVADE_PERCENTAGE,partialEvadePercentage);
    FixedDecimalsInteger partialEvadeMitigationPercentage=computeRating(_ratingsMgr.getPartialMitigation(),evade,level);
    partialEvadeMitigationPercentage.add(10);
    ret.setStat(STAT.PARTIAL_EVADE_MITIGATION_PERCENTAGE,partialEvadeMitigationPercentage);
    // Physical Mitigation %
    FixedDecimalsInteger physicalMitigation=stats.getStat(STAT.PHYSICAL_MITIGATION);
    CharacterClass cClass=c.getCharacterClass();
    RatingCurve mitigation=getMitigationCurve(cClass);
    FixedDecimalsInteger physicalMitigationPercentage=computeRating(mitigation,physicalMitigation,level);
    ret.setStat(STAT.PHYSICAL_MITIGATION_PERCENTAGE,physicalMitigationPercentage);
    // TODO Orc-craft and Fell-wrought mitigation
    FixedDecimalsInteger tacticalMitigation=stats.getStat(STAT.TACTICAL_MITIGATION);
    FixedDecimalsInteger tacticalMitigationPercentage=computeRating(mitigation,tacticalMitigation,level);
    ret.setStat(STAT.TACTICAL_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    return ret;
  }

  private RatingCurve getMitigationCurve(CharacterClass cClass)
  {
    ArmourType type=_proficiencies.getArmourTypeForMitigations(cClass);
    if (type==ArmourType.LIGHT) return _ratingsMgr.getLightArmorMitigation();
    if (type==ArmourType.MEDIUM) return _ratingsMgr.getMediumArmorMitigation();
    if (type==ArmourType.HEAVY) return _ratingsMgr.getHeavyArmorMitigation();
    return null;
  }

  private FixedDecimalsInteger computeRating(RatingCurve curve, FixedDecimalsInteger rating, int level)
  {
    FixedDecimalsInteger ret;
    Double percentage=curve.getPercentage(rating.doubleValue(),level);
    if (percentage!=null)
    {
      // Round to 1 decimal
      long roundedValue=Math.round(percentage.doubleValue()*10);
      ret=new FixedDecimalsInteger();
      ret.setRawValue((int)(roundedValue*10));
    }
    else
    {
      ret=new FixedDecimalsInteger();
    }
    return ret;
  }
}
