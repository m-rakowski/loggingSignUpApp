<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Logowanie</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>

<c:if test='${not empty "${username}"}'>${username} <!-- <a href="/logging-signup-app/logout">log me out</a> --></c:if>
<c:if test='${not empty "${error}"}'><span style="color: red">${error}</span></c:if>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

				<a class="navbar-brand" href="#">zeto logowanie</a> 

			</div>
			<div class="collapse navbar-collapse" id="myNavbar">

				<ul class="nav navbar-nav navbar-right">


					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

						<form:form method="POST" action="/logging-signup-app/login"
						modelAttribute="user" class="navbar-form navbar-right">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-user"></i></span><form:input path="name" placeholder="Enter username"
							class="form-control" /> 
							</div>

							<div class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-lock"></i></span><form:input path="passwordHash" type="password"
							placeholder="Enter password" required="required"
							class="form-control" /> 
							</div>

							<button type="submit" class="btn btn-primary">Login</button>
						</form:form>

					</div>
				</ul>
			</div>
		</div>
	</nav>



	<div>
		<div class="container">
			<div class="col-md-6">
				<div id="logbox">
					<form:form method="POST" action="/logging-signup-app/addUser"
						modelAttribute="user">
						<h1>Create an Account</h1>
						<form:input path="name" placeholder="Username"
							class="input pass" />
						<form:input path="email" type="email" placeholder="Email address"
							class="input pass" />
						<form:input path="passwordHash" type="password"
							placeholder="Choose a password" required="required"
							class="input pass" />
						<form:input path="passwordHash2" type="password"
							placeholder="Confirm password" required="required"
							class="input pass" />
						<input type="submit" value="Sign me up!" class="inputButton" />
					</form:form>
				</div>
			</div>
			<!--col-md-6-->


		</div>
	</div>

</body>
</html>