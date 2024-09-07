package delta.games.lotro.character.skills.attack;

import org.apache.log4j.Logger;

import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.DamageQualifiers;
import delta.games.lotro.common.global.CombatSystem;
import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.inductions.InductionsManager;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;

/**
 * @author dmorcellet
 */
public class SkillAttackComputer
{
  private static final Logger LOGGER=Logger.getLogger(SkillAttackComputer.class);

  private CharacterDataForSkills _character;
  private SkillDetails _skill;

  private float getDamageQualifier(DamageQualifier damageQualifier)
  {
    if (damageQualifier==null)
    {
      return 0;
    }
    ClassDataForSkills classData=_character.getClassData();
    RatingCurveId curveId=RatingCurveId.DAMAGE; // TODO Use a specific curve for tactical damage?
    StatDescription ratingStat=WellKnownStat.PHYSICAL_MASTERY;
    StatDescription percentageStat=WellKnownStat.MELEE_DAMAGE_PERCENTAGE;

    float nDmgQfrBase=classData.getBaseDamageForQualifier(damageQualifier);
    if (damageQualifier==DamageQualifiers.MELEE)
    {
      // Already set up
    }
    else if (damageQualifier==DamageQualifiers.TACTICAL)
    {
      ratingStat=WellKnownStat.TACTICAL_MASTERY;
      percentageStat=WellKnownStat.TACTICAL_DAMAGE_PERCENTAGE;
    }
    else if (damageQualifier==DamageQualifiers.RANGED)
    {
      percentageStat=WellKnownStat.RANGED_DAMAGE_PERCENTAGE;
    }
    return getQualifier(ratingStat,percentageStat,curveId,nDmgQfrBase);
  }

  private float getQualifier(StatDescription ratingStat, StatDescription percentageStat, RatingCurveId curveId, float base)
  {
    float nRating=_character.getStat(ratingStat);
    // TODO Use cap if stat not set?
    int characterLevel=_character.getLevel();
    float ratingPercentage=getPercentage(curveId,nRating,characterLevel);
    float nRatPercMP=1+ratingPercentage/100;
    float nDmgQfrMP=base+_character.getStat(percentageStat);
    float nDamageQualifier=nRatPercMP*nDmgQfrMP;
    return nDamageQualifier;
  }

  private float getPercentage(RatingCurveId curveId, float rating, int characterLevel)
  {
    RatingsMgr ratingsMgr=CombatSystem.getInstance().getRatingsMgr();
    RatingCurve curve=ratingsMgr.getCurve(curveId);
    Double percentage=curve.getPercentage(rating,characterLevel);
    return (percentage!=null)?percentage.floatValue():0;
  }

  private float getHealingQualifier()
  {
    return getQualifier(WellKnownStat.OUTGOING_HEALING,WellKnownStat.OUTGOING_HEALING_PERCENTAGE,RatingCurveId.HEALING,0);
  }

  public float getAttackDamage(SkillAttack attack, boolean minimum)
  {
    DamageQualifier damageQualifier=_skill.getDamagerQualifier();
    // Calculate Damage Qualifier
    float nDamageQualifier=getDamageQualifier(damageQualifier);
    LOGGER.info("Damage qualifier: "+nDamageQualifier);

    // Calculate Skill Action Duration
    float nSkillActionDuration = 1;
    Float nActionDurationContr = _skill.getActionDurationContribution();
    if (nActionDurationContr!=null)
    {
      nSkillActionDuration+=nActionDurationContr.floatValue();
    }
    Integer inductionActionID=_skill.getInductionActionID();
    float baseInductionDuration=getInductionDuration(inductionActionID);
    nSkillActionDuration+=baseInductionDuration;
    LOGGER.info("Skill duration: "+nSkillActionDuration);

    // Damage
    float damageModifier=attack.getDamageModifier(); // HKDAMAGEMOD
    // Handle damage modifiers mods // HKDAMAGEMODMODS
    return 0;
  }

