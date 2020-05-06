$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});
	// $("#formPayment").submit();
});
function onPaymentSaveComplete(response, status) {
	var resultSet = JSON.parse(response);
	if (resultSet.status.trim() == "success") {
		$("#alertSuccess").text("Successfully saved.");
		$("#alertSuccess").show();
		$("#divPaymentsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error") {
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}

	else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
	
	

}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidPaymentIDSave").val(
					$(this).closest("tr").find('#hidPaymentIDUpdate').val());
			$("#ChargeOfAppoinments").val($(this).closest("tr").find('td:eq(0)').text());
			$("#ChargeOfPharmacy").val($(this).closest("tr").find('td:eq(1)').text());
			$("#ChargeOfServices").val($(this).closest("tr").find('td:eq(2)').text());
			$("#TotalAmount").val($(this).closest("tr").find('td:eq(3)').text());
		});

// Remove============================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "PaymentId=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});

function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validatePaymentForm() {
	
	

	// PRICE-------------------------------
	if ($("#ChargeOfAppoinments").val().trim() == "") {
		return "Insert ChargeOfAppoinments.";
	}
	// is numerical value
	var tmpAppoinments = $("#ChargeOfAppoinments").val().trim();
	if (!$.isNumeric(tmpAppoinments)) {
		return "Insert a numerical value for ChargeOfAppoinments.";
	}
	// convert to decimal price
	$("#ChargeOfAppoinments").val(parseFloat(tmpAppoinments).toFixed(2));
	
	
	// PRICE-------------------------------
	if ($("#ChargeOfPharmacy").val().trim() == "") {
		return "Insert ChargeOfPharmacy.";
	}
	// is numerical value
	var tmpPharmacy = $("#ChargeOfPharmacy").val().trim();
	if (!$.isNumeric(tmpPharmacy)) {
		return "Insert a numerical value for ChargeOfPharmacy.";
	}
	// convert to decimal price
	$("#ChargeOfPharmacy").val(parseFloat(tmpPharmacy).toFixed(2));
	
	
	// PRICE-------------------------------
	if ($("#ChargeOfServices").val().trim() == "") {
		return "Insert ChargeOfServices.";
	}
	// is numerical value
	var tmpServices = $("#ChargeOfServices").val().trim();
	if (!$.isNumeric(tmpServices)) {
		return "Insert a numerical value for ChargeOfServices.";
	}
	// convert to decimal price
	$("#ChargeOfServices").val(parseFloat(tmpServices).toFixed(2));
	
	// PRICE-------------------------------
	if ($("#TotalAmount").val().trim() == "") {
		return "Insert TotalAmount.";
	}
	// is numerical value
	var tmpAmount = $("#TotalAmount").val().trim();
	if (!$.isNumeric(tmpAmount)) {
		return "Insert a numerical value for TotalAmount.";
	}
	// convert to decimal price
	$("#TotalAmount").val(parseFloat(tmpAmount).toFixed(2));


	return true;
}