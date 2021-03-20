package delta.games.lotro.character;

import java.io.File;
import java.util.Date;

import delta.common.utils.misc.Preferences;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.crafting.CraftingStatusManager;
import delta.games.lotro.character.details.CharacterDetails;
import delta.games.lotro.character.details.io.xml.CharacterDetailsXMLParser;
import delta.games.lotro.character.details.io.xml.CharacterDetailsXMLWriter;
import delta.games.lotro.character.io.xml.CharacterSummaryXMLParser;
import delta.games.lotro.character.io.xml.CharacterSummaryXMLWriter;
import delta.games.lotro.character.level.LevelHistory;
import delta.games.lotro.character.level.LevelHistoryComputer;
import delta.games.lotro.character.level.io.xml.LevelHistoryXMLParser;
import delta.games.lotro.character.level.io.xml.LevelHistoryXMLWriter;
import delta.games.lotro.character.log.CharacterLog;
import delta.games.lotro.character.log.CharacterLogsManager;
import delta.games.lotro.character.reputation.ReputationStatus;
import delta.games.lotro.character.reputation.io.xml.ReputationXMLParser;
import delta.games.lotro.character.reputation.io.xml.ReputationXMLWriter;
import delta.games.lotro.character.storage.stash.ItemsStash;
import delta.games.lotro.character.storage.stash.io.xml.StashXMLParser;
import delta.games.lotro.character.storage.stash.io.xml.StashXMLWriter;

/**
 * Character file description.
 * @author DAM
 */
public class CharacterFile
{
  private File _rootDir;
  private CharacterInfosManager _infosManager;
  private CharacterLogsManager _logsManager;
  private LevelHistory _levelHistory;
  private ReputationStatus _reputation;
  private CraftingStatusManager _craftingMgr;
  private CharacterDetails _details;
  private CharacterSummary _summary;
  private ItemsStash _stash;
  private Preferences _preferences;

  /**
   * Constructor.
   * @param rootDir Root directory for all toon files.
   */
  public CharacterFile(File rootDir)
  {
    _rootDir=rootDir;
    _infosManager=new CharacterInfosManager(this);
    _logsManager=new CharacterLogsManager(this);
    _craftingMgr=new CraftingStatusManager(this);
    _stash=null;
    File preferencesDir=new File(_rootDir,"preferences");
    _preferences=new Preferences(preferencesDir);
  }

  /**
   * Get the summary for this toon.
   * @return a summary or <code>null</code> if it could not be built!
   */
  public CharacterSummary getSummary()
  {
    if (_summary==null)
    {
      _summary=loadSummary();
    }
    if (_summary==null)
    {
      CharacterSummary summary=_infosManager.buildSummaryFromNewestData();
      if (summary!=null)
      {
        boolean ok=saveSummary(summary);
        if (ok)
        {
          _summary=summary;
          _infosManager.setSummary();
        }
      }
    }
    return _summary;
  }

  private CharacterSummary loadSummary()
  {
    CharacterSummary summary=null;
    File summaryFile=getSummaryFile();
    if (summaryFile.exists())
    {
      CharacterSummaryXMLParser parser=new CharacterSummaryXMLParser();
      summary=parser.parseXML(summaryFile);
    }
    return summary;
  }

  /**
   * Save summary to file.
   * @param summary Summary to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean saveSummary(CharacterSummary summary)
  {
    CharacterSummaryXMLWriter writer=new CharacterSummaryXMLWriter();
    File summaryFile=getSummaryFile();
    boolean ok=writer.writeCharacterSummaryFile(summaryFile,summary,EncodingNames.UTF_8);
    return ok;
  }

  private File getSummaryFile()
  {
    File summaryFile=new File(_rootDir,"summary.xml");
    return summaryFile;
  }

  /**
   * Get the details for this toon.
   * @return some details.
   */
  public CharacterDetails getDetails()
  {
    if (_details==null)
    {
      _details=loadDetails();
    }
    return _details;
  }

  private CharacterDetails loadDetails()
  {
    CharacterDetails details=null;
    File detailsFile=getDetailsFile();
    CharacterDetailsXMLParser parser=new CharacterDetailsXMLParser();
    details=parser.parseXML(detailsFile);
    return details;
  }

  /**
   * Save details to file.
   * @param details Details to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean saveDetails(CharacterDetails details)
  {
    CharacterDetailsXMLWriter writer=new CharacterDetailsXMLWriter();
    File detailsFile=getDetailsFile();
    boolean ok=writer.write(detailsFile,details,EncodingNames.UTF_8);
    return ok;
  }

  private File getDetailsFile()
  {
    File detailsFile=new File(_rootDir,"details.xml");
    return detailsFile;
  }

  /**
   * Get the items stash of this character.
   * @return an items stash.
   */
  public ItemsStash getStash()
  {
    if (_stash==null)
    {
      _stash=loadStash();
    }
    return _stash;
  }

  private ItemsStash loadStash()
  {
    ItemsStash stash=new ItemsStash();
    File stashFile=getStashFile();
    if (stashFile.exists())
    {
      StashXMLParser parser=new StashXMLParser();
      parser.parseXML(stashFile,stash);
    }
    return stash;
  }

  /**
   * Save stash to file.
   */
  public void saveStash()
  {
    if (_stash!=null)
    {
      File stashFile=getStashFile();
      StashXMLWriter writer=new StashXMLWriter();
      writer.write(stashFile,_stash,EncodingNames.UTF_8);
    }
  }

