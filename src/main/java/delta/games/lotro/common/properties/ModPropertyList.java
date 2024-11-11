package delta.games.lotro.common.properties;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.stats.StatOperator;

/**
 * List of modifier properties IDs.
 * @author DAM
 */
public class ModPropertyList
{
  // Naive implementation
  private List<Integer> _ids;
  // Operator
  private StatOperator _operator;

  /**
   * Constructor.
   */
  public ModPropertyList()
  {
    _ids=new ArrayList<Integer>();
    _operator=StatOperator.ADD;
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

  /**
   * Get the operator to use.
   * @return an operator.
   */
  public StatOperator getOperator()
  {
    return _operator;
  }

  /**
   * Set the operator to use.
   * @param operator Operator to set.
   */
  public void setOperator(StatOperator operator)
  {
    _operator=operator;
  }

  @Override
  public String toString()
  {
    return _ids.toString();
  }
}
