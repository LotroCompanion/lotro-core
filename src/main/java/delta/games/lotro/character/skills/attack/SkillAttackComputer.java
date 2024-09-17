package delta.games.lotro.character.skills.attack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.DamageQualifiers;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.global.CombatSystem;
import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.utils.maths.Progression;

/**
 * Skill attack computer.
 * @author DAM
 */
public class SkillAttackComputer
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillAttackComputer.class);

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

    float baseDamageQualifier=classData.getBaseDamageForQualifier(damageQualifier);
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
    return getQualifier(ratingStat,percentageStat,curveId,baseDamageQualifier);
  }

  private float getQualifier(StatDescription ratingStat, StatDescription percentageStat, RatingCurveId curveId, float baseDamageQualifier)
  {
    float rating=_character.getStat(ratingStat);
    // TODO Use cap if stat not set?
    int characterLevel=_character.getLevel();
    float ratingPercentage=getPercentage(curveId,rating,characterLevel);
    float ratingPercentageMultiplier=1+ratingPercentage/100;
    float damageQualifier=baseDamageQualifier+_character.getStat(percentageStat); // TODO use "bonus" percentage only here
    damageQualifier*=ratingPercentageMultiplier;
    return damageQualifier;
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
    // Calculate Damage Qualifier
    float damageQualifier=getDamageQualifier(attack.getDamageQualifier());
    LOGGER.info("Damage qualifier: "+damageQualifier);

    // Calculate Skill Action Duration
    float skillActionDuration=1;
    Float nActionDurationContr=_skill.getActionDurationContribution();
    if (nActionDurationContr!=null)
    {
      skillActionDuration+=nActionDurationContr.floatValue();
    }
    LOGGER.info("Skill duration (before induction): "+skillActionDuration);
    Induction induction=_skill.getInduction();
    if (induction!=null)
    {
      float baseInductionDuration=induction.getDuration();
      skillActionDuration+=baseInductionDuration;
    }
    LOGGER.info("Skill duration: "+skillActionDuration);

    // Damage
    float damageModifier=attack.getDamageModifier();
    ModPropertyList damageModifierMods=attack.getDamageModifiersMods();
    damageModifier+=_character.computeAdditiveModifiers(damageModifierMods);

    int charLevel=_character.getLevel();
    // Max damage
    float maxDamageProg=getProgressionValue(attack.getMaxDamageProgression(),charLevel,0);
    float maxDamage=attack.getMaxDamage()+maxDamageProg;
    ModPropertyList maxDamageMods=attack.getMaxDamageMods();
    maxDamage+=_character.computeAdditiveModifiers(maxDamageMods);
    // DPS
    float dpsAddModProg=getProgressionValue(attack.getDPSModProgression(),charLevel,0);
    float dpsAddMod=dpsAddModProg;
    ModPropertyList dpsMods=attack.getDPSMods();
    dpsAddMod+=_character.computeAdditiveModifiers(dpsMods);

    Float damageContribMultiplierFloat=attack.getDamageContributionMultiplier();
    float damageContribMultiplier=(damageContribMultiplierFloat!=null)?damageContribMultiplierFloat.floatValue():0;
    float damageAdd=skillActionDuration*dpsAddMod*damageContribMultiplier;
    maxDamage+=damageAdd;

    float damage=damageQualifier*damageModifier*maxDamage;
    if (minimum)
    {
      float variance=1-attack.getMaxDamageVariance();
      damage=damage*variance;
    }

    ImplementUsageType usesImpl=attack.getImplementUsageType();
    if (usesImpl!=null)
    {
      ItemInstance<? extends Item> item=_character.getImplement(usesImpl);
    }
    return 0;
  }
    /*
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

  private float getProgressionValue(Progression progression, int x, float defaultValue)
  {
    float ret=defaultValue;
    if (progression!=null)
    {
      Float progValue=progression.getValue(x);
      if (progValue!=null)
      {
        ret=progValue.floatValue();
      }
    }
    return ret;
  }
}
