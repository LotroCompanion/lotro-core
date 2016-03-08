package delta.games.lotro.character.stats.virtues;

import java.util.HashMap;

import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.VirtueId;

/**
 * Manager for contributions of virtues to player stats.
 * @author DAM
 */
public class VirtuesContributionsMgr
{
  private HashMap<VirtueId,VirtueContributionTable> _contribs;

  /**
   * Constructor.
   */
  public VirtuesContributionsMgr()
  {
    _contribs=new HashMap<VirtueId,VirtueContributionTable>();
    init();
  }

  /**
   * Get the contribution for a given virtue and rank.
   * @param virtueId Virtue identifier.
   * @param rank Rank (starting at 1).
   * @return A stats set or <code>null</code> if not found.
   */
  public BasicStatsSet getContribution(VirtueId virtueId, int rank)
  {
    BasicStatsSet stats=null;
    VirtueContributionTable table=_contribs.get(virtueId);
    if (table!=null)
    {
      stats=table.getContrib(rank);
    }
    return stats;
  }

  private void init()
  {
    // Charity
    {
      STAT[] stats={STAT.RESISTANCE,STAT.PHYSICAL_MITIGATION,STAT.OCPR};
      int[][] values={ {1,173,51,34}, {2,345,97,53}, {3,518,146,71}, {4,690,194,90},
          {5,862,243,109}, {6,1034,292,128}, {7,1207,340,146}, {8,1380,389,165},
          {9,1552,437,184}, {10,1725,486,203}, {11,1897,535,222}, {12,2070,583,241},
          {13,2242,632,259}, {14,2415,680,278}, {15,2587,729,297}, {16,2760,778,316},
          {17,2933,826,335}, {18,3105,875,353}, {19,3278,923,372}
      };
      VirtueContributionTable charity=new VirtueContributionTable();
      charity.setContribs(stats,values);
      _contribs.put(VirtueId.CHARITY,charity);
    }
    // Compassion
    {
      STAT[] stats= {STAT.PHYSICAL_MITIGATION,STAT.OCPR,STAT.TACTICAL_MITIGATION};
      int[][] values= { {1,84,51,29}, {2,162,79,54}, {3,243,107,81}, {4,324,135,108},
          {5,405,163,135}, {6,486,191,162}, {7,567,219,189}, {8,648,248,216},
          {9,729,276,243}, {10,810,304,270}, {11,891,333,297}, {12,972,361,324},
          {13,1053,389,351}, {14,1134,417,378}, {15,1215,445,405}, {16,1296,474,432},
          {17,1377,502,459}, {18,1458,530,486}, {19,1539,558,513,}};
      VirtueContributionTable compassion=new VirtueContributionTable();
      compassion.setContribs(stats,values);
      _contribs.put(VirtueId.COMPASSION,compassion);
    }
    // Confidence
    {
      STAT[] stats= {STAT.RESISTANCE,STAT.WILL,STAT.ICPR};
      int[][] values= { {1,173,6,4}, {2,345,12,8}, {3,518,18,12}, {4,690,24,16},
          {5,862,30,20}, {6,1034,36,24}, {7,1207,42,28}, {8,1380,48,32},
          {9,1552,54,36}, {10,1725,60,41}, {11,1897,66,45}, {12,2070,72,49},
          {13,2242,78,53}, {14,2415,84,57}, {15,2587,90,61}, {16,2760,96,65},
          {17,2933,102,69}, {18,3105,108,73}, {19,3278,114,77}};
      VirtueContributionTable confidence=new VirtueContributionTable();
      confidence.setContribs(stats,values);
      _contribs.put(VirtueId.CONFIDENCE,confidence);
    }
    // Determination
    {
      STAT[] stats= {STAT.AGILITY,STAT.ICMR,STAT.MORALE};
      int[][] values= { {1,9,7,12}, {2,18,17,19}, {3,27,25,26}, {4,36,34,33},
          {5,45,42,40}, {6,54,51,47}, {7,63,59,54}, {8,72,68,58},
          {9,81,76,65}, {10,90,84,72}, {11,99,93,79}, {12,108,101,86},
          {13,117,110,91}, {14,126,118,98}, {15,135,127,105}, {16,144,135,112},
          {17,153,143,117}, {18,162,152,123}, {19,171,160,130}};
      VirtueContributionTable determination=new VirtueContributionTable();
      determination.setContribs(stats,values);
      _contribs.put(VirtueId.DETERMINATION,determination);
    }
    // Discipline
    {
      STAT[] stats= {STAT.MIGHT,STAT.RESISTANCE,STAT.PHYSICAL_MITIGATION};
      int[][] values= { {1,9,89,29}, {2,18,175,54}, {3,27,261,81}, {4,36,347,108},
          {5,45,433,135}, {6,54,519,162}, {7,63,604,189}, {8,72,690,216},
          {9,81,776,243}, {10,90,862,270}, {11,99,948,297}, {12,108,1034,324},
          {13,117,1120,351}, {14,126,1207,378}, {15,135,1293,405}, {16,144,1379,432},
          {17,153,1465,459}, {18,162,1551,486}, {19,171,1638,513}};
      VirtueContributionTable discipline=new VirtueContributionTable();
      discipline.setContribs(stats,values);
      _contribs.put(VirtueId.DISCIPLINE,discipline);
    }
    // Empathy
    {
      STAT[] stats= {STAT.ARMOUR,STAT.FATE,STAT.RESISTANCE};
      int[][] values= { {1,45,4,52}, {2,90,8,104}, {3,135,12,155}, {4,180,16,207},
          {5,225,20,259}, {6,270,24,311}, {7,315,28,362}, {8,360,32,414},
          {9,405,36,466}, {10,450,40,518}, {11,495,44,569}, {12,540,48,621},
          {13,585,52,673}, {14,630,56,725}, {15,675,60,776}, {16,720,64,828},
          {17,765,68,880}, {18,810,72,932}, {19,855,76,984}};
      VirtueContributionTable empathy=new VirtueContributionTable();
      empathy.setContribs(stats,values);
      _contribs.put(VirtueId.EMPATHY,empathy);
    }
    // Fidelity
    {
      STAT[] stats= {STAT.TACTICAL_MITIGATION,STAT.VITALITY,STAT.POWER};
      int[][] values= { {1,84,4,5}, {2,162,8,8}, {3,243,12,11}, {4,324,16,14},
          {5,405,20,17}, {6,486,24,20}, {7,567,28,23}, {8,648,32,25},
          {9,729,36,28}, {10,810,40,31}, {11,891,44,34}, {12,972,48,37},
          {13,1053,52,39}, {14,1134,56,42}, {15,1215,60,45}, {16,1296,64,48},
          {17,1377,68,50}, {18,1458,72,53}, {19,1539,76,56}};
      VirtueContributionTable fidelity=new VirtueContributionTable();
      fidelity.setContribs(stats,values);
      _contribs.put(VirtueId.FIDELITY,fidelity);
    }
    // Fortitude
    {
      STAT[] stats= {STAT.OCMR,STAT.MIGHT,STAT.RESISTANCE};
      int[][] values= { {1,69,6,52}, {2,122,12,104}, {3,174,18,155}, {4,227,24,207},
          {5,279,30,259}, {6,332,36,311}, {7,384,42,362}, {8,437,48,414},
          {9,489,54,466}, {10,542,60,518}, {11,594,66,569}, {12,647,72,621},
          {13,700,78,673}, {14,753,84,725}, {15,806,90,776}, {16,858,96,828},
          {17,911,102,880}, {18,964,108,932}, {19,1017,114,984}};
      VirtueContributionTable fortitude=new VirtueContributionTable();
      fortitude.setContribs(stats,values);
      _contribs.put(VirtueId.FORTITUDE,fortitude);
    }
    // Honesty
    {
      STAT[] stats= {STAT.POWER,STAT.ARMOUR,STAT.FATE};
      int[][] values= { {1,20,27,2}, {2,32,54,4}, {3,43,81,6}, {4,54,108,8},
          {5,66,135,10}, {6,77,162,12}, {7,89,189,14}, {8,100,216,16},
          {9,111,243,18}, {10,123,270,20}, {11,134,297,22}, {12,145,324,24},
          {13,156,351,26}, {14,167,378,28}, {15,178,405,30}, {16,189,432,32},
          {17,200,459,34}, {18,211,486,36}, {19,222,513,38}};
      VirtueContributionTable honesty=new VirtueContributionTable();
      honesty.setContribs(stats,values);
      _contribs.put(VirtueId.HONESTY,honesty);
    }
    // Honour
    {
      STAT[] stats= {STAT.RESISTANCE,STAT.TACTICAL_MITIGATION,STAT.VITALITY};
      int[][] values= { {1,173,51,2}, {2,345,97,4}, {3,518,146,6}, {4,690,194,8},
          {5,862,243,10}, {6,1034,292,12}, {7,1207,340,14}, {8,1380,389,16},
          {9,1552,437,18}, {10,1725,486,20}, {11,1897,535,22}, {12,2070,583,24},
          {13,2242,632,26}, {14,2415,680,28}, {15,2587,729,30}, {16,2760,778,32},
          {17,2933,826,34}, {18,3105,875,36}, {19,3278,923,38}};
      VirtueContributionTable honour=new VirtueContributionTable();
      honour.setContribs(stats,values);
      _contribs.put(VirtueId.HONOUR,honour);
    }
    // Idealism
    {
      STAT[] stats= {STAT.FATE,STAT.RESISTANCE,STAT.WILL};
      int[][] values= { {1,6,89,3}, {2,12,175,6}, {3,18,261,9}, {4,24,347,12},
          {5,30,433,15}, {6,36,519,18}, {7,42,604,21}, {8,48,690,24},
          {9,54,776,27}, {10,60,862,30}, {11,66,948,33}, {12,72,1034,36},
          {13,78,1120,39}, {14,84,1207,42}, {15,90,1293,45}, {16,96,1379,48},
          {17,102,1465,51}, {18,108,1551,54}, {19,114,1638,57}};
      VirtueContributionTable idealism=new VirtueContributionTable();
      idealism.setContribs(stats,values);
      _contribs.put(VirtueId.IDEALISM,idealism);
    }
  }
}
