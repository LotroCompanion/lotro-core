package delta.games.lotro.character.skills;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.skills.io.xml.SkillDetailsXmlIO;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to skill details.
 * @author DAM
 */
public class SkillsDetailsManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillsDetailsManager.class);

  private static SkillsDetailsManager _instance=null;

  private HashMap<Integer,SkillDetails> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static SkillsDetailsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new SkillsDetailsManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private SkillsDetailsManager()
  {
    _cache=new HashMap<Integer,SkillDetails>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File file=cfg.getFile(DataFiles.SKILL_DETAILS);
    if (file.exists())
    {
      long now=System.currentTimeMillis();
      if ((file.exists()) && (file.canRead()))
      {
        List<SkillDetails> details=SkillDetailsXmlIO.parseXML(file);
        for(SkillDetails detail : details)
        {
          register(detail);
        }
      }
      long now2=System.currentTimeMillis();
      long duration=now2-now;
      LOGGER.info("Loaded "+_cache.size()+" skill details in "+duration+"ms.");
    }
  }

  /**
   * Register a new skill details.
   * @param details Data to register.
   */
  public void register(SkillDetails details)
  {
    _cache.put(Integer.valueOf(details.getIdentifier()),details);
  }

  /**
   * Get a list of all skill details, sorted by identifier.
   * @return A list of skill details.
   */
  public List<SkillDetails> getAll()
  {
    ArrayList<SkillDetails> skills=new ArrayList<SkillDetails>();
    skills.addAll(_cache.values());
    Collections.sort(skills,new IdentifiableComparator<SkillDetails>());
    return skills;
  }

  /**
   * Get a skill details using its identifier.
   * @param id Skill identifier.
   * @return A skill details or <code>null</code> if not found.
   */
  public SkillDetails getSkillDetails(int id)
  {
    SkillDetails ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
