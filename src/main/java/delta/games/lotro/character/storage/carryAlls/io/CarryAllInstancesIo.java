package delta.games.lotro.character.storage.carryAlls.io;

import java.io.File;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.storage.carryAlls.CarryAllInstance;
import delta.games.lotro.character.storage.carryAlls.io.xml.CarryAllInstanceXMLParser;
import delta.games.lotro.character.storage.carryAlls.io.xml.CarryAllInstanceXMLWriter;
import delta.games.lotro.common.id.InternalGameId;

/**
 * I/O methods for carry-all instances.
 * @author DAM
 */
public class CarryAllInstancesIo
{
  /**
   * Load a carry-all instance..
   * @param account Targeted account.
   * @param server Targeted server.
   * @param id Carry-all ID.
   * @return A carry-all instance or <code>null</code> if not found.
   */
  public static CarryAllInstance load(Account account, String server, InternalGameId id)
  {
    File fromFile=getCarryAllFile(account,server,id);
    CarryAllInstance ret=null;
    if (fromFile.exists())
    {
      ret=CarryAllInstanceXMLParser.parseXML(fromFile);
      if (ret!=null)
      {
        ret.setId(id);
      }
    }
    return ret;
  }

  /**
   * Save a carry-all.
   * @param account Targeted account.
   * @param server Targeted server.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(Account account, String server, CarryAllInstance data)
  {
    File toFile=getCarryAllFile(account,server,data.getId());
    boolean ok=CarryAllInstanceXMLWriter.write(toFile,data);
    return ok;
  }

  private static File getCarryAllFile(Account account, String server, InternalGameId id)
  {
    File rootDir=account.getRootDir();
    File serverDir=new File(rootDir,server);
    File carryAllsDir=new File(serverDir,"carry-alls");
    File carryAllFile=new File(carryAllsDir,id.asPersistedString()+".xml");
    return carryAllFile;
  }
}
