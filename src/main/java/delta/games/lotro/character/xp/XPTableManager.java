package delta.games.lotro.character.xp;

import java.io.File;

import delta.games.lotro.character.xp.io.xml.XPTableXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Provides access to the XP table.
 * @author DAM
 */
public class XPTableManager
{
  private static XPTableManager _instance=null;

  private XPTable _table;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static XPTableManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new XPTableManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private XPTableManager()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File xpFile=cfg.getFile(DataFiles.XP_TABLE);
    _table=XPTableXMLParser.parseXPTableFile(xpFile);
  }

  /**
   * Get the XP table.
   * @return the XP table.
   */
  public XPTable getXPTable()
  {
    return _table;
  }
}
