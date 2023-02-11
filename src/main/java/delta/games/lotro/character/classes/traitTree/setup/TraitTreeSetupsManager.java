package delta.games.lotro.character.classes.traitTree.setup;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.character.classes.traitTree.setup.events.TraitTreeSetupEvent;
import delta.games.lotro.character.classes.traitTree.setup.events.TraitTreeSetupEventType;
import delta.games.lotro.character.classes.traitTree.setup.io.xml.TraitTreeSetupsIO;
import delta.games.lotro.common.enums.TraitTreeType;
import delta.games.lotro.data.UserDataManager;
import delta.games.lotro.utils.events.EventsManager;

/**
 * Manages all trait tree setups.
 * @author DAM
 */
public class TraitTreeSetupsManager
{
  private static final String SEED="traitTreeSetup-";

  private static TraitTreeSetupsManager _instance=new TraitTreeSetupsManager();

  private File _rootDir;
  private List<TraitTreeSetup> _datas;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TraitTreeSetupsManager getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  private TraitTreeSetupsManager()
  {
    _datas=new ArrayList<TraitTreeSetup>();
    UserDataManager dataMgr=UserDataManager.getInstance();
    _rootDir=dataMgr.getTraitTreeSetupDir();
    _rootDir.mkdirs();
    init();
  }

  /**
   * Initialize data.
   */
  private void init()
  {
    loadAll();
  }

  /**
   * Get the setups for a given trait tree type.
   * @param type Trait tree type.
   * @return A possibly empty but not <code>null</code> list of trait tree setups.
   */
  public List<TraitTreeSetup> getSetups(TraitTreeType type)
  {
    List<TraitTreeSetup> ret=new ArrayList<TraitTreeSetup>();
    for(TraitTreeSetup setup : _datas)
    {
      TraitTreeType setupType=setup.getType();
      if (setupType==type)
      {
        ret.add(setup);
      }
    }
    return ret;
  }

  /**
   * Get a list of all trait tree setups.
   * @return an unmodifiable list.
   */
  public List<TraitTreeSetup> getAll()
  {
    return Collections.unmodifiableList(_datas);
  }

  private void loadAll()
  {
    File[] dataFiles=getDataFiles();
    if (dataFiles!=null)
    {
      for(File dataFile : dataFiles)
      {
        TraitTreeSetup data=TraitTreeSetupsIO.getTraitTreeSetup(dataFile);
        if (data!=null)
        {
          _datas.add(data);
        }
      }
    }
  }

  private File[] getDataFiles()
  {
    File[] files=null;
    if (_rootDir.exists())
    {
      FileFilter filter=new FileFilter()
      {
        public boolean accept(File pathname)
        {
          String name=pathname.getName();
          if ((name.startsWith(SEED)) && (name.endsWith(".xml")))
          {
            return true;
          }
          return false;
        }
      };
      files=_rootDir.listFiles(filter);
      if ((files!=null) && (files.length>0))
      {
        Arrays.sort(files);
      }
    }
    return files;
  }

  /**
   * Write a new data file for the given setup.
   * @param data Setup to write.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public boolean writeNewDataFile(TraitTreeSetup data)
  {
    File dataFile=getNewDataFile();
    boolean ret=TraitTreeSetupsIO.saveInfo(dataFile,data);
    if (ret)
    {
      data.setFile(dataFile);
      _datas.add(data);
    }
    if (ret)
    {
      // Notify
      TraitTreeSetupEvent event=new TraitTreeSetupEvent(TraitTreeSetupEventType.SETUP_ADDED,data);
      EventsManager.invokeEvent(event);
    }
    return ret;
  }

  /**
   * Delete a trait tree setup data.
   * @param data Targeted data.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  public boolean remove(TraitTreeSetup data)
  {
    File file=data.getFile();
    boolean ok=true;
    if (file.exists())
    {
      ok=file.delete();
    }
    if (ok)
    {
      _datas.remove(data);
      // Notify?
    }
    return ok;
  }

  private File getNewDataFile()
  {
    File ret=null;
    int index=0;
    while (true)
    {
      String indexStr=String.format("%1$05d",Integer.valueOf(index));
      File dataFile=new File(_rootDir,SEED+indexStr+".xml");
      if (!dataFile.exists())
      {
        ret=dataFile;
        break;
      }
      index++;
    }
    return ret;
  }
}
