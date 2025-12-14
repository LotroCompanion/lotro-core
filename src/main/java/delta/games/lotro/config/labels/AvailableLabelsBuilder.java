package delta.games.lotro.config.labels;

/**
 * Builds available labels.
 * @author DAM
 */
public class AvailableLabelsBuilder
{
  /**
   * Build the available labels for data. 
   * @return A labels configuration.
   */
  public static AvailableLabelsDefinition buildDataLabelsConfiguration()
  {
    AvailableLabelsDefinition cfg=new AvailableLabelsDefinition();
    // 
    // English
    LabelsEntry en=new LabelsEntry("en","en","English");
    cfg.registerEntry(en,true);
    // French
    LabelsEntry fr=new LabelsEntry("fr","fr","Français");
    cfg.registerEntry(fr);
    // German
    LabelsEntry de=new LabelsEntry("de","de","Deutsch");
    cfg.registerEntry(de);
    // Russian
    LabelsEntry ru=new LabelsEntry("ru","ru","Русский");
    cfg.registerEntry(ru);
    return cfg;
  }

  /**
   * Build the available labels for application. 
   * @return A labels configuration.
   */
  public static AvailableLabelsDefinition buildAppLabelsConfiguration()
  {
    AvailableLabelsDefinition cfg=new AvailableLabelsDefinition();
    // English
    LabelsEntry en=new LabelsEntry("en","en","English");
    cfg.registerEntry(en,true);
    // French
    LabelsEntry fr=new LabelsEntry("fr","fr","Français");
    cfg.registerEntry(fr);
    // Deutsch
    LabelsEntry ge=new LabelsEntry("de","de","Deutsch");
    cfg.registerEntry(ge);
    return cfg;
  }
}