  /*
    local nHookDamageMaxProgValue = GetProgressionValue(skd.GetProp(aAD,"HKDAMAGEMAXPROG"),aChar[CHAR_LEVEL]);
    local nHookDamageMax = skd.GetProp(aAD,"HKDAMAGEMAX") + nHookDamageMaxProgValue;
    nHookDamageMax = AdditiveModify(nHookDamageMax,aChar,aAD,"HKDAMAGEMAXMODS");

    local nDPSAddModProgValue = GetProgressionValue(skd.GetProp(aAD,"DPSADDMODPROG"),aChar[CHAR_LEVEL]);
    local nDPSAddMod = AdditiveModify(nDPSAddModProgValue,aChar,aAD,"DPSADDMODMODS");
    local nDamageAdd = nSkillActionDuration * nDPSAddMod * skd.GetProp(aAD,"DAMAGEADDCONTRMP");
    
    nHookDamageMax = nHookDamageMax + nDamageAdd;

    local nVarianceMP = 1;
    if bMinimum then
      nVarianceMP = nVarianceMP - skd.GetProp(aAD,"HKDAMAGEMAXVAR");
    end

    nDamage = nDamage + csm.RoundDbl(nDamageQualifier * nHookDamageModifier * nHookDamageMax * nVarianceMP);
    
    local nUsesImp = skd.GetProp(aAD,"IMPUSAGE");
    if nUsesImp ~= nil then
      -- calculate implement damage
      local nImplementSelect = skd.GetProp(aAD,"IMPUSAGE");
      local aImp;
    
      if nImplementSelect == IMPSEL_PRIMARY then
        aImp = aChar[CHAR_IMP_PRIMARY];
      elseif nImplementSelect == IMPSEL_SECONDARY then
        aImp = aChar[CHAR_IMP_SECONDARY];
      elseif nImplementSelect == IMPSEL_RANGED then
        aImp = aChar[CHAR_IMP_RANGED];
      elseif nImplementSelect == IMPSEL_TACTICAL then
        aImp = aChar[CHAR_IMP_TACTICAL];
      end
      
      if nImplementSelect ~= IMPSEL_UNDEF then
        local nImpDamage = 0;

        if nImplementSelect == IMPSEL_TACTICAL then
          if aImp[IMP_ILVL] > 0 then
            nImpDamage = csm.CalcStat("CombatBaseTacDPS",aImp[IMP_ILVL]);
          end
          if aChar[CHAR_LEVEL] > 50 then
            nImpDamage = nImpDamage + 50;
          else
            nImpDamage = nImpDamage + aChar[CHAR_LEVEL];
          end
          if bMinimum then
            nImpDamage = nImpDamage * 0.7;
          end
        elseif aImp[IMP_ILVL] > 0 then
          if bMinimum then
            nImpDamage = csm.CalcStat("WpnDmgMin",aImp[IMP_ILVL],aImp[IMP_WPNCODE]);
          
          else
            nImpDamage = csm.CalcStat("WpnDmgMax",aImp[IMP_ILVL],aImp[IMP_WPNCODE]);
          end
        end
        
        nDamage = nDamage + csm.RoundDbl(nDamageQualifier * nHookDamageModifier * skd.GetProp(aAD,"IMPCONTRMP") * nImpDamage);
      end
    end
  end

  return nDamage;
  end

  function GetAttackDamageMin(aAttack)
  return GetAttackDamage(aAttack,true);
  end

  function GetAttackDamageMax(aAttack)
  return GetAttackDamage(aAttack,false);
  end
  */

  private float getInductionDuration(Integer inductionActionID)
  {
    if (inductionActionID==null)
    {
      return 0;
    } 
    InductionsManager inductionsMgr=InductionsManager.getInstance();
    Induction induction=inductionsMgr.get(inductionActionID.intValue());
    if (induction==null)
    {
      // TODO Warn
      return 0;
    }
    float ret=induction.getDuration();
    return ret;
  }
}

