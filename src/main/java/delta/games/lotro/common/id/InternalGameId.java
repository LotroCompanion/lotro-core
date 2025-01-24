package delta.games.lotro.common.id;

import delta.common.utils.NumericTools;

/**
 * Internal in-game identifier.
 * @author DAM
 */
public class InternalGameId
{
  private static final String VALUE_SEPARATOR="/";
  private static final char TYPE_SEPARATOR=':';

  private int _id1;
  private int _id2;

  /**
   * Constructor.
   * @param id1 Identifier 1 (32 bits).
   * @param id2 Identifier 2 (32 bits).
   */
  public InternalGameId(int id1, int id2)
  {
    _id1=id1;
    _id2=id2;
  }

  /**
   * Constructor.
   * @param id Long identifier.
   */
  public InternalGameId(long id)
  {
    _id1=(int)(id&0xFFFFFFFF);
    _id2=(int)(id>>32);
  }

  /**
   * Copy constructor.
   * @param source Source to copy.
   */
  public InternalGameId(InternalGameId source)
  {
    _id1=source._id1;
    _id2=source._id2;
  }

  /**
   * Get a 'long' representation of this identifier.
   * @return a long value.
   */
  public long asLong()
  {
    long low=(_id1&0xFFFFFFFF);
    long high=(_id2&0xFFFFFFFF);
    long ret=(high<<32)+low;
    return ret;
  }

  /**
   * Get the value of the identifier 1.
   * @return the value of the identifier 1.
   */
  public int getId1()
  {
    return _id1;
  }

  /**
   * Get the value of the identifier 2.
   * @return the value of the identifier 2.
   */
  public int getId2()
  {
    return _id2;
  }

  /**
   * Get a representation of this ID as a single hexadecimal value string.
   * @return a displayable ID string.
   */
  private String asHexString()
  {
    String hex=String.format("%08X%08X",Integer.valueOf(_id2),Integer.valueOf(_id1));
    return hex;
  }

  /**
   * Build a displayable representation of this identifier.
   * @return a string.
   */
  public String asDisplayableString()
  {
    return asHexString();
  }

  /**
   * Get the representation of this identifier for persistence needs.
   * @return A persisted string.
   */
  public String asPersistedString()
  {
    return asHexString();
  }

  @Override
  public boolean equals(Object id)
  {
    if (id==this)
    {
      return true;
    }
    if (id instanceof InternalGameId)
    {
      InternalGameId otherId=(InternalGameId)id;
      return (_id1==otherId._id1) && (_id2==otherId._id2);
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return _id1+_id2;
  }

  @Override
  public String toString()
  {
    return "ID: "+asHexString();
  }

  /**
   * Perform 'light' match between 2 identifiers.
   * @param id1 Identifier 1.
   * @param id2 Identifier 2.
   * @return <code>true</code> if they match, <code>false</code> otherwise.
   */
  public static boolean lightMatch(long id1, long id2)
  {
    long lowId1=id1&0xFFFFFFFFL;
    long lowId2=id2&0xFFFFFFFFL;
    return (lowId1==lowId2);
  }

  /**
   * Build an identifier from a string.
   * @param idStr Input string.
   * @return An identifier or <code>null</code> if not valid.
   */
  public static InternalGameId fromString(String idStr)
  {
    InternalGameId ret=fromLegacyString(idStr);
    if (ret==null)
    {
      ret=fromPersistedString(idStr);
    }
    return ret;
  }

  private static InternalGameId fromPersistedString(String idStr)
  {
    InternalGameId ret=null;
    try
    {
      long value=Long.parseLong(idStr,16);
      ret=new InternalGameId(value);
    }
    catch(Exception e)
    {
      ret=null;
    }
    return ret;
  }

  private static InternalGameId fromLegacyString(String idStr)
  {
    // Ignore type separator if present
    int index=idStr.indexOf(TYPE_SEPARATOR);
    if (index!=-1)
    {
      idStr=idStr.substring(index+1);
    }
    index=idStr.indexOf(VALUE_SEPARATOR);
    if (index!=-1)
    {
      Integer id1=NumericTools.parseInteger(idStr.substring(0,index));
      Integer id2=NumericTools.parseInteger(idStr.substring(index+1));
      if ((id1!=null) && (id2!=null))
      {
        return new InternalGameId(id1.intValue(),id2.intValue());
      }
    }
    return null;
  }
}
