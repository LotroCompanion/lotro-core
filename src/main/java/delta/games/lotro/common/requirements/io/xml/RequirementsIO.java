package delta.games.lotro.common.requirements.io.xml;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.DifficultyRequirement;
import delta.games.lotro.common.requirements.EffectRequirement;
import delta.games.lotro.common.requirements.FactionRequirement;
import delta.games.lotro.common.requirements.GloryRankRequirement;
import delta.games.lotro.common.requirements.LevelCapRequirement;
import delta.games.lotro.common.requirements.LevelRangeRequirement;
import delta.games.lotro.common.requirements.ProfessionRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.requirements.Requirement;
import delta.games.lotro.common.requirements.Requirements;
import delta.games.lotro.common.requirements.TraitRequirement;
import delta.games.lotro.common.requirements.WorldEventRequirement;

/**
 * I/O facilities for requirements.
 * @author DAM
 */
public class RequirementsIO
{
  private Map<Class<? extends Requirement>,RequirementXMLReader<?>> _readers;
  private Map<Class<? extends Requirement>,RequirementSAXWriter<?>> _writers;

  /**
   * Constructor.
   */
  public RequirementsIO()
  {
    _readers=new HashMap<Class<? extends Requirement>,RequirementXMLReader<?>>();
    _writers=new HashMap<Class<? extends Requirement>,RequirementSAXWriter<?>>();
    // Level range requirements
    _readers.put(LevelRangeRequirement.class,new LevelRangeRequirementXMLIO());
    _writers.put(LevelRangeRequirement.class,new LevelRangeRequirementXMLIO());
    // Class requirement
    _readers.put(ClassRequirement.class,new ClassRequirementXMLIO());
    _writers.put(ClassRequirement.class,new ClassRequirementXMLIO());
    // Race requirement
    _readers.put(RaceRequirement.class,new RaceRequirementXMLIO());
    _writers.put(RaceRequirement.class,new RaceRequirementXMLIO());
    // Faction requirement
    _readers.put(FactionRequirement.class,new FactionRequirementXMLIO());
    _writers.put(FactionRequirement.class,new FactionRequirementXMLIO());
    // Quest requirement
    _readers.put(QuestRequirement.class,new QuestRequirementXMLIO());
    _writers.put(QuestRequirement.class,new QuestRequirementXMLIO());
    // Crafting requirement
    _readers.put(ProfessionRequirement.class,new CraftingRequirementXMLIO());
    _writers.put(ProfessionRequirement.class,new CraftingRequirementXMLIO());
    // Glory rank requirement
    _readers.put(GloryRankRequirement.class,new GloryRankRequirementXMLIO());
    _writers.put(GloryRankRequirement.class,new GloryRankRequirementXMLIO());
    // Effect requirement
    _readers.put(EffectRequirement.class,new EffectRequirementXMLIO());
    _writers.put(EffectRequirement.class,new EffectRequirementXMLIO());
    // Trait requirement
    _readers.put(TraitRequirement.class,new TraitRequirementXMLIO());
    _writers.put(TraitRequirement.class,new TraitRequirementXMLIO());
    // Difficulty requirement
    _readers.put(DifficultyRequirement.class,new DifficultyRequirementXMLIO());
    _writers.put(DifficultyRequirement.class,new DifficultyRequirementXMLIO());
    // Level cap requirement
    _readers.put(LevelCapRequirement.class,new LevelCapRequirementXMLIO());
    _writers.put(LevelCapRequirement.class,new LevelCapRequirementXMLIO());
    // World event requirement
    _readers.put(WorldEventRequirement.class,new WorldEventRequirementXMLIO());
    _writers.put(WorldEventRequirement.class,new WorldEventRequirementXMLIO());
  }

  private <T extends Requirement> RequirementSAXWriter<?> getWriter(Class<T> requirementClass)
  {
    return _writers.get(requirementClass);
  }

  <T extends Requirement> RequirementXMLReader<?> getReader(Class<T> requirementClass)
  {
    return _readers.get(requirementClass);
  }

  /**
   * Read requirements from SAX data.
   * @param attrs Input data.
   * @param storage Storage for loaded data.
   */
  public void readRequirements(Attributes attrs, Requirements storage)
  {
    for(RequirementXMLReader<?> reader : _readers.values())
    {
      Requirement requirement=reader.readSAX(attrs);
      if (requirement!=null)
      {
        storage.setRequirement(requirement);
      }
    }
  }

  /**
   * Read requirements from DOM data.
   * @param attrs Input data.
   * @param storage Storage for loaded data.
   */
  public void readRequirements(NamedNodeMap attrs, Requirements storage)
  {
    for(RequirementXMLReader<?> reader : _readers.values())
    {
      Requirement requirement=reader.readDOM(attrs);
      if (requirement!=null)
      {
        storage.setRequirement(requirement);
      }
    }
  }

  /**
   * Write some requirements.
   * @param requirements Input requirements.
   * @param attrs Storage for data to write.
   */
  public void writeRequirements(Requirement[] requirements, AttributesImpl attrs)
  {
    for(Requirement r : requirements)
    {
      writeRequirement(r,attrs);
    }
  }

  /**
   * Write a single requirement.
   * @param <T> Type of requirement.
   * @param r Input requirement.
   * @param attrs Storage for data to write.
   */
  public <T extends Requirement> void writeRequirement(T r, AttributesImpl attrs)
  {
    @SuppressWarnings("unchecked")
    RequirementSAXWriter<T> writer=(RequirementSAXWriter<T>)getWriter(r.getClass());
    if (writer!=null)
    {
      writer.write(attrs,r);
    }
  }
}
