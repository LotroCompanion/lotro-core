package delta.games.lotro.common.effects;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.effects.io.xml.EffectXMLParser2;
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
  private Map<Integer,Effect2> _effects;

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
    EffectXMLParser2 parser=new EffectXMLParser2(labelsMgr);
    List<Effect2> effects=parser.parseEffectsFile(from);
    for(Effect2 effect : effects)
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
    _effects=new HashMap<Integer,Effect2>();
  }

  /**
   * Add an effect.
   * @param effect Effect to add.
   */
  public void addEffect(Effect2 effect)
  {
    Integer key=Integer.valueOf(effect.getIdentifier());
    _effects.put(key,effect);
  }

  /**
   * Get an effect using its identifier.
   * @param effectId Effect identifier.
   * @return An effect or <code>null</code> if not found.
   */
  public Effect2 getEffectById(int effectId)
  {
    return _effects.get(Integer.valueOf(effectId));
  }

  /**
   * Get all effects.
   * @return a list of effects.
   */
  public List<Effect2> getEffects()
  {
    List<Effect2> ret=new ArrayList<Effect2>();
    ret.addAll(_effects.values());
    Collections.sort(ret,new IdentifiableComparator<Effect2>());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   */
  public void dump()
  {
    List<Effect2> effects=getEffects();
    System.out.println("Effect: ("+effects.size()+")");
    for(Effect2 effect : effects)
    {
      System.out.println("\t"+effect);
    }
  }
}
