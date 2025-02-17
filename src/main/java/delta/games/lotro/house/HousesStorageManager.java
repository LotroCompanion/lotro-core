package delta.games.lotro.house;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import delta.common.utils.files.comparator.FileNameComparator;
import delta.common.utils.files.filter.ExtensionPredicate;
import delta.common.utils.files.filter.FileTypePredicate;
import delta.games.lotro.character.status.housing.House;
import delta.games.lotro.character.status.housing.HouseIdentifier;
import delta.games.lotro.character.status.housing.io.HousingStatusIO;
import delta.games.lotro.data.UserDataManager;

/**
 * Storage manager for all houses.
 * @author DAM
 */
public class HousesStorageManager
{
  /**
   * Root directory for houses files.
   */
  private File _housesDir;

  /**
   * Constructor.
   */
  public HousesStorageManager()
  {
    UserDataManager dataMgr=UserDataManager.getInstance();
    _housesDir=dataMgr.getHousesDir();
    _housesDir.mkdirs();
  }

  /**
   * Get a list of all managed houses.
   * @return a list of houses.
   */
  public List<HouseEntry> getAllHouses()
  {
    List<HouseEntry> houses=new ArrayList<HouseEntry>();
    FileFilter dirFilter=new FileTypePredicate(FileTypePredicate.DIRECTORY);
    File[] serverDirs=_housesDir.listFiles(dirFilter);
    if (serverDirs!=null)
    {
      // Sort files by name to always get servers in the same order.
      Arrays.sort(serverDirs,new FileNameComparator());
      for(File serverDir : serverDirs)
      {
        FileFilter houseFileFilter=new ExtensionPredicate("xml");
        File[] houseFiles=serverDir.listFiles(houseFileFilter);
        if (houseFiles!=null)
        {
          for(File houseFile : houseFiles)
          {
            HouseIdentifier houseIdentifier=HousingStatusIO.buildHouseIdentifierFromFile(houseFile);
            HouseEntry entry=new HouseEntry(houseIdentifier);
            houses.add(entry);
          }
        }
      }
    }
    return houses;
  }

  /**
   * Save a house.
   * @param house House to save.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean saveHouse(House house)
  {
    return HousingStatusIO.saveHouse(house);
  }

  /**
   * Remove a house.
   * @param id Identifier of the house to remove.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean removeHouse(HouseIdentifier id)
  {
    File file=HousingStatusIO.getHouseFile(id);
    if (file.exists())
    {
      return file.delete();
    }
    return true;
  }
}
