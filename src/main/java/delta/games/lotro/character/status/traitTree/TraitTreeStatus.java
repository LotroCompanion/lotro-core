package delta.games.lotro.character.status.traitTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.common.utils.misc.IntegerHolder;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.classes.traitTree.TraitTreeCell;
import delta.games.lotro.character.classes.traitTree.TraitTreeCellDependency;
import delta.games.lotro.character.traits.TraitDescription;

/**
 * Trait tree status.
 * @author DAM
 */
public class TraitTreeStatus
{
  private static final Logger LOGGER=Logger.getLogger(TraitTreeStatus.class);

  private TraitTree _tree;
  private TraitTreeBranch _selectedBranch;
  private Map<Integer,IntegerHolder> _treeRanks;
  private int _cost;

  /**
   * Constructor.
   * @param tree Trait tree to use.
   */
  public TraitTreeStatus(TraitTree tree)
  {
    _tree=tree;
    _cost=0;
    _selectedBranch=_tree.getBranches().get(0);
    initRanks();
  }

  private void initRanks()
  {
    _treeRanks=new HashMap<Integer,IntegerHolder>();
    for(TraitTreeBranch branch : _tree.getBranches())
    {
      for(String cellId : branch.getCells())
      {
        TraitDescription trait=branch.getTraitForCell(cellId);
        Integer key=Integer.valueOf(trait.getIdentifier());
        _treeRanks.put(key,new IntegerHolder());
      }
    }
  }

  /**
   * Get the managed trait tree.
   * @return the managed trait tree.
   */
  public TraitTree getTraitTree()
  {
    return _tree;
  }

  /**
   * Reset rank values.
   */
  public void reset()
  {
    for(IntegerHolder rankValue : _treeRanks.values())
    {
      rankValue.setInt(0);
    }
  }

  /**
   * Get the selected branch.
   * @return the selected branch.
   */
  public TraitTreeBranch getSelectedBranch()
  {
    return _selectedBranch;
  }

  /**
   * Set the selected branch.
   * @param selectedBranch Branch to set.
   */
  public void setSelectedBranch(TraitTreeBranch selectedBranch)
  {
    _selectedBranch=selectedBranch;
  }

  /**
   * Get the total number of activated ranks in the whole tree. 
   * @return A ranks count.
   */
  public int getTotalRanksInTree()
  {
    int totalRanks=0;
    for(IntegerHolder rank : _treeRanks.values())
    {
      totalRanks+=rank.getInt();
    }
    return totalRanks;
  }

  /**
   * Get the number of activated ranks in the specified row of the specified branch.
   * @param branch Branch to use.
   * @param row Row, starting at 1.
   * @return A ranks count.
   */
  private int getRanksForRow(TraitTreeBranch branch, int row)
  {
    int totalRanks=0;
    String seed=String.valueOf(row)+"_";
    for(String cellId : branch.getCells())
    {
      if (cellId.startsWith(seed))
      {
        TraitTreeCell cell=branch.getCell(cellId);
        TraitDescription trait=cell.getTrait();
        Integer key=Integer.valueOf(trait.getIdentifier());
        int ranks=_treeRanks.get(key).getInt();
        totalRanks+=ranks;
      }
    }
    return totalRanks;
  }

  /**
   * Get the number of activated ranks in the given first rows of the specified branch.
   * @param branch Branch to use.
   * @param rows Rows to use.
   * @return A ranks count.
   */
  private int getRanksForRows(TraitTreeBranch branch, int rows)
  {
    int totalRanks=0;
    for(int row=1;row<=rows;row++)
    {
      totalRanks+=getRanksForRow(branch,row);
    }
    return totalRanks;
  }

  /**
   * Get the rank for the given cell.
   * @param cellId Cell identifier.
   * @return A rank value or <code>null</code> if no such cell.
   */
  public Integer getRankForCell(String cellId)
  {
    TraitTreeCell cell=getCellById(cellId);
    if (cell!=null)
    {
      TraitDescription trait=cell.getTrait();
      return getRankForTrait(trait.getIdentifier());
    }
    return null;
  }

  /**
   * Get the rank for the given trait.
   * @param traitId Trait identifier.
   * @return A rank value.
   */
  public Integer getRankForTrait(int traitId)
  {
    Integer key=Integer.valueOf(traitId);
    IntegerHolder ret=_treeRanks.get(key);
    return ret!=null?Integer.valueOf(ret.getInt()):null;
  }

