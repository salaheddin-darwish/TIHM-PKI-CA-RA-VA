<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title">Register</jsp:attribute>
    
    <jsp:body>
	<form method="post" action="/tihm-pki-ra/register">
		<legend>Register</legend>		
		<div class="form-group">
	        <label for="lastname">Last Name <span class="requis">*</span></label>
	        <input type="text" id="lastname" name="lastname" placeholder= "Last Name" value="${param.lastname}" size="20" maxlength="20" class="form-control" />
	    </div>
	       
        <div class="form-group">
	        <label for="firstname">First Name <span class="requis">*</span></label>
	        <input type="text" id="firstname" name="firstname" placeholder= "First Name"  value="${param.firstname}" size="20" maxlength="20" class="form-control" />
	    </div>        
	    <div class="form-group">
	        <label for="organization">Organization<span class="requis">*</span></label>
	        <input type= "text" id="organization" name="organization" placeholder="Organization Name" value="${param.email}" size="20" maxlength="60" class="form-group"/>	
	    </div>
	    
	    <div class="form-group">    
			<label for="organization">Organization<span class="requis">*</span></label>
<!-- 			 <select onchange="this.nextElementSibling.value=this.value">
			    <option value=""></option>
			    <option value="Arqiva">Arqiva</option>
				<option value="Docobo">Docobo</option>
				<option value="eLucid">eLucid</option>
				<option value="Halliday James">Halliday James</option>
				<option value="Healtrix">Healtrix</option>
				<option value="Intelesant">Intelesant</option>
				<option value="RHUL">RHUL</option>
				<option value="Sensely">Sensely</option>				
				<option value="University of Surrey">University of Surrey</option>
				<option value="Vision360">Vision360</option>
				<option value="Yecco">Yecco</option>
			</select>	 -->		 	 
			<input type= "text" id="organizations" name="organization" placeholder="Organization Name" value="${param.email}" size="20" maxlength="60" class="form-group"/>	
		</div> 	       
        <div class="form-group">
	        <label for="mail">Email <span class="requis">*</span></label>
	        <input type="text" id="email" name="email" placeholder="Email Address or Username" value="${param.email}" size="20" maxlength="60" class="form-control" />
	    </div>
	             
        <div class="form-group">
	        <label for="password1">Password <span class="requis">*</span></label>
	        <input type="password" id="password1" name="password1" placeholder= "Password" value="${param.passwords}" size="20" maxlength="60" class="form-control" />
	    </div>
	            
	     <div class="form-group">
	        <label for="retyppassword">Re-type Password <span class="requis">*</span></label>
	        <input type="password" id="password2" name="password2" placeholder= "Re-password" value="${param.passwords}" size="20" maxlength="60" class="form-control" />
	    </div>            
	    <input type="submit" value="Register" class="btn btn-default"  />
	</form> 
    </jsp:body>
</t:template>