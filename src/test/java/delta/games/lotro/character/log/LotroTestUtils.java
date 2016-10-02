package delta.games.lotro.character.log;

import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;

/**
 * Test utilities.
 * @author DAM
 */
public class LotroTestUtils
{
  private HashMap<String,CharacterFile> _files;

  /**
   * Constructor.
   */
  public LotroTestUtils()
  {
    _files=new HashMap<String,CharacterFile>();
    List<CharacterFile> allToons=getAllFiles();
    for(CharacterFile toon : allToons)
    {
      String name=toon.getName();
      _files.put(name,toon);
    }
  }

  /**
   * Get all registered files.
   * @return A list of character files, sorted by character name.
   */
  public List<CharacterFile> getAllFiles()
  {
    CharactersManager mgr=CharactersManager.getInstance();
    return mgr.getAllToons();
  }

  /**
   * Get test toon by its name.
   * @param name Name of the toon to get.
   * @return A character file or <code>null</code> if not found.
   */
  public CharacterFile getToonByName(String name)
  {
    return _files.get(name);
  }

  /**
   * Get main test toon. 
   * @return A character file.
   */
  public CharacterFile getMainToon()
  {
    return _files.get("Glumlug");
  }
}
