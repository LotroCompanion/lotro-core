package delta.games.lotro.character.stats;

import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.essences.Essence;
import delta.games.lotro.lore.items.essences.EssencesManager;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;

/**
 * Tools for character generation.
 * @author DAM
 */
public class CharacterGenerationTools
{
  private HashMap<Integer,HashMap<String,Essence>> _essencesMap;

  /**
   * Constructor.
   */
  public CharacterGenerationTools()
  {
    init();
  }

  private void init()
  {
    _essencesMap=new HashMap<Integer,HashMap<String,Essence>>();
    List<Essence> essences=EssencesManager.getInstance().getAll();
    for(Essence essence : essences)
    {
      Integer tier=essence.getTier();
      HashMap<String,Essence> mapbyTier=_essencesMap.get(tier);
      if (mapbyTier==null)
      {
        mapbyTier=new HashMap<String,Essence>();
        _essencesMap.put(tier,mapbyTier);
      }
      String name=essence.getName();
      mapbyTier.put(name,essence);
    }
  }

  /**
   * Get an item instance using its identifier.
   * @param id Identifier.
   * @return An item instance or <code>null</code> if not found.
   */
  public ItemInstance<? extends Item> getItemById(int id)
  {
    Item item=ItemsManager.getInstance().getItem(id);
    ItemInstance<? extends Item> itemInstance=ItemFactory.buildInstance(item);
    return itemInstance;
  }

  /**
   * Get an essence using its tier and name.
   * @param tier Tier to get.
   * @param name Essence name.
   * @return An essence item or <code>null</code> if not found.
   */
  public Essence getEssenceByName(int tier, String name)
  {
    Essence ret=null;
    HashMap<String,Essence> mapByTier=_essencesMap.get(Integer.valueOf(tier));
    if (mapByTier!=null)
    {
      ret=mapByTier.get(name);
    }
    return ret;
  }

  /**
   * Build a relic.
   * @param id Identifier.
   * @param name Name.
   * @param type Type.
   * @param requiredLevel Required level, if any.
   * @return a relic.
   */
  public Relic buildRelic(int id, String name, RelicType type, Integer requiredLevel)
  {
    Relic relic=new Relic(id,name);
    relic.addType(type);
    relic.setRequiredLevel(requiredLevel);
    return relic;
  }

  /**
   * Get a virtue using its legacy identifier.
   * @param key Identifier to use.
   * @return A virtue or <code>null</code> if not found.
   */
  public VirtueDescription getVirtue(String key)
  {
    return VirtuesManager.getInstance().getByKey(key);
  }
}
