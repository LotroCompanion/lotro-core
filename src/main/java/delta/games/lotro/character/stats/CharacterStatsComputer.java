package delta.games.lotro.character.stats;

import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.CharacterProficiencies;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr;
import delta.games.lotro.character.stats.base.io.DerivedStatContributionsIO;
import delta.games.lotro.character.stats.buffs.Buff;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.stats.buffs.BuffsManager;
import delta.games.lotro.character.stats.buffs.MoraleFromHopeOrDread;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.stats.contribs.StatsContributionsManager;
import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.character.stats.tomes.TomesContributionsMgr;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Character stats computer.
 * @author DAM
 */
public class CharacterStatsComputer
{
  private BaseStatsManager _baseStatsMgr;
  private TomesContributionsMgr _tomesMgr;
  private BuffInstance _hopeDread;
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
    _hopeDread=buildMoraleBuffFromHopeOrDread();
    _ratingsMgr=new RatingsMgr();
    _contribs=contribs;
  }

  private BuffInstance buildMoraleBuffFromHopeOrDread()
  {
    Buff buff=new Buff("MORALE_HOPE_DREAD", "", "Morale from Hope/Dread");
    buff.setImpl(new MoraleFromHopeOrDread());
    BuffInstance buffInstance=new BuffInstance(buff);
    return buffInstance;
  }

  private BasicStatsSet getEquipmentStats(CharacterEquipment equipment)
  {
    BasicStatsSet ret=new BasicStatsSet();
    for(EQUIMENT_SLOT slot : EQUIMENT_SLOT.values())
    {
      SlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents!=null)
      {
        ItemInstance<?> item=slotContents.getItem();
        if (item!=null)
        {
          BasicStatsSet itemStats=getItemStats(item);
          ret.addStats(itemStats);
          if (_contribs!=null)
          {
            StatsContribution contrib=StatsContribution.getGearContrib(slot,item,itemStats);
            _contribs.addContrib(contrib);
          }
        }
      }
    }
    return ret;
  }

  private BasicStatsSet getItemStats(ItemInstance<? extends Item> item)
  {
    return item.getStats();
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
    // Base stats (from character class, race and level)
    BasicStatsSet baseStats=_baseStatsMgr.getBaseStats(c.getCharacterClass(),c.getRace(),c.getLevel());
    if (_contribs!=null)
    {
      StatsContribution contrib=StatsContribution.getBodyContrib(baseStats);
      _contribs.addContrib(contrib);
    }
    // Virtues
    VirtuesContributionsMgr virtuesMgr=VirtuesContributionsMgr.get();
    VirtuesSet virtues=c.getVirtues();
    BasicStatsSet virtuesStats=virtuesMgr.getContribution(virtues,true);
    if (_contribs!=null)
    {
      // Active virtues
      for(int i=0;i<VirtuesSet.MAX_VIRTUES;i++)
      {
        VirtueId virtue=virtues.getSelectedVirtue(i);
        if (virtue!=null)
        {
          int rank=virtues.getVirtueRank(virtue);
          BasicStatsSet virtueContrib=virtuesMgr.getContribution(virtue,rank,false);
          StatsContribution contrib=StatsContribution.getVirtueContrib(virtue,rank,virtueContrib);
          _contribs.addContrib(contrib);
        }
      }
      // Passive virtues
      BasicStatsSet passiveStats=virtuesMgr.getContribution(virtues,false);
      StatsContribution contrib=StatsContribution.getPassiveVirtuesContrib(passiveStats);
      _contribs.addContrib(contrib);
    }
    // Tomes
    TomesSet tomes=c.getTomes();
    BasicStatsSet tomesStats=_tomesMgr.getContribution(tomes);
    if (_contribs!=null)
    {
      for(StatDescription stat : TomesSet.AVAILABLE_TOMES)
      {
        int rank=tomes.getTomeRank(stat);
        if (rank>0)
        {
          BasicStatsSet tomeContrib=_tomesMgr.getContribution(stat,rank);
          StatsContribution contrib=StatsContribution.getTomeContrib(stat,rank,tomeContrib);
          _contribs.addContrib(contrib);
        }
      }
    }

    // Equipment
    BasicStatsSet equipmentStats=getEquipmentStats(c.getEquipment());
    // Buffs
    BasicStatsSet buffs=c.getBuffs().getBuffs(c);
    if (_contribs!=null)
    {
      List<StatsContribution> contribs=c.getBuffs().getContributions(c);
      for(StatsContribution contrib : contribs)
      {
        _contribs.addContrib(contrib);
      }
    }
    // Misc
    BasicStatsSet additionalStats=c.getAdditionalStats();
    if (_contribs!=null)
    {
      if (additionalStats.getStatsCount()>0)
      {
        StatsContribution contrib=StatsContribution.getAdditionalContrib(additionalStats);
        _contribs.addContrib(contrib);
      }
    }

    // Total
    BasicStatsSet raw=new BasicStatsSet();
    raw.addStats(baseStats);
    raw.addStats(virtuesStats);
    raw.addStats(tomesStats);
    raw.addStats(equipmentStats);
    raw.addStats(buffs);
    raw.addStats(additionalStats);

    // Derived contributions
    DerivedStatsContributionsMgr derivedStatsMgr=DerivedStatContributionsIO.load();
    StatsContributionsManager contribsMgr=_contribs;
    if ((_contribs!=null) && (_contribs.isResolveIndirectContributions()))
    {
      contribsMgr=null;
    }
    {
      BasicStatsSet derivedContrib=derivedStatsMgr.getContribution(c.getCharacterClass(),raw,contribsMgr);
      raw.addStats(derivedContrib);
    }

    // Additional buff contributions
    BuffsManager buffsMgr=c.getBuffs();
    int nbBuffs=buffsMgr.getBuffsCount();
    for(int i=0;i<nbBuffs;i++)
    {
      BuffInstance buff=buffsMgr.getBuffAt(i);
      BasicStatsSet addedStats=BuffsManager.applyBuff(c,raw,_contribs,buff);
      if ((addedStats!=null) && (addedStats.getStatsCount()>0))
      {
        // Some of these buff may imply some derived stats (e.g armour gives mitigations)
        BasicStatsSet derivedContrib=derivedStatsMgr.getContribution(c.getCharacterClass(),addedStats,contribsMgr);
        raw.addStats(derivedContrib);
      }
    }

    // Hope
    BuffsManager.applyBuff(c,raw,_contribs,_hopeDread);

    // Ratings
    BasicStatsSet ratings=computeRatings(c,raw);
    raw.addStats(ratings);
    return raw;
  }

  private BasicStatsSet computeRatings(CharacterData c, BasicStatsSet stats)
  {
    int level=c.getLevel();
    BasicStatsSet ret=new BasicStatsSet();
    // Crit %
    FixedDecimalsInteger crit=stats.getStat(WellKnownStat.CRITICAL_RATING);
    FixedDecimalsInteger critPercentage=computePercentage(_ratingsMgr.getCriticalHitCurve(),crit,level);
    ret.setStat(WellKnownStat.CRITICAL_MELEE_PERCENTAGE,critPercentage);
    ret.setStat(WellKnownStat.CRITICAL_RANGED_PERCENTAGE,critPercentage);
    ret.setStat(WellKnownStat.CRITICAL_TACTICAL_PERCENTAGE,critPercentage);
    // Devastate %
    FixedDecimalsInteger devPercentage=computePercentage(_ratingsMgr.getDevastateHitCurve(),crit,level);
    ret.setStat(WellKnownStat.DEVASTATE_MELEE_PERCENTAGE,devPercentage);
    ret.setStat(WellKnownStat.DEVASTATE_RANGED_PERCENTAGE,devPercentage);
    ret.setStat(WellKnownStat.DEVASTATE_TACTICAL_PERCENTAGE,devPercentage);
    // Crit&Dev Magnitude %
    FixedDecimalsInteger critDevMagnitudePercentage=computePercentage(_ratingsMgr.getCritAndDevastateHitMagnitudeCurve(),crit,level);
    ret.setStat(WellKnownStat.CRIT_DEVASTATE_MAGNITUDE_MELEE_PERCENTAGE,critDevMagnitudePercentage);
    ret.setStat(WellKnownStat.CRIT_DEVASTATE_MAGNITUDE_RANGED_PERCENTAGE,critDevMagnitudePercentage);
    ret.setStat(WellKnownStat.CRIT_DEVASTATE_MAGNITUDE_TACTICAL_PERCENTAGE,critDevMagnitudePercentage);
    // Finesse %
    FixedDecimalsInteger finesse=stats.getStat(WellKnownStat.FINESSE);
    FixedDecimalsInteger finessePercentage=computePercentage(_ratingsMgr.getFinesse(),finesse,level);
    ret.setStat(WellKnownStat.FINESSE_PERCENTAGE,finessePercentage);
    // Physical Damage %
    FixedDecimalsInteger physicalMastery=stats.getStat(WellKnownStat.PHYSICAL_MASTERY);
    FixedDecimalsInteger damagePercentage=computePercentage(_ratingsMgr.getDamage(),physicalMastery,level);
    ret.setStat(WellKnownStat.MELEE_DAMAGE_PERCENTAGE,damagePercentage);
    ret.setStat(WellKnownStat.RANGED_DAMAGE_PERCENTAGE,damagePercentage);
    // Tactical Damage / Outgoing healing %
    FixedDecimalsInteger tacticalMastery=stats.getStat(WellKnownStat.TACTICAL_MASTERY);
    FixedDecimalsInteger tacticalDamagePercentage=computePercentage(_ratingsMgr.getDamage(),tacticalMastery,level);
    FixedDecimalsInteger outgoingHealing=stats.getStat(WellKnownStat.OUTGOING_HEALING);
    FixedDecimalsInteger outgoingHealingPercentage=computePercentage(_ratingsMgr.getHealing(),outgoingHealing,level);
    ret.setStat(WellKnownStat.TACTICAL_DAMAGE_PERCENTAGE,tacticalDamagePercentage);
    ret.setStat(WellKnownStat.OUTGOING_HEALING_PERCENTAGE,outgoingHealingPercentage);
    // Resistance %
    FixedDecimalsInteger resistance=stats.getStat(WellKnownStat.RESISTANCE);
    FixedDecimalsInteger resistancePercentage=computePercentage(_ratingsMgr.getResistance(),resistance,level);
    ret.setStat(WellKnownStat.RESISTANCE_PERCENTAGE,resistancePercentage);
    // Critical Defence %
    FixedDecimalsInteger critDefence=stats.getStat(WellKnownStat.CRITICAL_DEFENCE);
    FixedDecimalsInteger critDefencePercentage=computePercentage(_ratingsMgr.getCriticalDefence(),critDefence,level);
    ret.setStat(WellKnownStat.MELEE_CRITICAL_DEFENCE,critDefencePercentage);
    ret.setStat(WellKnownStat.RANGED_CRITICAL_DEFENCE,critDefencePercentage);
    ret.setStat(WellKnownStat.TACTICAL_CRITICAL_DEFENCE,critDefencePercentage);
    // Incoming healing %
    FixedDecimalsInteger incomingHealing=stats.getStat(WellKnownStat.INCOMING_HEALING);
    FixedDecimalsInteger incomingHealingPercentage=computePercentage(_ratingsMgr.getIncomingHealing(),incomingHealing,level);
    ret.setStat(WellKnownStat.INCOMING_HEALING_PERCENTAGE,incomingHealingPercentage);
    // Block %, Partial Block %, Block Mitigation %
    FixedDecimalsInteger block=stats.getStat(WellKnownStat.BLOCK);
    FixedDecimalsInteger blockPercentage=computePercentage(_ratingsMgr.getAvoidance(),block,level);
    ret.setStat(WellKnownStat.BLOCK_PERCENTAGE,blockPercentage);
    FixedDecimalsInteger partialBlockPercentage=computePercentage(_ratingsMgr.getPartialAvoidance(),block,level);
    ret.setStat(WellKnownStat.PARTIAL_BLOCK_PERCENTAGE,partialBlockPercentage);
    FixedDecimalsInteger partialBlockMitigationPercentage=computePercentage(_ratingsMgr.getPartialMitigation(),block,level);
    partialBlockMitigationPercentage.add(10);
    ret.setStat(WellKnownStat.PARTIAL_BLOCK_MITIGATION_PERCENTAGE,partialBlockMitigationPercentage);
    // Parry %, Partial Parry %, Parry Mitigation %
    FixedDecimalsInteger parry=stats.getStat(WellKnownStat.PARRY);
    FixedDecimalsInteger parryPercentage=computePercentage(_ratingsMgr.getAvoidance(),parry,level);
    ret.setStat(WellKnownStat.PARRY_PERCENTAGE,parryPercentage);
    FixedDecimalsInteger partialParryPercentage=computePercentage(_ratingsMgr.getPartialAvoidance(),parry,level);
    ret.setStat(WellKnownStat.PARTIAL_PARRY_PERCENTAGE,partialParryPercentage);
    FixedDecimalsInteger partialParryMitigationPercentage=computePercentage(_ratingsMgr.getPartialMitigation(),parry,level);
    partialParryMitigationPercentage.add(10);
    ret.setStat(WellKnownStat.PARTIAL_PARRY_MITIGATION_PERCENTAGE,partialParryMitigationPercentage);
    // Evade %, Partial Evade %, Evade Mitigation %
    FixedDecimalsInteger evade=stats.getStat(WellKnownStat.EVADE);
    FixedDecimalsInteger evadePercentage=computePercentage(_ratingsMgr.getAvoidance(),evade,level);
    ret.setStat(WellKnownStat.EVADE_PERCENTAGE,evadePercentage);
    FixedDecimalsInteger partialEvadePercentage=computePercentage(_ratingsMgr.getPartialAvoidance(),evade,level);
    ret.setStat(WellKnownStat.PARTIAL_EVADE_PERCENTAGE,partialEvadePercentage);
    FixedDecimalsInteger partialEvadeMitigationPercentage=computePercentage(_ratingsMgr.getPartialMitigation(),evade,level);
    partialEvadeMitigationPercentage.add(10);
    ret.setStat(WellKnownStat.PARTIAL_EVADE_MITIGATION_PERCENTAGE,partialEvadeMitigationPercentage);
    // Physical Mitigation %
    CharacterClass cClass=c.getCharacterClass();
    RatingCurve mitigation=getMitigationCurve(cClass);
    FixedDecimalsInteger physicalMitigation=stats.getStat(WellKnownStat.PHYSICAL_MITIGATION);
    FixedDecimalsInteger physicalMitigationPercentage=computePercentage(mitigation,physicalMitigation,level);
    ret.setStat(WellKnownStat.PHYSICAL_MITIGATION_PERCENTAGE,physicalMitigationPercentage);
    // Orc-craft and Fell-wrought mitigation %
    FixedDecimalsInteger ocfwMitigation=stats.getStat(WellKnownStat.OCFW_MITIGATION);
    FixedDecimalsInteger ocfwMitigationPercentage=computePercentage(mitigation,ocfwMitigation,level);
    ret.setStat(WellKnownStat.OCFW_MITIGATION_PERCENTAGE,ocfwMitigationPercentage);
    // Tactical mitigation %
    FixedDecimalsInteger tacticalMitigation=stats.getStat(WellKnownStat.TACTICAL_MITIGATION);
    FixedDecimalsInteger tacticalMitigationPercentage=computePercentage(mitigation,tacticalMitigation,level);
    ret.setStat(WellKnownStat.TACTICAL_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.FIRE_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.LIGHTNING_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.FROST_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.ACID_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    ret.addStat(WellKnownStat.SHADOW_MITIGATION_PERCENTAGE,tacticalMitigationPercentage);
    return ret;
  }

  private RatingCurve getMitigationCurve(CharacterClass cClass)
  {
    ArmourType type=CharacterProficiencies.getArmourTypeForMitigations(cClass);
    if (type==ArmourType.LIGHT) return _ratingsMgr.getLightArmorMitigation();
    if (type==ArmourType.MEDIUM) return _ratingsMgr.getMediumArmorMitigation();
    if (type==ArmourType.HEAVY) return _ratingsMgr.getHeavyArmorMitigation();
    return null;
  }

  private FixedDecimalsInteger computePercentage(RatingCurve curve, FixedDecimalsInteger rating, int level)
  {
    FixedDecimalsInteger ret=null;
    if (rating!=null)
    {
      Double percentage=curve.getPercentage(rating.doubleValue(),level);
      if (percentage!=null)
      {
        ret=new FixedDecimalsInteger(percentage.floatValue());
      }
    }
    if (ret==null)
    {
      ret=new FixedDecimalsInteger();
    }
    return ret;
  }
}
