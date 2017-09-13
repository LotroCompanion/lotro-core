package delta.games.lotro.character.crafting;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import delta.games.lotro.crafting.CraftingLevel;
import delta.games.lotro.crafting.Profession;

/**
 * Statistics about a single profession on a single toon.
 * @author DAM
 */
public class ProfessionStatus
{
  private Profession _profession;
  private Long _validityDate;
  private List<CraftingLevelStatus> _status;

  /**
   * Constructor.
   * @param profession Profession.
   */
  public ProfessionStatus(Profession profession)
  {
    _profession=profession;
    _status=new ArrayList<CraftingLevelStatus>();
    reset();
  }

  /**
   * Get the managed profession.
   * @return the managed profession.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Initialize profession.
   * @param date Event date.
   */
  public void initProfession(long date)
  {
    reset();
    CraftingLevelStatus status=getLevelStatus(CraftingLevel.BEGINNER);
    status.getMastery().setCompleted(date);
    status.getProficiency().setCompleted(date);
  }

  /**
   * Get the validity date of this status (for instance, date of last log item used to build this data).
   * @return A date or <code>null</code> if not set.
   */
  public Long getValidityDate()
  {
    return _validityDate;
  }

  /**
   * Set the validity date of this data.
   * @param date Date to set.
   */
  public void setValidityDate(Long date)
  {
    _validityDate=date;
  }

  /**
   * Reset data.
   */
  private void reset()
  {
    _status.clear();
    for(CraftingLevel level : CraftingLevel.ALL_TIERS)
    {
      CraftingLevelStatus status=new CraftingLevelStatus(level);
      _status.add(status);
    }
  }

  /**
   * Get the status of level.
   * @param level Targeted level.
   * @return A crafting level status.
   */
  public CraftingLevelStatus getLevelStatus(int level)
  {
    return _status.get(level);
  }

  /**
   * Get the status of level.
   * @param level Targeted level.
   * @return A crafting level status.
   */
  public CraftingLevelStatus getLevelStatus(CraftingLevel level)
  {
    return _status.get(level.getTier());
  }

  /**
   * Get the proficiency level.
   * @return the proficiency level.
   */
  public CraftingLevel getProficiencyLevel()
  {
    CraftingLevel ret=null;
    for(CraftingLevelStatus status : _status)
    {
      if (status.getProficiency().isCompleted())
      {
        ret=status.getLevel();
      }
    }
    return ret;
  }

  /**
   * Get the mastery level.
   * @return the mastery level.
   */
  public CraftingLevel getMasteryLevel()
  {
    CraftingLevel ret=null;
    for(CraftingLevelStatus status : _status)
    {
      if (status.getMastery().isCompleted())
      {
        ret=status.getLevel();
      }
    }
    return ret;
  }

  /**
   * Update a completion status.
   * @param targetedLevel Targeted level.
   * @param mastery Targeted tier.
   * @param completed New completion status.
   */
  public void setCompletionStatus(CraftingLevel targetedLevel, boolean mastery, boolean completed)
  {
    CraftingLevelStatus levelStatus=getLevelStatus(targetedLevel);
    CraftingLevelTierStatus tierStatus=mastery?levelStatus.getMastery():levelStatus.getProficiency();
    boolean currentCompletionStatus=tierStatus.isCompleted();
    if (currentCompletionStatus!=completed)
    {
      if (completed)
      {
        // Update the targeted level
        tierStatus.setCompleted(true);
        tierStatus.setAcquiredXP(tierStatus.getLevelTier().getXP());
        // Set previous levels to 'completed'
        for(CraftingLevel level : CraftingLevel.ALL_TIERS)
        {
          if (level==targetedLevel)
          {
            break;
          }
          setCompletionStatus(level,mastery,completed);
        }
        // If mastery, then proficiency is also completed
        if (mastery)
        {
          setCompletionStatus(targetedLevel,false,true);
        }
      }
      else
      {
        // Update the targeted level
        tierStatus.setCompleted(false);
        tierStatus.setCompletionDate(0);
        // Set higher levels to 'not completed'
        for(CraftingLevel level : CraftingLevel.ALL_TIERS)
        {
          if (level.getTier()>targetedLevel.getTier())
          {
            setCompletionStatus(level,mastery,false);
          }
        }
        // If proficiency, then mastery is also 'not completed'
        if (!mastery)
        {
          setCompletionStatus(targetedLevel,true,false);
        }
      }
    }
  }

  /**
   * Dump the contents of this object to the given stream.
   * @param ps Output stream to use.
   */
  public void dump(PrintStream ps)
  {
    ps.println("History for profession ["+_profession+"]:");
    CraftingLevel proficiency=getProficiencyLevel();
    if (proficiency!=null)
    {
      ps.println("Proficiency:");
      for(int i=0;i<=proficiency.getTier();i++)
      {
        long date=getLevelStatus(i).getProficiency().getCompletionDate();
        if (date!=0)
        {
          CraftingLevel level=CraftingLevel.getByTier(i);
          String label=(level!=null)?level.getProficiency().getLabel():"???";
          ps.println("\t"+label+": "+new Date(date));
        }
      }
    }
    CraftingLevel mastery=getMasteryLevel();
    if (mastery!=null)
    {
      ps.println("Mastery:");
      for(int i=0;i<=mastery.getTier();i++)
      {
        long date=getLevelStatus(i).getMastery().getCompletionDate();
        if (date!=0)
        {
          CraftingLevel level=CraftingLevel.getByTier(i);
          String label=(level!=null)?level.getMastery().getLabel():"???";
          ps.println("\t"+label+": "+new Date(date));
        }
      }
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_profession);
    sb.append(": mastery=");
    CraftingLevel masteryLevel=getMasteryLevel();
    String masteryLabel=(masteryLevel!=null)?masteryLevel.getMastery().getLabel():"???";
    sb.append(masteryLabel);
    sb.append(", proficiency=");
    CraftingLevel proficiencyLevel=getProficiencyLevel();
    String proficiencyLabel=(proficiencyLevel!=null)?proficiencyLevel.getProficiency().getLabel():"???";
    sb.append(proficiencyLabel);
    return sb.toString();
  }
}
