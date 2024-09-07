package delta.games.lotro.lore.crafting;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.crafting.io.xml.CraftingXMLParser;

/**
 * Provides facilities related to the crafting system.
 * @author DAM
 */
public class CraftingSystem
{
  private static final Logger LOGGER=LoggerFactory.getLogger(CraftingSystem.class);

  private static CraftingSystem _instance=null;

  private CraftingData _data;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static CraftingSystem getInstance()
  {
    if (_instance==null)
    {
      _instance=new CraftingSystem();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private CraftingSystem()
  {
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.CRAFTING_DATA);
    long now=System.currentTimeMillis();
    _data=new CraftingXMLParser().parseCraftingSystem(inputFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded crafting system data in "+duration+"ms.");
  }

  /**
   * Get data for the crafting system.
   * @return some data.
   */
  public CraftingData getData()
  {
    return _data;
  }
}
