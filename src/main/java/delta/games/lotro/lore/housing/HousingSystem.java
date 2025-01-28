package delta.games.lotro.lore.housing;

import java.io.File;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.housing.io.xml.HousingXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Provides facilities related to the housing system.
 * @author DAM
 */
public class HousingSystem
{
  private static HousingSystem _instance=null;

  private HousingManager _data;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static HousingSystem getInstance()
  {
    if (_instance==null)
    {
      _instance=new HousingSystem();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private HousingSystem()
  {
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.HOUSING);
    long now=System.currentTimeMillis();
    _data=new HousingXMLParser().parseHousingFile(inputFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog("housing system data",duration);
  }

  /**
   * Get data for the housing system.
   * @return some data.
   */
  public HousingManager getData()
  {
    return _data;
  }
}
