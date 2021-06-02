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

  /**
   * Constructor.
   */
  public KinshipSummary()
  {
    _kinshipID=null;
    _name="";
    _leaderID=null;
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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("ID [").append(_kinshipID).append("], ");
    sb.append("Name [").append(_name).append("], ");
    sb.append("Leader ID [").append(_leaderID).append(']');
    return sb.toString();
  }
}
