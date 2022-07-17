package delta.games.lotro.character;

/**
 * Test class that loads all the character configurations.
 * @author DAM
 */
public class MainTestLoadAll
{
  /**
   * Main method for this class.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    CharactersManager charsMgr=CharactersManager.getInstance();
    for(CharacterFile toon : charsMgr.getAllToons())
    {
      CharacterInfosManager infosMgr=toon.getInfosManager();
      infosMgr.sync();
    }
  }
}
