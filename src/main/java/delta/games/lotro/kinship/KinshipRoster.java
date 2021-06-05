package delta.games.lotro.kinship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.kinship.comparators.KinshipMemberIdComparator;

/**
 * Kinship roster.
 * @author DAM
 */
public class KinshipRoster
{
  private Map<Integer,KinshipRank> _ranks;
  private Map<Long,KinshipMember> _members;

  /**
   * Constructor.
   */
  public KinshipRoster()
  {
    _ranks=new HashMap<Integer,KinshipRank>();
    _members=new HashMap<Long,KinshipMember>();
  }

  /**
   * Define a new rank.
   * @param rank Rank to add.
   */
  public void addRank(KinshipRank rank)
  {
    Integer key=Integer.valueOf(rank.getCode());
    _ranks.put(key,rank);
  }

  /**
   * Add a member.
   * @param member Member to add.
   */
  public void addMember(KinshipMember member)
  {
    Long key=member.getID();
    if (key!=null)
    {
      _members.put(key,member);
    }
  }

  /**
   * Get the defined ranks, sorted by code.
   * @return a list of ranks.
   */
  public List<KinshipRank> getRanks()
  {
    List<KinshipRank> ret=new ArrayList<KinshipRank>();
    ret.addAll(_ranks.values());
    // TODO Sort
    return ret;
  }

  /**
   * Get the members.
   * @return a list of members (sorted by member ID).
   */
  public List<KinshipMember> getAllMembers()
  {
    List<KinshipMember> ret=new ArrayList<KinshipMember>();
    ret.addAll(_members.values());
    Collections.sort(ret,new KinshipMemberIdComparator());
    return ret;
  }

  /**
   * Get a kinship rank using its code.
   * @param code A code.
   * @return A kinship rank or <code>null</code> if not found.
   */
  public KinshipRank getRankByCode(int code)
  {
    return _ranks.get(Integer.valueOf(code));
  }

  /**
   * Get a kinship member using its identifier.
   * @param characterID Character identifiier.
   * @return A kinship member or <code>null</code> if not found.
   */
  public KinshipMember getMemberByID(long characterID)
  {
    return _members.get(Long.valueOf(characterID));
  }
}
