package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.DamageTypes;

/**
 * @author dm
 */
public class EffectDisplayUtils
{
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
}
