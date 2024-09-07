package delta.games.lotro.lore.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.Interactable;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobsManager;
import delta.games.lotro.lore.agents.npcs.NPCsManager;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Utility methods related to interactables.
 * @author DAM
 */
public class InteractableUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(InteractableUtils.class);

  /**
   * Find an interactable using its identifier.
   * @param id Identifier of the object to get.
   * @return an object or <code>null</code> if not found.
   */
  public static Interactable findInteractable(int id)
  {
    // NPC?
    NPCsManager npcsManager=NPCsManager.getInstance();
    NpcDescription npc=npcsManager.getNPCById(id);
    if (npc!=null)
    {
      return npc;
    }
    // Mob?
    MobsManager mobsManager=MobsManager.getInstance();
    MobDescription mob=mobsManager.getMobById(id);
    if (mob!=null)
    {
      return mob;
    }
    // Item?
    ItemsManager itemsMgr=ItemsManager.getInstance();
    Item item=itemsMgr.getItem(id);
    if (item!=null)
    {
      return item;
    }
    LOGGER.warn("Interactable not found: ID="+id);
    return null;
  }
}
