package de.hofmanns.esper.exampel

import java.util.logging.Level;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener
import org.apache.log4j.Logger


/**
 * Listeners are invoked by the engine in response to one or more events that
 * change a statement's result set. Listeners implement the UpdateListener
 * interface and act on EventBean instances as the next code snippet outlines:
 */
class EventListener implements UpdateListener{
	
	private final Logger logger = Logger.getLogger(this.getClass().getName())

	@Override
	public void update(EventBean[] newBean, EventBean[] oldBean) {
		logger.setLevel(org.apache.log4j.Level.INFO)
		
		for (newEvent in newBean) {
			logger.info("new event "+newEvent.underlying)
		}
		
	}
}
