package delta.games.lotro.character.status.crafting;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.crafting.io.xml.CraftingStatusXMLParser;
import delta.games.lotro.character.status.crafting.io.xml.CraftingStatusXMLWriter;

/**
 * Manages access to the crafting status for a character.
 * @author DAM
 */
public class CraftingStatusManager
{
  private CharacterFile _toon;
  private CraftingStatus _crafting;

  /**
   * Constructor.
   * @param toon Associated toon.
   */
  public CraftingStatusManager(CharacterFile toon)
  {
    _toon=toon;
  }

  /**
   * Get the crafting status for this toon.
   * @return A crafting status.
   */
  public CraftingStatus getCraftingStatus()
  {
    if (_crafting==null)
    {
      _crafting=loadCrafting();
      if (_crafting==null)
      {
        CraftingStatusComputer c=new CraftingStatusComputer();
        _crafting=c.buildCraftingStatus(_toon);
        saveCrafting();
      }
    }
    return _crafting;
  }

  private CraftingStatus loadCrafting()
  {
    CraftingStatus crafting=null;
    File craftingFile=getCraftingFile();
    if ((craftingFile.exists()) && (craftingFile.canRead()))
    {
      CraftingStatusXMLParser parser=new CraftingStatusXMLParser();
      crafting=parser.parseXML(craftingFile);
    }
    return crafting;
  }

  /**
   * Revert crafting status from disk.
   */
  public void revertCrafting()
  {
    _crafting=loadCrafting();
  }

  /**
   * Save crafting status to file.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean saveCrafting()
  {
    File craftingFile=getCraftingFile();
    CraftingStatusXMLWriter writer=new CraftingStatusXMLWriter();
    boolean ok=writer.write(craftingFile,_crafting,EncodingNames.ISO8859_1);
    return ok;
  }

  private File getCraftingFile()
  {
    File rootDir=_toon.getRootDir();
    return new File(rootDir,"crafting.xml");
  }
}
