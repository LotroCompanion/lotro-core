package delta.games.lotro.lore.items;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.io.xml.DisenchantmentResultXMLParser;
import delta.games.lotro.utils.Registry;

/**
 * Dienchantment manager.
 * @author DAM
 */
public class DisenchantmentManager
{
  private static final Logger LOGGER=Logger.getLogger(DisenchantmentManager.class);

  private static DisenchantmentManager _instance;

  private Registry<DisenchantmentResult> _disenchantmentResults;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static DisenchantmentManager getInstance()
  {
    if (_instance==null)
    {
      _instance=loadDisenchantmentManager();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public DisenchantmentManager()
  {
    _disenchantmentResults=new Registry<DisenchantmentResult>();
  }

  private static DisenchantmentManager loadDisenchantmentManager()
  {
    DisenchantmentManager ret=new DisenchantmentManager();
    File disenchantmentResultsFile=LotroCoreConfig.getInstance().getFile(DataFiles.DISENCHANTMENTS);
    if (disenchantmentResultsFile.exists())
    {
      long now=System.currentTimeMillis();
      DisenchantmentResultXMLParser parser=new DisenchantmentResultXMLParser();
      List<DisenchantmentResult> results=parser.parseXML(disenchantmentResultsFile);
      for(DisenchantmentResult result : results)
      {
        ret.getDisenchantmentResults().add(result);
      }
      long now2=System.currentTimeMillis();
      long duration=now2-now;
      LOGGER.info("Loaded disenchantment results in "+duration+"ms.");
    }
    return ret;
  }

  /**
   * Get the registry for disenchantment results.
   * @return the registry for disenchantment results.
   */
  public Registry<DisenchantmentResult> getDisenchantmentResults()
  {
    return _disenchantmentResults;
  }

  /**
   * Dump some statistics about disenchantments.
   */
  public void dump()
  {
    System.out.println("Disenchantment manager has:");
    System.out.println("\t"+_disenchantmentResults.size()+" disenchantment results");
  }
}
