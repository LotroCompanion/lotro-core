package delta.games.lotro.character.status.collections;

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
      CollectionsStatusManager mgr=new CollectionsStatusBuilder().build(toon,null);
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
