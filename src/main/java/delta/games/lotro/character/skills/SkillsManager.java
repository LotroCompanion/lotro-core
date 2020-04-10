package delta.games.lotro.character.skills;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.skills.io.xml.SkillDescriptionXMLParser;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to skills.
 * @author DAM
 */
public class SkillsManager
{
  private static final Logger LOGGER=Logger.getLogger(SkillsManager.class);

  private static SkillsManager _instance=null;

  private HashMap<Integer,SkillDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static SkillsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new SkillsManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private SkillsManager()
  {
    _cache=new HashMap<Integer,SkillDescription>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File skillsFile=cfg.getFile(DataFiles.SKILLS);
    long now=System.currentTimeMillis();
    if ((skillsFile.exists()) && (skillsFile.canRead()))
    {
      List<SkillDescription> skills=SkillDescriptionXMLParser.parseSkillsFile(skillsFile);
      for(SkillDescription skill : skills)
      {
        registerSkill(skill);
      }
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" skills in "+duration+"ms.");
  }

  /**
   * Register a new skill.
   * @param skill Skill to register.
   */
  public void registerSkill(SkillDescription skill)
  {
    _cache.put(Integer.valueOf(skill.getIdentifier()),skill);
  }

  /**
   * Get a list of all skills, sorted by identifier.
   * @return A list of skills.
   */
  public List<SkillDescription> getAll()
  {
    ArrayList<SkillDescription> skills=new ArrayList<SkillDescription>();
    skills.addAll(_cache.values());
    Collections.sort(skills,new IdentifiableComparator<SkillDescription>());
    return skills;
  }

  /**
   * Get a skill using its identifier.
   * @param id Skill identifier.
   * @return A skill description or <code>null</code> if not found.
   */
  public SkillDescription getSkill(int id)
  {
    SkillDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
