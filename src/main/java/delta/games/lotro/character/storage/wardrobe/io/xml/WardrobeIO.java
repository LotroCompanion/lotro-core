package delta.games.lotro.character.storage.wardrobe.io.xml;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.character.storage.wardrobe.Wardrobe;

/**
 * I/O utilities for wardrobes.
 * @author DAM
 */
public class WardrobeIO
{
  /**
   * Load a wardrobe from a file.
   * @param accountOnServer Account/server to use.
   * @return A wardrobe or <code>null</code> if an error occurred.
   */
  public static Wardrobe loadWardrobe(AccountOnServer accountOnServer)
  {
    Wardrobe ret=null;
    File wardrobeFile=getWardrobeFile(accountOnServer);
    if (wardrobeFile.exists())
    {
      WardrobeXMLParser parser=new WardrobeXMLParser();
      ret=parser.parseXML(wardrobeFile);
    }
    return ret;
  }

  /**
   * Save a wardrobe to file.
   * @param accountOnServer Account/server to use.
   * @param wardrobe Wardrobe to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean saveWardrobe(AccountOnServer accountOnServer, Wardrobe wardrobe)
  {
    File wardrobeFile=getWardrobeFile(accountOnServer);
    WardrobeXMLWriter writer=new WardrobeXMLWriter();
    boolean ok=writer.write(wardrobeFile,wardrobe,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Build a wardrobe file.
   * @param accountOnServer Account/server to use.
   * @return A friends file.
   */
  public static File getWardrobeFile(AccountOnServer accountOnServer)
  {
    File rootDir=accountOnServer.getRootDir();
    File wardrobeFile=new File(rootDir,"wardrobe.xml");
    return wardrobeFile;
  }
}