  /**
   * Set the rank for the given trait.
   * @param traitId Trait identifier.
   * @param value Rank value to set.
   */
  public void setRankForTrait(int traitId, int value)
  {
    Integer key=Integer.valueOf(traitId);
    IntegerHolder intHolder=_treeRanks.get(key);
    if (intHolder!=null)
    {
      intHolder.setInt(value);
    }
  }

  /**
   * Set the rank for the specified cell.
   * @param cellId Cell identifier.
   * @param rank Rank to set.
   */
  public void setRankForCell(String cellId, int rank)
  {
    TraitTreeCell cell=getCellById(cellId);
    TraitDescription trait=cell.getTrait();
    setRankForTrait(trait.getIdentifier(),rank);
  }

  /**
   * Get the cost of this setup.
   * @return a cost in trait points.
   */
  public int getCost()
  {
    return _cost;
  }

  /**
   * Set the cost of this setup.
   * @param cost Cost to set.
   */
  public void setCost(int cost)
  {
    _cost=cost;
  }

  /**
   * Get the total cost for this tree.
   * @return a cost in trait points.
   */
  public int computeCost()
  {
    int total=0;
    for(TraitTreeBranch branch : _tree.getBranches())
    {
      int ranks=getRanksForRows(branch,10); // Assume max 10 ranks
      int factor=(branch==_selectedBranch)?1:2;
      total+=factor*ranks;
    }
    return total;
  }

  /**
   * Indicates if a cell is enabled or not.
   * @param cellId Identifier of the cell to test.
   * @return <code>true</code> if enabled, <code>false</code> otherwise.
   */
  public boolean isEnabled(String cellId)
  {
    return checkDependencies(cellId) && checkRanksToEnableCell(cellId);
  }

  private boolean checkRanksToEnableCell(String cellId)
  {
    int row=getRowForCell(cellId);
    if (row==1)
    {
      return true;
    }
    TraitTreeBranch branch=getBranchForCell(cellId);
    int neededRanks=branch.getProgression().getSteps().get(row-2).intValue();
    int gotRanks=getRanksForRows(branch,row-1);
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Cell "+cellId+" got ranks: "+gotRanks+", needed: "+neededRanks);
    }
    return gotRanks>=neededRanks;
  }

  private boolean checkDependencies(String cellId)
  {
    TraitTreeCell cell=getCellById(cellId);
    if (cell==null)
    {
      return false;
    }
    List<TraitTreeCellDependency> dependencies=cell.getDependencies();
    for(TraitTreeCellDependency dependency : dependencies)
    {
      String depCellId=dependency.getCellId();
      TraitTreeCell depCell=getCellById(depCellId);
      TraitDescription trait=depCell.getTrait();
      Integer key=Integer.valueOf(trait.getIdentifier());
      int ranks=_treeRanks.get(key).getInt();
      if (ranks<dependency.getRank())
      {
        if (LOGGER.isDebugEnabled())
        {
          LOGGER.debug("Cell "+cellId+": dependency not met on trait "+trait+": ranks="+ranks+", requires: "+dependency.getRank());
        }
        return false;
      }
    }
    return true;
  }

  private TraitTreeBranch getBranchForCell(String cellId)
  {
    for(TraitTreeBranch branch : _tree.getBranches())
    {
      TraitTreeCell cell=branch.getCell(cellId);
      if (cell!=null)
      {
        return branch;
      }
    }
    return null;
  }

  private TraitTreeCell getCellById(String cellId)
  {
    for(TraitTreeBranch branch : _tree.getBranches())
    {
      TraitTreeCell cell=branch.getCell(cellId);
      if (cell!=null)
      {
        return cell;
      }
    }
    return null;
  }

  private int getRowForCell(String cellId)
  {
    int index=cellId.indexOf('_');
    int y=NumericTools.parseInt(cellId.substring(0,index),0);
    return y;
  }

  /**
   * Copy the contents of this status from the given source.
   * @param source Source to use.
   */
  public void copyFrom(TraitTreeStatus source)
  {
    _selectedBranch=source._selectedBranch;
    _cost=source._cost;
    // Reset
    reset();
    for(Map.Entry<Integer,IntegerHolder> entry : source._treeRanks.entrySet())
    {
      int traitId=entry.getKey().intValue();
      int rank=entry.getValue().getInt();
      setRankForTrait(traitId,rank);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Trait tree status:");
    if (_selectedBranch!=null)
    {
      sb.append(" selected branch=").append(_selectedBranch.getName());
    }
    sb.append(" ranks=").append(_treeRanks);
    return sb.toString();
  }
}
