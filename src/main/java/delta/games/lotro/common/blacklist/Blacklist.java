package delta.games.lotro.common.blacklist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Blacklist of identifiers.
 * @author DAM
 */
public class Blacklist
{
  private File _file;
  private Set<Integer> _blacklistedIDs;

  /**
   * Constructor.
   * @param file Storage file.
   */
  public Blacklist(File file)
  {
    _file=file;
    _blacklistedIDs=new HashSet<Integer>();
  }

  /**
   * Get the storage file.
   * @return the storage file.
   */
  public File getFile()
  {
    return _file;
  }

  /**
   * Get all the blacklisted IDs.
   * @return A sorted list of IDs.
   */
  public List<Integer> getAllBLacklistedIDs()
  {
    List<Integer> ids=new ArrayList<Integer>(_blacklistedIDs);
    Collections.sort(ids);
    return ids;
  }

  /**
   * Indicates if the given ID is blacklisted or not.
   * @param id Identifier to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isBlacklisted(int id)
  {
    return _blacklistedIDs.contains(Integer.valueOf(id));
  }

  /**
   * Add an identifier into this blacklist.
   * @param id Identifier to add.
   */
  public void add(int id)
  {
    _blacklistedIDs.add(Integer.valueOf(id));
  }

  /**
   * Remove an identifier from this blacklist.
   * @param id Identifier to remove.
   */
  public void remove(int id)
  {
    _blacklistedIDs.remove(Integer.valueOf(id));
  }

  @Override
  public String toString()
  {
    return "Blacklist: "+getAllBLacklistedIDs();
  }
}
