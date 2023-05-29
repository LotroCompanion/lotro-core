package delta.games.lotro.account.status.friends.io.xml;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.account.status.friends.FriendsManager;

/**
 * I/O utilities for friends.
 * @author DAM
 */
public class FriendsIO
{
  /**
   * Load friends from a file.
   * @param friendsFile Friends file.
   * @return A friends manager or <code>null</code> if an error occurred.
   */
  public static FriendsManager loadFriends(File friendsFile)
  {
    FriendsManager ret=null;
    if (friendsFile.exists())
    {
      FriendsXMLParser parser=new FriendsXMLParser();
      ret=parser.parseXML(friendsFile);
    }
    return ret;
  }

  /**
   * Save a friends manager to file.
   * @param friendsFile File to write.
   * @param friendsMgr Friends to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean saveFriends(File friendsFile, FriendsManager friendsMgr)
  {
    FriendsXMLWriter writer=new FriendsXMLWriter();
    boolean ok=writer.write(friendsFile,friendsMgr,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Build a friends file.
   * @param rootDir Root directory for an account/server.
   * @return A friends file.
   */
  public static File getFriendsFile(File rootDir)
  {
    File friendsFile=new File(rootDir,"friends.xml");
    return friendsFile;
  }
}
