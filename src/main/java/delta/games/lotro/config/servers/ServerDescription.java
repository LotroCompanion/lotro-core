package delta.games.lotro.config.servers;

import java.net.InetAddress;

/**
 * Server desciption.
 * @author DAM
 */
public class ServerDescription
{
  private String _name;
  private InetAddress _address;
  private String _location;

  /**
   * Constructor.
   */
  public ServerDescription()
  {
    _name="";
    _address=null;
    _location="";
  }

  /**
   * Get the server name.
   * @return the server name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the server name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the server address.
   * @return the server address.
   */
  public InetAddress getAddress()
  {
    return _address;
  }

  /**
   * Set the server address.
   * @param address the address to set.
   */
  public void setAddress(InetAddress address)
  {
    _address=address;
  }

  /**
   * Get the server location.
   * @return the server location.
   */
  public String getLocation()
  {
    return _location;
  }

  /**
   * Set the server location.
   * @param location the location to set.
   */
  public void setLocation(String location)
  {
    _location=location;
  }

  @Override
  public String toString()
  {
    return "Server: "+_name+" ("+_location+") @"+_address;
  }
}
