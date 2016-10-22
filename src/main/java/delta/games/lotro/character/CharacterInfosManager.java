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

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.io.xml.CharacterXMLParser;
import delta.games.lotro.character.io.xml.CharacterXMLWriter;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;
import delta.games.lotro.utils.Formats;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Manages info files for a single toon.
 * @author DAM
 */
public class CharacterInfosManager
{
  private static final Logger _logger=LotroLoggers.getCharacterLogger();

  private CharacterFile _toon;
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
   * Ensure that internal data are synchronized with persisted data.
   */
  public void sync()
  {
    loadAll();
  }

  private void loadAll()
  {
    if (_datas.size()>0)
    {
      return;
    }
    File[] dataFiles=getDataFiles();
    if (dataFiles!=null)
    {
      for(File dataFile : dataFiles)
      {
        CharacterData data=getCharacterDescription(dataFile);
        _datas.add(data);
      }
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
          if ((name.startsWith("data-")) && (name.endsWith(".xml")))
          {
            return true;
          }
          return false;
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
    if (_datas.size()==0)
    {
      loadAll();
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
   * Get the character data for a given file.
   * @param infoFile File to read.
   * @return A character data or <code>null</code> if a problem occurs.
   */
  public CharacterData getCharacterDescription(File infoFile)
  {
    CharacterXMLParser xmlInfoParser=new CharacterXMLParser();
    CharacterData c=xmlInfoParser.parseXML(infoFile);
    if (c!=null)
    {
      Date date=getDateFromFilename(infoFile.getName());
      if (date!=null)
      {
        c.setDate(Long.valueOf(date.getTime()));
      }
    }
    return c;
  }

  /**
   * Get all the available character data files.
   * @return an array of files.
   */
  public File[] getInfoFiles()
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
          if ((name.startsWith("info ")) && (name.endsWith(".xml")))
          {
            return true;
          }
          return false;
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
        CharacterData data=getCharacterDescription(oldInfoFile);
        if (data!=null)
        {
          File newFile=getNewInfoFile();
          updateOldCharacterData(oldInfoFile,data);
          saveInfo(newFile,data);
          oldInfoFile.delete();
        }
      }
    }
  }

  private void updateOldCharacterData(File oldFile, CharacterData data)
  {
    BasicStatsSet stats=data.getStats();
    for(STAT statKey : stats.getStats())
    {
      FixedDecimalsInteger value=stats.getStat(statKey);
      FixedDecimalsInteger newValue=value.multiply(100);
      stats.setStat(statKey,newValue);
    }
    Date date=getDateFromFilename(oldFile.getName());
    String dateTimeStr=Formats.getDateTimeString(date);
    data.setShortDescription(dateTimeStr);
    String description="Imported from mylotro. Generated: "+dateTimeStr;
    data.setDescription(description);
  }

  /**
   * Write a new info file for this toon.
   * @param info Character info to write.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public boolean writeNewInfo(CharacterData info)
  {
    File infoFile=getNewInfoFile();
    boolean ret=saveInfo(infoFile,info);
    if (ret)
    {
      _datas.add(info);
    }
    return ret;
  }

  /**
   * Write a new info file for this toon.
   * @param toFile File to write to.
   * @param info Character info to write.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public boolean saveInfo(File toFile, CharacterData info)
  {
    boolean ret=true;
    File parentFile=toFile.getParentFile();
    if (!parentFile.exists())
    {
      ret=parentFile.mkdirs();
      if (!ret)
      {
        _logger.error("Cannot create directory ["+parentFile+"]!");
      }
    }
    if (ret)
    {
      CharacterXMLWriter writer=new CharacterXMLWriter();
      ret=writer.write(toFile,info,EncodingNames.UTF_8);
    }
    return ret;
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
   * Get the date of the lastest info file.
   * @return A date or <code>null</code> if no info file.
   */
  public Date getLastInfoDate()
  {
    Date ret=null;
    CharacterData lastData=getLastCharacterDescription();
    if (lastData!=null)
    {
      ret=new Date(lastData.getDate().longValue());
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
        _logger.error("Cannot parse filename ["+filename+"]!",pe);
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
    loadAll();
    CharacterSummary summary=null;
    CharacterData data=getLastCharacterDescription();
    if (data!=null)
    {
      summary=new CharacterSummary(data.getSummary());
    }
    return summary;
  }
}
