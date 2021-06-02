package delta.games.lotro.kinship;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.kinship.io.xml.KinshipSummaryXMLParser;
import delta.games.lotro.kinship.io.xml.KinshipSummaryXMLWriter;

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
   * @param rootDir Root directory for all kinship files.
   */
  public Kinship(File rootDir)
  {
    _rootDir=rootDir;
    _summary=null;
    _roster=new KinshipRoster();
  }

  /**
   * Get the summary for this kinship.
   * @return a summary or <code>null</code> if it could not be built!
   */
  public KinshipSummary getSummary()
  {
    if (_summary==null)
    {
      _summary=loadSummary();
    }
    if (_summary==null)
    {
      _summary=new KinshipSummary();
    }
    return _summary;
  }

  private KinshipSummary loadSummary()
  {
    KinshipSummary summary=null;
    File summaryFile=getSummaryFile();
    if (summaryFile.exists())
    {
      KinshipSummaryXMLParser parser=new KinshipSummaryXMLParser();
      summary=parser.parseXML(summaryFile);
    }
    return summary;
  }

  /**
   * Save summary to file.
   * @param summary Summary to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean saveSummary(KinshipSummary summary)
  {
    KinshipSummaryXMLWriter writer=new KinshipSummaryXMLWriter();
    File summaryFile=getSummaryFile();
    boolean ok=writer.write(summaryFile,summary,EncodingNames.UTF_8);
    return ok;
  }

  private File getSummaryFile()
  {
    File summaryFile=new File(_rootDir,"summary.xml");
    return summaryFile;
  }

  /**
   * Get the name of this kinship.
   * @return a kinhip name.
   */
  public String getName()
  {
    KinshipSummary summary=getSummary();
    return (_summary!=null)?summary.getName():null;
  }

  /**
   * Get the root directory of the kinship's storage. 
   * @return a root directory.
   */
  public File getRootDir()
  {
    return _rootDir;
  }

  /**
   * Get the kinship roster.
   * @return the kinship roster.
   */
  public KinshipRoster getRoster()
  {
    return _roster;
  }

  @Override
  public String toString()
  {
    return getName();
  }
}
