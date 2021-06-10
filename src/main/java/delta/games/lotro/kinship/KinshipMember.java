package delta.games.lotro.kinship;

import delta.games.lotro.common.id.InternalGameId;

/**
 * Kinship member.
 * @author DAM
 */
public class KinshipMember
{
  private KinshipCharacterSummary _character;
  private Long _joinDate;
  private KinshipRank _rank;
  private String _notes;

  /**
   * Constructor.
   */
  public KinshipMember()
  {
    _character=new KinshipCharacterSummary();
    _notes="";
  }

  /**
   * Get the character ID.
   * @return the character ID or <code>null</code> if not defined.
   */
  public Long getID()
  {
    InternalGameId id=_character.getId();
    if (id!=null)
    {
      return Long.valueOf(id.asLong());
    }
    return null;
  }

  /**
   * Get the character summary.
   * @return the character summary.
   */
  public KinshipCharacterSummary getSummary()
  {
    return _character;
  }

  /**
   * Get the join date for this character.
   * @return a timestamp (milliseconds since Epoch) or <code>null</code>.
   */
  public Long getJoinDate()
  {
    return _joinDate;
  }

  /**
   * Set the join date for this character.
   * @param joinDate the date to set.
   */
  public void setJoinDate(Long joinDate)
  {
    _joinDate=joinDate;
  }

  /**
   * Get the kinship rank.
   * @return a rank or <code>null</code> if not set.
   */
  public KinshipRank getRank()
  {
    return _rank;
  }

  /**
   * Set the kinship rank.
   * @param rank Rank to set.
   */
  public void setRank(KinshipRank rank)
  {
    _rank=rank;
  }

  /**
   * Get the member's notes.
   * @return some notes (may be empty but never <code>null</code>).
   */
  public String getNotes()
  {
    return _notes;
  }

  /**
   * Set the member's notes.
   * @param notes Notes to set.
   */
  public void setNotes(String notes)
  {
    if (notes==null)
    {
      notes="";
    }
    _notes=notes;
  }
}
