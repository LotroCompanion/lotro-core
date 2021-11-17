package delta.games.lotro.character.status.allegiances;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.status.allegiances.io.AllegiancesStatusIo;

/**
 * Simple test class for the allegiances status manager.
 * @author DAM
 */
public class MainTestAllegiancesStatusManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    CharacterFile toon=CharactersManager.getInstance().getToonById("Landroval","Giswald");
    if (toon==null)
    {
      return;
    }
    AllegiancesStatusManager status=AllegiancesStatusIo.load(toon);
    for(AllegianceStatus allegianceStatus : status.getAll())
    {
      System.out.println(allegianceStatus);
    }
  }
}
