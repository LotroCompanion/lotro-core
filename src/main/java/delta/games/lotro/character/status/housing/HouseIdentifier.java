package delta.games.lotro.character.status.housing;

/**
 * House identifier: server+address.
 * @author DAM
 */
public class HouseIdentifier
{
  private String _server;
  private HouseAddress _address; 

  /**
   * Constructor.
   * @param server Server name.
   * @param address Address.
   */
  public HouseIdentifier(String server, HouseAddress address)
  {
    _server=server;
    _address=address;
  }

  /**
   * Get the server name.
   * @return a server name.
   */
  public String getServer()
  {
    return _server;
  }

  /**
   * Get the house address.
   * @return the house address.
   */
  public HouseAddress getAddress()
  {
    return _address;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("House identifier: server=");
    sb.append(_server);
    sb.append(", address=");
    sb.append(_address);
    return sb.toString();
  }
}
