package delta.games.lotro.character.storage.carryalls.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.carryalls.CarryAllsManager;
import delta.games.lotro.character.storage.carryalls.io.xml.CarryAllsXMLParser;
import delta.games.lotro.character.storage.carryalls.io.xml.CarryAllsXMLWriter;

/**
 * I/O methods for carry-alls.
 * @author DAM
 */
public class CarryAllsIo
{
  /**
   * Load the carry-alls for a character.
   * @param character Targeted character.
   * @return A carry-alls manager.
   */
  public static CarryAllsManager load(CharacterFile character)
  {
    File fromFile=getCarryAllsFile(character);
    CarryAllsManager carryAllsManager=null;
    if (fromFile.exists())
    {
      CarryAllsXMLParser parser=new CarryAllsXMLParser();
      carryAllsManager=parser.parseXML(fromFile);
    }
    if (carryAllsManager==null)
    {
      carryAllsManager=new CarryAllsManager();
      save(character,carryAllsManager);
    }
    return carryAllsManager;
  }

  /**
   * Save the carry-alls for a character.
   * @param character Targeted character.
   * @param carryAllsManager Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, CarryAllsManager carryAllsManager)
  {
    File toFile=getCarryAllsFile(character);
    CarryAllsXMLWriter writer=new CarryAllsXMLWriter();
    boolean ok=writer.write(toFile,carryAllsManager,EncodingNames.UTF_8);
    return ok;
  }

  private static File getCarryAllsFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File carryAllsFile=new File(rootDir,"carryAlls.xml");
    return carryAllsFile;
  }
}
