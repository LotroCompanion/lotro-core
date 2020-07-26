package delta.games.lotro.lore.maps;

import delta.games.lotro.common.Identifiable;

/**
 * Dungeon.
 * @author DAM
 */
public class Dungeon implements Identifiable
{
  private int _identifier;
  private String _name;
  private Dungeon _parentDungeon;
  private int _imageId;
  // allowed mount types (Dungeon_Allowed_Mount_Types)
  // Music

  /**
   * Constructor.
   * @param identifier Internal identifier.
   * @param name Dungeon's name.
   * @param imageId Dungeon map image.
   */
  public Dungeon(int identifier, String name, int imageId)
  {
    _identifier=identifier;
    _name=name;
    _imageId=imageId;
  }

  /**
   * Get the internal identifier.
   * @return the internal identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the dungeon's name.
   * @return the dungeon's name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the parent dungeon.
   * @return the parent dungeon.
   */
  public Dungeon getParentDungeon()
  {
    return _parentDungeon;
  }

  /**
   * Set the parent dungeon.
   * @param parentDungeon Parent dungeon to set.
   */
  public void setParentDungeon(Dungeon parentDungeon)
  {
    _parentDungeon=parentDungeon;
  }

  /**
   * Get the image for this dungeon.
   * @return an image identifier.
   */
  public int getImageId()
  {
    return _imageId;
  }

  /**
   * Set the image identifier for this dungeon.
   * @param imageId Image identifier to set.
   */
  public void setImageId(int imageId)
  {
    _imageId=imageId;
  }

  /**
   * Dump the contents of this area as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Dungeon: ").append(_name);
    sb.append(" (").append(_identifier).append(')');
    if (_parentDungeon!=null)
    {
      sb.append(" Parent: ").append(_parentDungeon.getName());
    }
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return "Dungeon: "+_name;
  }
}
