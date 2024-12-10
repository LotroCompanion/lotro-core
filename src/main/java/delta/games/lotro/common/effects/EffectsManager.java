package delta.games.lotro.common.effects;

import java.io.File;
import java.io.PrintStream;
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
import delta.games.lotro.utils.PerfUtils;
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
    if (from.exists())
    {
      long now=System.currentTimeMillis();
      SingleLocaleLabelsManager labelsMgr=I18nFacade.getLabelsMgr("effects");
      EffectXMLParser parser=new EffectXMLParser(labelsMgr);
      List<Effect> effects=parser.parseEffectsFile(from);
      for(Effect effect : effects)
      {
        ret.addEffect(effect);
      }
      long now2=System.currentTimeMillis();
      long duration=now2-now;
      PerfUtils.showLoadedLog(+ret._effects.size(),"effects",duration);
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
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    List<Effect> effects=getEffects();
    out.println("Effect: ("+effects.size()+")");
    for(Effect effect : effects)
    {
      out.println("\t"+effect);
    }
  }
}
