package jessica.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jessica.Wrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class IPInfoUtils {
  private static IPInfoUtils instance;
  
  public JsonObject object;
  
  public String server = "";
  
  public static IPInfoUtils getInstance() {
    return instance;
  }
  
  public IPInfoUtils(String ip) {
	ip = Wrapper.mc().player.connection.getNetworkManager().getRemoteAddress().toString();
    instance = this;
    this.server = "https://ipinfo.io/" + ip + "/json";
    this.object = (new JsonParser()).parse(websitedata(this.server)).getAsJsonObject();
  }
  
  public String getHostname() {
	  return getObjectString("hostname");
  }
  public String getIp() {
	  return getObjectString("ip");
  }
  public String getCity() {
	  return getObjectString("city");
  }
  public String getRegion() {
	  return getObjectString("region");
  }
  public String getCountry() {
	  return getObjectString("country");
  }
  public String getCoord() {
	  return getObjectString("loc");
  }
  public String getOrg() {
	  return getObjectString("org");
  }
  public String getZip() {
	  return getObjectString("postal");
  }
  public String getTimeZone() {
	  return getObjectString("timezone");
  }
  
  
  public String getObjectString(String obj) {
    try {
      return this.object.get(obj).getAsString();
    } catch (Exception e) {
      return "Pinging...";
    } 
  }
  
  public String websitedata(String website) {
	  String content = "";
    try {
      URL url = new URL(website);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
      content = format((List<String>) bufferedReader.lines().collect((Collector)Collectors.toList()));
      bufferedReader.close();
      return content;
    } catch (Exception e) {
    	e.printStackTrace();
    }
	return content;
  }
  
  private String format(List<String> arrayList) {
    String out = "";
    for (String entry : arrayList)
      out = String.valueOf(out) + entry + "\n"; 
    return out;
  }
}
