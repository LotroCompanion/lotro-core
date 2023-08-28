package delta.games.lotro.config.labels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Definition of available labels.
 * @author DAM
 */
public class AvailableLabelsDefinition
{
  private LabelsEntry _default;
  private List<LabelsEntry> _entries;
  private Map<String,LabelsEntry> _entriesMap;

  /**
   * Constructor.
   */
  public AvailableLabelsDefinition()
  {
    _default=null;
    _entries=new ArrayList<LabelsEntry>();
    _entriesMap=new HashMap<String,LabelsEntry>();
  }

  /**
   * Get an entry using its identifying key.
   * @param key Key to use.
   * @return An entry or <code>null</code> if not found.
   */
  public LabelsEntry getEntryByKey(String key)
  {
    return _entriesMap.get(key);
  }

  /**
   * Register an entry.
   * @param entry Entry to register.
   */
  public void registerEntry(LabelsEntry entry)
  {
    registerEntry(entry,false);
  }

  /**
   * Register an entry.
   * @param entry Entry to register.
   * @param isDefault <code>true</code> if this is the default entry.
   */
  public void registerEntry(LabelsEntry entry, boolean isDefault)
  {
    _entries.add(entry);
    _entriesMap.put(entry.getKey(),entry);
    if (isDefault)
    {
      _default=entry;
    }
  }

  /**
   * Get all entries.
   * @return A list of entries.
   */
  public List<LabelsEntry> getEntries()
  {
    return _entries;
  }

  /**
   * Get the default entry.
   * @return An entry (should not be <code>null</code>).
   */
  public LabelsEntry getDefault()
  {
    return _default;
  }
}
