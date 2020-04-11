package delta.games.lotro.common.id;

import java.util.Locale;

/**
 * Internal in-game identifier.
 * @author DAM
 */
public class InternalGameId
{
  protected static final String VALUE_SEPARATOR="/";

  protected int _id1;
  protected int _id2;

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
   * Set identifier value from a long value.
   * @param id Input long value.
   */
  public void setIdAsLong(long id)
  {
    _id1=(int)(id&0xFFFFFFFF);
    _id2=(int)(id>>32);
  }

  /**
   * Get a 'long' represenation of this identifier.
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
  public int getId1() {
    return _id1;
  }

  /**
   * Get the value of the identifier 2.
   * @return the value of the identifier 2.
   */
  public int getId2() {
    return _id2;
  }

  /**
   * Get a representation of this ID as a single hexadecimal value string.
   * @return a displayable ID string.
   */
  public String asSingleHexString()
  {
    String hex=Integer.toHexString(_id2)+"-"+Integer.toHexString(_id1);
    hex=hex.toUpperCase(Locale.ROOT);
    return hex;
  }

  /**
   * Build a string representation of this identifier.
   * @return a string.
   */
  public String asString()
  {
    return _id1+InternalGameId.VALUE_SEPARATOR+_id2;
  }

  @Override
  public String toString()
  {
    return "ID: "+_id1+"/"+_id2;
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
}
