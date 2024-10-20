package delta.games.lotro.common.effects.display;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
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
   * Format a list of damage types.
   * @param damageTypes Input damge types.
   * @return A displayable string.
   */
  public static String formatDamageType(List<DamageType> damageTypes)
  {
    if ((damageTypes.size()==1) && (damageTypes.get(0)==DamageTypes.ALL))
    {
      return "";
    }
    StringBuilder sb=new StringBuilder();
    for(DamageType damageType : damageTypes)
    {
      if (sb.length()>0) sb.append(",");
      sb.append(damageType.getLabel());
    }
    sb.append(' ');
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
      if (sb.length()>0) sb.append(",");
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
}
