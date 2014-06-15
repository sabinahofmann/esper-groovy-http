package de.hofmanns.esper.exampel

import com.espertech.esper.client.EPRuntime;

class ProduktseiteEreignis {
	

	Integer product_ID
	String productName
	
	public ProduktseiteEreignis(Integer product_ID, String productName) {
		super();
		this.product_ID = product_ID;
		this.productName = productName;
	}

	public Integer getProduct_ID() {
		return product_ID;
	}

	public void setProduct_ID(Integer product_ID) {
		this.product_ID = product_ID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void send(EPRuntime cepRT){
		cepRT.send(this)
	}

}
