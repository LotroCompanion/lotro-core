package delta.games.lotro.lore.items.stats;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;
import delta.common.utils.url.URLTools;
import delta.games.lotro.utils.TypedProperties;

/**
 * Manages all scaling rules.
 * @author DAM
 */
public class ScalingManager
{
  private static final Logger LOGGER=Logger.getLogger(ScalingManager.class);

  private HashMap<String,ScalingRule> _mappings;

  /**
   * Constructor.
   */
  public ScalingManager()
  {
    _mappings=new HashMap<String,ScalingRule>();
    init();
  }

  private void init()
  {
    List<String> ids=loadIds();
    for(String id : ids)
    {
      ScalingRule rule=new ScalingRule(id);
      _mappings.put(id,rule);
    }
  }

  private List<String> loadIds()
  {
    List<String> ret=new ArrayList<String>();
    URL url=URLTools.getFromClassPath("scalingRules.properties",ScalingManager.class.getPackage());
    if (url!=null)
    {
      InputStream is=null;
      try
      {
        is=url.openStream();
        TypedProperties props=new TypedProperties();
        props.loadFromInputStream(is);
        ret.addAll(props.getStringList("rule"));
      }
      catch(IOException ioe)
      {
        LOGGER.error("Cannot load scaling properties from "+url,ioe);
      }
      finally
      {
        StreamTools.close(is);
      }
    }
    return ret;
  }

  /**
   * Get a scaling rule usig its identifier.
   * @param id Identifier of the rule to get.
   * @return A scaling rule or <code>null</code> if not found.
   */
  public ScalingRule getRule(String id)
  {
    return _mappings.get(id);
  }
}
