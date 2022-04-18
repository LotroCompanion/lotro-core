package delta.games.lotro.character;

import java.io.File;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.io.xml.CharacterDataIO;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.buffs.BuffsManager;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Storage class for a LOTRO character definition.
 * @author DAM
 */
public class CharacterData
{
  private File _file;
  private CharacterDataSummary _summary;
  private Long _date;
  private String _shortDescription;
  private String _description;
  private BasicStatsSet _stats;
  private CharacterGear _equipment;
  private VirtuesSet _virtues;
  private TomesSet _tomes;
  private BuffsManager _buffs;
  private BasicStatsSet _additionalStats;

  /**
   * Constructor.
   */
  public CharacterData()
  {
    _file=null;
    _summary=new CharacterDataSummary();
    _date=null;
    _shortDescription="";
    _description="";
    _stats=new BasicStatsSet();
    _equipment=new CharacterGear();
    _virtues=new VirtuesSet();
    _tomes=new TomesSet();
    _buffs=new BuffsManager();
    _additionalStats=new BasicStatsSet();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public CharacterData(CharacterData source)
  {
    _file=null;
    _summary=new CharacterDataSummary(source.getSummary());
    _date=source._date;
    _shortDescription=source._shortDescription;
    _description=source._description;
    _stats=new BasicStatsSet(source.getStats());
    _equipment=new CharacterGear();
    _equipment.copyFrom(source._equipment);
    _equipment.setWearer(_summary);
    _virtues=new VirtuesSet();
    _virtues.copyFrom(source.getVirtues());
    _tomes=new TomesSet();
    _tomes.copyFrom(source._tomes);
    _buffs=new BuffsManager();
    _buffs.copyFrom(source._buffs);
    _additionalStats=new BasicStatsSet(source.getAdditionalStats());
  }

  /**
   * Get the file to use to load/save data.
   * @return A file.
   */
  public File getFile()
  {
    return _file;
  }

  /**
   * Set the file to use to load/save data.
   * @param file File to use.
   */
  public void setFile(File file)
  {
    _file=file;
  }

  /**
   * Get the character summary.
   * @return the character summary.
   */
  public CharacterDataSummary getSummary()
  {
    return _summary;
  }

  /**
   * Get the date for this character data.
   * @return a date or <code>null</code> if not set.
   */
  public Long getDate()
  {
    return _date;
  }

  /**
   * Set the date for this character data.
   * @param date Date to set.
   */
  public void setDate(Long date)
  {
    _date=date;
  }

  /**
   * Get the short description.
   * @return the short description.
   */
  public String getShortDescription()
  {
    return _shortDescription;
  }

  /**
   * Set the short description.
   * @param shortDescription the short description to set.
   */
  public void setShortDescription(String shortDescription)
  {
    if (shortDescription==null)
    {
      shortDescription="";
    }
    _shortDescription=shortDescription;
  }

  /**
   * Get the description.
   * @return the description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the character's name.
   * @return the character's name.
   */
  public String getName()
  {
    return _summary.getName();
  }

  /**
   * Get the character's server.
   * @return the character's server.
   */
  public String getServer()
  {
    return _summary.getServer();
  }

  /**
   * Get the character's class.
   * @return the character's class.
   */
  public CharacterClass getCharacterClass()
  {
    return _summary.getCharacterClass();
  }

  /**
   * Get the character's race.
   * @return the character's race.
   */
  public Race getRace()
  {
    return _summary.getRace();
  }

  /**
   * Get the character's level.
   * @return the character's level.
   */
  public int getLevel()
  {
    return _summary.getLevel();
  }

  /**
   * Get character stats.
   * @return The stats storage..
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  /**
   * Get additional stats (stats to add to automatically
   * computed stats: base/virtues/tomes/equipment).
   * @return The additional stats storage.
   */
  public BasicStatsSet getAdditionalStats()
  {
    return _additionalStats;
  }

  /**
   * Get the character's equipment.
   * @return the character's equipment.
   */
  public CharacterGear getEquipment()
  {
    return _equipment;
  }

  /**
   * Get the character's virtues.
   * @return the character's virtues.
   */
  public VirtuesSet getVirtues()
  {
    return _virtues;
  }

  /**
   * Get the character's tomes.
   * @return the character's tomes.
   */
  public TomesSet getTomes()
  {
    return _tomes;
  }

  /**
   * Get the character's buffs.
   * @return the character's buffs.
   */
  public BuffsManager getBuffs()
  {
    return _buffs;
  }

  /**
   * Revert data from file storage.
   */
  public void revert()
  {
    clear();
    if (_file!=null)
    {
      CharacterDataIO.loadCharacter(this);
    }
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _date=null;
    _shortDescription="";
    _description="";
    getStats().clear();
    getEquipment().clear();
    getVirtues().clear();
    getTomes().clear();
    getAdditionalStats().clear();
    getBuffs().clear();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_summary.toString()).append(", ");
    sb.append("Date [").append(_date).append("], ");
    sb.append("Short description: ").append(_shortDescription).append(", ");
    sb.append("Description: ").append(_description).append(", ");
    sb.append("Stats: ").append(_stats).append(", ");
    sb.append("Equipment:").append(_equipment).append(EndOfLine.NATIVE_EOL);
    sb.append("Virtues:").append(_virtues).append(EndOfLine.NATIVE_EOL);
    sb.append("Tomes:").append(_tomes).append(EndOfLine.NATIVE_EOL);
    sb.append("Buffs:").append(_buffs).append(EndOfLine.NATIVE_EOL);
    sb.append("Additional stats:").append(_additionalStats).append(EndOfLine.NATIVE_EOL);
    return sb.toString().trim();
  }
}
