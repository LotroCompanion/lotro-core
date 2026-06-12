package delta.games.lotro.lore.quests.loots;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.enums.AgentClass;
import delta.games.lotro.common.enums.Alignment;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.MobDivision;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.lore.agents.AgentClassification;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobLocation;
import delta.games.lotro.lore.agents.mobs.MobSelection;
import delta.games.lotro.lore.agents.mobs.MobsManager;

/**
 * Utility methods related to achievable loots.
 * @author DAM
 */
public class AchievableLootUtils
{
  /**
   * Find the mobs that are targeted by the given loot.
   * @param loot Loot to use.
   * @return A list of mobs.
   */
  public Set<MobDescription> findMobs(AchievableLoot loot)
  {
    Set<MobDescription> ret=new HashSet<MobDescription>();
    MobDescription mob=loot.getMob();
    if (mob!=null)
    {
      ret.add(mob);
      return ret;
    }
    List<MobSelection> monsterSpecs=loot.getMonsterSpecs();
    for(MobSelection monsterSpec : monsterSpecs)
    {
      ret.addAll(findMobs(monsterSpec));
    }
    if (!loot.getExcludedMonsterSpecs().isEmpty())
    {
      for(MobSelection monsterSpec : monsterSpecs)
      {
        ret.removeAll(findMobs(monsterSpec));
      }
    }
    return ret;
  }

  private Set<MobDescription> findMobs(MobSelection monsterSpec)
  {
    Set<MobDescription> ret=new HashSet<MobDescription>();
    for(MobDescription mob : MobsManager.getInstance().getMobs())
    {
      if (accept(monsterSpec,mob))
      {
        ret.add(mob);
      }
    }
    return ret;
  }

  /**
   * Indicates if the given mob passes the given mob spec.
   * @param spec Mob spec.
   * @param mob Mob to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean accept(MobSelection spec, MobDescription mob)
  {
    MobLocation specLocation=spec.getWhere();
    boolean ret=accept(specLocation,mob);
    if (!ret)
    {
      return false;
    }
    AgentClassification specClassification=spec.getWhat();
    ret=accept(specClassification,mob.getClassification());
    return ret;
  }

  private boolean accept(MobLocation specLocation, MobDescription mob)
  {
    if (specLocation==null)
    {
      return true;
    }
    // Mob division
    MobDivision specMobDivision=specLocation.getMobDivision();
    if (specMobDivision!=null)
    {
      MobDivision mobDivision=mob.getDivision();
      if (specMobDivision!=mobDivision)
      {
        return false;
      }
    }
    // Not used: landmark
    // TODO LandDivision
    return true;
  }

  private boolean accept(AgentClassification specClassification, AgentClassification mobClassification)
  {
    if (specClassification==null)
    {
      return true;
    }
    // Class
    AgentClass specClass=specClassification.getAgentClass();
    if (specClass!=null)
    {
      AgentClass mobClass=mobClassification.getAgentClass();
      if (specClass!=mobClass)
      {
        return false;
      }
    }
    // Alignment
    Alignment specAlignment=specClassification.getAlignment();
    if (specAlignment!=null)
    {
      Alignment mobAlignment=mobClassification.getAlignment();
      if (specAlignment!=mobAlignment)
      {
        return false;
      }
    }
    // Classification
    EntityClassification specEntityClassification=specClassification.getEntityClassification();
    EntityClassification mobEntityClassification=mobClassification.getEntityClassification();
    return accept(specEntityClassification,mobEntityClassification);
  }

  private boolean accept(EntityClassification specClassification, EntityClassification mobClassification)
  {
    // Species
    Species species=specClassification.getSpecies();
    if (species!=null)
    {
      Species mobSpecies=mobClassification.getSpecies();
      if (species!=mobSpecies)
      {
        return false;
      }
    }
    // Subspecies
    SubSpecies subSpecies=specClassification.getSubSpecies();
    if (subSpecies!=null)
    {
      SubSpecies mobSubSpecies=mobClassification.getSubSpecies();
      if (subSpecies!=mobSubSpecies)
      {
        return false;
      }
    }
    // Genus
    List<Genus> specGenuses=specClassification.getGenuses();
    if (!specGenuses.isEmpty())
    {
      boolean genusOK=false;
      for(Genus specGenus : specGenuses)
      {
        if (mobClassification.getGenuses().contains(specGenus))
        {
          genusOK=true;
          break;
        }
      }
      if (!genusOK)
      {
        return false;
      }
    }
    return true;
  }
}
