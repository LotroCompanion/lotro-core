package delta.games.lotro.lore.items.legendary2;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.legendary2.io.xml.EnhancementRunesXMLParser;

/**
 * Manager for all known enhancement runes.
 * @author DAM
 */
public class EnhancementRunesManager
{
  private static final Logger LOGGER=Logger.getLogger(EnhancementRunesManager.class);

  private static EnhancementRunesManager _instance=null;

  private HashMap<Integer,EnhancementRune> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static EnhancementRunesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new EnhancementRunesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public EnhancementRunesManager()
  {
    _cache=new HashMap<Integer,EnhancementRune>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File enhancementRunesFile=cfg.getFile(DataFiles.ENHANCEMENT_RUNES);
    long now=System.currentTimeMillis();
   List<EnhancementRune> enhancementRunes=EnhancementRunesXMLParser.parseEnhancementRunesFile(enhancementRunesFile);
    for(EnhancementRune enhancementRune : enhancementRunes)
    {
      registerEnhancementRune(enhancementRune);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" enhancement runes in "+duration+"ms.");
  }

  /**
   * Register a new rune.
   * @param rune Rune to register.
   */
  private void registerEnhancementRune(EnhancementRune rune)
  {
    _cache.put(Integer.valueOf(rune.getIdentifier()),rune);
  }

  /**
   * Get a rune using its identifier.
   * @param id Rune identifier.
   * @return A rune or <code>null</code> if not found.
   */
  public EnhancementRune getEnhancementRune(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }
}
