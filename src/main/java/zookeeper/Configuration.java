package zookeeper;


import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//import storage.FileServices;



/**
 * Application Lifecycle Listener implementation class Zooconf
 *
 */
@WebListener
public class Configuration implements ServletContextListener {
	private   List<String> zookeeperIPs = new ArrayList<String>();
	private String host="";
	private  String myip;
	private  String identifier;
	private  String secretkey;
	private  String name;
	private  String dirpath;
	private  String authpath;
	private  String webpath;
	private  String zoouser; 
	private  String zoopass; 
	private  ZooKeeper zoo;
	private List<String> dirList = null;
	private List<String> authList = null;
	private List<Map> DirSystems=new ArrayList<Map>();
	private List<Map> AuthSystems=new ArrayList<Map>();
	final CountDownLatch connectedSignal = new CountDownLatch(1);
	private static Configuration ConfInstance = null;

	

	   
	public  List<Map> getAvailableDirectorySystems() {
		
		return Configuration.getInstance().DirSystems;
	}	
	
	public  List<Map> getAvailableAuthSystems() {
		return Configuration.getInstance().AuthSystems;
	}	
	
	public  String getKey() {
		return Configuration.getInstance().secretkey;
	}
	
	public  String getMyIdentifier() {
		return Configuration.getInstance().identifier;
	}
	
	public  String getMyIP() {
		return Configuration.getInstance().myip;
	}
	
	public  String getZookeeperIPs(){
		
		boolean first=true;
		for (String ip:zookeeperIPs) {
			if (first) {
				Configuration.getInstance().host=ip;
				first =false;
			}
			else
				Configuration.getInstance().host= Configuration.getInstance().host+","+ip;				
		}
		return Configuration.getInstance().host;
	}
	
