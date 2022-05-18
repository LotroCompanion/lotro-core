package delta.games.lotro.common.stats;

/**
 * Base class for stat providers.
 * @author DAM
 */
public abstract class AbstractStatProvider implements StatProvider
{
  private StatOperator _operator;
  private StatDescription _stat;
  private String _descriptionOverride;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  protected AbstractStatProvider(StatDescription stat)
  {
    _stat=stat;
    _operator=StatOperator.ADD;
  }

  @Override
  public StatDescription getStat()
  {
    return _stat;
  }

  @Override
  public void setStat(StatDescription stat)
  {
    _stat=stat;
  }

  @Override
  public StatOperator getOperator()
  {
    return _operator;
  }

  @Override
  public void setOperator(StatOperator operator)
  {
    _operator=operator;
  }

  @Override
  public String getDescriptionOverride()
  {
    return _descriptionOverride;
  }

  @Override
  public void setDescriptionOverride(String descriptionOverride)
  {
    _descriptionOverride=descriptionOverride;
  }
}
