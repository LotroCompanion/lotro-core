package delta.games.lotro.account.status.rewardsTrack.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.account.status.rewardsTrack.RewardsTracksStatusManager;
import delta.games.lotro.account.status.rewardsTrack.io.xml.RewardsTracksStatusXMLParser;
import delta.games.lotro.account.status.rewardsTrack.io.xml.RewardsTracksStatusXMLWriter;

/**
 * I/O methods for rewards tracks status.
 * @author DAM
 */
public class RewardsTracksStatusIo
{
  /**
   * Load the rewards tracks status for an account/server.
   * @param accountOnServer Targeted account/server.
   * @return A rewards tracks status manager.
   */
  public static RewardsTracksStatusManager load(AccountOnServer accountOnServer)
  {
    File fromFile=getStatusFile(accountOnServer);
    RewardsTracksStatusManager status=null;
    if (fromFile.exists())
    {
      RewardsTracksStatusXMLParser parser=new RewardsTracksStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new RewardsTracksStatusManager();
      save(accountOnServer,status);
    }
    return status;
  }

  /**
   * Save the rewards tracks status for an account/server.
   * @param accountOnServer Targeted account/server.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(AccountOnServer accountOnServer, RewardsTracksStatusManager status)
  {
    File toFile=getStatusFile(accountOnServer);
    RewardsTracksStatusXMLWriter writer=new RewardsTracksStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(AccountOnServer accountOnServer)
  {
    File rootDir=accountOnServer.getRootDir();
    File statusFile=new File(rootDir,"rewardsTracks.xml");
    return statusFile;
  }
}
