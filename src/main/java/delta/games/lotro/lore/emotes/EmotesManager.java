package delta.games.lotro.lore.emotes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.emotes.io.xml.EmoteXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for emotes access.
 * @author DAM
 */
public class EmotesManager
{
  private static EmotesManager _instance=null;

  private HashMap<Integer,EmoteDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static EmotesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new EmotesManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the emotes database shall be loaded or not.
   */
  private EmotesManager(boolean load)
  {
    _cache=new HashMap<Integer,EmoteDescription>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all emotes.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File emotesFile=cfg.getFile(DataFiles.EMOTES);
    long now=System.currentTimeMillis();
    List<EmoteDescription> emotes=new EmoteXMLParser().parseXML(emotesFile);
    for(EmoteDescription emote : emotes)
    {
      _cache.put(Integer.valueOf(emote.getIdentifier()),emote);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"emotes",duration);
  }

  /**
   * Get a list of all emotes, sorted by identifier.
   * @return A list of emotes.
   */
  public List<EmoteDescription> getAll()
  {
    ArrayList<EmoteDescription> emotes=new ArrayList<EmoteDescription>();
    emotes.addAll(_cache.values());
    Collections.sort(emotes,new IdentifiableComparator<EmoteDescription>());
    return emotes;
  }

  /**
   * Get an emote using its identifier.
   * @param id Emote identifier.
   * @return An emote description or <code>null</code> if not found.
   */
  public EmoteDescription getEmote(int id)
  {
    EmoteDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
