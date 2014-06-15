package de.hofmanns.esper.exampel

import java.text.MessageFormat


/**
 * Java classes, Map-based or XML event representations are a good for representing events
 * -> no Groovy Classes because Engine can't find Property in Statement
 */
class OrderEvent {
	
	private String itemName
	private double price

	public OrderEvent(String itemName, double price) {
		this.itemName = itemName
		this.price = price
	}

	public String getItemName() {
		return itemName
	}

	public double getPrice() {
		return price
	}

	public String toString() {
		Object[] arguments = { itemName new Double(price)}
		return MessageFormat.format("OrderEvent [itemName={0}, price={1}]", arguments)
	}


}
