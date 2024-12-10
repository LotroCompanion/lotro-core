package delta.games.lotro.lore.parameters;

import java.io.File;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.parameters.io.xml.GameXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Provides facilities related to the crafting system.
 * @author DAM
 */
public class Game
{
  private static Game _instance=null;

  private GameParameters _data;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static Game getInstance()
  {
    if (_instance==null)
    {
      _instance=new Game();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private Game()
  {
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.GAME_DATA);
    long now=System.currentTimeMillis();
    _data=new GameXMLParser().parseGameData(inputFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog("game data",duration);
  }

  /**
   * Get the parameters for the game.
   * @return some parameters.
   */
  public static GameParameters getParameters()
  {
    return getInstance()._data;
  }
}
