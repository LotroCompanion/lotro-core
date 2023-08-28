package delta.games.lotro.config.labels;

/**
 * Definition of available labels.
 * @author DAM
 */
public class DefinitionOfAvailableLabels
{
  private AvailableLabelsDefinition _dataLabels;
  private AvailableLabelsDefinition _appLabels;

  /**
   * Constructor.
   */
  public DefinitionOfAvailableLabels()
  {
    _dataLabels=AvailableLabelsBuilder.buildDataLabelsConfiguration();
    _appLabels=AvailableLabelsBuilder.buildAppLabelsConfiguration();
  }

  /**
   * Get the definition of available labels for data.
   * @return a labels definition.
   */
  public AvailableLabelsDefinition getDataLabels()
  {
    return _dataLabels;
  }

  /**
   * Get the definition of available labels for application.
   * @return a labels definition.
   */
  public AvailableLabelsDefinition getAppLabels()
  {
    return _appLabels;
  }
}
