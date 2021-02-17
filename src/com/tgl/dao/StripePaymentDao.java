package com.tgl.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tgl.common.DbConnection;
import com.tgl.common.Logs;
import com.tgl.common.QueryManager;
import com.tgl.entities.StripePayment;

public class StripePaymentDao {
	private final static String className = "StripePaymentDao";
	
	public static Integer addStripePayment(StripePayment p) {
		String query = null;
		Integer id = -1;
		Map<String, String> colValues = new HashMap<String, String>();
		Map<String, Object> colValuesExQuotes = new HashMap<String, Object>();
		String tblName = "_stripe_payment";
		
		try {
			if(p.getCustomer_email() != null) {
				colValues.put("customer_email", p.getCustomer_email());
			}
			if(p.getOrder_title() != null) {
				colValues.put("order_title", p.getOrder_title());
			}
			if(p.getOrder_description() != null) {
				colValues.put("order_description", p.getOrder_description());
			}
			if(p.getAmount() != null) {
				colValuesExQuotes.put("amount", p.getAmount());
			}
			if(p.getAmount_currency() != null) {
				colValues.put("amount_currency", p.getAmount_currency());
			}
			if(p.getInvoice_id() != null) {
				colValues.put("invoice_id", p.getInvoice_id());
			}
			if(p.getPayment_status() != null) {
				colValues.put("payment_status", p.getPayment_status());
			}
			if(p.getStripe_fees() != null) {
				colValuesExQuotes.put("stripe_fees", p.getStripe_fees());
			}
			if(p.getInvoice_url() != null) {
				colValues.put("invoice_url", p.getInvoice_url());
			}
			if(p.getInvoice_pdf() != null) {
				colValues.put("invoice_pdf", p.getInvoice_pdf());
			}
			if(p.getAmount_paid() != null) {
				colValuesExQuotes.put("amount_paid", p.getAmount_paid());
			}
			if(p.getId() == null || p.getId() <= 0) {
				colValuesExQuotes.put("timestamp", "utc_timestamp()");
				
				query = QueryManager.buildInsertQuery(colValues, colValuesExQuotes,
						tblName);
	
				if (query != null) {
					id = DbConnection.executeInsertQuery(query);
				}
			}else {
				ArrayList<String> conditions = new ArrayList<String>();
				conditions.add("id = " + p.getId());
				query = QueryManager.buildUpdateQuery(colValues, colValuesExQuotes, conditions, tblName, null);
				
				if(query != null){
					DbConnection.executeUpdateQuery(query);
					id = p.getId();
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.printErrorLog(className, e.getMessage());
		}
		
		return id;
	}
}
