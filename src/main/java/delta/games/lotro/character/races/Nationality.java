package delta.games.lotro.character.races;

import delta.common.utils.id.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Nationality.
 * @author DAM
 */
public class Nationality implements Identifiable, Named
{
  private int _identifier;
  private String _name;
  private String _description;
  private int _iconID;
  private String _namingGuidelineMale;
  private String _namingGuidelineFemale;
  private int _titleID;
  // Factions table?

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public Nationality(int identifier)
  {
    _identifier=identifier;
  }

  @Override
  public int getIdentifier()
  {
    return _identifier;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this nationality.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the nationality description.
   * @return a nationality description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this nationality.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the icon identifier for this nationality.
   * @return an icon ID.
   */
  public int getIconID()
  {
    return _iconID;
  }

  /**
   * Set the icon ID for this nationality.
   * @param iconID Icon ID to set.
   */
  public void setIconID(int iconID)
  {
    _iconID=iconID;
  }

  /**
   * Get the naming guideline for male characters.
   * @return a guideline string.
   */
  public String getNamingGuidelineMale()
  {
    return _namingGuidelineMale;
  }

  /**
   * Set the naming guideline for male characters.
   * @param namingGuidelineMale the guideline to set.
   */
  public void setNamingGuidelineMale(String namingGuidelineMale)
  {
    _namingGuidelineMale=namingGuidelineMale;
  }

  /**
   * Get the naming guideline for female characters.
   * @return a guideline string.
   */
  public String getNamingGuidelineFemale()
  {
    return _namingGuidelineFemale;
  }

  /**
   * Set the naming guideline for female characters.
   * @param namingGuidelineFemale the guideline to set.
   */
  public void setNamingGuidelineFemale(String namingGuidelineFemale)
  {
    _namingGuidelineFemale=namingGuidelineFemale;
  }

  /**
   * Get the identifier of the associated title.
   * @return A title identifier.
   */
  public int getTitleID()
  {
    return _titleID;
  }

  /**
   * Set the title identifier.
   * @param titleID the title identifier to set.
   */
  public void setTitleID(int titleID)
  {
    _titleID=titleID;
  }

  @Override
  public String toString()
  {
    return "Nationality: ID="+_identifier+", name="+_name;
  }
}
