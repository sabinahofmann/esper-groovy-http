package de.hofmanns.esper.http

import com.espertech.esper.client.Configuration
import com.espertech.esper.client.EPServiceProvider
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement
import com.espertech.esper.client.EventBean
import com.espertech.esper.client.UpdateListener
import com.espertech.esperio.http.EsperIOHTTPAdapterPlugin;

class EsperHttpInputAdapter {
	public void run() throws Exception {
		
				String port = "8083";
				boolean isNio = true;
		
				String esperIOHTTPConfig = "<esperio-http-configuration>\n" +
						"<service name=\"service1\" port=\"" + port + "\" nio=\"" + isNio + "\"/>" +
						"<get service=\"service1\" pattern=\"*\"/>" +
						"</esperio-http-configuration>";
		
				Configuration config = new Configuration();
				config.addPluginLoader("EsperIOHTTPAdapter", EsperIOHTTPAdapterPlugin.class.getName(), new Properties(), esperIOHTTPConfig);
		
				config.addEventTypeAutoName("de.hofmanns.esper.http");
				EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
				String expression = "select date from AccessLogEvent.win:time(30 sec)";
				EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		
				MyListener listener = new MyListener();
				statement.addListener(listener);
		
		
				SupportHTTPClient client = new SupportHTTPClient();
				client.request(8083, "sendevent", "stream", "AccessLogEvent", "date", "mydate");
		
				epService.destroy();
		
			}
		
			public class MyListener implements UpdateListener {
				public void update(EventBean[] newEvents, EventBean[] oldEvents) {
					newEvents.each { println it.underlying }
				}
			}
		
			public static void main(String[] args) throws Exception {
				EsperHttpInputAdapter test = new EsperHttpInputAdapter();
				test.run();
			}
}
