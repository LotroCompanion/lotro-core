package delta.games.lotro.character.status.crafting;

import java.util.List;

import delta.games.lotro.LotroTestUtils;
import delta.games.lotro.character.CharacterFile;

/**
 * Test for crafting stats.
 * @author DAM
 */
public class MainTestCraftingStats
{
  /**
   * Basic main method for test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    LotroTestUtils utils=new LotroTestUtils();
    List<CharacterFile> toons=utils.getAllFiles();
    for(CharacterFile toon : toons)
    {
      CraftingStatus stats=toon.getCraftingMgr().getCraftingStatus();
      stats.dump(System.out);
    }
  }
}
