package de.hofmanns.esper

import com.espertech.esper.client.EPRuntime
import com.espertech.esper.client.EPServiceProvider
import com.espertech.esper.client.EPServiceProviderManager
import com.espertech.esper.client.EPStatement
import org.springframework.beans.factory.BeanNameAware
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

/*
 * The EsperBean class below represents a thin wrapper for an EPServiceProvider:
 */
class EsperBean implements BeanNameAware, InitializingBean, DisposableBean{
	private EPServiceProvider epServiceProvider
	private EPRuntime epRuntime
	private String name
	private Set<StatementBean> statementBeans = new LinkedHashSet<StatementBean>()


	public void setStatements(StatementBean... statementBeans) {
	for (StatementBean statementBean : statementBeans) {
		addStatement(statementBean);
	}
	}

	public void addStatement(StatementBean statementBean) {
	statementBeans.add(statementBean);
	}

	public void sendEvent(Object event) {
	epRuntime.sendEvent(event);
	}

	public void setBeanName(String name) {
	this.name = name;
	}

	public void afterPropertiesSet() throws Exception {
	epServiceProvider = EPServiceProviderManager.getProvider(name);
	epRuntime = epServiceProvider.getEPRuntime();
	for (StatementBean statementBean : statementBeans) {
		EPStatement epStatement = epServiceProvider.getEPAdministrator().createEPL(statementBean.getEPL());
		statementBean.setEPStatement(epStatement);
	}
	}

	public void destroy() throws Exception {
	epServiceProvider.destroy();
	}

}
