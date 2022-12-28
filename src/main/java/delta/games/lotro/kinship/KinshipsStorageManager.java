package delta.games.lotro.kinship;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.files.FilesDeleter;
import delta.common.utils.files.filter.FileTypePredicate;
import delta.games.lotro.data.UserDataManager;
import delta.games.lotro.kinship.io.xml.KinshipsIO;

/**
 * Storage manager for all kinships.
 * @author DAM
 */
public class KinshipsStorageManager
{
  /**
   * Seed for kinship directory names.
   */
  private static final String KINSHIP_SEED="kinship-";
  private File _kinshipDir;

  /**
   * Constructor.
   */
  public KinshipsStorageManager()
  {
    UserDataManager dataMgr=UserDataManager.getInstance();
    _kinshipDir=dataMgr.getKinshipsDir();
    _kinshipDir.mkdirs();
  }

  /**
   * Create a new kinship.
   * @param summary Kinship summary.
   * @return A new kinship or <code>null</code> if a problem occurred.
   */
  public Kinship newKinship(KinshipSummary summary)
  {
    File newDir=getNewKinshipDirectory();
    newDir.mkdirs();
    Kinship kinship=new Kinship();
    kinship.setSummary(summary);
    kinship.setRootFile(newDir);
    File summaryFile=KinshipsIO.getSummaryFile(newDir);
    boolean ok=KinshipsIO.saveSummary(summaryFile,summary);
    if (!ok)
    {
      return null;
    }
    return kinship;
  }

  /**
   * Remove a kinship.
   * It removes all data for this kinship.
   * @param kinship Targeted kinship.
   */
  public void removeKinship(Kinship kinship)
  {
    File kinshipDir=kinship.getRootDir();
    if (kinshipDir.exists())
    {
      FilesDeleter deleter=new FilesDeleter(kinshipDir,null,true);
      deleter.doIt();
    }
  }

  private File getNewKinshipDirectory()
  {
    File ret=null;
    int index=0;
    while (true)
    {
      String indexStr=String.format("%1$05d",Integer.valueOf(index));
      File dataFile=new File(_kinshipDir,KINSHIP_SEED+indexStr);
      if (!dataFile.exists())
      {
        ret=dataFile;
        break;
      }
      index++;
    }
    return ret;
  }

  /**
   * Get a list of all managed kinships, sorted by name.
   * @return a list of kinships.
   */
  public List<Kinship> getAllKinships()
  {
    List<Kinship> kinships=new ArrayList<Kinship>();
    FileFilter fileFilter=new FileTypePredicate(FileTypePredicate.DIRECTORY);
    File[] kinshipDirs=_kinshipDir.listFiles(fileFilter);
    if (kinshipDirs!=null)
    {
      for(File kinshipDir : kinshipDirs)
      {
        String dirName=kinshipDir.getName();
        if (dirName.startsWith(KINSHIP_SEED))
        {
          Kinship kinship=new Kinship();
          kinship.setRootFile(kinshipDir);
          File summaryFile=KinshipsIO.getSummaryFile(kinshipDir);
          KinshipSummary summary=KinshipsIO.loadSummary(summaryFile);
          if (summary!=null)
          {
            kinship.setSummary(summary);
            kinships.add(kinship);
          }
        }
      }
    }
    return kinships;
  }
}