  private File getStashFile()
  {
    File stashFile=new File(getRootDir(),"stash.xml");
    return stashFile;
  }

  /**
   * Get the name of the character file.
   * @return a character file name.
   */
  public String getName()
  {
    CharacterSummary summary=getSummary();
    return (_summary!=null)?summary.getName():null;
  }

  /**
   * Get the server name of the character file.
   * @return a server name.
   */
  public String getServerName()
  {
    CharacterSummary summary=getSummary();
    return (_summary!=null)?summary.getServer():null;
  }

  /**
   * Get the account name of the character file.
   * @return an account name.
   */
  public String getAccountName()
  {
    CharacterSummary summary=getSummary();
    return (_summary!=null)?summary.getAccountName():null;
  }

  /**
   * Get a unique identifier for this toon file.
   * @return a string identifier.
   */
  public String getIdentifier()
  {
    return getServerName()+"#"+getName();
  }

  /**
   * Get the root directory of the character's file storage. 
   * @return a root directory.
   */
  public File getRootDir()
  {
    return _rootDir;
  }

  /**
   * Get the level history for this toon.
   * @return A level history.
   */
  public LevelHistory getLevelHistory()
  {
    if (_levelHistory==null)
    {
      _levelHistory=loadLevelHistory();
      if (_levelHistory==null)
      {
        LevelHistoryComputer c=new LevelHistoryComputer();
        _levelHistory=c.buildLevelHistory(this);
        saveLevelHistory();
      }
    }
    return _levelHistory;
  }

  private LevelHistory loadLevelHistory()
  {
    LevelHistory history=null;
    File historyFile=getLevelHistoryFile();
    if ((historyFile.exists()) && (historyFile.canRead()))
    {
      LevelHistoryXMLParser parser=new LevelHistoryXMLParser();
      history=parser.parseXML(historyFile);
    }
    return history;
  }

  /**
   * Save reputation to file.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean saveLevelHistory()
  {
    File historyFile=getLevelHistoryFile();
    LevelHistoryXMLWriter writer=new LevelHistoryXMLWriter();
    boolean ok=writer.write(historyFile,_levelHistory,EncodingNames.ISO8859_1);
    return ok;
  }

  private File getLevelHistoryFile()
  {
    return new File(_rootDir,"levels.xml");
  }

  /**
   * Get the reputation status for this toon.
   * @return A reputation status.
   */
  public ReputationStatus getReputation()
  {
    if (_reputation==null)
    {
      _reputation=loadReputation();
      if (_reputation==null)
      {
        _reputation=new ReputationStatus();
        saveReputation();
      }
    }
    return _reputation;
  }

  private ReputationStatus loadReputation()
  {
    ReputationStatus reputation=null;
    File reputationFile=getReputationFile();
    if ((reputationFile.exists()) && (reputationFile.canRead()))
    {
      ReputationXMLParser parser=new ReputationXMLParser();
      reputation=parser.parseXML(reputationFile);
    }
    return reputation;
  }

  /**
   * Revert reputation status from disk.
   */
  public void revertReputation()
  {
    _reputation=loadReputation();
  }

  /**
   * Save reputation to file.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean saveReputation()
  {
    File reputationFile=getReputationFile();
    ReputationXMLWriter writer=new ReputationXMLWriter();
    boolean ok=writer.write(reputationFile,_reputation,EncodingNames.ISO8859_1);
    return ok;
  }

  private File getReputationFile()
  {
    return new File(_rootDir,"reputation.xml");
  }

  /**
   * Get the manager for the crafting status.
   * @return the crafting status manager.
   */
  public CraftingStatusManager getCraftingMgr()
  {
    return _craftingMgr;
  }

  /**
   * Get the date of the last character update.
   * @return A date or <code>null</code> if there's no log.
   */
  public Date getLastInfoUpdate()
  {
    Date ret=_infosManager.getLastInfoDate();
    return ret;
  }

  /**
   * Get the date of the last log update.
   * @return A date or <code>null</code> if there's no log.
   */
  public Date getLastLogUpdate()
  {
    Date ret=null;
    File lastFile=_logsManager.getLastLogFile();
    if (lastFile!=null)
    {
      ret=CharacterLogsManager.getDateFromFilename(lastFile.getName());
    }
    return ret;
  }

  /**
   * Get latest character log.
   * @return A character log or <code>null</code> if an error occurs.
   */
  public CharacterLog getLastCharacterLog()
  {
    CharacterLog log=_logsManager.getLastLog();
    return log;
  }

  /**
   * Get the logs manager.
   * @return the logs manager.
   */
  public CharacterLogsManager getLogsManager()
  {
    return _logsManager;
  }

  /**
   * Get the character infos manager.
   * @return the infos manager.
   */
  public CharacterInfosManager getInfosManager()
  {
    return _infosManager;
  }

  /**
   * Get the preferences for this toon.
   * @return the preferences for this toon.
   */
  public Preferences getPreferences()
  {
    return _preferences;
  }

  /**
   * Indicates if this character has a log file.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasLog()
  {
    return _logsManager.hasLog();
  }

  /**
   * Release as much memory as possible.
   */
  public void gc()
  {
    _infosManager.gc();
    _stash=null;
    _levelHistory=null;
    _reputation=null;
  }

  @Override
  public String toString()
  {
    return getIdentifier();
  }
}
