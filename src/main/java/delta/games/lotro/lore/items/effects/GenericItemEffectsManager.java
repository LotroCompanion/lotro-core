package delta.games.lotro.lore.items.effects;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.effects.io.xml.GenericItemEffectsXMLParser;

/**
 * Manager for generic item effects.
 * @author DAM
 */
public class GenericItemEffectsManager
{
  private static final GenericItemEffectsManager _instance=new GenericItemEffectsManager();

  private Map<EquipmentCategory,GenericItemEffects> _map;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static GenericItemEffectsManager getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  public GenericItemEffectsManager()
  {
    _map=new HashMap<EquipmentCategory,GenericItemEffects>();
    load();
  }

  private void load()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File registryFile=cfg.getFile(DataFiles.GENERIC_ITEM_EFFECTS);
    GenericItemEffectsXMLParser parser=new GenericItemEffectsXMLParser();
    List<GenericItemEffects> allItemEffects=parser.parseGenericItemEffectsFile(registryFile);
    for(GenericItemEffects itemEffects : allItemEffects)
    {
      add(itemEffects);
    }
  }

  private void add(GenericItemEffects effects)
  {
    EquipmentCategory category=effects.getCategory();
    _map.put(category,effects);
  }

  /**
   * Get the effects for a single equipment category.
   * @param category Category to use.
   * @return some effects or <code>null</code>.
   */
  public GenericItemEffects getEffects(EquipmentCategory category)
  {
    return _map.get(category);
  }
}
