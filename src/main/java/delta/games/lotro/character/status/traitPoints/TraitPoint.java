package delta.games.lotro.character.status.traitPoints;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.character.classes.ClassDescription;

/**
 * Represents a trait point.
 * @author DAM
 */
public class TraitPoint
{
  private String _id;
  private String _category;
  private String _label;
  private Set<ClassDescription> _requiredCharacterClasses;
  private int _achievableId;

  /**
   * Constructor.
   * @param id Internal unique identifier.
   */
  public TraitPoint(String id)
  {
    _id=id;
    _category=null;
    _label=id;
    _requiredCharacterClasses=new HashSet<ClassDescription>();
  }

  /**
   * Get the internal identifier.
   * @return a string identifier.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the category.
   * @return a category.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category.
   * @param category Category to set.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  /**
   * Get the short descriptive label.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Set the label.
   * @param label Label to set.
   */
  public void setLabel(String label)
  {
    _label=label;
  }

  /**
   * Get the required class, if any.
   * @return the required class or <code>null</code>.
   */
  public Set<ClassDescription> getRequiredClasses()
  {
    return _requiredCharacterClasses;
  }

  /**
   * Add a required class.
   * @param characterClass Class to add.
   */
  public void addRequiredClass(ClassDescription characterClass)
  {
    if (characterClass!=null)
    {
      _requiredCharacterClasses.add(characterClass);
    }
  }

  /**
   * Indicates if this trait is enabled for the given class.
   * @param characterClass Character class to test.
   * @return <code>true</code> if enabled, <code>false</code> otherwise.
   */
  public boolean isEnabledForClass(ClassDescription characterClass)
  {
    return _requiredCharacterClasses.isEmpty()||_requiredCharacterClasses.contains(characterClass);
  }

  /**
   * Get the identifier of the associated achievable.
   * @return an achievable ID or 0.
   */
  public int getAchievableId()
  {
    return _achievableId;
  }

  /**
   * Set the identifier of the associated achievable.
   * @param achievableId Identifier to set.
   */
  public void setAchievableId(int achievableId)
  {
    _achievableId=achievableId;
  }
}
