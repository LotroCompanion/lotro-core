package delta.games.lotro.character;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.files.FilesDeleter;
import delta.common.utils.files.filter.FileTypePredicate;
import delta.games.lotro.LotroCoreConfig;

/**
 * Storage manage for all characters.
 * @author DAM
 */
public class CharactersStorageManager
{
  private File _toonsDir;

  /**
   * Constructor.
   */
  public CharactersStorageManager()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    _toonsDir=cfg.getToonsDir();
    init();
  }

  /**
   * Create a new toon.
   * @param initialData Character initial data.
   * @return A new file or <code>null</code> if a problem occured.
   */
  public CharacterFile newToon(CharacterData initialData)
  {
    File newDir=getNewToonDirectory();
    newDir.mkdirs();
    CharacterFile file=new CharacterFile(newDir);
    CharacterSummary summary=initialData.getSummary();
    boolean ok=file.saveSummary(summary);
    if (!ok)
    {
      return null;
    }
    ok=file.getInfosManager().writeNewCharacterData(initialData);
    if (!ok)
    {
      return null;
    }
    return file;
  }


  /**
   * Remove a toon.
   * It removes all data for this toon.
   * @param toon Targeted toon.
   */
  public void removeToon(CharacterFile toon)
  {
    File toonDir=toon.getRootDir();
    if (toonDir.exists())
    {
      FilesDeleter deleter=new FilesDeleter(toonDir,null,true);
      deleter.doIt();
    }
  }

  private File getNewToonDirectory()
  {
    File ret=null;
    int index=0;
    while (true)
    {
      String indexStr=String.format("%1$05d",Integer.valueOf(index));
      File dataFile=new File(_toonsDir,"toon-"+indexStr);
      if (!dataFile.exists())
      {
        ret=dataFile;
        break;
      }
      index++;
    }
    return ret;
  }

  private void init()
  {
    // Perform migration from old server/toon structure, if needed (LC<=3.0)
    oldDataMigration();
  }

  /**
   * Get a list of all managed toons, sorted by name.
   * @return a list of toons.
   */
  public List<CharacterFile> getAllToons()
  {
    List<CharacterFile> toons=new ArrayList<CharacterFile>();
    FileFilter fileFilter=new FileTypePredicate(FileTypePredicate.DIRECTORY);
    File[] toonDirs=_toonsDir.listFiles(fileFilter);
    if (toonDirs!=null)
    {
      for(File toonDir : toonDirs)
      {
        String dirName=toonDir.getName();
        if (dirName.startsWith("toon-"))
        {
          CharacterFile toon=new CharacterFile(toonDir);
          CharacterSummary summary=toon.getSummary();
          if (summary!=null)
          {
            toons.add(toon);
            System.out.println("Loaded: "+summary);
          }
        }
      }
    }
    return toons;
  }

  private void oldDataMigration()
  {
    FileFilter fileFilter=new FileTypePredicate(FileTypePredicate.DIRECTORY);
    File[] serverDirs=_toonsDir.listFiles(fileFilter);
    if (serverDirs!=null)
    {
      for(File serverDir : serverDirs)
      {
        String serverName=serverDir.getName();
        if (!serverName.startsWith("toon-"))
        {
          oldServerDirMigration(serverDir);
          serverDir.delete();
        }
      }
    }
  }

  private void oldServerDirMigration(File serverDir)
  {
    FileFilter fileFilter=new FileTypePredicate(FileTypePredicate.DIRECTORY);
    File[] toonDirs=serverDir.listFiles(fileFilter);
    if (toonDirs!=null)
    {
      for(File toonDir : toonDirs)
      {
        File[] toonFiles=toonDir.listFiles();
        if ((toonFiles!=null) && (toonFiles.length>0))
        {
          File newDir=getNewToonDirectory();
          toonDir.renameTo(newDir);
        }
      }
    }
  }
}
