package delta.games.lotro.character.status.housing.io;

import java.io.File;

import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.character.status.housing.AccountHousingData;
import delta.games.lotro.character.status.housing.House;
import delta.games.lotro.character.status.housing.HouseAddress;
import delta.games.lotro.character.status.housing.HouseIdentifier;
import delta.games.lotro.character.status.housing.io.xml.HousingStatusXMLWriter;
import delta.games.lotro.data.UserDataManager;

/**
 * I/O utilities for housing status.
 * @author DAM
 */
public class HousingStatusIO
{
  /**
   * Save an account/server-wide housing data.
   * @param accountServer Account/server.
   * @param data Data to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean saveAccountHousingData(AccountOnServer accountServer, AccountHousingData data)
  {
    File rootDir=accountServer.getRootDir();
    File toFile=getAccountHousingFile(rootDir);
    HousingStatusXMLWriter writer=new HousingStatusXMLWriter();
    boolean ok=writer.writeAccountHousingData(toFile,data);
    return ok;
  }

  /**
   * Save a house.
   * @param house House to save.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean saveHouse(House house)
  {
    File toFile=getHouseFile(house.getIdentifier());
    HousingStatusXMLWriter writer=new HousingStatusXMLWriter();
    boolean ok=writer.writeHouseContents(toFile,house);
    return ok;
  }

  /**
   * Get the path for an account housing data file.
   * @param rootDir Root directory for the account.
   * @return A data file.
   */
  public static File getAccountHousingFile(File rootDir)
  {
    File housingFile=new File(rootDir,"housing.xml");
    return housingFile;
  }

  /**
   * Get the file for a house.
   * @param id House identifier.
   * @return A data file.
   */
  public static File getHouseFile(HouseIdentifier id)
  {
    UserDataManager dataMgr=UserDataManager.getInstance();
    File housesDir=dataMgr.getHousesDir();
    String serverName=id.getServer();
    File serverDir=new File(housesDir,serverName);
    HouseAddress address=id.getAddress();
    int neighborhoodID=address.getNeighborhoodID();
    int houseID=address.getHouseID();
    String filename="house-"+neighborhoodID+"-"+houseID+".xml";
    return new File(serverDir,filename);
  }
}
