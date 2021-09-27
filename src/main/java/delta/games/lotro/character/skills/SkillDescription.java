package delta.games.lotro.character.skills;

import delta.games.lotro.common.Identifiable;

/**
 * Skill.
 * @author DAM
 */
public class SkillDescription implements Identifiable
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
  private String _category;

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
    _category="";
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
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this skill.
   * @param category the category to set.
   */
  public void setCategory(String category)
  {
    if (category==null) category="";
    _category=category;
  }
}
