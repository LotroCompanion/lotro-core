package delta.games.lotro.common.effects;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.effects.io.xml.EffectXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Manager for effects.
 * @author DAM
 */
public class EffectsManager
{
  private static EffectsManager _instance=null;
  private Map<Integer,Effect> _effects;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static EffectsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  private static EffectsManager load()
  {
    EffectsManager ret=new EffectsManager();
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.EFFECTS);
    SingleLocaleLabelsManager labelsMgr=I18nFacade.getLabelsMgr("effects");
    List<Effect> effects=EffectXMLParser.parseEffectsFile(from,labelsMgr);
    for(Effect effect : effects)
    {
      ret.addEffect(effect);
    }
    return ret;
  }

  /**
   * Constructor.
   */
  public EffectsManager()
  {
    _effects=new HashMap<Integer,Effect>();
  }

  /**
   * Add an effect.
   * @param effect Effect to add.
   */
  public void addEffect(Effect effect)
  {
    Integer key=Integer.valueOf(effect.getIdentifier());
    _effects.put(key,effect);
  }

  /**
   * Get an effect using its identifier.
   * @param effectId Effect identifier.
   * @return An effect or <code>null</code> if not found.
   */
  public Effect getEffectById(int effectId)
  {
    return _effects.get(Integer.valueOf(effectId));
  }

  /**
   * Get all effects.
   * @return a list of effects.
   */
  public List<Effect> getEffects()
  {
    List<Effect> ret=new ArrayList<Effect>();
    ret.addAll(_effects.values());
    Collections.sort(ret,new IdentifiableComparator<Effect>());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   */
  public void dump()
  {
    List<Effect> effects=getEffects();
    System.out.println("Effect: ("+effects.size()+")");
    for(Effect effect : effects)
    {
      System.out.println("\t"+effect);
    }
  }
}
