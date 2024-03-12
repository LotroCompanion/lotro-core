package delta.games.lotro.character.traits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.common.enums.TraitGroup;
import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;

/**
 * Simple test to check mount appearances traits.
 * @author DAM
 */
public class MainCheckMountAppearanceTraits
{
  private void doIt()
  {
    Map<TraitGroup,List<TraitDescription>> foundGroups=new HashMap<TraitGroup,List<TraitDescription>>();
    TraitsManager traitsMgr=TraitsManager.getInstance();
    List<TraitDescription> traits=traitsMgr.getAll();
    for(TraitDescription trait : traits)
    {
      /*
      SkillCategory category=trait.getCategory();
      if (category==null)
      {
        continue;
      }
      if (category.getCode()!=54)
      {
        continue;
      }
      */
      TraitNature nature=trait.getNature();
      if (nature==null)
      {
        continue;
      }
      if (nature.getCode()!=16)
      {
        continue;
      }
      List<TraitGroup> groups=trait.getTraitGroups();
      if (groups.size()>1)
      {
        System.out.println("Trait: "+trait+" has several groups: "+groups);
      }
      else if (groups.isEmpty())
      {
        System.out.println("Trait: "+trait+" is empty");
      }
      else
      {
        for(TraitGroup group : groups)
        {
          List<TraitDescription> traitsForGroup=foundGroups.get(group);
          if (traitsForGroup==null)
          {
            traitsForGroup=new ArrayList<TraitDescription>();
            foundGroups.put(group,traitsForGroup);
          }
          traitsForGroup.add(trait);
        }
      }
    }
    List<TraitGroup> groups=new ArrayList<TraitGroup>(foundGroups.keySet());
    Collections.sort(groups,new LotroEnumEntryCodeComparator<TraitGroup>());
    for(TraitGroup group : groups)
    {
      System.out.println("Group: "+group);
      List<TraitDescription> traitsForGroup=foundGroups.get(group);
      Collections.sort(traitsForGroup,new NamedComparator());
      for(TraitDescription trait : traitsForGroup)
      {
        System.out.println("\t"+trait);
      }
    }
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainCheckMountAppearanceTraits().doIt();
  }
}
