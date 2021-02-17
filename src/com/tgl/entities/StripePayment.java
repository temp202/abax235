package com.tgl.entities;

public class StripePayment {
	Integer id;
	String customer_email;
	String order_title;
	String order_description;
	Double amount;
	String amount_currency;
	String invoice_id;
	String payment_status;
	Double stripe_fees;
	String invoice_url;
	String invoice_pdf;
	Double amount_paid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAmount_currency() {
		return amount_currency;
	}
	public void setAmount_currency(String amount_currency) {
		this.amount_currency = amount_currency;
	}
	public String getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public Double getStripe_fees() {
		return stripe_fees;
	}
	public void setStripe_fees(Double stripe_fees) {
		this.stripe_fees = stripe_fees;
	}
	public String getInvoice_url() {
		return invoice_url;
	}
	public void setInvoice_url(String invoice_url) {
		this.invoice_url = invoice_url;
	}
	public String getInvoice_pdf() {
		return invoice_pdf;
	}
	public void setInvoice_pdf(String invoice_pdf) {
		this.invoice_pdf = invoice_pdf;
	}
	public Double getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(Double amount_paid) {
		this.amount_paid = amount_paid;
	}
	public String getOrder_title() {
		return order_title;
	}
	public void setOrder_title(String order_title) {
		this.order_title = order_title;
	}
	public String getOrder_description() {
		return order_description;
	}
	public void setOrder_description(String order_description) {
		this.order_description = order_description;
	}	
}
