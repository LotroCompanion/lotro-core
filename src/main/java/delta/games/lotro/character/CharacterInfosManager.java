package delta.games.lotro.character;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.events.CharacterEvent;
import delta.games.lotro.character.events.CharacterEventType;
import delta.games.lotro.character.io.xml.CharacterDataIO;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.utils.Formats;
import delta.games.lotro.utils.NumericUtils;
import delta.games.lotro.utils.events.EventsManager;

/**
 * Manages info files for a single toon.
 * @author DAM
 */
public class CharacterInfosManager
{
  private static final Logger LOGGER=Logger.getLogger(CharacterInfosManager.class);

  private CharacterFile _toon;
  private CharacterData _current;
  private List<CharacterData> _datas;

  /**
   * Constructor.
   * @param toon Toon to manage.
   */
  public CharacterInfosManager(CharacterFile toon)
  {
    _toon=toon;
    _datas=new ArrayList<CharacterData>();
    init();
  }

  /**
   * Initialize data.
   */
  private void init()
  {
    oldDataMigration();
  }

  /**
   * Get the number of data files.
   * @return a number of data files.
   */
  public int getDataCount()
  {
    return _datas.size();
  }

  /**
   * Get the character data at the given index.
   * @param index Index of data to get, starting at 0.
   * @return A character data.
   */
  public CharacterData getData(int index)
  {
    return _datas.get(index);
  }

  /**
   * Release as much memory as possible.
   */
  public void gc()
  {
    _datas.clear();
  }

  /**
   * Ensure that internal data are synchronized with persisted data.
   */
  public void sync()
  {
    loadAll();
  }

  private void loadAll()
  {
    loadAllData();
    setSummary();
  }

  private void loadAllData()
  {
    if (!_datas.isEmpty())
    {
      return;
    }
    File[] dataFiles=getDataFiles();
    if (dataFiles!=null)
    {
      for(File dataFile : dataFiles)
      {
        CharacterData data=CharacterDataIO.getCharacterDescription(dataFile);
        if (data!=null)
        {
          _datas.add(data);
        }
      }
    }
  }

  /**
   * Get the current data for this character.
   * @return a character description.
   */
  public CharacterData getCurrentData()
  {
    CharacterData ret=_current;
    if (ret==null)
    {
      ret=loadCurrentData();
      _current=ret;
    }
    if (ret==null)
    {
      ret=getLastCharacterDescription();
      _current=ret;
    }
    return ret;
  }

  private CharacterData loadCurrentData()
  {
    CharacterData ret=null;
    File dataFile=getCurrentDataFile();
    if (dataFile.exists())
    {
      ret=CharacterDataIO.getCharacterDescription(dataFile);
    }
    return ret;
  }

  /**
   * Update the current character data.
   * @param data Character data to use.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public boolean updateCurrentData(CharacterData data)
  {
    File dataFile=getCurrentDataFile();
    boolean ret=CharacterDataIO.saveInfo(dataFile,data);
    if (ret)
    {
      data.setFile(dataFile);
      _current=data;
    }
    if (ret)
    {
      // Notify
      CharacterEvent event=new CharacterEvent(CharacterEventType.CURRENT_CHARACTER_DATA_UPDATED,_toon,data);
      EventsManager.invokeEvent(event);
    }
    return ret;
  }

  private File getCurrentDataFile()
  {
    File characterDir=_toon.getRootDir();
    File ret=new File(characterDir,"current.xml");
    return ret;
  }

  /**
   * Apply the parent summary to all loaded data.
   */
  public void setSummary()
  {
    CharacterSummary characterSummary=_toon.getSummary();
    for(CharacterData data : _datas)
    {
      data.getSummary().setSummary(characterSummary);
    }
  }

  private File[] getDataFiles()
  {
    File[] files=null;
    File characterDir=_toon.getRootDir();
    if (characterDir.exists())
    {
      FileFilter filter=new FileFilter()
      {
        public boolean accept(File pathname)
        {
          String name=pathname.getName();
          return ((name.startsWith("data-")) && (name.endsWith(".xml")));
        }
      };
      files=characterDir.listFiles(filter);
      if ((files!=null) && (files.length>0))
      {
        Arrays.sort(files);
      }
    }
    return files;
  }

  /**
   * Get the most recent character description.
   * @return A character description or <code>null</code> if not found or error.
   */
  public CharacterData getLastCharacterDescription()
  {
    if (_datas.isEmpty())
    {
      loadAllData();
    }
    CharacterData c=null;
    Long latestDate=null;
    for(CharacterData data : _datas)
    {
      Long date=data.getDate();
      if (date!=null)
      {
        if (c==null)
        {
          c=data;
        }
        if ((latestDate==null) || (latestDate.longValue()<date.longValue()))
        {
          latestDate=date;
          c=data;
        }
      }
    }
    return c;
  }

