package com.tgl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import com.google.gson.Gson;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tgl.common.DbConnection;

public class StripeController extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void checkout() {
		// Stripe secret key
		Stripe.apiKey = "sk_test_QBRDWzFqrQNmG1omiiEP7gDt";
		SessionCreateParams params =
		          SessionCreateParams.builder()
		            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
		            .setMode(SessionCreateParams.Mode.PAYMENT)
		            .setSuccessUrl(DbConnection.base_url + "success.html")
		            .setCancelUrl(DbConnection.base_url + "cancel.html")
		            .addLineItem(
		              SessionCreateParams.LineItem.builder()
		                .setQuantity(1L)
		                .setPriceData(
		                  SessionCreateParams.LineItem.PriceData.builder()
		                    .setCurrency("usd")
		                    .setUnitAmount(2000L)
		                    .setProductData(
		                      SessionCreateParams.LineItem.PriceData.ProductData.builder()
		                        .setName("Autoparts Order")
		                        .build())
		                    .build())
		                .build())
		            .build();
		      Session session;
		      HashMap<String, String> responseData = new HashMap<String, String>();
			try {
				session = Session.create(params);
				responseData.put("id", session.getId());
				System.out.println("id: " + session.getId());
			} catch (StripeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		      
		      
		      String data = new Gson().toJson(responseData);
		      
		      HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("application/json");
				PrintWriter out;
				try {
					out = response.getWriter();
					out.write(data);
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	@Override
	public String execute() {
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
    	
	}
}
