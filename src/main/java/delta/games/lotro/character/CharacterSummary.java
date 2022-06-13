package delta.games.lotro.character;

import java.util.Date;

import delta.games.lotro.character.races.NationalityDescription;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Storage class for a LOTRO character summary.
 * @author DAM
 */
public class CharacterSummary extends BaseCharacterSummary
{
  private NationalityDescription _nationality;
  private InternalGameId _kinshipID;
  private Long _importDate;

  /**
   * Constructor.
   */
  public CharacterSummary()
  {
    super();
    _nationality=null;
    _kinshipID=null;
    _importDate=null;
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public CharacterSummary(CharacterSummary source)
  {
    super(source);
    _nationality=source._nationality;
    _kinshipID=source._kinshipID;
    _importDate=source._importDate;
  }

  /**
   * Set the character's region.
   * @return the character's region.
   */
  public String getRegion()
  {
    return (_nationality!=null)?_nationality.getName():"";
  }

  /**
   * Get the nationality.
   * @return A nationality or <code>null</code> if not set.
   */
  public NationalityDescription getNationality()
  {
    return _nationality;
  }

  /**
   * Set nationality.
   * @param nationality Nationality to set.
   */
  public void setNationality(NationalityDescription nationality)
  {
    _nationality=nationality;
  }

  /**
   * Get the identifier of the character's kinship.
   * @return a kinship identifier or <code>null</code> if not set.
   */
  public InternalGameId getKinshipID()
  {
    return _kinshipID;
  }

  /**
   * Set the identifier of the character's kinship (if any).
   * @param kinshipID Identifier to set.
   */
  public void setKinshipID(InternalGameId kinshipID)
  {
    _kinshipID=kinshipID;
  }

  /**
   * Get the date for the last import for this character.
   * @return a date or <code>null</code> if not set.
   */
  public Long getImportDate()
  {
    return _importDate;
  }

  /**
   * Set the date of the last import for this character.
   * @param importDate Date to set.
   */
  public void setImportDate(Long importDate)
  {
    _importDate=importDate;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(super.toString());
    sb.append("], Region [").append(getRegion()).append(']');
    if (_kinshipID!=null)
    {
      sb.append("], Kinship [").append(_kinshipID.asDisplayableString()).append(']');
    }
    if (_importDate!=null)
    {
      sb.append("], Import date [").append(_importDate).append(" (").append(new Date(_importDate.longValue())).append(")]");
    }
    return sb.toString();
  }
}
