package delta.games.lotro.lore.items.stats;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.common.utils.io.StreamTools;
import delta.common.utils.misc.TypedProperties;
import delta.common.utils.url.URLTools;

/**
 * Mapping rules between required levels and item levels.
 * @author DAM
 */
public class ScalingRule
{
  private static final Logger LOGGER=Logger.getLogger(ScalingRule.class);

  private String _id;
  private Map<Integer,Integer> _mapItemLevel2RequiredLevel;
  private Map<Integer,Integer> _mapRequiredLevel2ItemLevel;
  private int[] _itemLevelRange;
  private int[] _requiredLevelRange;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public ScalingRule(String id)
  {
    _id=id;
    _mapRequiredLevel2ItemLevel=new HashMap<Integer,Integer>();
    _mapItemLevel2RequiredLevel=new HashMap<Integer,Integer>();
    _itemLevelRange=new int[2];
    _requiredLevelRange=new int[2];
    load();
  }

  /**
   * Get the identifier of this rule.
   * @return an identifier.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the item level for a required level.
   * @param requiredLevel Level to use.
   * @return An item level or <code>null</code> if not found.
   */
  public Integer getItemLevel(int requiredLevel)
  {
    return _mapRequiredLevel2ItemLevel.get(Integer.valueOf(requiredLevel));
  }

  /**
   * Get all managed item levels.
   * @return A list of sorted item levels.
   */
  public List<Integer> getItemLevels()
  {
    List<Integer> levels=new ArrayList<Integer>();
    levels.addAll(_mapItemLevel2RequiredLevel.keySet());
    Collections.sort(levels);
    return levels;
  }

  /**
   * Get the required level for an item level.
   * @param itemLevel Level to use.
   * @return A required level or <code>null</code> if not found.
   */
  public Integer getRequiredLevel(int itemLevel)
  {
    return _mapItemLevel2RequiredLevel.get(Integer.valueOf(itemLevel));
  }

  /**
   * Get all managed required levels.
   * @return A list of sorted required levels.
   */
  public List<Integer> getRequiredLevels()
  {
    List<Integer> levels=new ArrayList<Integer>();
    levels.addAll(_mapRequiredLevel2ItemLevel.keySet());
    Collections.sort(levels);
    return levels;
  }

  /**
   * Get the range of required levels.
   * @return An array of 2 ints (min and max, inclusive).
   */
  public int[] getRequiredLevelRange()
  {
    return _requiredLevelRange;
  }

  /**
   * Get the range of required levels.
   * @return An array of 2 ints (min and max, inclusive).
   */
  public int[] getItemLevelRange()
  {
    return _itemLevelRange;
  }

  private void load()
  {
    URL url=URLTools.getFromClassPath(_id+".properties",ScalingRule.class.getPackage());
    if (url!=null)
    {
      InputStream is=null;
      try
      {
        is=url.openStream();
        TypedProperties props=new TypedProperties();
        props.loadFromInputStream(is);
        readProperties(props);
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
  }

  private void readProperties(TypedProperties props)
  {
    Set<String> keys=props.getAllKeys();
    for(String key : keys)
    {
      readProperty(key,props.getProperty(key,String.class));
    }
  }

  private void readProperty(String key, String value)
  {
    int index=key.indexOf('-');
    if (index!=-1)
    {
      Integer min=NumericTools.parseInteger(key.substring(0,index));
      Integer max=NumericTools.parseInteger(key.substring(index+1));
      if ((min!=null) && (max!=null))
      {
        for(int i=min.intValue();i<=max.intValue();i++)
        {
          handleMapping(i,value);
        }
      }
    }
    else
    {
      Integer requiredLevel=NumericTools.parseInteger(key);
      if (requiredLevel!=null)
      {
        handleMapping(requiredLevel.intValue(),value);
      }
    }
  }

  private void handleMapping(int requiredLevel, String value)
  {
    if ("same".equals(value))
    {
      registerMapping(requiredLevel,requiredLevel);
    }
    else
    {
      Integer intValue=NumericTools.parseInteger(value);
      if (intValue!=null)
      {
        registerMapping(requiredLevel,intValue.intValue());
      }
      else
      {
        LOGGER.warn("Invalid value: ["+value+"] in rule "+_id);
      }
    }
  }

  private void registerMapping(int requiredLevel, int itemLevel)
  {
    Integer itemLevelInt=Integer.valueOf(itemLevel);
    Integer requiredLevelInt=Integer.valueOf(requiredLevel);
    _mapItemLevel2RequiredLevel.put(itemLevelInt,requiredLevelInt);
    _mapRequiredLevel2ItemLevel.put(requiredLevelInt,itemLevelInt);
    if (requiredLevel<_requiredLevelRange[0])
    {
      _requiredLevelRange[0]=requiredLevel;
    }
    if (requiredLevel>_requiredLevelRange[1])
    {
      _requiredLevelRange[1]=requiredLevel;
    }
    if (itemLevel<_itemLevelRange[0])
    {
      _itemLevelRange[0]=itemLevel;
    }
    if (itemLevel>_itemLevelRange[1])
    {
      _itemLevelRange[1]=itemLevel;
    }
  }
}
