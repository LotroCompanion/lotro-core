package delta.games.lotro.character.log;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.log.io.xml.CharacterLogXMLParser;
import delta.games.lotro.character.log.io.xml.CharacterLogXMLWriter;
import delta.games.lotro.utils.Formats;

/**
 * Manages log files for a single toon.
 * @author DAM
 */
public class CharacterLogsManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(CharacterLogsManager.class);

  private CharacterFile _toon;

  /**
   * Constructor.
   * @param toon Toon to manage.
   */
  public CharacterLogsManager(CharacterFile toon)
  {
    _toon=toon;
    pruneLogFiles();
  }

  /**
   * Get the most recent character log.
   * @return A character log or <code>null</code> if not found or error.
   */
  public CharacterLog getLastLog()
  {
    CharacterLog log=null;
    File lastLog=getLastLogFile();
    if (lastLog!=null)
    {
      CharacterLogXMLParser xmlLogParser=new CharacterLogXMLParser();
      log=xmlLogParser.parseXML(lastLog);
    }
    return log;
  }

  /**
   * Prune character log files.
   */
  public void pruneLogFiles()
  {
    int nbMax=1;
    File[] files=getLogFiles();
    if ((files!=null) && (files.length>nbMax))
    {
      int nbToRemove=files.length-nbMax;
      for(int i=0;i<nbToRemove;i++)
      {
        File toRemove=files[i];
        boolean ok=toRemove.delete();
        if (!ok)
        {
          LOGGER.warn("Cannot delete file [{}]!",toRemove);
        }
      }
    }
  }

  /**
   * Get the most recent log file.
   * @return a file or <code>null</code> if there is no log file for this toon.
   */
  public File getLastLogFile()
  {
    File lastLog=null;
    File[] files=getLogFiles();
    if ((files!=null) && (files.length>0))
    {
      lastLog=files[files.length-1];
    }
    return lastLog;
  }

  private File[] getLogFiles()
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
          return ((name.startsWith("log ")) && (name.endsWith(".xml")));
        }
      };
      files=characterDir.listFiles(filter);
    }
    if ((files!=null) && (files.length>0))
    {
      Arrays.sort(files);
    }
    return files;
  }

  /**
   * Indicates if this character has a log file.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasLog()
  {
    File lastLog=getLastLogFile();
    return (lastLog!=null);
  }

  /**
   * Write a new log file for this toon.
   * @param log Log to write.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public boolean writeNewLog(CharacterLog log)
  {
    boolean ret=true;
    File logFile=getNewLogFile();
    File parentFile=logFile.getParentFile();
    if (!parentFile.exists())
    {
      ret=parentFile.mkdirs();
      if (!ret)
      {
        LOGGER.error("Cannot create directory [{}]!",parentFile);
      }
    }
    if (ret)
    {
      CharacterLogXMLWriter writer=new CharacterLogXMLWriter();
      ret=writer.write(logFile,log,EncodingNames.UTF_8);
    }
    return ret;
  }

  private File getNewLogFile()
  {
    SimpleDateFormat sdf=new SimpleDateFormat(Formats.FILE_DATE_PATTERN);
    String filename="log "+sdf.format(new Date())+".xml";
    File characterDir=_toon.getRootDir();
    File logFile=new File(characterDir,filename);
    return logFile;
  }

  /**
   * Get a date from a filename.
   * @param filename Filename to use.
   * @return A date or <code>null</code> if it cannot be parsed!
   */
  public static Date getDateFromFilename(String filename)
  {
    Date ret=null;
    if ((filename.startsWith("log ")) && (filename.endsWith(".xml")))
    {
      filename=filename.substring(4,filename.length()-4);
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
}
