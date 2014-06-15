package de.hofmanns.esper.exampel

import java.util.TreeMap.PrivateEntryIterator;

import com.espertech.esper.client.Configuration
import com.espertech.esper.client.EPAdministrator
import com.espertech.esper.client.EPRuntime
import com.espertech.esper.client.EPServiceProvider
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement
import com.espertech.esper.client.EventSender

class MainEsper {

	static main(args) {
	
		 
		//The Configuration is meant only as an initialization-time object.
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("ProduktseiteEreignis", ProduktseiteEreignis.class.getName());
        EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
		def ProduktseiteEreignis = new ProduktseiteEreignis(cepRT)
		
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from ProduktseiteEreignis")
 
        cepStatement.addListener(new EventListener());
		
		// Optionally, use initialize if the same engine instance has been used before to start clean
		cep.initialize();
		
		// Destroy the engine instance when no longer needed, frees up resources
		cep.destroy();
		
	}
	
	  def generateEvent(Object event) {
 
        cepRT.sendEvent(event);
 
    }

}
