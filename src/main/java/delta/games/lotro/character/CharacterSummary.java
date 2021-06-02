package delta.games.lotro.character;


/**
 * Storage class for a LOTRO character summary.
 * @author DAM
 */
public class CharacterSummary extends BaseCharacterSummary
{
  private String _region;
  // TODO Add kinship ID

  /**
   * Constructor.
   */
  public CharacterSummary()
  {
    super();
    _region="";
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public CharacterSummary(CharacterSummary source)
  {
    super(source);
    _region=source._region;
  }

  /**
   * Set the character's region.
   * @return the character's region.
   */
  public String getRegion()
  {
    return _region;
  }

  /**
   * Set the character's region.
   * @param region the region to set.
   */
  public void setRegion(String region)
  {
    if (region==null)
    {
      region="";
    }
    _region=region;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(super.toString());
    sb.append("], Region [").append(_region).append(']');
    return sb.toString();
  }
}
