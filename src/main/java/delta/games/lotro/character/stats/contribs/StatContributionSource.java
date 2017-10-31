package delta.games.lotro.character.stats.contribs;

/**
 * Stat contribution source.
 * @author DAM
 */
public class StatContributionSource
{
  private String _id;
  private String _label;

  /**
   * Constructor.
   * @param id Unique identifier for this source.
   * @param label Label for this source.
   */
  public StatContributionSource(String id, String label)
  {
    _id=id;
    _label=label;
  }

  /**
   * Get the source identifier.
   * @return the source identifier.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the source label.
   * @return the source label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _id;
  }
}
