package delta.games.lotro.character.status.travels.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.travels.DiscoveredDestinations;
import delta.games.lotro.character.status.travels.io.xml.DiscoveredDestinationsXMLParser;
import delta.games.lotro.character.status.travels.io.xml.DiscoveredDestinationsXMLWriter;

/**
 * I/O methods for discovered destinations.
 * @author DAM
 */
public class DiscoveredDestinationsIo
{
  /**
   * Load the discovered destinations for a character.
   * @param character Targeted character.
   * @return the discovered destinations.
   */
  public static DiscoveredDestinations load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    DiscoveredDestinations data=null;
    if (fromFile.exists())
    {
      DiscoveredDestinationsXMLParser parser=new DiscoveredDestinationsXMLParser();
      data=parser.read(fromFile);
    }
    if (data==null)
    {
      data=new DiscoveredDestinations();
      save(character,data);
    }
    return data;
  }

  /**
   * Save the discovered destinations for a character.
   * @param character Targeted character.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, DiscoveredDestinations data)
  {
    File toFile=getStatusFile(character);
    DiscoveredDestinationsXMLWriter writer=new DiscoveredDestinationsXMLWriter();
    boolean ok=writer.write(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File ret=new File(rootDir,"discoveredDestinations.xml");
    return ret;
  }
}