  /**
   * Get all the available character data files.
   * @return an array of files.
   */
  private File[] getInfoFiles()
  {
    File[] files=null;
    File characterDir=_toon.getRootDir();
    if (characterDir.exists())
    {
      FileFilter filter=new FileFilter()
      {
        public boolean accept(File pathname)
        {
          String name=pathname.getName();
          return ((name.startsWith("info ")) && (name.endsWith(".xml")));
        }
      };
      files=characterDir.listFiles(filter);
      if ((files!=null) && (files.length>0))
      {
        Arrays.sort(files);
      }
    }
    return files;
  }

  /**
   * Perform migration from old data file (LC<=3.0).
   */
  public void oldDataMigration()
  {
    File[] oldInfoFiles=getInfoFiles();
    if (oldInfoFiles!=null)
    {
      for(File oldInfoFile : oldInfoFiles)
      {
        CharacterData data=CharacterDataIO.getCharacterDescription(oldInfoFile);
        if (data!=null)
        {
          File newFile=getNewInfoFile();
          updateOldCharacterData(oldInfoFile,data);
          CharacterDataIO.saveInfo(newFile,data);
          boolean ok=oldInfoFile.delete();
          if (!ok)
          {
            LOGGER.warn("Could not delete file: "+oldInfoFile);
          }
        }
      }
    }
  }

  private void updateOldCharacterData(File oldFile, CharacterData data)
  {
    BasicStatsSet stats=data.getStats();
    for(StatDescription statKey : stats.getStats())
    {
      Number value=stats.getStat(statKey);
      Number newValue=NumericUtils.multiply(value,Integer.valueOf(100));
      stats.setStat(statKey,newValue);
    }
    Date date=getDateFromFilename(oldFile.getName());
    if (date!=null)
    {
      String dateTimeStr=Formats.getDateTimeString(date);
      data.setShortDescription(dateTimeStr);
      String description="Imported from mylotro. Generated: "+dateTimeStr;
      data.setDescription(description);
      data.setDate(Long.valueOf(date.getTime()));
    }
    else
    {
      long fileDate=oldFile.lastModified();
      data.setDate(Long.valueOf(fileDate));
    }
  }

  /**
   * Write a new info file for this toon.
   * @param data Character info to write.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public boolean writeNewCharacterData(CharacterData data)
  {
    sync();
    File dataFile=getNewInfoFile();
    boolean ret=CharacterDataIO.saveInfo(dataFile,data);
    if (ret)
    {
      data.setFile(dataFile);
      _datas.add(data);
    }
    if (ret)
    {
      // Notify
      CharacterEvent event=new CharacterEvent(CharacterEventType.CHARACTER_DATA_ADDED,_toon,data);
      EventsManager.invokeEvent(event);
    }
    return ret;
  }

  /**
   * Delete a character data.
   * @param data Targeted data.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean remove(CharacterData data)
  {
    File file=data.getFile();
    boolean ok=file.delete();
    if (ok)
    {
      _datas.remove(data);
    }
    return ok;
  }

  private File getNewInfoFile()
  {
    File ret=null;
    File characterDir=_toon.getRootDir();
    int index=0;
    while (true)
    {
      String indexStr=String.format("%1$05d",Integer.valueOf(index));
      File dataFile=new File(characterDir,"data-"+indexStr+".xml");
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
   * Get a date from a filename.
   * @param filename Filename to use.
   * @return A date or <code>null</code> if it cannot be parsed!
   */
  private Date getDateFromFilename(String filename)
  {
    Date ret=null;
    if ((filename.startsWith("info ")) && (filename.endsWith(".xml")))
    {
      filename=filename.substring(5,filename.length()-4);
      SimpleDateFormat sdf=new SimpleDateFormat(Formats.FILE_DATE_PATTERN);
      try
      {
        ret=sdf.parse(filename);
      }
      catch(ParseException pe)
      {
        LOGGER.error("Cannot parse filename ["+filename+"]!",pe);
      }
    }
    return ret;
  }

  /**
   * Build a summary from newest character data.
   * @return a summary or <code>null</code> if no data.
   */
  public CharacterSummary buildSummaryFromNewestData()
  {
    loadAllData();
    CharacterSummary summary=null;
    CharacterData data=getLastCharacterDescription();
    if (data!=null)
    {
      summary=new CharacterSummary(data.getSummary().getSummary());
    }
    return summary;
  }
}
