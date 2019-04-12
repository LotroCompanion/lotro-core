package delta.games.lotro.common.rewards;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.money.Money;

/**
 * Rewards description.
 * @author DAM
 */
public class Rewards
{
  private boolean _itemXP;
  private Money _money;
  private Reputation _reputation;
  private int _destinyPoints;
  private int _lotroPoints;
  private int _classPoints;
  private List<TraitReward> _traits;
  private List<SkillReward> _skills;
  private List<TitleReward> _titles;
  private List<VirtueReward> _virtues;
  private List<EmoteReward> _emotes;
  private ItemsSetReward _objects;
  private ItemsSetReward _selectObjects;

  /**
   * Constructor.
   */
  public Rewards()
  {
    _itemXP=false;
    _money=new Money();
    _reputation=new Reputation();
    _destinyPoints=0;
    _lotroPoints=0;
    _classPoints=0;
    _traits=null;
    _skills=null;
    _titles=null;
    _virtues=null;
    _emotes=null;
    _objects=new ItemsSetReward();
    _selectObjects=new ItemsSetReward();
  }

  /**
   * Get the money reward.
   * @return the money reward.
   */
  public Money getMoney()
  {
    return _money;
  }
  
  /**
   * Get the reputation reward.
   * @return the reputation reward.
   */
  public Reputation getReputation()
  {
    return _reputation;
  }

  /**
   * Get the destiny points.
   * @return the destiny points.
   */
  public int getDestinyPoints()
  {
    return _destinyPoints;
  }

  /**
   * Set the destiny points.
   * @param destinyPoints Destiny points to set.
   */
  public void setDestinyPoints(int destinyPoints)
  {
    _destinyPoints=destinyPoints;
  }

  /**
   * Get the LOTRO points.
   * @return the LOTRO points.
   */
  public int getLotroPoints()
  {
    return _lotroPoints;
  }

  /**
   * Set the LOTRO points.
   * @param lotroPoints LOTRO points to set.
   */
  public void setLotroPoints(int lotroPoints)
  {
    _lotroPoints=lotroPoints;
  }

  /**
   * Get the class points.
   * @return the class points.
   */
  public int getClassPoints()
  {
    return _classPoints;
  }

  /**
   * Set the class points.
   * @param classPoints Class points to set.
   */
  public void setClassPoints(int classPoints)
  {
    _classPoints=classPoints;
  }

  /**
   * Get the objects reward.
   * @return the objects reward.
   */
  public ItemsSetReward getObjects()
  {
    return _objects;
  }

  /**
   * Get the objects reward.
   * @return the objects reward.
   */
  public ItemsSetReward getSelectObjects()
  {
    return _selectObjects;
  }

  /**
   * Indicates if this reward includes item XP.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasItemXP()
  {
    return _itemXP;
  }

  /**
   * Set the 'item XP' flag.
   * @param itemXP Value to set.
   */
  public void setHasItemXP(boolean itemXP)
  {
    _itemXP=itemXP;
  }

  /**
   * Add a trait.
   * @param trait Trait to add.
   */
  public void addTrait(TraitReward trait)
  {
    if (_traits==null)
    {
      _traits=new ArrayList<TraitReward>();
    }
    _traits.add(trait);
  }

  /**
   * Get all traits.
   * @return An array of traits or <code>null</code> if there's none.
   */
  public TraitReward[] getTraits()
  {
    TraitReward[] ret=null;
    if (_traits!=null)
    {
      ret=new TraitReward[_traits.size()];
      ret=_traits.toArray(ret);
    }
    return ret;
  }

  /**
   * Add a skill.
   * @param skill Skill to add.
   */
  public void addSkill(SkillReward skill)
  {
    if (_skills==null)
    {
      _skills=new ArrayList<SkillReward>();
    }
    _skills.add(skill);
  }

  /**
   * Get all skills.
   * @return An array of skills or <code>null</code> if there's none.
   */
  public SkillReward[] getSkills()
  {
    SkillReward[] ret=null;
    if (_skills!=null)
    {
      ret=new SkillReward[_skills.size()];
      ret=_skills.toArray(ret);
    }
    return ret;
  }

  /**
   * Add a title.
   * @param title Title to add.
   */
  public void addTitle(TitleReward title)
  {
    if (_titles==null)
    {
      _titles=new ArrayList<TitleReward>();
    }
    _titles.add(title);
  }

  /**
   * Get all titles.
   * @return An array of titles or <code>null</code> if there's none.
   */
  public TitleReward[] getTitles()
  {
    TitleReward[] ret=null;
    if (_titles!=null)
    {
      ret=new TitleReward[_titles.size()];
      ret=_titles.toArray(ret);
    }
    return ret;
  }

  /**
   * Add a virtue.
   * @param virtue Virtue to add.
   */
  public void addVirtue(VirtueReward virtue)
  {
    if (_virtues==null)
    {
      _virtues=new ArrayList<VirtueReward>();
    }
    _virtues.add(virtue);
  }

  /**
   * Get all virtues.
   * @return An array of virtues or <code>null</code> if there's none.
   */
  public VirtueReward[] getVirtues()
  {
    VirtueReward[] ret=null;
    if (_virtues!=null)
    {
      ret=new VirtueReward[_virtues.size()];
      ret=_virtues.toArray(ret);
    }
    return ret;
  }

  /**
   * Add a emote.
   * @param emote Emote to add.
   */
  public void addEmote(EmoteReward emote)
  {
    if (_emotes==null)
    {
      _emotes=new ArrayList<EmoteReward>();
    }
    _emotes.add(emote);
  }

  /**
   * Get all titles.
   * @return An array of titles or <code>null</code> if there's none.
   */
  public EmoteReward[] getEmotes()
  {
    EmoteReward[] ret=null;
    if (_emotes!=null)
    {
      ret=new EmoteReward[_emotes.size()];
      ret=_emotes.toArray(ret);
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    boolean firstDone=false;
    if (!_money.isEmpty())
    {
      sb.append(_money);
      firstDone=true;
    }
    if (!_reputation.isEmpty())
    {
      if (firstDone) sb.append(" / ");
      sb.append(_reputation);
      firstDone=true;
    }
    if (_destinyPoints>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append(_destinyPoints).append(" destiny points");
      firstDone=true;
    }
    if (_lotroPoints>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append(_lotroPoints).append(" LOTRO points");
      firstDone=true;
    }
    if (_classPoints>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append(_classPoints).append(" class points");
      firstDone=true;
    }
    int nbObjects=_objects.getNbObjectItems();
    if (nbObjects>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append(_objects);
      firstDone=true;
    }
    int nbSelectObjects=_selectObjects.getNbObjectItems();
    if (nbSelectObjects>0)
    {
      if (firstDone) sb.append(" / Select one of: ");
      sb.append(_selectObjects);
      firstDone=true;
    }
    if (_traits!=null)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Traits: ");
      sb.append(_traits);
      firstDone=true;
    }
    if (_skills!=null)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Skills: ");
      sb.append(_skills);
      firstDone=true;
    }
    if (_titles!=null)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Titles: ");
      sb.append(_titles);
      firstDone=true;
    }
    if (_virtues!=null)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Virtues: ");
      sb.append(_virtues);
      firstDone=true;
    }
    if (_emotes!=null)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Emotes: ");
      sb.append(_emotes);
      firstDone=true;
    }
    if (_itemXP)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Item XP");
      firstDone=true;
    }
    return sb.toString();
  }
}
