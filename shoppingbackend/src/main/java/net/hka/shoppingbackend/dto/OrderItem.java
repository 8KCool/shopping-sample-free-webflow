package net.hka.shoppingbackend.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Private fields
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/*
	 * linking the oderItem with a product with one to one relation mapping
	 */
	@OneToOne
	private Product product;
	
	
	/*
	 * linking the oderItem with orderDetail with many to one relation mapping,
	 * column for orderDetail will be created in the order item table
	 */
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderDetail orderDetail;

	
	
	/*
	 *  setters and getters	
	 */
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	@Column (name = "buying_price")
	private double buyingPrice;
	
	@Column (name = "product_count")
	private int productCount;
	
	private double total;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	public double getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
		
}
