package delta.games.lotro.config.labels;

import delta.common.utils.misc.Preferences;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.config.UserConfig;

/**
 * Configuration of the labels system.
 * @author DAM
 */
public class LabelsConfiguration
{
  // Labels
  private static final String LABELS_CONFIGURATION="Labels";
  private static final String DATA_LABELS_KEY="DataLabelsKey";
  private static final String APP_LABELS_KEY="AppLabelsKey";

  private String _dataLabelsKey;
  private String _appLabelsKey;

  /**
   * Constructor.
   */
  public LabelsConfiguration()
  {
    DefinitionOfAvailableLabels cfg=new DefinitionOfAvailableLabels();
    LabelsEntry defaultDataEntry=cfg.getDataLabels().getDefault();
    _dataLabelsKey=defaultDataEntry.getKey();
    LabelsEntry defaultAppEntry=cfg.getAppLabels().getDefault();
    _appLabelsKey=defaultAppEntry.getKey();
  }

  /**
   * Get the key for application labels.
   * @return a key.
   */
  public String getAppLabelsKey()
  {
    return _appLabelsKey;
  }

  /**
   * Set the key for application labels.
   * @param appLabelsKey the key to use.
   */
  public void setAppLabelsKey(String appLabelsKey)
  {
    _appLabelsKey=appLabelsKey;
  }

  /**
   * Get the key for data labels.
   * @return a key.
   */
  public String getDataLabelsKey()
  {
    return _dataLabelsKey;
  }

  /**
   * Set the key for data labels.
   * @param dataLabelsKey the key to use.
   */
  public void setDataLabelsKey(String dataLabelsKey)
  {
    _dataLabelsKey=dataLabelsKey;
  }

  /**
   * Save configuration.
   * @param userCfg User configuration.
   */
  public void save(UserConfig userCfg)
  {
    userCfg.setStringValue(LABELS_CONFIGURATION,DATA_LABELS_KEY,_dataLabelsKey);
    userCfg.setStringValue(LABELS_CONFIGURATION,APP_LABELS_KEY,_appLabelsKey);
  }

  /**
   * Initialize from preferences.
   * @param preferences Preferences to use.
   */
  public void fromPreferences(Preferences preferences)
  {
    TypedProperties props=preferences.getPreferences(LABELS_CONFIGURATION);
    String dataLabelsKey=props.getStringProperty(DATA_LABELS_KEY,getDataLabelsKey());
    setDataLabelsKey(dataLabelsKey);
    String appLabelsKey=props.getStringProperty(APP_LABELS_KEY,getAppLabelsKey());
    setAppLabelsKey(appLabelsKey);
  }
}
