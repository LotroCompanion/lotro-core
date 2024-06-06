package delta.games.lotro.utils.i18n;

import java.io.File;

import delta.common.utils.i18n.LabelsFacade;
import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.config.labels.LabelsConfiguration;

/**
 * Facade for localization utilities.
 * @author DAM
 */
public class I18nFacade
{
  private static final I18nFacade _instance=new I18nFacade();

  private LabelsFacade _labels;
  private String _localeKey;

  private I18nFacade()
  {
    LabelsConfiguration cfg=LotroCoreConfig.getInstance().getLabelsConfiguration();
    _localeKey=cfg.getDataLabelsKey();
    File rootDir=LotroCoreConfig.getInstance().getFile(DataFiles.LABELS);
    _labels=new LabelsFacade(rootDir);
  }

  /**
   * Get the labels manager for the given key and the current locale.
   * @param key Set key to use.
   * @return A labels manager.
   */
  private SingleLocaleLabelsManager getLabelsMgrImpl(String key)
  {
    return _labels.getLabelsMgr(key,_localeKey);
  }

  /**
   * Get the labels manager for the given key and the current locale.
   * @param key Set key to use.
   * @return A labels manager.
   */
  public static SingleLocaleLabelsManager getLabelsMgr(String key)
  {
    return _instance.getLabelsMgrImpl(key);
  }
}
