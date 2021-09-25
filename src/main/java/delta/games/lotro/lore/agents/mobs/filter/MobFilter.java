package delta.games.lotro.lore.agents.mobs.filter;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.common.enums.AgentClass;
import delta.games.lotro.common.enums.Alignment;
import delta.games.lotro.common.enums.ClassificationFilter;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.enums.filter.LotroEnumValueFilter;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.utils.DataProvider;

/**
 * Mob filter.
 * @author DAM
 */
public class MobFilter implements Filter<MobDescription>
{
  private Filter<MobDescription> _filter;

  private MobNameFilter _nameFilter;
  private LotroEnumValueFilter<Alignment,MobDescription> _alignmentFilter;
  private LotroEnumValueFilter<AgentClass,MobDescription> _agentClassFilter;
  private LotroEnumValueFilter<ClassificationFilter,MobDescription> _classificationFilter;
  private LotroEnumValueFilter<Genus,MobDescription> _genusFilter;
  private LotroEnumValueFilter<Species,MobDescription> _speciesFilter;
  private LotroEnumValueFilter<SubSpecies,MobDescription> _subspeciesFilter;

  /**
   * Constructor.
   */
  public MobFilter()
  {
    List<Filter<MobDescription>> filters=new ArrayList<Filter<MobDescription>>();
    // Name
    _nameFilter=new MobNameFilter();
    filters.add(_nameFilter);
    // Alignment
    _alignmentFilter=buildAlignmentFilter();
    filters.add(_alignmentFilter);
   // Agent class
   _agentClassFilter=buildAgentClassFilter();
   filters.add(_agentClassFilter);
   // Classification
   _classificationFilter=buildClassificationFilter();
   filters.add(_classificationFilter);
   // Genus
   _genusFilter=buildGenusFilter();
   filters.add(_genusFilter);
   // Species
   _speciesFilter=buildSpeciesFilter();
   filters.add(_speciesFilter);
   // Sub-species
   _subspeciesFilter=buildSubSpeciesFilter();
   filters.add(_subspeciesFilter);
    _filter=new CompoundFilter<MobDescription>(Operator.AND,filters);
  }

  private LotroEnumValueFilter<Alignment,MobDescription> buildAlignmentFilter()
  {
    DataProvider<MobDescription,Alignment> provider=new DataProvider<MobDescription,Alignment>()
    {
      @Override
      public Alignment getData(MobDescription mob)
      {
        return mob.getClassification().getAlignment();
      }
    };
    return new LotroEnumValueFilter<Alignment,MobDescription>(provider,null);
  }

  private LotroEnumValueFilter<AgentClass,MobDescription> buildAgentClassFilter()
  {
    DataProvider<MobDescription,AgentClass> provider=new DataProvider<MobDescription,AgentClass>()
    {
      @Override
      public AgentClass getData(MobDescription mob)
      {
        return mob.getClassification().getAgentClass();
      }
    };
    return new LotroEnumValueFilter<AgentClass,MobDescription>(provider,null);
  }

  private LotroEnumValueFilter<ClassificationFilter,MobDescription> buildClassificationFilter()
  {
    DataProvider<MobDescription,ClassificationFilter> provider=new DataProvider<MobDescription,ClassificationFilter>()
    {
      @Override
      public ClassificationFilter getData(MobDescription mob)
      {
        return mob.getClassification().getClassificationFilter();
      }
    };
    return new LotroEnumValueFilter<ClassificationFilter,MobDescription>(provider,null);
  }

  private LotroEnumValueFilter<Genus,MobDescription> buildGenusFilter()
  {
    DataProvider<MobDescription,Genus> provider=new DataProvider<MobDescription,Genus>()
    {
      @Override
      public Genus getData(MobDescription mob)
      {
        return mob.getClassification().getEntityClassification().getGenuses().get(0);
      }
    };
    return new LotroEnumValueFilter<Genus,MobDescription>(provider,null);
  }

  private LotroEnumValueFilter<Species,MobDescription> buildSpeciesFilter()
  {
    DataProvider<MobDescription,Species> provider=new DataProvider<MobDescription,Species>()
    {
      @Override
      public Species getData(MobDescription mob)
      {
        return mob.getClassification().getEntityClassification().getSpecies();
      }
    };
    return new LotroEnumValueFilter<Species,MobDescription>(provider,null);
  }

  private LotroEnumValueFilter<SubSpecies,MobDescription> buildSubSpeciesFilter()
  {
    DataProvider<MobDescription,SubSpecies> provider=new DataProvider<MobDescription,SubSpecies>()
    {
      @Override
      public SubSpecies getData(MobDescription mob)
      {
        return mob.getClassification().getEntityClassification().getSubSpecies();
      }
    };
    return new LotroEnumValueFilter<SubSpecies,MobDescription>(provider,null);
  }

  /**
   * Get the filter on mob name.
   * @return a mob name filter.
   */
  public MobNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on alignment.
   * @return an alignment filter.
   */
  public LotroEnumValueFilter<Alignment,MobDescription> getAlignmentFilter()
  {
    return _alignmentFilter;
  }

  /**
   * Get the filter on agent class.
   * @return an agent class filter.
   */
  public LotroEnumValueFilter<AgentClass,MobDescription> getAgentClassFilter()
  {
    return _agentClassFilter;
  }

  /**
   * Get the filter on classification.
   * @return a classification filter.
   */
  public LotroEnumValueFilter<ClassificationFilter,MobDescription> getClassificationFilter()
  {
    return _classificationFilter;
  }

  /**
   * Get the filter on genus.
   * @return a genus filter.
   */
  public LotroEnumValueFilter<Genus,MobDescription> getGenusFilter()
  {
    return _genusFilter;
  }

  /**
   * Get the filter on species.
   * @return a species filter.
   */
  public LotroEnumValueFilter<Species,MobDescription> getSpeciesFilter()
  {
    return _speciesFilter;
  }

  /**
   * Get the filter on subspecies.
   * @return a subspecies filter.
   */
  public LotroEnumValueFilter<SubSpecies,MobDescription> getSubSpeciesFilter()
  {
    return _subspeciesFilter;
  }

  @Override
  public boolean accept(MobDescription item)
  {
    return _filter.accept(item);
  }
}
