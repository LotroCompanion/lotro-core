package delta.games.lotro.kinship;

import java.io.File;

import delta.games.lotro.common.id.InternalGameId;

/**
 * Kinship description.
 * @author DAM
 */
public class Kinship
{
  private File _rootDir;
  private KinshipSummary _summary;
  private KinshipRoster _roster;

  /**
   * Constructor.
   */
  public Kinship()
  {
    _rootDir=null;
    _summary=new KinshipSummary();
    _roster=new KinshipRoster();
  }

  /**
   * Get the root directory of the kinship's storage. 
   * @return a root directory or <code>null</code> if not set..
   */
  public File getRootDir()
  {
    return _rootDir;
  }

  /**
   * Set the root directory for kinship files.
   * @param rootDir Directory to set.
   */
  public void setRootDir(File rootDir)
  {
    _rootDir=rootDir;
  }

  /**
   * Get the kinship ID.
   * @return a kinship ID or <code>null</code>.
   */
  public Long getID()
  {
    InternalGameId id=_summary.getKinshipID();
    return id!=null?Long.valueOf(id.asLong()):null;
  }

  /**
   * Get the summary for this kinship.
   * @return a summary or <code>null</code> if it could not be built!
   */
  public KinshipSummary getSummary()
  {
    return _summary;
  }

  /**
   * Set the kinship summary.
   * @param summary summary to set.
   */
  public void setSummary(KinshipSummary summary)
  {
    _summary=summary;
  }

  /**
   * Get the name of this kinship.
   * @return a kinship name.
   */
  public String getName()
  {
    KinshipSummary summary=getSummary();
    return summary.getName();
  }

  /**
   * Get the kinship roster.
   * @return the kinship roster.
   */
  public KinshipRoster getRoster()
  {
    return _roster;
  }

  /**
   * Set kinship roster.
   * @param roster Roster to set.
   */
  public void setRoster(KinshipRoster roster)
  {
    _roster=roster;
  }

  @Override
  public String toString()
  {
    return getName();
  }
}
