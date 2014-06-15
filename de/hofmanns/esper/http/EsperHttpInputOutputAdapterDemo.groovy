package de.hofmanns.esper.http


import com.espertech.esper.client.Configuration
import com.espertech.esper.client.EPServiceProvider
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement
import com.espertech.esper.client.EventBean
import com.espertech.esper.client.UpdateListener;
import com.espertech.esperio.http.EsperIOHTTPAdapter
import com.espertech.esperio.http.EsperIOHTTPAdapterPlugin;
import com.espertech.esperio.http.config.ConfigurationHTTPAdapter
import com.espertech.esperio.http.config.Request

import de.hofmanns.esper.exampel.EventListener;

class EsperHttpInputOutputAdapterDemo{
	
	
	
	private final static String ENGINE_URI = "CEP-SERVICE-0000";
	
		public void run() throws Exception {
	
			String port = "8083";
			boolean isNio = true;
	
			/**
			 * Configure HTTP Output Adapter
			 */
			ConfigurationHTTPAdapter adapterConfig = new ConfigurationHTTPAdapter();
	
			Request requestOne = new Request();
			requestOne.setStream("SupportBean");
			requestOne.setUri("http://localhost:8084/root");
			adapterConfig.getRequests().add(requestOne);
	
//			Request requestTwo = new Request();
//			requestTwo.setStream("SupportBean");
//			requestTwo.setUri("http://localhost:8085/root");
//			adapterConfig.getRequests().add(requestTwo);
	
			EsperIOHTTPAdapter httpOutputAdapter = new EsperIOHTTPAdapter(adapterConfig, ENGINE_URI);
	
			/**
			 * Configure HTTP Input Adapter
			 */
			String esperIOHTTPConfig = "<esperio-http-configuration>\n" +
					"<service name=\"service1\" port=\"" + port + "\" nio=\"" + isNio + "\"/>" +
					"<get service=\"service1\" pattern=\"*\"/>" +
					"</esperio-http-configuration>";
	
			Configuration config = new Configuration();
			config.addPluginLoader("EsperIOHTTPAdapter", EsperIOHTTPAdapterPlugin.class.getName(), new Properties(), esperIOHTTPConfig);
	
			config.addEventType("AccessLogEvent", AccessLogEvent.class);
	
			/**
			 * Create CEP Engine Instance
			 */
			EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, config);
	
			
			/**
			 *  Optionally, use initialize if the same engine instance has been used before to start clean
			 */
			epService.initialize();
			
	
			/**
			 * Publish EPL Statement
			 */
			String expression = "insert into SupportBean select ipAddress, page, date from AccessLogEvent.win:time(30 sec)";
			EPStatement statement = epService.getEPAdministrator().createEPL(expression);
	
			/**
			 * Start up HTTP Output Adapter
			 */
			httpOutputAdapter.start();
	
			/**
			 * Run HTTP Adapter for TEST
			 */
			SupportHTTPServer server8084 = new SupportHTTPServer(8084);
			server8084.start();
	
//			SupportHTTPServer server8085 = new SupportHTTPServer(8085);
//			server8085.start();
	
			/**
			 * Subscribe EPL Statement Listener
			 * anstatt Interface UpdateListener zzgl. addListener
			 * setSubscriber mit variable Anzahl an Methodenparametern
			 */
			def listener = new EventListener()
			statement.addListener(listener);
	
			/**
			 * Send sample Event for TEST
			 */
			SupportHTTPClient client = new SupportHTTPClient();
			client.request(8083, "sendevent", "stream", "AccessLogEvent", "ipAddress", "localhost", "page", "mypage", "date", "mydate");
	
			/**
			 * Stop down HTTP Output Adapter
			 */
			Thread.sleep(30000);
			
			server8084.stop();
//			server8085.stop();
	
			/**
			 * Destory CEP Engine Instance
			 */
			epService.destroy();
	
		}

	static main(args) {
		EsperHttpInputOutputAdapterDemo test = new EsperHttpInputOutputAdapterDemo();
		test.run();
	}

}
