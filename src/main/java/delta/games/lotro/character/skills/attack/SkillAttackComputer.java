package delta.games.lotro.character.skills.attack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.DamageQualifiers;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.global.CombatSystem;
import delta.games.lotro.common.global.WeaponStrikeModifiers;
import delta.games.lotro.common.global.WeaponStrikeModifiersManager;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.DamageTypes;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Skill attack computer.
 * @author DAM
 */
public class SkillAttackComputer
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillAttackComputer.class);

  private CharacterDataForSkills _character;
  private StatModifiersComputer _statModsComputer;

  /**
   * Constructor.
   * @param data Access to character data related to skills.
   */
  public SkillAttackComputer(CharacterDataForSkills data)
  {
    _character=data;
    _statModsComputer=new StatModifiersComputer(data);
  }

  /**
   * Get the damage type for an attack.
   * @param attack Attack to use.
   * @return A damage type.
   */
  public DamageType getDamageType(SkillAttack attack)
  {
    DamageType damageType=attack.getDamageType();
    if (damageType!=null)
    {
      return damageType;
    }
    ImplementUsageType usesImpl=attack.getImplementUsageType();
    if (usesImpl!=null)
    {
      ItemInstance<? extends Item> item=_character.getImplement(usesImpl);
      if (item instanceof WeaponInstance<?>)
      {
        WeaponInstance<?> weapon=(WeaponInstance<?>)item;
        damageType=weapon.getEffectiveDamageType();
      }
    }
    if (damageType==null)
    {
      damageType=DamageTypes.COMMON;
    }
    return damageType;
  }

  /**
   * Get the damage qualifier value from the given damage qualifier.
   * @param damageQualifier Damage qualifier (melee/tactical/range).
   * @return A value.
   */
  public float getDamageQualifier(DamageQualifier damageQualifier)
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
    int characterLevel=_character.getLevel();
    float ratingPercentage=getPercentage(curveId,rating,characterLevel);
    float fullPercentage=_character.getStat(percentageStat);
    LOGGER.debug("Using percentage stat: {}",percentageStat.getName());

    float ratingPercentageMultiplier=1+ratingPercentage/100;
    LOGGER.debug("Rating % x: {}",Float.valueOf(ratingPercentageMultiplier));
    float damageQualifier=baseDamageQualifier;
    damageQualifier*=ratingPercentageMultiplier;
    float percentageBonus=fullPercentage-ratingPercentage;
    float bonusPercentageMultiplier=(1+percentageBonus/100);
    LOGGER.debug("Bonus % x: {}",Float.valueOf(bonusPercentageMultiplier));
    damageQualifier*=bonusPercentageMultiplier;
    return damageQualifier;
  }

  private float getPercentage(RatingCurveId curveId, float rating, int characterLevel)
  {
    RatingsMgr ratingsMgr=CombatSystem.getInstance().getRatingsMgr();
    RatingCurve curve=ratingsMgr.getCurve(curveId);
    Double percentage=curve.getPercentage(rating,characterLevel);
    return (percentage!=null)?percentage.floatValue():0;
  }

  /**
   * Get the healing qualifier value.
   * @return A value.
   */
  public float getHealingQualifier()
  {
    float fullPercentage=_character.getStat(WellKnownStat.OUTGOING_HEALING_PERCENTAGE);
    float ratingPercentageMultiplier=1+fullPercentage/100;
    return ratingPercentageMultiplier;
  }

  /**
   * Get the damage for a skill attack.
   * @param attack Attack to use.
   * @param minimum Use minimum damage or maximum damage.
   * @param skillActionDuration Action duration (seconds).
   * @return A damage value.
   */
  public float getAttackDamage(SkillAttack attack, boolean minimum, float skillActionDuration)
  {
    LOGGER.debug("Attack details: {}", attack);
    // Calculate Damage Qualifier
    DamageQualifier damageQualifier=attack.getDamageQualifier();
    LOGGER.debug("Damage qualifier: {}",damageQualifier);
    float damageQualifierValue=getDamageQualifier(damageQualifier);
    LOGGER.debug("Damage qualifier value: {}",Float.valueOf(damageQualifierValue));

    // Damage
    float damageModifier=attack.getDamageModifier();
    LOGGER.debug("Damage modifier (from attack): {}",Float.valueOf(damageModifier));
    ModPropertyList damageModifierMods=attack.getDamageModifiersMods();
    float damageModifierModValue=_statModsComputer.computeAdditiveModifiers(damageModifierMods);
    damageModifier+=damageModifierModValue;
    LOGGER.debug("Damage modifier (with modifiers): {}",Float.valueOf(damageModifier));

    int charLevel=_character.getLevel();
    // Max damage
    float maxDamageAttack=attack.getMaxDamage();
    LOGGER.debug("Max damage (attack): {}",Float.valueOf(maxDamageAttack));
    float maxDamageProg=getProgressionValue(attack.getMaxDamageProgression(),charLevel,0);
    LOGGER.debug("Max damage (progression): {}",Float.valueOf(maxDamageProg));
    float maxDamage=attack.getMaxDamage()+maxDamageProg;
    LOGGER.debug("Max damage: {}",Float.valueOf(maxDamage));
    ModPropertyList maxDamageMods=attack.getMaxDamageMods();
    float maxDamageModsValue=_statModsComputer.computeAdditiveModifiers(maxDamageMods);
    maxDamage+=maxDamageModsValue;
    LOGGER.debug("Max damage (with modifiers): {}",Float.valueOf(maxDamage));
    // DPS
    float dpsAddModProg=getProgressionValue(attack.getDPSModProgression(),charLevel,0);
    LOGGER.debug("DPS add mod (progression): {}",Float.valueOf(dpsAddModProg));
    ModPropertyList dpsMods=attack.getDPSMods();
    float dpsModsValue=_statModsComputer.computeAdditiveModifiers(dpsMods);
    float dpsAddMod=dpsAddModProg+dpsModsValue;
    LOGGER.debug("DPS add mod (with modifiers): {}",Float.valueOf(dpsAddMod));

    Float damageContribMultiplierFloat=attack.getDamageContributionMultiplier();
    float damageContribMultiplier=(damageContribMultiplierFloat!=null)?damageContribMultiplierFloat.floatValue():0;
    float damageAdd=skillActionDuration*dpsAddMod*damageContribMultiplier;
    maxDamage+=damageAdd;

    float damage=damageQualifierValue*damageModifier*maxDamage;
    LOGGER.debug("Hook max damage: damageQualifier*damageModifier*maxDamage");
    LOGGER.debug("Hook max damage: {}*{}*{}={}",Float.valueOf(damageQualifierValue),Float.valueOf(damageModifier),Float.valueOf(maxDamage),Float.valueOf(damage));
    if (minimum)
    {
      float variance=1-attack.getMaxDamageVariance();
      LOGGER.debug("Hook damage variance: {}",Float.valueOf(variance));
      damage=damage*variance;
      LOGGER.debug("Hook min damage: {}",Float.valueOf(damage));
    }

    float ret=damage;

    ImplementUsageType usesImpl=attack.getImplementUsageType();
    if (usesImpl!=null)
    {
      LOGGER.debug("Implement usage: {}",usesImpl);
      ItemInstance<? extends Item> item=_character.getImplement(usesImpl);
      LOGGER.debug("Using item: {}",item);

      boolean isWeapon=(item instanceof WeaponInstance);
      float implementBaseDamage=0;
      if ((usesImpl==ImplementUsageTypes.PRIMARY) || (usesImpl==ImplementUsageTypes.SECONDARY) || (usesImpl==ImplementUsageTypes.RANGED))
      {
        if (isWeapon)
        {
          WeaponInstance<?> weapon=(WeaponInstance<?>)item;
          implementBaseDamage=(minimum?weapon.getEffectiveMinDamageFloat():weapon.getEffectiveMaxDamageFloat());
          LOGGER.debug("Weapon base damage: {}",Float.valueOf(implementBaseDamage));
          // Weapon strike
          float factor=getWeaponStrikeMultiplier(weapon);
          LOGGER.debug("Using weapon strike multiplier: {}",Float.valueOf(factor));
          implementBaseDamage*=factor;
          LOGGER.debug("Weapon damage: {}",Float.valueOf(implementBaseDamage));
        }
      }
      else if (usesImpl==ImplementUsageTypes.TACTICAL_DPS)
      {
        if (item!=null)
        {
          StatDescription tacticalDPS=StatsRegistry.getInstance().getByKey("Combat_TacticalDPS_Modifier");
          Number dpsN=item.getStats().getStat(tacticalDPS);
          float dps=(dpsN!=null)?dpsN.floatValue():0;
          LOGGER.debug("Item tactical DPS: {}",Float.valueOf(dps));
          implementBaseDamage=dps;
        }
        implementBaseDamage+=20;
        LOGGER.debug("Tactical DPS: {}",Float.valueOf(implementBaseDamage));
        if (minimum)
        {
          float variance=_character.getTacticalDamageVariance();
          implementBaseDamage*=(1-variance);
          LOGGER.debug("Tactical min DPS: {}",Float.valueOf(implementBaseDamage));
        }
      }

      Float implementContribMultiplierFloat=attack.getImplementContributionMultiplier();
      float implementContribMultiplier=(implementContribMultiplierFloat!=null)?implementContribMultiplierFloat.floatValue():0;
      float implementDamage=damageQualifierValue*damageModifier*implementContribMultiplier*implementBaseDamage;
      LOGGER.debug("implementDamage=damageQualifier*damageModifier*implementContribMultiplier*implementBaseDamage");
      LOGGER.debug("implementDamage={}*{}*{}*{}",Float.valueOf(damageQualifierValue),Float.valueOf(damageModifier),Float.valueOf(implementContribMultiplier),Float.valueOf(implementBaseDamage));
      LOGGER.debug("Implement damage: {}",Float.valueOf(implementDamage));
      ret+=implementDamage;
    }
    LOGGER.debug("Total damage: {}",Float.valueOf(ret));

    return ret;
  }

  private float getWeaponStrikeMultiplier(WeaponInstance<?> weaponInstance)
  {
    WeaponStrikeModifiersManager weaponStrikeModsMgr=CombatSystem.getInstance().getData().getWeaponStrikeModifiersMgr();
    Weapon weapon=weaponInstance.getReference();
    WeaponType weaponType=weapon.getWeaponType();
    WeaponStrikeModifiers weaponStrikeMods=weaponStrikeModsMgr.getStrikeModifiers(weaponType);
    if (weaponStrikeMods!=null)
    {
      Integer weaponDamageMultiplierMod=weaponStrikeMods.getWeaponDamageMultiplier();
      if (weaponDamageMultiplierMod!=null)
      {
        StatDescription stat=StatsRegistry.getInstance().getById(weaponDamageMultiplierMod.intValue());
        float multiplier=_character.getStat(stat);
        if (multiplier>0)
        {
          float factor=1+(multiplier/100);
          return factor;
        }
      }
    }
    return 1;
  }

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
