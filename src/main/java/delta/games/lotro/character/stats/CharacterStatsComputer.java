package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotContents;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr;
import delta.games.lotro.character.stats.base.io.DerivedStatContributionsIO;
import delta.games.lotro.character.stats.buffs.MoraleFromHopeOrDread;
import delta.games.lotro.character.stats.computer.MultiplyContribsComputer;
import delta.games.lotro.character.stats.computer.StatsStorage;
import delta.games.lotro.character.stats.computer.SubstractContribsComputer;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.stats.contribs.StatsContributionsManager;
import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.character.stats.tomes.TomesContributionsMgr;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.global.CombatSystem;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Character stats computer.
 * @author DAM
 */
public class CharacterStatsComputer
{
  private static final Logger LOGGER=Logger.getLogger(CharacterStatsComputer.class);

  private BaseStatsManager _baseStatsMgr;
  private TomesContributionsMgr _tomesMgr;
  private ItemsSetStatsComputer _itemsSetsMgr;
  private TraceriesSetStatsComputer _traceriesSetsMgr;
  private MoraleFromHopeOrDread _hopeDread;
  private RatingsMgr _ratingsMgr;
  private StatsContributionsManager _contribs;

  /**
   * Constructor.
   */
  public CharacterStatsComputer()
  {
    this(null);
  }

  /**
   * Constructor.
   * @param contribs Optional contributions manager.
   */
  public CharacterStatsComputer(StatsContributionsManager contribs)
  {
    _baseStatsMgr=new BaseStatsManager();
    _tomesMgr=new TomesContributionsMgr();
    _itemsSetsMgr=new ItemsSetStatsComputer();
    _traceriesSetsMgr=new TraceriesSetStatsComputer();
    _hopeDread=new MoraleFromHopeOrDread();
    _ratingsMgr=CombatSystem.getInstance().getRatingsMgr();
    _contribs=contribs;
  }

