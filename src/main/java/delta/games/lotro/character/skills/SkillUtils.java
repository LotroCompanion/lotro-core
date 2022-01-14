package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RaceTrait;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.skills.filters.SkillCategoryFilter;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.ItemDetailsManager;

/**
 * @author dm
 */
public class SkillUtils
{
  //private static final Logger LOGGER=Logger.getLogger(SkillConstraintsComputer.class);

  private Filter<SkillDescription> _filter;
  private int _level;
  private CharacterClass _class;
  private Race _race;
  private Map<Integer,SkillDescription> _selectedSkills;

  public SkillUtils(Filter<SkillDescription> filter, int level, CharacterClass characterClass, Race race)
  {
    _filter=filter;
    _level=level;
    _class=characterClass;
    _race=race;
    _selectedSkills=new HashMap<Integer,SkillDescription>();
  }

  private void doIt()
  {
    inspectClassSkills();
    inspectRaceSkills();
    inspectItemGrantedSkills();
    List<SkillDescription> selected=new ArrayList<SkillDescription>(_selectedSkills.values());
    Collections.sort(selected,new NamedComparator());
    for(SkillDescription skill : selected)
    {
      System.out.println(skill);
    }
  }

  private void inspectClassSkills()
  {
    ClassesManager mgr=ClassesManager.getInstance();
    ClassDescription classDescription=mgr.getClassDescription(_class);
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
    RacesManager mgr=RacesManager.getInstance();
    RaceDescription raceDescription=mgr.getRaceDescription(_race);
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
            if ((itemReq==null) || (itemReq.accepts(_level,_class,_race)))
            {
              addSkill(skill);
            }
          }
        }
      }
    }
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    SkillCategoryFilter filter=new SkillCategoryFilter();
    filter.setCategory(102);
    new SkillUtils(filter,130,CharacterClass.CHAMPION,Race.MAN).doIt();
  }
}