	private ZooKeeper zooConnect() throws IOException,InterruptedException {
		System.out.println("start zooConnect on "+getZookeeperIPs());
		
		ZooKeeper zk = new ZooKeeper(getZookeeperIPs(), 3000, new Watcher() {
			@Override
			public void process(WatchedEvent we) {
				if (we.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});
		connectedSignal.await();
		
		zk.addAuthInfo("digest", new String(zoouser+":"+zoopass).getBytes());
		
		System.out.println("finished zooConnect");

		return zk;
	}

	public  ZooKeeper getZooConnection() {
		return Configuration.getInstance().zoo;
	}
class DirWatcher implements Watcher {
        
        public void process(WatchedEvent event) {
            System.err.println("Watcher triggered");
			Watcher watcher = new DirWatcher();
			watchForDirChanges(watcher);
        }
    }

	private void initDirWatches() {
		Configuration.getInstance().dirList = Collections.synchronizedList(new ArrayList<String>());
		Watcher watcher = new DirWatcher();
		watchForDirChanges(watcher);
	}
	
	
	private void watchForDirChanges(Watcher watcher) {
		// we want to get the list of available FS, and watch for changes
		try {
			Configuration.getInstance().DirSystems.clear();
			List<String> dirChildren = Configuration.getInstance().zoo.getChildren(Configuration.getInstance().dirpath, watcher);
			for (String dir : dirChildren) {
				Map<String,String> system = null;
				Stat stat = Configuration.getInstance().zoo.exists(Configuration.getInstance().dirpath+"/"+dir, watcher);
				byte[] data=Configuration.getInstance().zoo.getData(Configuration.getInstance().dirpath+"/"+dir, watcher, stat);
				JSONObject datajson=new JSONObject(new String(data));
				system=new HashMap<String,String>();
				system.put("identifier", datajson.getString("id"));
				system.put("URL", datajson.getString("URL"));
				system.put("keybase64", datajson.getString("key"));
				Configuration.getInstance().DirSystems.add(system);	
			}
		}
		catch (KeeperException ex) {
			System.err.println("getStatusText KeeperException "+ex.getMessage());
		}
		catch (InterruptedException ex) {
			System.err.println("getStatusText InterruptedException");
		}
		////FileServices.updateDB();
		
	}
	
	
class AuthWatcher implements Watcher {
        
        public void process(WatchedEvent event) {
            System.err.println("Watcher triggered");
			Watcher watcher = new AuthWatcher();
			watchForAuthChanges(watcher);
        }
    }

	private void initAuthWatches() {
		authList = Collections.synchronizedList(new ArrayList<String>());
		Watcher watcher = new AuthWatcher();
		watchForAuthChanges(watcher);
	}
	
	
	private void watchForAuthChanges(Watcher watcher) {
		// we want to get the list of available FS, and watch for changes
		try {
			AuthSystems.clear();
			List<String> authChildren = Configuration.getInstance().zoo.getChildren(authpath, watcher);
			for (String auth : authChildren) {
				Map<String,String> system = null;
				Stat stat = Configuration.getInstance().zoo.exists(Configuration.getInstance().authpath+"/"+auth, watcher);
				byte[] data=Configuration.getInstance().zoo.getData(Configuration.getInstance().authpath+"/"+auth, watcher, stat);
				JSONObject datajson=new JSONObject(new String(data));
				system=new HashMap<String,String>();
				system.put("identifier", datajson.getString("id"));
				system.put("URL", datajson.getString("URL"));
				system.put("keybase64", datajson.getString("key"));
				Configuration.getInstance().AuthSystems.add(system);	
			}
		}
		catch (KeeperException ex) {
			System.err.println("getStatusText KeeperException "+ex.getMessage());
		}
		catch (InterruptedException ex) {
			System.err.println("getStatusText InterruptedException");
		}
		//FileServices.updateDB();
		
	}
	
	
	
	public void ReadConfigurationFile() {
		
		
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path + "/config.xml";
		//Read configuration file
	        try {
	            File inputFile = new File(path);
	            SAXBuilder saxBuilder = new SAXBuilder();
	            Document document = saxBuilder.build(inputFile);
	            System.out.println("Root element :" + document.getRootElement().getName());
	            Element classElement = document.getRootElement();
	            System.out.println("----------------------------");
	            Element setting=classElement.getChild("zookeeper");
	            System.out.println("\nCurrent Element dirservice :" 
		                  + setting.getName());
	            List<Element>ips=setting.getChildren("zooip");
	            for(Element ip:ips) {
	            	Configuration.getInstance().zookeeperIPs.add(ip.getValue().toString());
	            	System.out.println(ip.getValue().toString());
	                
	            	
	            }
	            Configuration.getInstance().dirpath=setting.getChild("dirservicepath").getValue();
	            Configuration.getInstance().authpath=setting.getChild("authservicepath").getValue();
	            Configuration.getInstance().webpath=setting.getChild("webservicepath").getValue();
	            Configuration.getInstance().zoouser=setting.getChild("zoouser").getValue();
	            Configuration.getInstance().zoopass=setting.getChild("zoopass").getValue();
	            Configuration.getInstance().identifier=classElement.getChild("identifier").getValue();    
	            Configuration.getInstance().secretkey=classElement.getChild("skkey").getValue();
	            Configuration.getInstance().name=classElement.getChild("name").getValue();
	            Configuration.getInstance().myip=classElement.getChild("hostname").getValue();
	            
	        

	            
	         } catch(JDOMException e) {
	            e.printStackTrace();
	         } catch(IOException ioe) {
	            ioe.printStackTrace();
	         }
		
	}
	public void PublishService(ServletContextEvent sce) {
		
		ACL acl = null;
		try {
			String base64EncodedSHA1Digest = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA1").digest((Configuration.getInstance().zoouser+":"+Configuration.getInstance().zoopass).getBytes()));
			acl = new ACL(ZooDefs.Perms.ALL, new Id("digest",Configuration.getInstance().zoouser+":" + base64EncodedSHA1Digest));
		}
		catch (NoSuchAlgorithmException ex) {
			System.err.println("destroy NoSuchAlgorithmException");
		}
		
	     
	       JSONObject configJSON=new JSONObject();
	     
	       configJSON.put("key", Configuration.getInstance().secretkey);
	       configJSON.put("id", Configuration.getInstance().identifier);
	       configJSON.put("name", Configuration.getInstance().name);
	       configJSON.put("URL", Configuration.getInstance().myip);
	       
	       System.out.println("publishing service");  
	       try {
			Stat stat = Configuration.getInstance().zoo.exists(Configuration.getInstance().webpath, false);
			if(stat==null) {
				System.out.println("Node does not exist, creating node");
				Configuration.getInstance().zoo.create(Configuration.getInstance().webpath, "".getBytes(), Arrays.asList(acl),
						CreateMode.PERSISTENT);
			}
			Configuration.getInstance().zoo.create(Configuration.getInstance().webpath+"/"+Configuration.getInstance().identifier, configJSON.toString().getBytes(),Arrays.asList(acl),
					CreateMode.EPHEMERAL);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Configuration getInstance() {
		if (ConfInstance == null) {
			ConfInstance = new Configuration();
		}
		return ConfInstance;
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		 System.err.println("Dirservice Context destroyed");
		 
			try {
				if (Configuration.getInstance().zoo != null) {
					Configuration.getInstance().zoo.close();
				}
			}
			catch ( InterruptedException ex) {
				System.err.println("destroy InterruptedException");
			}

		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Configuration.getInstance().ReadConfigurationFile();
	
		try {
			Configuration.getInstance().zoo = Configuration.getInstance().zooConnect();
			Configuration.getInstance().PublishService(sce);
			Configuration.getInstance().initDirWatches();
			Configuration.getInstance().initAuthWatches();
			//FileServices.updateDB();
	  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    }
	
		

	
}

