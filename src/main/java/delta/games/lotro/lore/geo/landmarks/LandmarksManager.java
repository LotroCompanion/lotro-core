package delta.games.lotro.lore.geo.landmarks;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.geo.landmarks.io.xml.LandmarksXMLParser;

/**
 * Manager for all landmarks.
 * @author DAM
 */
public class LandmarksManager
{
  private static LandmarksManager _instance=null;
  private Map<Integer,LandmarkDescription> _landmarks;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static LandmarksManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  private static LandmarksManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.LANDMARKS);
    return new LandmarksXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public LandmarksManager()
  {
    _landmarks=new HashMap<Integer,LandmarkDescription>();
  }

  /**
   * Add a landmark.
   * @param landmark Landmark to add.
   */
  public void addLandmark(LandmarkDescription landmark)
  {
    Integer key=Integer.valueOf(landmark.getIdentifier());
    _landmarks.put(key,landmark);
  }

  /**
   * Get a landmark using its identifier.
   * @param landmarkId Landmark identifier.
   * @return A landmark or <code>null</code> if not found.
   */
  public LandmarkDescription getLandmarkById(int landmarkId)
  {
    return _landmarks.get(Integer.valueOf(landmarkId));
  }

  /**
   * Get all landmarks.
   * @return a list of landmarks.
   */
  public List<LandmarkDescription> getLandmarks()
  {
    List<LandmarkDescription> ret=new ArrayList<LandmarkDescription>();
    ret.addAll(_landmarks.values());
    Collections.sort(ret,new IdentifiableComparator<LandmarkDescription>());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    List<LandmarkDescription> landmarks=getLandmarks();
    out.println("Landmarks: ("+landmarks.size()+")");
    for(LandmarkDescription landmark : landmarks)
    {
      out.println("\t"+landmark);
    }
  }
}
