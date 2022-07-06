package delta.games.lotro.character.status.collections;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.skills.SkillsStatusManager;
import delta.games.lotro.character.status.skills.io.SkillsStatusIo;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.lore.collections.CollectionDescription;
import delta.games.lotro.lore.collections.CollectionsManager;

/**
 * Builds collections status for characters.
 * @author DAM
 */
public class CollectionsStatusBuilder
{
  /**
   * Build the collections status for a character.
   * @param f Input character.
   * @return a new collections status manager.
   */
  public CollectionsStatusManager build(CharacterFile f)
  {
    SkillsStatusManager statusMgr=SkillsStatusIo.load(f);
    CollectionsStatusManager ret=new CollectionsStatusManager();
    List<CollectionDescription> collections=getCollections(f.getSummary());
    for(CollectionDescription collection : collections)
    {
      CollectionStatus collectionStatus=new CollectionStatus(collection);
      collectionStatus.assess(statusMgr);
      ret.addStatus(collectionStatus);
    }
    return ret;
  }

  private List<CollectionDescription> getCollections(BaseCharacterSummary summary)
  {
    List<CollectionDescription> ret=new ArrayList<CollectionDescription>();
    CollectionsManager collectionsMgr=CollectionsManager.getInstance();
    for(CollectionDescription collection : collectionsMgr.getAll())
    {
      if (canUse(collection,summary))
      {
        ret.add(collection);
      }
    }
    return ret;
  }

  private boolean canUse(CollectionDescription collection, BaseCharacterSummary summary)
  {
    RaceRequirement raceRequirement=collection.getRaceRequirement();
    if (raceRequirement==null)
    {
      return true;
    }
    return raceRequirement.accept(summary.getRace());
  }
}
