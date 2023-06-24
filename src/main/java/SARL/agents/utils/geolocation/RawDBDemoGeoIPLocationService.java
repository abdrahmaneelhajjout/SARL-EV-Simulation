package SARL.agents.utils.geolocation;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class RawDBDemoGeoIPLocationService{
    private DatabaseReader dbReader;
    
    public RawDBDemoGeoIPLocationService() throws Exception {
    	URL resource = getClass().getClassLoader().getResource("GeoLite2-City.mmdb");
    	 if (resource == null) {
    	      throw new IllegalArgumentException("file not found!");
    	  } else {

        File database = new File(resource.toURI());
        dbReader = new DatabaseReader.Builder(database).build();
    	  }
    }
    
    public GeoIP getLocation(String ip) 
      throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        
        String cityName = response.getCity().getName();
        String latitude = 
          response.getLocation().getLatitude().toString();
        String longitude = 
          response.getLocation().getLongitude().toString();
        return new GeoIP(ip, cityName, latitude, longitude);
    }
}