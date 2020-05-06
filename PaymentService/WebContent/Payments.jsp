<%@page import="model.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payments.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payments Management V10.1</h1>
				<form id="formPayment" name="formPayment">
					
					ChargeOfAppoinments: <input id="ChargeOfAppoinments" name="ChargeOfAppoinments" type="text"
						class="form-control form-control-sm"> <br>
						
					ChargeOfPharmacy: <input id="ChargeOfPharmacy" name="ChargeOfPharmacy" type="text"
						class="form-control form-control-sm"> <br>
						
					ChargeOfServices: <input id="ChargeOfServices" name="ChargeOfServices" type="text"
						class="form-control form-control-sm"> <br>
						
					TotalAmount: <input id="TotalAmount" name="TotalAmount" type="text"
						class="form-control form-control-sm"> <br>	
					
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divPaymentsGrid">
					<%
						Payment paymentObj = new Payment();
					out.print(paymentObj.readPayments());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>