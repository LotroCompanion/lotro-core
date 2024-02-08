package delta.games.lotro.character.skills;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.SkillCategory;

/**
 * Skill.
 * @author DAM
 */
public class SkillDescription implements Identifiable,Named
{
  /**
   * Skill identifier.
   */
  private int _identifier;
  /**
   * Skill name (never <code>null</code>).
   */
  private String _name;
  /**
   * Skill description (never <code>null</code>).
   */
  private String _description;
  /**
   * Skill icon identifier.
   */
  private int _iconId;
  /**
   * Category.
   */
  private SkillCategory _category;
  // TODO Add required effects
  // TODO Add required traits
  /**
   * Effects.
   */
  private SkillEffectsManager _effects;

  /**
   * Constructor.
   */
  public SkillDescription()
  {
    super();
    _identifier=0;
    _name="";
    _description="";
    _iconId=0;
    _category=null;
  }

  /**
   * Get the identifier of this skill.
   * @return a skill identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this skill.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this skill.
   * @return a skill name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this skill.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the description of this skill.
   * @return a skill description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this skill.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Get the icon ID for this skill.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID for this skill.
   * @param iconId Icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the category of this skill.
   * @return a skill category.
   */
  public SkillCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this skill.
   * @param category the category to set.
   */
  public void setCategory(SkillCategory category)
  {
    _category=category;
  }

  /**
   * Get the effects manager.
   * @return an effects manager or <code>null</code> if no effects.
   */
  public SkillEffectsManager getEffects()
  {
    return _effects;
  }

  @Override
  public String toString()
  {
    return "Skill ID="+_identifier+", name="+_name;
  }

  /**
   * Add an effect to the given skill.
   * @param skill Skill to use.
   * @param effect Effect to add.
   */
  public static void addEffect(SkillDescription skill, SkillEffectGenerator effect)
  {
    SkillEffectsManager mgr=skill.getEffects();
    if (mgr==null)
    {
      mgr=new SkillEffectsManager();
      skill._effects=mgr;
    }
    mgr.addEffect(effect);
  }
}
