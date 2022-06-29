package delta.games.lotro.lore.collections.pets;

import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.collections.Collectable;

/**
 * Cosmetic pet.
 * @author DAM
 */
public class CosmeticPetDescription implements Collectable
{
  private int _identifier;
  private String _name;
  private String _initialName;
  private String _description;
  private String _sourceDescription;
  private int _iconId;
  private EntityClassification _classification;
  // TODO:
  // - Alignment
  // - Class (always melee)

  /**
   * Constructor.
   * @param id Pet identifier.
   */
  public CosmeticPetDescription(int id)
  {
    _identifier=id;
    _name="";
    _initialName="";
    _description="";
    _sourceDescription="";
    _iconId=0;
    _classification=new EntityClassification();
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the pet name.
   * @return a pet name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the pet name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the pet initial name.
   * @return a pet name.
   */
  public String getInitialName()
  {
    return _initialName;
  }

  /**
   * Set the pet initial name.
   * @param initialName Name to set.
   */
  public void setInitialName(String initialName)
  {
    _initialName=initialName;
  }

  /**
   * Get the pet description.
   * @return a pet description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the pet description.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the description of the pet source.
   * @return a description.
   */
  public String getSourceDescription()
  {
    return _sourceDescription;
  }

  /**
   * Set the description of the pet source.
   * @param sourceDescription Description to set.
   */
  public void setSourceDescription(String sourceDescription)
  {
    _sourceDescription=sourceDescription;
  }

  /**
   * Get the icon ID for this pet.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID for this pet.
   * @param iconId Icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the pet classification.
   * @return the pet classification.
   */
  public EntityClassification getClassification()
  {
    return _classification;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Cosmetic pet: ID=").append(_identifier);
    sb.append(", name=").append(_name);
    sb.append(", initial name=").append(_initialName);
    sb.append(", description=").append(_description);
    sb.append(", source description=").append(_sourceDescription);
    sb.append(", iconID=").append(_iconId);
    sb.append(", classification=").append(_classification);
    return sb.toString();
  }
}
