package delta.games.lotro.lore.collections;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.CollectionCategory;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Description of a collection.
 * @author DAM
 */
public class CollectionDescription implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private CollectionCategory _category;
  private List<Collectable> _elements;
  private Rewards _rewards;
  private RaceRequirement _raceRequirement;
 
  /**
   * Constructor.
   * @param id Collection identifier.
   */
  public CollectionDescription(int id)
  {
    _identifier=id;
    _name="";
    _category=null;
    _elements=new ArrayList<Collectable>();
    _rewards=new Rewards();
    _raceRequirement=null;
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
   * Get the collection name.
   * @return a collection name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the collection name.
   * @param name Name to set.
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
   * Get the collection category.
   * @return a collection category.
   */
  public CollectionCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the collection category.
   * @param category Category to set.
   */
  public void setCategory(CollectionCategory category)
  {
    _category=category;
  }

  /**
   * Get the elements in this collections.
   * @return A list of elements.
   */
  public List<Collectable> getElements()
  {
    return new ArrayList<Collectable>(_elements);
  }

  /**
   * Add an element in this collection.
   * @param element Element to add.
   */
  public void addElement(Collectable element)
  {
    _elements.add(element);
  }

  /**
   * Get the rewards for this collection.
   * @return some rewards (may be empty but never <code>null</code>).
   */
  public Rewards getRewards()
  {
    return _rewards;
  }

  /**
   * Get the race requirements.
   * @return Some race requirements, if any (may be <code>null</code>).
   */
  public RaceRequirement getRaceRequirement()
  {
    return _raceRequirement;
  }

  /**
   * Set the race requirement.
   * @param raceRequirement Requirement to set.
   */
  public void setRaceRequirement(RaceRequirement raceRequirement)
  {
    _raceRequirement=raceRequirement;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Collection: ID=").append(_identifier);
    sb.append(", name=").append(_name);
    sb.append(", category=").append(_category);
    sb.append(", elements=").append(_elements);
    sb.append(", rewards=").append(_rewards);
    if (_raceRequirement!=null)
    {
      sb.append(", race requirement=").append(_raceRequirement);
    }
    return sb.toString();
  }

}
