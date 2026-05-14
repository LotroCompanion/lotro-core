package delta.games.lotro.lore.xrefs.portraitFrames;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.ProvidesPortraitFrame;
import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to traits.
 * @author DAM
 */
public class PortraitFrameReferencesBuilder
{
  private List<Reference<Item,PortraitFrameRole>> _storage;

  /**
   * Constructor.
   */
  public PortraitFrameReferencesBuilder()
  {
    _storage=new ArrayList<Reference<Item,PortraitFrameRole>>();
  }

  /**
   * Search for a portrait frame.
   * @param code Portrait frame code.
   * @return the found references.
   */
  public List<Reference<Item,PortraitFrameRole>> inspectPortraitFrame(int code)
  {
    _storage.clear();
    findInItems(code);
    List<Reference<Item,PortraitFrameRole>> ret=new ArrayList<Reference<Item,PortraitFrameRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInItems(int code)
  {
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      ItemDetailsManager details=item.getDetails();
      if (details==null)
      {
        continue;
      }
      for(ProvidesPortraitFrame provider : details.getItemDetails(ProvidesPortraitFrame.class))
      {
        PortraitFrameDescription portraitFrame=provider.getPortraitFrame(); 
        if (portraitFrame.getCode()==code)
        {
          _storage.add(new Reference<Item,PortraitFrameRole>(item,PortraitFrameRole.PROVIDED_BY_ITEM));
        }
      }
    }
  }
}
