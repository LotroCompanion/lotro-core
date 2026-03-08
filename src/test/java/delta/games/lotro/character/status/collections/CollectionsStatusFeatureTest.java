package delta.games.lotro.character.status.collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;

/**
 * Test class for collection status computations.
 * @author DAM
 */
class CollectionsStatusFeatureTest
{
  /**
   * Test the computation of the collections status for a character.
   */
  @Test
  void testStatusComputation()
  {
    for(CharacterFile toon : CharactersManager.getInstance().getAllToons())
    {
      assertNotNull(toon);
      CollectionsStatusManager mgr=new CollectionsStatusBuilder().build(toon,null);
      assertNotNull(mgr);
      for(CollectionStatus collectionStatus : mgr.getAll())
      {
        assertNotNull(collectionStatus);
        if (collectionStatus.getCompletedCount()>0)
        {
          String characterName=toon.getName();
          System.out.println(characterName+" => "+collectionStatus);
        }
      }
    }
  }
}
