package delta.games.lotro.character.cosmetics.io.xml;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.cosmetics.OutfitsManager;

/**
 * I/O utilities for outfits.
 * @author DAM
 */
public class OutfitsIO
{
  /**
   * Load outfits from a file.
   * @param character Character to use.
   * @return An outfits manager or <code>null</code> if an error occurred.
   */
  public static OutfitsManager loadOutfits(CharacterFile character)
  {
    OutfitsManager ret=null;
    File outfitsFile=getOutfitsFile(character);
    if (outfitsFile.exists())
    {
      OutfitsXMLParser parser=new OutfitsXMLParser();
      ret=parser.parseXML(outfitsFile);
    }
    return ret;
  }

  /**
   * Save outfits to a file.
   * @param character Account/server to use.
   * @param outfitsMgr Outfits to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean saveOutfits(CharacterFile character, OutfitsManager outfitsMgr)
  {
    File outfitsFile=getOutfitsFile(character);
    OutfitsXMLWriter writer=new OutfitsXMLWriter();
    boolean ok=writer.write(outfitsFile,outfitsMgr,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Build an outfits file.
   * @param character Character to use.
   * @return An outfits file.
   */
  public static File getOutfitsFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File outfitsFile=new File(rootDir,"outfits.xml");
    return outfitsFile;
  }
}
