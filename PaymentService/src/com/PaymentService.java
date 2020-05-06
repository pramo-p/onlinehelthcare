package com;

import model.Payment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payments")
public class PaymentService {
	Payment paymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments() {
		return paymentObj.readPayments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayments(@FormParam("ChargeOfAppoinments") String ChargeOfAppoinments,
			@FormParam("ChargeOfPharmacy") String ChargeOfPharmacy,
			@FormParam("ChargeOfServices") String ChargeOfServices,
			@FormParam("TotalAmount") String TotalAmount) {
		String output = paymentObj.insertPayments(ChargeOfAppoinments, ChargeOfPharmacy, ChargeOfServices, TotalAmount);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayments(String paymentData) {
		// Convert the input string to a JSON object
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		// Read the values from the JSON object
		String PaymentId = paymentObject.get("PaymentId").getAsString();
		String ChargeOfAppoinments = paymentObject.get("ChargeOfAppoinments").getAsString();
		String ChargeOfPharmacy = paymentObject.get("ChargeOfPharmacy").getAsString();
		String ChargeOfServices = paymentObject.get("ChargeOfServices").getAsString();
		String TotalAmount = paymentObject.get("TotalAmount").getAsString();
		String output = paymentObj.updatePayments(PaymentId, ChargeOfAppoinments, ChargeOfPharmacy, ChargeOfServices, TotalAmount);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element <PaymentId>
		String PaymentId = doc.select("PaymentId").text();
		String output = paymentObj.deletePayment(PaymentId);
		return output;
	}

}