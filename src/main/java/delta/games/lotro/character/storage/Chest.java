package delta.games.lotro.character.storage;

/**
 * Chest.
 * @author DAM
 */
public class Chest extends ItemsContainer
{
  private String _name;

  /**
   * Constructor.
   */
  public Chest()
  {
    super();
    _name="";
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
    System.out.println("Chest: "+_name);
    super.dump(level+1);
  }
}
