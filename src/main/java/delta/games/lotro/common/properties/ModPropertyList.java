package delta.games.lotro.common.properties;

import java.util.ArrayList;
import java.util.List;

/**
 * List of modifier properties IDs.
 * @author DAM
 */
public class ModPropertyList
{
  // Naive implementation
  private List<Integer> _ids;

  /**
   * Constructor.
   */
  public ModPropertyList()
  {
    _ids=new ArrayList<Integer>();
  }

  /**
   * Add an identifier.
   * @param id Identifier to add.
   */
  public void addID(int id)
  {
    _ids.add(Integer.valueOf(id));
  }

  /**
   * Get the identifiers.
   * @return a list of identifiers.
   */
  public List<Integer> getIDs()
  {
    return _ids;
  }

  @Override
  public String toString()
  {
    return _ids.toString();
  }
}
