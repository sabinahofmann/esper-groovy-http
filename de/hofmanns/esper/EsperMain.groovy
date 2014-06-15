package de.hofmanns.esper

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPRuntime
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.AdapterInputSource
import com.espertech.esperio.csv.CSVInputAdapter
import de.hofmanns.esper.exampel.OrderEvent
import org.apache.log4j.Logger
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext


class EsperMain {

	
	static main(args) {
		/*
		 * loading the XML file in Spring, which will automatically hook up the statements and listeners as defined in the XML
		 */
		def appContext = new ClassPathXmlApplicationContext("esperspring.xml")
		
		def esperBean = (EsperBean) appContext.getBean("esperBean", EsperBean.class)
		esperBean.setBeanName("OrderEvent")
		esperBean.sendEvent(new OrderEvent("a", 1))
		// ...when done, destroy the context...
		appContext.destroy();
	
	}

}
