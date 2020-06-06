package delta.games.lotro.config.servers;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.config.servers.io.xml.ServerXMLParser;

/**
 * Facade for servers access.
 * @author DAM
 */
public class ServersManager
{
  private static final Logger LOGGER=Logger.getLogger(ServersManager.class);

  private static ServersManager _instance=null;

  private List<ServerDescription> _cache;
  private Map<InetAddress,ServerDescription> _mapByAddress;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ServersManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ServersManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private ServersManager()
  {
    _cache=new ArrayList<ServerDescription>(10);
    _mapByAddress=new HashMap<InetAddress,ServerDescription>();
    loadAll();
  }

  /**
   * Load all servers.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File serversFile=cfg.getFile(DataFiles.SERVERS_DESCRIPTION);
    long now=System.currentTimeMillis();
    List<ServerDescription> servers=new ServerXMLParser().parseXML(serversFile);
    for(ServerDescription server : servers)
    {
      _cache.add(server);
      InetAddress address=server.getAddress();
      if (address!=null)
      {
        _mapByAddress.put(address,server);
      }
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" servers in "+duration+"ms.");
  }

  /**
   * Get a list of all emotes, sorted by identifier.
   * @return A list of emotes.
   */
  public List<ServerDescription> getAll()
  {
    return new ArrayList<ServerDescription>(_cache);
  }

  /**
   * Get a server using its address.
   * @param address Address to use.
   * @return A server description or <code>null</code> if not found.
   */
  public ServerDescription getServerByAddress(InetAddress address)
  {
    return _mapByAddress.get(address);
  }
}
