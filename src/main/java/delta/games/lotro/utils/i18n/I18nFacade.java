package delta.games.lotro.utils.i18n;

import java.io.File;

import org.apache.log4j.Logger;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.i18n.io.xml.LabelsXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for localization utilities.
 * @author DAM
 */
public class I18nFacade
{
  private static final Logger LOGGER=Logger.getLogger(I18nFacade.class);

  // TODO Use current locale!
  private static final String CURRENT_LOCALE="en";

  /**
   * Get the labels manager for the given key and the current locale.
   * @param key Set key to use.
   * @return A labels manager.
   */
  public static SingleLocaleLabelsManager getLabelsMgr(String key)
  {
    return buildLabelsMgr(key);
  }

  private static SingleLocaleLabelsManager buildLabelsMgr(String key)
  {
    LOGGER.debug("Loading labels file: "+key);
    File rootDir=LotroCoreConfig.getInstance().getFile(DataFiles.LABELS);
    File labelsDir=new File(rootDir,CURRENT_LOCALE);
    String filename=key+".xml";
    File from=new File(labelsDir,filename);
    SingleLocaleLabelsManager mgr;
    if (from.exists())
    {
      long now=System.currentTimeMillis();
      mgr=new LabelsXMLParser().parseSingleLocaleLabels(from);
      long now2=System.currentTimeMillis();
      LOGGER.debug("Took: "+(now2-now)+"ms");
    }
    else
    {
      mgr=new SingleLocaleLabelsManager(CURRENT_LOCALE);
    }
    return mgr;
  }
}
