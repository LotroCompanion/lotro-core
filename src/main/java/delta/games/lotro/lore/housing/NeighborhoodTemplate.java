package delta.games.lotro.lore.housing;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.id.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.lore.geo.BlockReference;

/**
 * Neighborhood template.
 * @author DAM
 */
public class NeighborhoodTemplate implements Identifiable,Named
{
  private int _id;
  private String _name;
  private List<Integer> _houses;
  private List<BlockReference> _blocks;
  private ExtendedPosition _entrance;
  private ExtendedPosition _boot;

  /*
  NeighborhoodTemplate_BootPosition: house_hobbit_micheldelving_neighborhood_exit
  NeighborhoodTemplate_Boundaries: 
    #1: NeighborhoodTemplate_AllowedLandblock R=1,I=0,C=-1,bx=89,by=108,x=0.0,y=0.0,z=0.0 => Lon=-75.6/lat=-38.0
    ...
  NeighborhoodTemplate_HouseList: 1879093274
  NeighborhoodTemplate_Name: Shire Homesteads
  NeighborhoodTemplate_SceneID: 1879101754
  NeighborhoodTemplate_Telepad: house_hobbit_micheldelving_neighborhood_entrance
  */

  /**
   * Constructor.
   * @param id Identifier.
   */
  public NeighborhoodTemplate(int id)
  {
    _id=id;
    _houses=new ArrayList<Integer>();
    _blocks=new ArrayList<BlockReference>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the list of houses.
   * @return a list of house identifiers.
   */
  public List<Integer> getHouses()
  {
    return _houses;
  }

  /**
   * Add a house.
   * @param houseID House identifier.
   */
  public void addHouse(int houseID)
  {
    _houses.add(Integer.valueOf(houseID));
  }

  /**
   * Get the land blocks.
   * @return a list of block references.
   */
  public List<BlockReference> getBlocks()
  {
    return _blocks;
  }

  /**
   * Add a block.
   * @param block Block reference to add.
   */
  public void addBlock(BlockReference block)
  {
    _blocks.add(block);
  }

  /**
   * Get the position of the entrance.
   * @return a position.
   */
  public ExtendedPosition getEntrance()
  {
    return _entrance;
  }

  /**
   * Set the position of the entrance.
   * @param entrance the position to set.
   */
  public void setEntrance(ExtendedPosition entrance)
  {
    _entrance=entrance;
  }

  /**
   * Get the position of the player when he leaves the neighborhood.
   * @return A position.
   */
  public ExtendedPosition getBoot()
  {
    return _boot;
  }

  /**
   * Set the position of the player when he leaves the neighborhood.
   * @param boot the boot to set
   */
  public void setBoot(ExtendedPosition boot)
  {
    _boot=boot;
  }
}
