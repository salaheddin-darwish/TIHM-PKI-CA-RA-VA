<%@tag description="Layout" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<link rel="stylesheet" href="/tihm-pki-ra/css/bootstrap.min.css">
	<link rel="stylesheet" href="/tihm-pki-ra/css/mystyles.css">
 	<link rel="stylesheet" href="/tihm-pki-ra/jquery.dataTables.min.css"> 
	<style type="text/css" class="init"> </style>	
	<script type="text/javascript" language="javascript" src="/tihm-pki-ra/jquery-1.12.4.js"> </script>
	<script type="text/javascript" language="javascript" src="/tihm-pki-ra/jquery.dataTables.min.js"> </script>
	<script type="text/javascript" language="javascript" src="/tihm-pki-ra/bootstrap.min.js"> </script>
	<script type="text/javascript" class="init">
	$(document).ready(function() {
	    var t = $('#example').DataTable( {
	        "columnDefs": [ {
	            "searchable": false,
	            "orderable": false,
	            "targets": 0
	        } ],
	        "order": [[ 1, 'asc' ]]
	    } );	 
	    t.on( 'order.dt search.dt', function () {
	        t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
	            cell.innerHTML = i+1;
	        } );
	    } ).draw();
	} ); </script>
	<script type="text/javascript" class="init">
	function resetForm(form) {
	    // clearing inputs
	    var inputs = form.getElementsByTagName('input');
	    for (var i = 0; i<inputs.length; i++) {
	        switch (inputs[i].type) {
	            // case 'hidden':
	            case 'text':
	                inputs[i].value = '';
	                break;
	            case 'radio':
	            case 'checkbox':
	                inputs[i].checked = false;   
	        }
	    }

	    // clearing selects
	    var selects = form.getElementsByTagName('select');
	    for (var i = 0; i<selects.length; i++)
	        selects[i].selectedIndex = 0;

	    // clearing textarea
	    var text= form.getElementsByTagName('textarea');
	    for (var i = 0; i<text.length; i++)
	        text[i].innerHTML= '';

	    return false;
	}</script>
	<title><jsp:invoke fragment="title"/></title>

</head>
  <body class="container" background="/tihm-pki-ra/images/bacground7.jpg">
	<nav class="navbar navbar-default" role="navigation">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <strong><a class="navbar-brand" href="/tihm-pki-ra/">PKI(MAIN)</a></strong>
	    </div>
     <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	      	<c:if test="${not empty userSession and userSession.authenticated }">
	      		<li><a href="/tihm-pki-ra/secure/certificates">Certificates</a></li>
	      		<li><a href="/tihm-pki-ra/secure/newcsr">New Certificate</a></li>
			</c:if>
			<c:if test="${empty userSession or not userSession.authenticated}">
		        <li><a href="/tihm-pki-ra/register">Register</a></li>
		        <li><a href="/tihm-pki-ra/login">Login</a></li>
			</c:if>
			<li><a href="/tihm-pki-ra/files/TIHM-CA.crt">TIHM Root Certificate</a></li>
			<li><a href="/tihm-pki-ra/crl">TIHM CRL</a></li>
	      </ul>
	      
	      <c:if test="${not empty userSession and userSession.authenticated}">
		      <ul class="nav navbar-nav navbar-right">
		      	<li><a href="/tihm-pki-ra/secure/account">Hello, <strong>
		      	<c:out value="${ userSession.firstname }"/> 
		      	<c:out value="${ userSession.lastname }"/>
		      	(<c:out value="${ userSession.organization }"/>)		      	
		      	</strong></a></li>
		        <li><a href="/tihm-pki-ra/login?disconnect">Logout</a></li>
		      </ul>
	      </c:if>
	     
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>

    <div class="container-fluid">
		<c:if test="${not empty errors}">
			<div class="alert alert-dismissable alert-warning">${errors}</div>
		</c:if>
		
		<c:if test="${not empty success}">
			<div class="alert alert-dismissable alert-success">${success}</div>
		</c:if>
		<jsp:doBody/>
    </div>
    <jsp:include page="/footer.jsp"></jsp:include>
  </body>
</html>