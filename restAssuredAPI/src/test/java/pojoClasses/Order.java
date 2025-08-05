package pojoClasses;

import java.util.List;

public class Order {
	
	private List<OrderDetails> order;


	public List<OrderDetails> getOrder() {
		return order;
	}

	public void setOrder(List<OrderDetails> order) {
		this.order = order;
	}
	
}
