package delta.games.lotro.common.effects.display;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.DamageTypes;

/**
 * Utility methods related to effects display.
 * @author DAM
 */
public class EffectDisplayUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(EffectDisplayUtils.class);

  /**
   * Indicates if the given damage types list means "any" damage type.
   * @param damageTypes List to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public static boolean isAnyDamageType(List<DamageType> damageTypes)
  {
    return ((damageTypes.size()==1) && (damageTypes.get(0)==DamageTypes.ALL));
  }

  /**
   * Format a list of damage types.
   * @param damageTypes Input damage types.
   * @return A displayable string.
   */
  public static String formatDamageTypes(List<DamageType> damageTypes)
  {
    StringBuilder sb=new StringBuilder();
    for(DamageType damageType : damageTypes)
    {
      if (sb.length()>0) sb.append(",");
      sb.append(damageType.getLabel());
    }
    return sb.toString();
  }

  /**
   * Format a list of damage qualifiers.
   * @param damageQualifiers Input damage qualifiers.
   * @return A displayable string.
   */
  public static String formatDamageQualifiers(List<DamageQualifier> damageQualifiers)
  {
    StringBuilder sb=new StringBuilder();
    for(DamageQualifier damageQualifier : damageQualifiers)
    {
      if (sb.length()>0) sb.append(",");
      sb.append(damageQualifier.getLabel());
    }
    return sb.toString();
  }

  /**
   * Format a list of resist categories.
   * @param categories Input resist categories.
   * @return A displayable string.
   */
  public static String formatResistCategories(List<ResistCategory> categories)
  {
    StringBuilder sb=new StringBuilder();
    for(ResistCategory category : categories)
    {
      if (sb.length()>0) sb.append(", ");
      sb.append(category.getLabel());
    }
    return sb.toString();
  }

  /**
   * Format a list of skill types.
   * @param skillTypes Input skill types.
   * @return A displayable string.
   */
  public static String formatSkillTypes(List<SkillType> skillTypes)
  {
    StringBuilder sb=new StringBuilder();
    for(SkillType skillType : skillTypes)
    {
      if (sb.length()>0) sb.append(",");
      sb.append(skillType.getLabel());
    }
    return sb.toString();
  }

  /**
   * Get the label for the given state.
   * @param state State to use.
   * @return A label.
   */
  public static String getStateLabel(CombatState state)
  {
    int code=state.getCode();
    if (code==2) return "Fear";
    if (code==3) return "Daze";
    if ((code==9) || (code==15)) return "Stun";
    if (code==10) return "Root";
    LOGGER.warn("Unmanaged state: {}",state);
    return state.getLabel();
  }

  /**
   * Get the duration (or interval) of an effect.
   * @param effect Effect to use.
   * @param statModsComputer Stat modifiers computer.
   * @return A duration (seconds).
   */
  public static float getDuration(Effect effect, StatModifiersComputer statModsComputer)
  {
    EffectDuration effectDuration=effect.getEffectDuration();
    if (effectDuration==null)
    {
      return 0;
    }
    float ret=0;
    Float intervalDuration=effectDuration.getDuration();
    if (intervalDuration!=null)
    {
      ret=intervalDuration.floatValue();
    }
    if (statModsComputer!=null)
    {
      ModPropertyList mods=effectDuration.getDurationModifiers();
      ret+=statModsComputer.computeAdditiveModifiers(mods);
    }
    return ret;
  }

  /**
   * Get the total duration of an effect.
   * @param effect Effect to use.
   * @param statModsComputer Stat modifiers computer.
   * @return A duration (seconds).
   */
  public static float getTotalDuration(Effect effect, StatModifiersComputer statModsComputer)
  {
    EffectDuration effectDuration=effect.getEffectDuration();
    if (effectDuration==null)
    {
      return 0;
    }
    float interval=getDuration(effect,statModsComputer);
    float totalDuration=interval;
    int pulseCount=getPulseCount(effect,statModsComputer);
    if (pulseCount>0)
    {
      totalDuration=interval*pulseCount;
    }
    return totalDuration;
  }

  /**
   * Get the pulses count of an effect.
   * @param effect Effect to use.
   * @param statModsComputer Stat modifiers computer.
   * @return A number of pulses.
   */
  public static int getPulseCount(Effect effect, StatModifiersComputer statModsComputer)
  {
    EffectDuration effectDuration=effect.getEffectDuration();
    if (effectDuration==null)
    {
      return 0;
    }
    int pulseCount=effectDuration.getPulseCount();
    if (pulseCount>0)
    {
      if (statModsComputer!=null)
      {
        pulseCount+=statModsComputer.computeAdditiveModifiers(effectDuration.getPulseCountModifiers());
      }
    }
    return pulseCount;
  }
}
