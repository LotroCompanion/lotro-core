package delta.games.lotro.lore.housing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.HouseType;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Manager for housing reference data.
 * @author DAM
 */
public class HousingManager
{
  private Map<Integer,Neighborhood> _neighborhoods;
  private Map<Integer,NeighborhoodTemplate> _neighborhoodTemplates;
  private Map<Integer,HouseDefinition> _houses;
  private Map<HouseType,HouseTypeInfo> _infos;

  /**
   * Constructor.
   */
  public HousingManager()
  {
    _neighborhoods=new HashMap<Integer,Neighborhood>();
    _neighborhoodTemplates=new HashMap<Integer,NeighborhoodTemplate>();
    _houses=new HashMap<Integer,HouseDefinition>();
    _infos=new HashMap<HouseType,HouseTypeInfo>();
  }

  /**
   * Register a neighborhood.
   * @param neighborhood Neighborhood to add.
   */
  public void registerNeighborhood(Neighborhood neighborhood)
  {
    _neighborhoods.put(Integer.valueOf(neighborhood.getIdentifier()),neighborhood);
  }

  /**
   * Get the neighborhoods.
   * @return A list of neighborhoods, sorted by ID.
   */
  public List<Neighborhood> getNeighborhoods()
  {
    List<Neighborhood> ret=new ArrayList<Neighborhood>(_neighborhoods.values());
    Collections.sort(ret,new IdentifiableComparator<Neighborhood>());
    return ret;
  }

  /**
   * Get a neighborhood using its identifier.
   * @param neighborhoodID Neighborhood identifier.
   * @return A neighborhood or <code>null</code>.
   */
  public Neighborhood getNeighborhood(int neighborhoodID)
  {
    return _neighborhoods.get(Integer.valueOf(neighborhoodID));
  }

  /**
   * Register a neighborhood template.
   * @param neighborhoodTemplate Neighborhood template to add.
   */
  public void registerNeighborhoodTemplate(NeighborhoodTemplate neighborhoodTemplate)
  {
    _neighborhoodTemplates.put(Integer.valueOf(neighborhoodTemplate.getIdentifier()),neighborhoodTemplate);
  }

  /**
   * Get the neighborhood templates.
   * @return A list of neighborhood templates, sorted by ID.
   */
  public List<NeighborhoodTemplate> getNeighborhoodTemplates()
  {
    List<NeighborhoodTemplate> ret=new ArrayList<NeighborhoodTemplate>(_neighborhoodTemplates.values());
    Collections.sort(ret,new IdentifiableComparator<NeighborhoodTemplate>());
    return ret;
  }

  /**
   * Get a neighborhood template using its identifier.
   * @param neighborhoodTemplateID Neighborhood template identifier.
   * @return A neighborhood template or <code>null</code>.
   */
  public NeighborhoodTemplate getNeighborhoodTemplate(int neighborhoodTemplateID)
  {
    return _neighborhoodTemplates.get(Integer.valueOf(neighborhoodTemplateID));
  }

  /**
   * Register a house.
   * @param house House to add.
   */
  public void registerHouse(HouseDefinition house)
  {
    _houses.put(Integer.valueOf(house.getIdentifier()),house);
  }

  /**
   * Get the houses.
   * @return A list of houses, sorted by ID.
   */
  public List<HouseDefinition> getHouses()
  {
    List<HouseDefinition> ret=new ArrayList<HouseDefinition>(_houses.values());
    Collections.sort(ret,new IdentifiableComparator<HouseDefinition>());
    return ret;
  }

  /**
   * Get a house using its identifier.
   * @param houseID House identifier.
   * @return A house or <code>null</code>.
   */
  public HouseDefinition getHouse(int houseID)
  {
    return _houses.get(Integer.valueOf(houseID));
  }

  /**
   * Register a house infos.
   * @param info House infos to add.
   */
  public void registerHouseInfo(HouseTypeInfo info)
  {
    _infos.put(info.getHouseType(),info);
  }

  /**
   * Get the house infos.
   * @return A list of houses infos, sorted by house type code.
   */
  public List<HouseTypeInfo> getHouseInfos()
  {
    List<HouseTypeInfo> ret=new ArrayList<HouseTypeInfo>(_infos.values());
    DataProvider<HouseTypeInfo,HouseType> provider=new DataProvider<HouseTypeInfo,HouseType>()
    {
      @Override
      public HouseType getData(HouseTypeInfo p)
      {
        return p.getHouseType();
      }
    };
    DelegatingComparator<HouseTypeInfo,HouseType> delegatingComparator=new DelegatingComparator<HouseTypeInfo,HouseType>(provider,new LotroEnumEntryCodeComparator<HouseType>());
    Collections.sort(ret,delegatingComparator);
    return ret;
  }

  /**
   * Get a house info.
   * @param type House type.
   * @return A house info.
   */
  public HouseTypeInfo getHouseInfo(HouseType type)
  {
    return _infos.get(type);
  }
}
