package delta.games.lotro.utils.i18n;

import java.io.File;

import org.apache.log4j.Logger;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.i18n.io.xml.LabelsXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.config.labels.LabelsConfiguration;

/**
 * Facade for localization utilities.
 * @author DAM
 */
public class I18nFacade
{
  private static final Logger LOGGER=Logger.getLogger(I18nFacade.class);

  private static final I18nFacade _instance=new I18nFacade();

  private String _localeKey;

  private I18nFacade()
  {
    LabelsConfiguration cfg=LotroCoreConfig.getInstance().getLabelsConfiguration();
    _localeKey=cfg.getDataLabelsKey();
  }

  /**
   * Get the labels manager for the given key and the current locale.
   * @param key Set key to use.
   * @return A labels manager.
   */
  public static SingleLocaleLabelsManager getLabelsMgr(String key)
  {
    return _instance.buildLabelsMgr(key);
  }

  private SingleLocaleLabelsManager buildLabelsMgr(String key)
  {
    LOGGER.debug("Loading labels file: "+key);
    SingleLocaleLabelsManager mgr=loadLabelsFile(key,_localeKey);
    if (mgr==null)
    {
      // If no labels file, use the English file
      mgr=loadLabelsFile(key,"en");
    }
    if (mgr==null)
    {
      mgr=new SingleLocaleLabelsManager(_localeKey);
    }
    return mgr;
  }

  private SingleLocaleLabelsManager loadLabelsFile(String key, String localeKey)
  {
    File rootDir=LotroCoreConfig.getInstance().getFile(DataFiles.LABELS);
    File labelsDir=new File(rootDir,localeKey);
    String filename=key+".xml";
    File from=new File(labelsDir,filename);
    SingleLocaleLabelsManager mgr=null;
    if (from.exists())
    {
      long now=System.currentTimeMillis();
      mgr=new LabelsXMLParser().parseSingleLocaleLabels(from);
      long now2=System.currentTimeMillis();
      LOGGER.debug("Took: "+(now2-now)+"ms");
    }
    return mgr;
  }
}
