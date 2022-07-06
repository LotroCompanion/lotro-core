package delta.games.lotro.character.status.collections;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import junit.framework.TestCase;

/**
 * Test class for collection status computations.
 * @author DAM
 */
public class CollectionsStatusFeatureTest extends TestCase
{
  /**
   * Test the computation of the collections status for a character.
   */
  public void testStatusComputation()
  {
    for(CharacterFile toon : CharactersManager.getInstance().getAllToons())
    {
      CollectionsStatusManager mgr=new CollectionsStatusBuilder().build(toon);
      for(CollectionStatus collectionStatus : mgr.getAll())
      {
        if (collectionStatus.getCompletedCount()>0)
        {
          String characterName=toon.getName();
          System.out.println(characterName+" => "+collectionStatus);
        }
      }
    }
  }
}
