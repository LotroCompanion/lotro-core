package delta.games.lotro.kinship;

import delta.games.lotro.common.id.InternalGameId;

/**
 * Storage class for a LOTRO kinship summary.
 * @author DAM
 */
public class KinshipSummary
{
  private InternalGameId _kinshipID;
  private String _name;
  private InternalGameId _leaderID;
  private InternalGameId _founderID;
  private Long _creationDate;
  private String _motd;

  /**
   * Constructor.
   */
  public KinshipSummary()
  {
    _kinshipID=null;
    _name="";
    _leaderID=null;
    _founderID=null;
    _creationDate=null;
    _motd="";
  }

  /**
   * Get the kinship identifier.
   * @return a kinship identifier.
   */
  public InternalGameId getKinshipID()
  {
    return _kinshipID;
  }

  /**
   * Set the kinship identifier.
   * @param kinshipID Kinship identifier to set.
   */
  public void setKinshipID(InternalGameId kinshipID)
  {
    _kinshipID=kinshipID;
  }

  /**
   * Get the kinship name.
   * @return the kinship name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the kinship name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the leader identifier.
   * @return a leader identifier.
   */
  public InternalGameId getLeaderID()
  {
    return _leaderID;
  }

  /**
   * Set the leader identifier.
   * @param leaderID Leader identifier to set.
   */
  public void setLeaderID(InternalGameId leaderID)
  {
    _leaderID=leaderID;
  }


  /**
   * Get the founder identifier.
   * @return a founder identifier.
   */
  public InternalGameId getFounderID()
  {
    return _founderID;
  }

  /**
   * Set the founder identifier.
   * @param founderID Founder identifier to set.
   */
  public void setFounderID(InternalGameId founderID)
  {
    _founderID=founderID;
  }

  /**
   * Get the creation date.
   * @return a timestamp (milliseconds since Epoch) or <code>null</code>.
   */
  public Long getCreationDate()
  {
    return _creationDate;
  }

  /**
   * Set the creation date.
   * @param creationDate the date to set.
   */
  public void setCreationDate(Long creationDate)
  {
    _creationDate=creationDate;
  }

  /**
   * Get the message of the day (motd).
   * @return the message of the day.
   */
  public String getMotd()
  {
    return _motd;
  }

  /**
   * Set the message of the day (motd).
   * @param motd the message to set.
   */
  public void setMotd(String motd)
  {
    if (motd==null)
    {
      motd="";
    }
    _motd=motd;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("ID [").append(_kinshipID).append("], ");
    sb.append("Name [").append(_name).append("], ");
    sb.append("Leader ID [").append(_leaderID).append(']');
    sb.append("Founder ID [").append(_founderID).append(']');
    sb.append("Creation dates [").append(_creationDate).append(']');
    sb.append("MOTD [").append(_motd).append(']');
    return sb.toString();
  }
}
