package delta.games.lotro.common.effects;

import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.character.skills.attack.SkillAttackComputer;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.utils.maths.Progression;

/**
 * @author dm
 */
public class EffectDisplay2
{
  private CharacterDataForSkills _character;
  private SkillAttackComputer _attackComputer;

  public EffectDisplay2(CharacterDataForSkills character, SkillDetails details)
  {
    _character=character;
    _attackComputer=new SkillAttackComputer(_character,details);
  }

  private float implementContrib(ImplementUsageType implementUsage, ItemInstance<?> item)
  {
    float vps=0;
    boolean noTHRClass=false; // TODO
    int level=_character.getLevel();
    if (implementUsage==ImplementUsageTypes.TACTICAL_DPS)
    {
      StatDescription tacticalDPS=StatsRegistry.getInstance().getByKey("Combat_TacticalDPS_Modifier");
      Number dpsN=item.getStats().getStat(tacticalDPS);
      vps=(dpsN!=null)?-dpsN.floatValue():0;
      if (level>50)
      {
        vps-=50;
      }
      else
      {
        vps-=level;
      }
    }
    else if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
    {
      if (noTHRClass)
      {
        //nVPS = csm.CalcStat("CombatBaseTacHPSNoClass",aChar[CHAR_LEVEL]);
        vps=100; // TODO
      }
      else
      {
        if (item!=null)
        {
          StatDescription tacticalDPS=StatsRegistry.getInstance().getByKey("Combat_TacticalHPS_Modifier");
          Number hpsN=item.getStats().getStat(tacticalDPS);
          vps=(hpsN!=null)?hpsN.floatValue():0;
        }
        else
        {
          vps=5.659143f; // Combat_Current_TacticalHPS
        }
        /*
        if (level>50)
        {
          vps+=0;
        }
        else
        {
          vps+=level;
        }
        */
      }
    }
    else
    {
      boolean isWeapon=(item instanceof WeaponInstance);
      if (isWeapon)
      {
        WeaponInstance<?> weapon=(WeaponInstance<?>)item;
        vps=-weapon.getEffectiveDPS();
      }
    }
    return vps;
  }

  public float getVitalChange(SkillEffectGenerator generator, BaseVitalEffect effect, VitalChangeDescription description, DamageQualifier damageQualifier, boolean initial, boolean minimum)
  {
    float change=0;
    float damageQualifierValue=_attackComputer.getDamageQualifier(damageQualifier);
    float healingQualifierValue=_attackComputer.getHealingQualifier();
    float modifiers=_character.computeAdditiveModifiers(description.getModifiers());
    Progression prog=description.getProgression();
    Float progValueF=prog.getValue(_character.getLevel());
    float progValue=(progValueF!=null)?progValueF.floatValue():0;
    ImplementUsageType implementUsage=generator.getImplementUsage();
    if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
    {
      change=(healingQualifierValue+modifiers)*progValue;
    }
    else
    {
      change=damageQualifierValue*(1+modifiers)*progValue;
    }
    Float vpsMultiplierValue=description.getVPSMultiplier();
    float vpsMultiplier=(vpsMultiplierValue!=null)?vpsMultiplierValue.floatValue():0;

    ItemInstance<?> item=_character.getImplement(implementUsage);
    if (implementUsage!=null)
    {
      float vps=implementContrib(implementUsage,item);
      if (!initial)
      {
        Float duration=effect.getEffectDuration().getDuration();
        if (duration!=null)
        {
          vps*=duration.floatValue();
        }
      }
      if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
      {
        change+=(healingQualifierValue+modifiers)*vpsMultiplier*vps;
      }
      else
      {
        change+=damageQualifierValue*(1+modifiers)*vpsMultiplier*vps;
      }
    }

    Float variance=description.getVariance();
    if ((variance!=null) && (minimum))
    {
      change*=(1-variance.floatValue());
    }
    return change;
  }
}
