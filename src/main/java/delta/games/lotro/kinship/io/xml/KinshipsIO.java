package delta.games.lotro.kinship.io.xml;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.kinship.KinshipSummary;

/**
 * I/O utilities for kinships.
 * @author DAM
 */
public class KinshipsIO
{
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
}
