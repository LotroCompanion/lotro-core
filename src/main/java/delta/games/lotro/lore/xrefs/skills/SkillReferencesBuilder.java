package delta.games.lotro.lore.xrefs.skills;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.ItemDetailsManager;

/**
 * Finds references to skills.
 * @author DAM
 */
public class SkillReferencesBuilder
{
  private List<SkillReference<?>> _storage;

  /**
   * Constructor.
   */
  public SkillReferencesBuilder()
  {
    _storage=new ArrayList<SkillReference<?>>();
  }

  /**
   * Search for a skill.
   * @param skillID Skill identifier.
   * @return the found references.
   */
  public List<SkillReference<?>> inspectSkill(int skillID)
  {
    _storage.clear();
    findInClasses(skillID);
    findInItems(skillID);
    findInTraits(skillID);
    List<SkillReference<?>> ret=new ArrayList<SkillReference<?>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInClasses(int skillID)
  {
    for(AbstractClassDescription classDescription : ClassesManager.getInstance().getAllClasses())
    {
      findInClass(classDescription, skillID);
    }
  }

  private void findInClass(AbstractClassDescription classDescription, int skillID)
  {
    List<ClassSkill> classSkills=classDescription.getSkills();
    for(ClassSkill classSkill : classSkills)
    {
      SkillDescription skill=classSkill.getSkill();
      if (skill.getIdentifier()==skillID)
      {
        _storage.add(new SkillReference<AbstractClassDescription>(classDescription,SkillRole.CLASS_SKILL));
      }
    }
  }

  private void findInItems(int skillID)
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
          if (identifiable.getIdentifier()==skillID)
          {
            _storage.add(new SkillReference<Item>(item,SkillRole.GRANTED_BY_ITEM));
          }
        }
      }
    }
  }

  private void findInTraits(int skillID)
  {
    for(TraitDescription trait : TraitsManager.getInstance().getAll())
    {
      List<SkillDescription> traitSkills=trait.getSkills();
      for(SkillDescription traitSkill : traitSkills)
      {
        if (traitSkill.getIdentifier()==skillID)
        {
          _storage.add(new SkillReference<TraitDescription>(trait,SkillRole.GRANTED_BY_TRAIT));
        }
      }
    }
  }
}
