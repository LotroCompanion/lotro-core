package delta.games.lotro.lore.xrefs.birds;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.objectives.InventoryItemCondition;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to titles.
 * @author DAM
 */
public class BirdReferencesBuilder
{
  private List<Reference<?,BirdRole>> _storage;

  /**
   * Constructor.
   */
  public BirdReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,BirdRole>>();
  }

  /**
   * Search for a bird.
   * @param birdID Bird identifier.
   * @return the found references.
   */
  public List<Reference<?,BirdRole>> inspectBird(int birdID)
  {
    _storage.clear();
    findInDeeds(birdID);
    List<Reference<?,BirdRole>> ret=new ArrayList<Reference<?,BirdRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInDeeds(int birdID)
  {
    // Deeds
    DeedsManager deedsManager=DeedsManager.getInstance();
    List<DeedDescription> deeds=deedsManager.getAll();
    for(DeedDescription deed : deeds)
    {
      findInDeed(deed,birdID);
    }
  }

  private void findInDeed(DeedDescription deed, int birdID)
  {
    ObjectivesManager objectives=deed.getObjectives();
    for(Objective objective : objectives.getObjectives())
    {
      for(ObjectiveCondition condition : objective.getConditions())
      {
        if (condition instanceof InventoryItemCondition)
        {
          InventoryItemCondition inventoryItemCondition=(InventoryItemCondition)condition;
          Item item=inventoryItemCondition.getItem();
          if ((item!=null) && (item.getIdentifier()==birdID))
          {
            _storage.add(new Reference<DeedDescription,BirdRole>(deed,BirdRole.INVOLVED_IN_DEED));
          }
        }
      }
    }
  }
}
