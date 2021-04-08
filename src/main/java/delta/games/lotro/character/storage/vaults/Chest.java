package delta.games.lotro.character.storage.vaults;

/**
 * Chest.
 * @author DAM
 */
public class Chest extends ItemInstancesContainer
{
  private int _id;
  private String _name;

  /**
   * Constructor.
   * @param id Chest id.
   */
  public Chest(int id)
  {
    super();
    _id=id;
    _name="";
  }

  /**
   * Get the chest identifier.
   * @return the chest identifier.
   */
  public int getChestId()
  {
    return _id;
  }

  /**
   * Get the name of this chest.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this chest.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Dump contents.
   * @param level Indentation level.
   */
  @Override
  public void dump(int level)
  {
    for(int i=0;i<level;i++) System.out.print('\t');
    System.out.println("Chest: ID="+_id+", name="+_name);
    super.dump(level+1);
  }
}
