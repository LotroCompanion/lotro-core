package delta.games.lotro.lore.housing;

import delta.common.utils.id.Identifiable;
import delta.games.lotro.common.Named;

/**
 * A neighborhood.
 * @author DAM
 */
public class Neighborhood implements Identifiable,Named
{
  private int _id;
  private String _name;
  private NeighborhoodTemplate _template;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public Neighborhood(int id)
  {
    _id=id;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this neighborhood.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the neighborhood template.
   * @return the neighborhood template.
   */
  public NeighborhoodTemplate getTemplate()
  {
    return _template;
  }

  /**
   * Set the neighborhood template.
   * @param template the template to set.
   */
  public void setTemplate(NeighborhoodTemplate template)
  {
    _template=template;
  }
}
