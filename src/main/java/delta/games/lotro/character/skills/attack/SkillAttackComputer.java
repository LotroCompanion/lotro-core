package delta.games.lotro.character.skills.attack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillDetailsUtils;
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
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.WellKnownStat;
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
  private SkillDetails _skill;

  /**
   * Constructor.
   * @param data Access to character data related to skills.
   * @param details Skill details.
   */
  public SkillAttackComputer(CharacterDataForSkills data, SkillDetails details)
  {
    _character=data;
    _skill=details;
  }

  private float getDamageQualifier(DamageQualifier damageQualifier)
  {
    if (damageQualifier==null)
    {
      return 0;
    }
    System.out.println("Damage qualifier: "+damageQualifier);
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
    System.out.println("Using percentage stat: "+percentageStat.getName());

    float ratingPercentageMultiplier=1+ratingPercentage/100;
    System.out.println("Rating % x: "+ratingPercentageMultiplier);
    float damageQualifier=baseDamageQualifier;
    damageQualifier*=ratingPercentageMultiplier;
    float percentageBonus=fullPercentage-ratingPercentage;
    float bonusPercentageMultipler=(1+percentageBonus/100);
    System.out.println("Bonus % x: "+bonusPercentageMultipler);
    damageQualifier*=bonusPercentageMultipler;
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

  /**
   * Get the damage for a skill attack.
   * @param attack Attack to use.
   * @param minimum Use minimum damage or maximum damage.
   * @return A damage value.
   */
  public float getAttackDamage(SkillAttack attack, boolean minimum)
  {
    System.out.println("Attack details: "+attack);
    float ret=0.0f;
    // Calculate Damage Qualifier
    float damageQualifier=getDamageQualifier(attack.getDamageQualifier());
    System.out.println("Damage qualifier: "+damageQualifier);

    // Calculate Skill Action Duration
    float skillActionDuration=1;
    Float actionDurationContribution=_skill.getActionDurationContribution();
    if (actionDurationContribution!=null)
    {
      skillActionDuration+=actionDurationContribution.floatValue();
    }
    System.out.println("Skill duration (before induction): "+skillActionDuration);
    float inductionDuration=SkillDetailsUtils.getInductionDuration(_skill,_character);
        skillActionDuration+=inductionDuration;
    System.out.println("Skill duration: "+skillActionDuration);

    // Damage
    float damageModifier=attack.getDamageModifier();
    System.out.println("Damage modifier (base): "+damageModifier);
    System.out.println("Damage modifier mods:");
    ModPropertyList damageModifierMods=attack.getDamageModifiersMods();
    float damageModifierModValue=_character.computeAdditiveModifiers(damageModifierMods);
    System.out.println("  => Damage modifier mods value: "+damageModifierModValue);
    damageModifier+=damageModifierModValue;
    System.out.println("Damage modifier: "+damageModifier);

    int charLevel=_character.getLevel();
    // Max damage
    float maxDamageProg=getProgressionValue(attack.getMaxDamageProgression(),charLevel,0);
    float maxDamage=attack.getMaxDamage()+maxDamageProg;
    System.out.println("Max damage: "+maxDamage);
    System.out.println("Max damage mods:");
    ModPropertyList maxDamageMods=attack.getMaxDamageMods();
    float maxDamageModsValue=_character.computeAdditiveModifiers(maxDamageMods);
    maxDamage+=maxDamageModsValue;
    System.out.println("  => Max damage mods value: "+maxDamageModsValue);
    // DPS
    float dpsAddModProg=getProgressionValue(attack.getDPSModProgression(),charLevel,0);
    float dpsAddMod=dpsAddModProg;
    System.out.println("DPS add mod: "+dpsAddMod);
    System.out.println("DPS mods:");
    ModPropertyList dpsMods=attack.getDPSMods();
    float dpsModsValue=_character.computeAdditiveModifiers(dpsMods);
    System.out.println("  => DPS mods value: "+dpsModsValue);
    dpsAddMod+=dpsModsValue;

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
    System.out.println("Hook damage: "+damage);
    ret+=damage;

    ImplementUsageType usesImpl=attack.getImplementUsageType();
    System.out.println("Implement usage: "+usesImpl);
    if (usesImpl!=null)
    {
      ItemInstance<? extends Item> item=_character.getImplement(usesImpl);
      System.out.println("Using item: "+item);

      boolean isWeapon=(item instanceof WeaponInstance);
      float implementBaseDamage=0;
      if ((usesImpl==ImplementUsageTypes.PRIMARY) || (usesImpl==ImplementUsageTypes.SECONDARY) || (usesImpl==ImplementUsageTypes.RANGED))
      {
        if (isWeapon)
        {
          WeaponInstance<?> weapon=(WeaponInstance<?>)item;
          implementBaseDamage=weapon.getEffectiveMaxDamageFloat();
          System.out.println("Weapon base damage: "+implementBaseDamage);
          // Weapon strike
          float factor=getWeaponStrikeMultiplier(weapon);
          System.out.println("Using weapon strike multiplier: "+factor);
          implementBaseDamage*=factor;
        }
      }
      else if (usesImpl==ImplementUsageTypes.TACTICAL_DPS)
      {
        if (item!=null)
        {
          StatDescription tacticalDPS=StatsRegistry.getInstance().getByKey("Combat_TacticalDPS_Modifier");
          Number dpsN=item.getStats().getStat(tacticalDPS);
          float dps=(dpsN!=null)?dpsN.floatValue():0;
          System.out.println("Tactical DPS: "+dps);
          implementBaseDamage=dps;
        }
        implementBaseDamage+=20;
      }
      System.out.println("Implement base damage: "+implementBaseDamage);

      Float implementContribMultiplierFloat=attack.getImplementContributionMultiplier();
      float implementContribMultiplier=(implementContribMultiplierFloat!=null)?implementContribMultiplierFloat.floatValue():0;
      System.out.println("implementDamage=damageQualifier*damageModifier*implementContribMultiplier*implementBaseDamage");
      System.out.println("implementDamage="+damageQualifier+"*"+damageModifier+"*"+implementContribMultiplier+"*"+implementBaseDamage);
      float implementDamage=damageQualifier*damageModifier*implementContribMultiplier*implementBaseDamage;
      System.out.println("Implement damage: "+implementDamage);
      ret+=implementDamage;
    }

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
