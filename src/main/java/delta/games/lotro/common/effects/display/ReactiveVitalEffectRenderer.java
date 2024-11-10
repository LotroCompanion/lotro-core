package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.EffectAndProbability;
import delta.games.lotro.common.effects.ReactiveChange;
import delta.games.lotro.common.effects.ReactiveVitalChange;
import delta.games.lotro.common.effects.ReactiveVitalEffect;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.lore.items.DamageType;

/**
 * Renderer for 'ReactiveVitalEffect' effects.
 * @author DAM
 */
public class ReactiveVitalEffectRenderer extends PropertyModificationEffectRenderer<ReactiveVitalEffect>
{
  protected String getConditionLine(ReactiveVitalEffect effect)
  {
    List<DamageType> damageTypes=effect.getDamageTypes();
    String damageTypesStr=null;
    if (!damageTypes.isEmpty())
    {
      damageTypesStr=EffectDisplayUtils.formatDamageTypes(damageTypes);
    }
    String damageQualifiersStr=null;
    List<DamageQualifier> damageQualifiers=effect.getDamageQualifiers();
    if (!damageQualifiers.isEmpty())
    {
      damageQualifiersStr=EffectDisplayUtils.formatDamageQualifiers(damageQualifiers);
    }
    String onDamageLine=null;
    if ((damageTypesStr!=null) || (damageQualifiersStr!=null))
    {
      String adjective="";
      if (damageQualifiersStr!=null)
      {
        adjective=damageQualifiersStr;
      }
      if (damageTypesStr!=null)
      {
        boolean isAnyDamage=EffectDisplayUtils.isAnyDamageType(damageTypes);
        if (isAnyDamage)
        {
          if (adjective.isEmpty())
          {
            adjective="any";
          }
        }
        else
        {
          adjective=damageTypesStr+" "+adjective;
        }
      }
      onDamageLine="On "+adjective+" damage:";
      if (effect.isRemoveOnProc())
      {
        onDamageLine="(1 time) "+onDamageLine;
      }
    }
    return onDamageLine;
  }

  @Override
  protected void renderSpecifics(List<String> storage, ReactiveVitalEffect effect)
  {
    VitalEffectsUtils utils=new VitalEffectsUtils(getContext());
    // Defender
    ReactiveChange defender=effect.getDefenderReactiveChange();
    if (defender!=null)
    {
      ReactiveVitalChange change=defender.getVitalChange();
      if (change!=null)
      {
        Float value=utils.getValue(change);
        float probability=change.getProbability();
        boolean multiplicative=change.isMultiplicative();
        int percentage=(int)(probability*100);
        if (!multiplicative)
        {
          int damage=(value!=null)?((int)value.floatValue()):0;
          if (percentage!=100)
          {
            String text=percentage+"% chance to Negate "+damage+" damage";
            storage.add(text);
          }
          else
          {
            // Never?
            String text="Negate "+damage+" damage";
            storage.add(text);
          }
        }
        else
        {
          int percentageDamage=(value!=null)?((int)(value.floatValue()*100)):0;
          if (percentage!=100)
          {
            // Never?
            String text=percentage+"% chance to Negate "+percentageDamage+"% damage";
            storage.add(text);
          }
          else
          {
            // Negate X% damage
            String text="Negate "+percentageDamage+"% damage";
            storage.add(text);
          }
        }
      }
      EffectAndProbability defenderEffect=defender.getEffect();
      if (defenderEffect!=null)
      {
        float effectProbability=defenderEffect.getProbability();
        int effectPercentage=(int)(effectProbability*100);
        String text=effectPercentage+"% chance to Receive effect:";
        storage.add(text);
        EffectRenderingState state=getState();
        boolean isRootEffectBackup=state.isRootEffect();
        state.setRootEffect(false);
        displayEffect(storage,defenderEffect.getEffect());
        state.setRootEffect(isRootEffectBackup);
      }
    }
    // Attacker
    ReactiveChange attacker=effect.getAttackerReactiveChange();
    if (attacker!=null)
    {
      ReactiveVitalChange change=attacker.getVitalChange();
      if (change!=null)
      {
        Float value=utils.getValue(change);
        float probability=change.getProbability();
        int percentage=(int)(probability*100);
        float safeValue=(value!=null)?value.floatValue():0;
        boolean multiplicative=change.isMultiplicative();
        if (!multiplicative)
        {
          int damage=Math.round(Math.abs(safeValue));
          if (percentage!=100)
          {
            String text=percentage+"% chance to Reflect "+damage+" damage";
            storage.add(text);
          }
          else
          {
            String text="Reflect "+damage+" damage";
            storage.add(text);
          }
        }
        else
        {
          // Never multiplicative?
          int percentageDamage=(int)(safeValue*100);
          if (percentage!=100)
          {
            String text=percentage+"% chance to Reflect "+percentageDamage+"% damage";
            storage.add(text);
          }
          else
          {
            String text="Reflect "+percentageDamage+"% damage";
            storage.add(text);
          }
        }
      }
      EffectAndProbability attackerEffect=attacker.getEffect();
      if (attackerEffect!=null)
      {
        float effectProbability=attackerEffect.getProbability();
        int effectPercentage=(int)(effectProbability*100);
        String text=effectPercentage+"% chance to Reflect effect:";
        storage.add(text);
        EffectRenderingState state=getState();
        boolean isRootEffectBackup=state.isRootEffect();
        state.setRootEffect(false);
        displayEffect(storage,attackerEffect.getEffect());
        state.setRootEffect(isRootEffectBackup);
      }
    }
  }
}
