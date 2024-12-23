package delta.games.lotro.character.skills.combos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Utility methods related to skill combos.
 * @author DAM
 */
public class SkillCombosUtils
{
  /**
   * Resolve proxies in skill combos.
   * @param skills Skills to use.
   */
  public static void resolveCombos(List<SkillDescription> skills)
  {
    Map<Integer,SkillDescription> map=buildSkillsMap(skills);
    for(SkillDescription skill : skills)
    {
      SkillCombos combos=skill.getCombos();
      if (combos==null)
      {
        continue;
      }
      for(SkillComboElement element : combos.getElements())
      {
        Proxy<SkillDescription> proxy=element.getSkill();
        int skillID=proxy.getId();
        SkillDescription referencedSkill=map.get(Integer.valueOf(skillID));
        if (referencedSkill!=null)
        {
          proxy.setObject(referencedSkill);
          proxy.setName(referencedSkill.getName());
        }
      }
    }
  }

  private static Map<Integer,SkillDescription> buildSkillsMap(List<SkillDescription> skills)
  {
    Map<Integer,SkillDescription> ret=new HashMap<Integer,SkillDescription>();
    for(SkillDescription skill : skills)
    {
      Integer key=Integer.valueOf(skill.getIdentifier());
      ret.put(key,skill);
    }
    return ret;
  }
}
