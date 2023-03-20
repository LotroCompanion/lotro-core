package delta.games.lotro.lore.collections.pets;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.collections.Collectable;

/**
 * Cosmetic pet.
 * @author DAM
 */
public class CosmeticPetDescription extends SkillDescription implements Collectable
{
  private String _initialName;
  private String _sourceDescription;
  private EntityClassification _classification;
  // TODO:
  // - Alignment
  // - Class (always melee)

  /**
   * Constructor.
   */
  public CosmeticPetDescription()
  {
    super();
    _initialName="";
    _sourceDescription="";
    _classification=new EntityClassification();
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
    sb.append("Cosmetic pet: ID=").append(getIdentifier());
    sb.append(", name=").append(getName());
    sb.append(", initial name=").append(_initialName);
    sb.append(", description=").append(getDescription());
    sb.append(", source description=").append(_sourceDescription);
    sb.append(", iconID=").append(getIconId());
    sb.append(", classification=").append(_classification);
    return sb.toString();
  }
}
