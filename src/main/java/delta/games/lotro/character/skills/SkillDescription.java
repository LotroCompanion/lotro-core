package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.utils.Proxy;

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
  /**
   * Required trait.
   */
  private Proxy<TraitDescription> _requiredTrait;
  /**
   * Required effects.
   */
  private List<Effect> _requiredEffects;
  /**
   * Details.
   */
  private SkillDetails _details;

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
    _requiredTrait=null;
    _requiredEffects=null;
    _details=null;
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
    if (name==null)
    {
      name="";
    }
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
   * Get the required trait.
   * @return A trait proxy or <code>null</code>.
   */
  public Proxy<TraitDescription> getRequiredTrait()
  {
    return _requiredTrait;
  }

  /**
   * Set the required trait.
   * @param requiredTrait Required trait to set.
   */
  public void setRequiredTrait(Proxy<TraitDescription> requiredTrait)
  {
    _requiredTrait=requiredTrait;
  }

  /**
   * Add a required effect.
   * @param effect Effect to add.
   */
  public void addRequiredEffect(Effect effect)
  {
    if (_requiredEffects==null)
    {
      _requiredEffects=new ArrayList<Effect>();
    }
    _requiredEffects.add(effect);
  }

  /**
   * Get all the required effects.
   * @return A list of effects or <code>null</code>.
   */
  public List<Effect> getRequiredEffects()
  {
    return _requiredEffects;
  }

  /**
   * Get the effects manager.
   * @return an effects manager or <code>null</code> if no effects.
   */
  public SkillDetails getDetails()
  {
    return _details;
  }

  /**
   * Set the details.
   * @param details Details to set.
   */
  public void setDetails(SkillDetails details)
  {
    _details=details;
  }

  @Override
  public String toString()
  {
    return "Skill ID="+_identifier+", name="+_name;
  }
}
