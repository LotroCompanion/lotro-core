package delta.games.lotro.lore.housing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.common.utils.collections.CompoundComparator;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.HouseType;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Neighborhood template.
 * @author DAM
 */
public class NeighborhoodTemplate implements Identifiable,Named
{
  private int _id;
  private String _name;
  private List<HouseDefinition> _houses;
  private List<BlockReference> _blocks;
  private Position _entrance;
  private Position _boot;

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
    _houses=new ArrayList<HouseDefinition>();
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
   * @return a list of houses.
   */
  public List<HouseDefinition> getHouses()
  {
    List<HouseDefinition> ret=new ArrayList<HouseDefinition>(_houses);

    DataProvider<HouseDefinition,HouseType> provider=new DataProvider<HouseDefinition,HouseType>()
    {
      @Override
      public HouseType getData(HouseDefinition p)
      {
        return p.getHouseType();
      }
    };
    List<Comparator<HouseDefinition>> list=new ArrayList<Comparator<HouseDefinition>>();
    DelegatingComparator<HouseDefinition,HouseType> delegatingComparator=new DelegatingComparator<HouseDefinition,HouseType>(provider,new LotroEnumEntryCodeComparator<HouseType>());
    list.add(delegatingComparator);
    list.add(new IdentifiableComparator<HouseDefinition>());
    CompoundComparator<HouseDefinition> cc=new CompoundComparator<HouseDefinition>(list);
    Collections.sort(ret,cc);
    return ret;
  }

  /**
   * Add a house.
   * @param house House to add.
   */
  public void addHouse(HouseDefinition house)
  {
    _houses.add(house);
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
  public Position getEntrance()
  {
    return _entrance;
  }

  /**
   * Set the position of the entrance.
   * @param entrance the position to set.
   */
  public void setEntrance(Position entrance)
  {
    _entrance=entrance;
  }

  /**
   * Get the position of the player when he leaves the neighborhood.
   * @return A position.
   */
  public Position getBoot()
  {
    return _boot;
  }

  /**
   * Set the position of the player when he leaves the neighborhood.
   * @param boot the boot to set
   */
  public void setBoot(Position boot)
  {
    _boot=boot;
  }
}
