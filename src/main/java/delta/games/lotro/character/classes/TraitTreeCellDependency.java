package delta.games.lotro.character.classes;

/**
 * Single dependency of a trait tree cell towards another cell.
 * @author DAM
 */
public class TraitTreeCellDependency
{
  private String _cellId;
  private int _rank;

  /**
   * Constructor.
   * @param cellId Cell identifier.
   * @param rank Required trait rank.
   */
  public TraitTreeCellDependency(String cellId, int rank)
  {
    _cellId=cellId;
    _rank=rank;
  }

  /**
   * Get the cell identifier.
   * @return the cell identifier.
   */
  public String getCellId()
  {
    return _cellId;
  }

  /**
   * Get the required trait rank.
   * @return a rank.
   */
  public int getRank()
  {
    return _rank;
  }
}
