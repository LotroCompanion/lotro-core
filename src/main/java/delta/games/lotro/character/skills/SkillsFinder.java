package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.BasicCharacterAttributes;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RaceTrait;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.ItemDetailsManager;

/**
 * Finds the skills that match some filter/constraints.
 * @author DAM
 */
public class SkillsFinder
{
  private Filter<SkillDescription> _filter;
  private BasicCharacterAttributes _constraints;
  private Map<Integer,SkillDescription> _selectedSkills;

  /**
   * Constructor.
   * @param filter Skills filter.
   * @param constraints Character constraints.
   */
  public SkillsFinder(Filter<SkillDescription> filter, BasicCharacterAttributes constraints)
  {
    _filter=filter;
    _constraints=constraints;
    _selectedSkills=new HashMap<Integer,SkillDescription>();
  }

  /**
   * Find skills.
   * @return A list of skills.
   */
  public List<SkillDescription> find()
  {
    inspectClassSkills();
    inspectRaceSkills();
    inspectItemGrantedSkills();
    List<SkillDescription> ret=new ArrayList<SkillDescription>(_selectedSkills.values());
    Collections.sort(ret,new NamedComparator());
    _selectedSkills.clear();
    return ret;
  }

  private void inspectClassSkills()
  {
    ClassesManager mgr=ClassesManager.getInstance();
    ClassDescription classDescription=mgr.getClassDescription(_constraints.getCharacterClass());
    List<ClassSkill> classSkills=classDescription.getSkills();
    for(ClassSkill classSkill : classSkills)
    {
      SkillDescription skill=classSkill.getSkill();
      if (_filter.accept(skill))
      {
        addSkill(skill);
      }
    }
  }

  private void inspectRaceSkills()
  {
    RaceDescription raceDescription=_constraints.getRace();
    List<RaceTrait> raceTraits=raceDescription.getTraits();
    for(RaceTrait raceTrait : raceTraits)
    {
      TraitDescription trait=raceTrait.getTrait();
      handleRaceTrait(trait);
    }
    for(TraitDescription trait : raceDescription.getEarnableTraits())
    {
      handleRaceTrait(trait);
    }
  }

  private void handleRaceTrait(TraitDescription trait)
  {
    List<SkillDescription> traitSkills=trait.getSkills();
    for(SkillDescription traitSkill : traitSkills)
    {
      if (_filter.accept(traitSkill))
      {
        addSkill(traitSkill);
      }
    }
  }

  private void addSkill(SkillDescription skill)
  {
    Integer key=Integer.valueOf(skill.getIdentifier());
    _selectedSkills.put(key,skill);
  }

  private void inspectItemGrantedSkills()
  {
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      ItemDetailsManager detailsMgr=item.getDetails();
      if (detailsMgr==null)
      {
        continue;
      }
      for(GrantedElement<?> element : detailsMgr.getItemDetails(GrantedElement.class))
      {
        Identifiable identifiable=element.getGrantedElement();
        if (identifiable instanceof SkillDescription)
        {
          SkillDescription skill=(SkillDescription)identifiable;
          if (_filter.accept(skill))
          {
            UsageRequirement itemReq=item.getUsageRequirements();
            int level=_constraints.getLevel();
            CharacterClass characterClass=_constraints.getCharacterClass();
            RaceDescription race=_constraints.getRace();
            if ((itemReq==null) || (itemReq.accepts(level,characterClass,race)))
            {
              addSkill(skill);
            }
          }
        }
      }
    }
  }
}
