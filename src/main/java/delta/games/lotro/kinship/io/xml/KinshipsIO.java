package delta.games.lotro.kinship.io.xml;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.kinship.Kinship;
import delta.games.lotro.kinship.KinshipRoster;
import delta.games.lotro.kinship.KinshipSummary;

/**
 * I/O utilities for kinships.
 * @author DAM
 */
public class KinshipsIO
{
  /**
   * Fully load a kinship.
   * @param kinship Kinship to load.
   */
  public static void fullyLoadKinship(Kinship kinship)
  {
    File rosterFile=KinshipsIO.getRosterFile(kinship.getRootDir());
    KinshipRoster roster=loadRoster(rosterFile);
    kinship.setRoster(roster);
  }

  /**
   * Load a kinship summary from a file.
   * @param summaryFile Summary file.
   * @return A summary or <code>null</code> if an error occurred.
   */
  public static KinshipSummary loadSummary(File summaryFile)
  {
    KinshipSummary ret=null;
    if (summaryFile.exists())
    {
      KinshipSummaryXMLParser parser=new KinshipSummaryXMLParser();
      ret=parser.parseXML(summaryFile);
    }
    return ret;
  }

  /**
   * Save a kinship summary to file.
   * @param summaryFile Summary file to write.
   * @param summary Summary to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean saveSummary(File summaryFile, KinshipSummary summary)
  {
    KinshipSummaryXMLWriter writer=new KinshipSummaryXMLWriter();
    boolean ok=writer.write(summaryFile,summary,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Build a summary file.
   * @param rootDir Root directory for a kinship.
   * @return A kinship summary file.
   */
  public static File getSummaryFile(File rootDir)
  {
    File summaryFile=new File(rootDir,"summary.xml");
    return summaryFile;
  }

  /**
   * Load a kinship roster from a file.
   * @param rosterFile Roster file.
   * @return A roster or <code>null</code> if an error occurred.
   */
  public static KinshipRoster loadRoster(File rosterFile)
  {
    KinshipRoster ret=null;
    if (rosterFile.exists())
    {
      KinshipRosterXMLParser parser=new KinshipRosterXMLParser();
      ret=parser.parseXML(rosterFile);
    }
    return ret;
  }

  /**
   * Save a kinship roster to file.
   * @param rosterFile Roster file to write.
   * @param roster Roster to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean saveRoster(File rosterFile, KinshipRoster roster)
  {
    KinshipRosterXMLWriter writer=new KinshipRosterXMLWriter();
    boolean ok=writer.write(rosterFile,roster,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Build a roster file.
   * @param rootDir Root directory for a kinship.
   * @return A kinship roster file.
   */
  public static File getRosterFile(File rootDir)
  {
    File rosterFile=new File(rootDir,"roster.xml");
    return rosterFile;
  }
}
