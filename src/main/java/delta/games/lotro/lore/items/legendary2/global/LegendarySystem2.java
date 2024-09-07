package delta.games.lotro.lore.items.legendary2.global;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.legendary2.global.io.xml.LegendaryData2XMLParser;
import delta.games.lotro.utils.maths.Progression;

/**
 * Provides facilities related to the legendary items system (reloaded).
 * @author DAM
 */
public class LegendarySystem2
{
  private static final Logger LOGGER=LoggerFactory.getLogger(LegendarySystem2.class);

  private static LegendarySystem2 _instance=null;

  private LegendaryData2 _data;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LegendarySystem2 getInstance()
  {
    if (_instance==null)
    {
      _instance=new LegendarySystem2();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private LegendarySystem2()
  {
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.LEGENDARY_DATA2);
    long now=System.currentTimeMillis();
    _data=LegendaryData2XMLParser.parseLegendaryDataFile(inputFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded legendary system (reloaded) in "+duration+"ms.");
  }

  /**
   * Get data for the legendary items system.
   * @return some data.
   */
  public LegendaryData2 getData()
  {
    return _data;
  }

  /**
   * Get the item level to use for legendary items reforged by a character at the given level.
   * @param characterLevel Character level.
   * @return An item level.
   */
  public int getItemLevelForCharacterLevel(int characterLevel)
  {
    Progression progression=_data.getCharacterLevel2ItemLevelProgression();
    if (progression!=null)
    {
      Float itemLevel=progression.getValue(characterLevel);
      return (itemLevel!=null)?itemLevel.intValue():1;
    }
    return 1;
  }
}
