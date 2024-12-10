package delta.games.lotro.lore.items;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.io.xml.DisenchantmentResultXMLParser;
import delta.games.lotro.utils.PerfUtils;
import delta.games.lotro.utils.Registry;

/**
 * Dienchantment manager.
 * @author DAM
 */
public class DisenchantmentManager
{
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
      PerfUtils.showLoadedLog(results.size(),"disenchantment results",duration);
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
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    out.println("Disenchantment manager has:");
    out.println("\t"+_disenchantmentResults.size()+" disenchantment results");
  }
}
