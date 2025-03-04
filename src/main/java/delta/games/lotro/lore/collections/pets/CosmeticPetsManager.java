package delta.games.lotro.lore.collections.pets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for cosmetic pets access.
 * @author DAM
 */
public class CosmeticPetsManager
{
  private static CosmeticPetsManager _instance=null;

  private HashMap<Integer,CosmeticPetDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static CosmeticPetsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new CosmeticPetsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the pets database shall be loaded or not.
   */
  private CosmeticPetsManager(boolean load)
  {
    _cache=new HashMap<Integer,CosmeticPetDescription>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all pets.
   */
  private void loadAll()
  {
    _cache.clear();
    long now=System.currentTimeMillis();
    List<SkillDescription> skills=SkillsManager.getInstance().getAll();
    for(SkillDescription skill : skills)
    {
      if (skill instanceof CosmeticPetDescription)
      {
        CosmeticPetDescription pet=(CosmeticPetDescription)skill;
        _cache.put(Integer.valueOf(pet.getIdentifier()),pet);
      }
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"pets",duration);
  }

  /**
   * Get a list of all pets, sorted by identifier.
   * @return A list of pets.
   */
  public List<CosmeticPetDescription> getAll()
  {
    ArrayList<CosmeticPetDescription> titles=new ArrayList<CosmeticPetDescription>();
    titles.addAll(_cache.values());
    Collections.sort(titles,new IdentifiableComparator<CosmeticPetDescription>());
    return titles;
  }

  /**
   * Get a pet using its identifier.
   * @param id Pet identifier.
   * @return A pet description or <code>null</code> if not found.
   */
  public CosmeticPetDescription getPet(int id)
  {
    CosmeticPetDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
