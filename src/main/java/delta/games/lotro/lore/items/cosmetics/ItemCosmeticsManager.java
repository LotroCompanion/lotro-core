package delta.games.lotro.lore.items.cosmetics;

import java.io.File;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.cosmetics.io.xml.ItemCosmeticsXMLParser;

/**
 * Provides access to item cosmetics data.
 * @author DAM
 */
public class ItemCosmeticsManager
{
  private static ItemCosmeticsManager _instance=new ItemCosmeticsManager();

  private ItemCosmetics _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ItemCosmeticsManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private ItemCosmeticsManager()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.ITEM_COSMETICS);
    _cache=ItemCosmeticsXMLParser.parseFile(from);
  }

  /**
   * Get the managed data.
   * @return the managed data.
   */
  public ItemCosmetics getData()
  {
    return _cache;
  }
}
