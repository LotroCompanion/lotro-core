package delta.games.lotro.common.enums;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Single LOTRO enum manager.
 * @param <T> Type of entries.
 * @author DAM
 */
public class LotroEnum<T extends LotroEnumEntry> implements Identifiable,Named
{
  private static final Logger LOGGER=Logger.getLogger(LotroEnum.class);

  private int _id;
  private String _name;
  private List<T> _entries;
  private HashMap<Integer,T> _cache;
  private Class<T> _entryImplClass; 

  /**
   * Constructor.
   * @param id Internal ID.
   * @param name Name.
   * @param entryImplClass Implementation class for an entry.
   */
  public LotroEnum(int id, String name, Class<T> entryImplClass)
  {
    _id=id;
    _name=name;
    _entries=new ArrayList<T>();
    _cache=new HashMap<Integer,T>(10);
    _entryImplClass=entryImplClass;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Register a new entry.
   * @param entry Entry to register.
   */
  public void registerEntry(T entry)
  {
    _entries.add(entry);
    _cache.put(Integer.valueOf(entry.getCode()),entry);
  }

  /**
   * Get a list of all entries.
   * @return A list of entries.
   */
  public List<T> getAll()
  {
    ArrayList<T> entries=new ArrayList<T>(_entries);
    return entries;
  }

  /**
   * Get an entry using its code.
   * @param code Code.
   * @return An entry or <code>null</code> if not found.
   */
  public T getEntry(int code)
  {
    T ret=null;
    ret=_cache.get(Integer.valueOf(code));
    return ret;
  }

  /**
   * Get an entry by key.
   * @param key Key to use.
   * @return An entry or <code>null</code> if not found.
   */
  public T getByKey(String key)
  {
    if (key==null)
    {
      return null;
    }
    for(T entry : _entries)
    {
      if (key.equals(entry.getKey()))
      {
        return entry;
      }
    }
    return null;
  }

  /**
   * Get an entry by label.
   * @param label Label to use.
   * @return An entry or <code>null</code> if not found.
   */
  public T getByLabel(String label)
  {
    for(T entry : _entries)
    {
      if (label.equals(entry.getLabel()))
      {
        return entry;
      }
    }
    return null;
  }

  /**
   * Build an instance of the entry class.
   * @param code Internal code.
   * @param key String key.
   * @param label Label.
   * @return A new instance or <code>null</code> if it failed.
   */
  public T buildEntryInstance(int code, String key, String label)
  {
    T ret=null;
    try
    {
      ret=_entryImplClass.newInstance();
      ret.set(code,key,label);
    }
    catch(Exception e)
    {
      LOGGER.warn("Could not build an instance of "+_entryImplClass);
    }
    return ret;
  }

  /**
   * Build a list of entries from a bit set.
   * @param data Bit set.
   * @return A string or <code>null</code> if no bit set.
   */
  public List<T> getFromBitSet(BitSet data)
  {
    List<T> ret=new ArrayList<T>();
    int length=data.length();
    if (length>0)
    {
      for(int i=0;i<length;i++)
      {
        if (data.get(i))
        {
          T entry=getEntry(i+1);
          if (entry!=null)
          {
            ret.add(entry);
          }
        }
      }
    }
    return ret;
  }

  /**
   * Build a list of entries from a flags value.
   * @param data Flags value.
   * @return A string or <code>null</code> if no bit set.
   */
  public List<T> getFromBitSet(int data)
  {
    List<T> ret=new ArrayList<T>();
    int mask=1;
    for(int i=1;i<=32;i++)
    {
      if ((data&mask)!=0)
      {
        T entry=getEntry(i);
        if (entry!=null)
        {
          ret.add(entry);
        }
      }
      mask<<=1;
    }
    return ret;
  }
}