  private List<StatsContribution> getEquipmentStats(int characterLevel, CharacterGear equipment)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    // Iterate on slots
    for(GearSlot slot : GearSlot.values())
    {
      GearSlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents!=null)
      {
        ItemInstance<?> item=slotContents.getItem();
        if (item!=null)
        {
          BasicStatsSet itemStats=getItemStats(characterLevel,item);
          StatsContribution contrib=StatsContribution.getGearContrib(slot,item,itemStats);
          ret.add(contrib);
        }
      }
    }
    // Items sets
    List<StatsContribution> itemsSetsStats=_itemsSetsMgr.getStats(characterLevel,equipment);
    ret.addAll(itemsSetsStats);
    // Traceries sets
    List<StatsContribution> traceriesSetsStats=_traceriesSetsMgr.getStats(characterLevel,equipment);
    ret.addAll(traceriesSetsStats);
    return ret;
  }

  private BasicStatsSet getItemStats(int characterLevel, ItemInstance<? extends Item> item)
  {
    boolean isApplicable=StatsComputationUtils.itemIsApplicable(item);
    if (isApplicable)
    {
      return item.getStats();
    }
    return new BasicStatsSet();
  }

  /**
   * Compute the stats of a character.
   * @param c Character to use.
   * @return A set of stats.
   */
  public BasicStatsSet getStats(CharacterData c)
  {
    if (_contribs!=null)
    {
      _contribs.clear();
    }
    List<StatsContribution> allContribs=new ArrayList<StatsContribution>();
    // Base stats (from character class, race and level)
    BasicStatsSet baseStats=_baseStatsMgr.getBaseStats(c.getCharacterClass(),c.getRace(),c.getLevel());
    StatsContribution baseStatsContrib=StatsContribution.getBodyContrib(baseStats);
    allContribs.add(baseStatsContrib);

    // Tomes
    TomesSet tomes=c.getTomes();
    List<StatsContribution> tomeStatsContribs=_tomesMgr.getContribution(tomes);
    allContribs.addAll(tomeStatsContribs);

    // Equipment
    List<StatsContribution> equipmentStats=getEquipmentStats(c.getLevel(),c.getEquipment());
    allContribs.addAll(equipmentStats);

    // Buffs
    List<StatsContribution> buffContribs=c.getBuffs().getContributions(c);
    allContribs.addAll(buffContribs);

    // Traits
    List<StatsContribution> traitContribs=c.getTraits().getContributions(c);
    allContribs.addAll(traitContribs);

    // Virtues
    VirtuesContributionsMgr virtuesMgr=VirtuesContributionsMgr.get();
    VirtuesSet virtues=c.getVirtues();
    BasicStatsSet total=getStats(allContribs);
    virtues.setContributingStats(total);
    List<StatsContribution> virtuesStats=virtuesMgr.getContributions(virtues,true,true);
    allContribs.addAll(virtuesStats);

    // Misc
    StatsContribution additionalContrib=null;
    BasicStatsSet additionalStats=c.getAdditionalStats();
    if (additionalStats.getStatsCount()>0)
    {
      additionalContrib=StatsContribution.getAdditionalContrib(additionalStats);
      allContribs.add(additionalContrib);
    }

    // Hope
    List<StatsContribution> hopeContribs=handleHopeDread(allContribs);
    allContribs.addAll(hopeContribs);

    // Fix armour stats
    fixArmorStats(allContribs);

    // Aggregated
    total=getStats(allContribs);

    List<StatsContribution> allContribsWithDerivedStats=new ArrayList<StatsContribution>(allContribs);
    // Derived contributions
    DerivedStatsContributionsMgr derivedStatsMgr=DerivedStatContributionsIO.load();
    List<StatsContribution> derivedStatContribs=derivedStatsMgr.getContributions(c.getCharacterClass(),total);
    if ((_contribs!=null) && (_contribs.isResolveIndirectContributions()))
    {
      // Do not add derived contribs
    }
    else
    {
      allContribs.addAll(derivedStatContribs);
    }
    allContribsWithDerivedStats.addAll(derivedStatContribs);

    // New total with derived stats
    total=getStats(allContribs);

    // Total without multiplies, including derived stats
    BasicStatsSet noMultiplies=buildStatsStorage(allContribsWithDerivedStats).aggregate(false);

    // Handle multiply contribs
    MultiplyContribsComputer multiplyContribsComputer=new MultiplyContribsComputer();
    multiplyContribsComputer.handleMultiplyContribs(noMultiplies,allContribs);
    // Handle substract contribs
    SubstractContribsComputer substractContribsComputer=new SubstractContribsComputer();
    substractContribsComputer.handleSubstractContribs(allContribs);

    // Ratings
    BasicStatsSet ratings=computeRatings(c,total,allContribs);
    total.addStats(ratings);

    if (LOGGER.isDebugEnabled())
    {
      showContrib("Base",baseStatsContrib);
      showContribs("Stat tomes",tomeStatsContribs);
      showContribs("Equipment",equipmentStats);
      showContribs("Buffs",buffContribs);
      showContribs("Virtues",virtuesStats);
      showContribs("Derivations",derivedStatContribs);
      showContrib("Misc",additionalContrib);
      showContribs("Hope/dread",hopeContribs);
      LOGGER.debug("Ratings");
      LOGGER.debug("\t"+ratings);
      LOGGER.debug("Total");
      LOGGER.debug("\t"+total);
    }

    if (_contribs!=null)
    {
      for(StatsContribution contrib : allContribs)
      {
        _contribs.addContrib(contrib);
      }
    }

    // Total
    return total;
  }

  private void fixArmorStats(List<StatsContribution> contribs)
  {
    StatDescription armorFloatStat=WellKnownStat.get("Combat_Agent_Armor_Value_Float");
    for(StatsContribution contrib : contribs)
    {
      BasicStatsSet stats=contrib.getStats();
      StatsSetElement armorStatElement=stats.findElement(armorFloatStat);
      Number armorFloatValue=stats.getStat(armorFloatStat);
      if (armorStatElement!=null)
      {
        stats.removeStat(armorFloatStat);
        StatsSetElement newStatElement=new StatsSetElement(WellKnownStat.ARMOUR,armorStatElement.getOperator());
        newStatElement.setValue(armorFloatValue);
        newStatElement.setDescriptionOverride(armorStatElement.getDescriptionOverride());
        stats.addStat(newStatElement);
      }
    }
  }

  private void showContribs(String theme, List<StatsContribution> contribs)
  {
    if (contribs==null)
    {
      return;
    }
    LOGGER.debug(theme);
    if (!contribs.isEmpty())
    {
      for(StatsContribution contrib : contribs)
      {
        LOGGER.debug("\t"+contrib);
      }
    }
  }

  private void showContrib(String theme, StatsContribution contrib)
  {
    if (contrib!=null)
    {
      LOGGER.debug(theme);
      LOGGER.debug("\t"+contrib);
    }
  }

  private List<StatsContribution> handleHopeDread(List<StatsContribution> source)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    BasicStatsSet stats=getStats(source);
    Number hopeLevel=stats.getStat(WellKnownStat.HOPE);
    if ((hopeLevel!=null) && (hopeLevel.intValue()!=0))
    {
      float factor=_hopeDread.getMoraleFactor(hopeLevel.intValue());
      BasicStatsSet hopeStats=new BasicStatsSet();
      hopeStats.setStat(WellKnownStat.MORALE,StatOperator.MULTIPLY,Float.valueOf(factor),null);
      StatsContribution contrib=new StatsContribution("HOPE_DREAD","Morale from Hope/Dread",hopeStats);
      ret.add(contrib);
    }
    return ret;
  }

  private BasicStatsSet getStats(List<StatsContribution> contribs)
  {
    StatsStorage storage=buildStatsStorage(contribs);
    BasicStatsSet ret=storage.aggregate();
    return ret;
  }

  private StatsStorage buildStatsStorage(List<StatsContribution> contribs)
  {
    StatsStorage storage=new StatsStorage();
    for(StatsContribution contrib : contribs)
    {
      storage.addContrib(contrib);
    }
    return storage;
  }

  private BasicStatsSet computeRatings(CharacterData c, BasicStatsSet stats, List<StatsContribution> allContribs)
  {
    int level=c.getLevel();
    BasicStatsSet ret=new BasicStatsSet();
    // Crit %
    Number crit=stats.getStat(WellKnownStat.CRITICAL_RATING);
    float critPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.CRITICAL_HIT),crit,level);
    ret.setStat(WellKnownStat.CRITICAL_MELEE_PERCENTAGE,critPercentage);
    ret.setStat(WellKnownStat.CRITICAL_RANGED_PERCENTAGE,critPercentage);
    ret.setStat(WellKnownStat.CRITICAL_TACTICAL_PERCENTAGE,critPercentage);
    // Devastate %
    float devPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.DEVASTATE_HIT),crit,level);
    ret.setStat(WellKnownStat.DEVASTATE_MELEE_PERCENTAGE,devPercentage);
    ret.setStat(WellKnownStat.DEVASTATE_RANGED_PERCENTAGE,devPercentage);
    ret.setStat(WellKnownStat.DEVASTATE_TACTICAL_PERCENTAGE,devPercentage);
    // Crit&Dev Magnitude %
    float critDevMagnitudePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.CRIT_DEVASTATE_MAGNITUDE),crit,level);
    ret.setStat(WellKnownStat.CRIT_DEVASTATE_MAGNITUDE_MELEE_PERCENTAGE,critDevMagnitudePercentage);
    ret.setStat(WellKnownStat.CRIT_DEVASTATE_MAGNITUDE_RANGED_PERCENTAGE,critDevMagnitudePercentage);
    ret.setStat(WellKnownStat.CRIT_DEVASTATE_MAGNITUDE_TACTICAL_PERCENTAGE,critDevMagnitudePercentage);
    // Finesse %
    Number finesse=stats.getStat(WellKnownStat.FINESSE);
    float finessePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.FINESSE),finesse,level);
    ret.setStat(WellKnownStat.FINESSE_PERCENTAGE,finessePercentage);
    // Physical Damage %
    Number physicalMastery=stats.getStat(WellKnownStat.PHYSICAL_MASTERY);
    float damagePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.DAMAGE),physicalMastery,level);
    ret.setStat(WellKnownStat.MELEE_DAMAGE_PERCENTAGE,damagePercentage);
    ret.setStat(WellKnownStat.RANGED_DAMAGE_PERCENTAGE,damagePercentage);
    // Tactical Damage
    Number tacticalMastery=stats.getStat(WellKnownStat.TACTICAL_MASTERY);
    float tacticalDamagePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.DAMAGE),tacticalMastery,level);
    ret.setStat(WellKnownStat.TACTICAL_DAMAGE_PERCENTAGE,tacticalDamagePercentage);
    allContribs.add(StatsContribution.buildRatingContrib(WellKnownStat.TACTICAL_DAMAGE_PERCENTAGE,Float.valueOf(tacticalDamagePercentage),WellKnownStat.TACTICAL_MASTERY));
    // Outgoing healing %
    Number outgoingHealing=stats.getStat(WellKnownStat.OUTGOING_HEALING);
    float outgoingHealingPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.HEALING),outgoingHealing,level);
    ret.setStat(WellKnownStat.OUTGOING_HEALING_PERCENTAGE,outgoingHealingPercentage);
    allContribs.add(StatsContribution.buildRatingContrib(WellKnownStat.OUTGOING_HEALING_PERCENTAGE,Float.valueOf(outgoingHealingPercentage),WellKnownStat.OUTGOING_HEALING));
    // Resistance %
    Number resistance=stats.getStat(WellKnownStat.RESISTANCE);
    float resistancePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.RESISTANCE),resistance,level);
    ret.setStat(WellKnownStat.RESISTANCE_PERCENTAGE,resistancePercentage);
    // Critical Defence %
    Number critDefence=stats.getStat(WellKnownStat.CRITICAL_DEFENCE);
    float critDefencePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.CRITICAL_DEFENCE),critDefence,level);
    ret.setStat(WellKnownStat.MELEE_CRITICAL_DEFENCE,critDefencePercentage);
    ret.setStat(WellKnownStat.RANGED_CRITICAL_DEFENCE,critDefencePercentage);
    ret.setStat(WellKnownStat.TACTICAL_CRITICAL_DEFENCE,critDefencePercentage);
    // Incoming healing %
    Number incomingHealing=stats.getStat(WellKnownStat.INCOMING_HEALING);
    float incomingHealingPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.INCOMING_HEALING),incomingHealing,level);
    ret.setStat(WellKnownStat.INCOMING_HEALING_PERCENTAGE,incomingHealingPercentage);
    // Block %, Partial Block %, Block Mitigation %
    Number block=stats.getStat(WellKnownStat.BLOCK);
    float blockPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.AVOIDANCE),block,level);
    ret.setStat(WellKnownStat.BLOCK_PERCENTAGE,blockPercentage);
    float partialBlockPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.PARTIAL_AVOIDANCE),block,level);
    ret.setStat(WellKnownStat.PARTIAL_BLOCK_PERCENTAGE,partialBlockPercentage);
    float partialBlockMitigationPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.PARTIAL_MITIGATION),block,level);
    partialBlockMitigationPercentage+=10;
    ret.setStat(WellKnownStat.PARTIAL_BLOCK_MITIGATION_PERCENTAGE,partialBlockMitigationPercentage);
    // Parry %, Partial Parry %, Parry Mitigation %
    Number parry=stats.getStat(WellKnownStat.PARRY);
    float parryPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.AVOIDANCE),parry,level);
    ret.setStat(WellKnownStat.PARRY_PERCENTAGE,parryPercentage);
    float partialParryPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.PARTIAL_AVOIDANCE),parry,level);
    ret.setStat(WellKnownStat.PARTIAL_PARRY_PERCENTAGE,partialParryPercentage);
    float partialParryMitigationPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.PARTIAL_MITIGATION),parry,level);
    partialParryMitigationPercentage+=10;
    ret.setStat(WellKnownStat.PARTIAL_PARRY_MITIGATION_PERCENTAGE,partialParryMitigationPercentage);
    // Evade %, Partial Evade %, Evade Mitigation %
    Number evade=stats.getStat(WellKnownStat.EVADE);
    float evadePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.AVOIDANCE),evade,level);
    ret.setStat(WellKnownStat.EVADE_PERCENTAGE,evadePercentage);
    float partialEvadePercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.PARTIAL_AVOIDANCE),evade,level);
    ret.setStat(WellKnownStat.PARTIAL_EVADE_PERCENTAGE,partialEvadePercentage);
    float partialEvadeMitigationPercentage=computePercentage(_ratingsMgr.getCurve(RatingCurveId.PARTIAL_MITIGATION),evade,level);
    partialEvadeMitigationPercentage+=10;
    ret.setStat(WellKnownStat.PARTIAL_EVADE_MITIGATION_PERCENTAGE,partialEvadeMitigationPercentage);
    // Physical Mitigation %
    ClassDescription cClass=c.getCharacterClass();
    RatingCurve mitigation=getMitigationCurve(cClass);
    Number physicalMitigation=stats.getStat(WellKnownStat.PHYSICAL_MITIGATION);
    float physicalMitigationPercentage=computePercentage(mitigation,physicalMitigation,level);
    ret.setStat(WellKnownStat.PHYSICAL_MITIGATION_PERCENTAGE,physicalMitigationPercentage);
    // Orc-craft and Fell-wrought mitigation %
    Number ocfwMitigation=stats.getStat(WellKnownStat.OCFW_MITIGATION);
    float ocfwMitigationPercentage=computePercentage(mitigation,ocfwMitigation,level);
    ret.setStat(WellKnownStat.OCFW_MITIGATION_PERCENTAGE,ocfwMitigationPercentage);
    // Tactical mitigation %
    Number tacticalMitigation=stats.getStat(WellKnownStat.TACTICAL_MITIGATION);
    float tacticalMitigationPercentage=computePercentage(mitigation,tacticalMitigation,level);
    ret.setStat(WellKnownStat.TACTICAL_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.FIRE_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.LIGHTNING_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.FROST_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.ACID_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.SHADOW_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    return ret;
  }

  private RatingCurve getMitigationCurve(ClassDescription classDescription)
  {
    ArmourType type=classDescription.getProficiencies().getArmourTypeForMitigations();
    if (type==ArmourType.LIGHT) return _ratingsMgr.getCurve(RatingCurveId.LIGHT_MITIGATION);
    if (type==ArmourType.MEDIUM) return _ratingsMgr.getCurve(RatingCurveId.MEDIUM_MITIGATION);
    if (type==ArmourType.HEAVY) return _ratingsMgr.getCurve(RatingCurveId.HEAVY_MITIGATION);
    return null;
  }

  private float computePercentage(RatingCurve curve, Number rating, int level)
  {
    float ret=0f;
    if (rating!=null)
    {
      Double percentage=curve.getPercentage(rating.doubleValue(),level);
      if (percentage!=null)
      {
        ret=percentage.floatValue();
      }
    }
    return ret;
  }
}
