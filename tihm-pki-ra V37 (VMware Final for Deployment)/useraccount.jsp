<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title">User Account(<c:out value="${ userSession.firstname }"/> 
		      	<c:out value="${ userSession.lastname }"/>)   
   </jsp:attribute>
    
    <jsp:body>
    <fieldset style="text-align:left;border:1px solid #c0c0c0;margin:0 1px;padding:0.35em 0.625em 0.75em; width:90%" >  
    <legend><font face="Times New Roman"><strong><i> Account Details:-</i></strong></font></legend>	
	<form method="post" action="/tihm-pki-ra/secure/account?id=cud">			
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>
		 	<div class="form-group">
	        <label for="mail"><Strong> User email or Username:</Strong></label>
	        <input type="text" id="email" name="email" value="${userSession.email}" size="20" maxlength="60" class="form-control" />
	    	</div>
	    </td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> 
			<div class="form-group">
	        <label for="lastname"><strong>Last Name</strong></label>
	        <input type="text" id="lastname" name="lastname" placeholder= "Last Name" value="${userSession.lastname}" size="20" maxlength="20" class="form-control" />
	    	</div>	
	    </td>	    
	    </tr>
	    <tr>
        <td width="300px"> 
            <label for="Organization"><strong>User Organization</strong></label>
	        <div class="input-group dropdown">	        
	          <input width="300px"  type="text" id="organization" name="organization" placeholder= "Organization Name"  class="form-control countrycode dropdown-toggle" value="${userSession.organization}" >
	          <ul class="dropdown-menu" width="300px">
	            <li><a href="#" data-value="Arqiva">Arqiva</a></li>
	            <li><a href="#" data-value="Docobo">Docobo</a></li>
	            <li><a href="#" data-value="eLucid">eLucid</a></li>	   
	            <li><a href="#" data-value="Intelesant">Intelesant</a></li>
	            <li><a href="#" data-value="Halliday James">Halliday James</a></li>
	            <li><a href="#" data-value="RHUL">RHUL</a></li>						
				<li><a href="#" data-value="Safe Patient(SPS)"> Safe Patient(SPS)</a></li>		
				<li><a href="#" data-value="Sensely">Sensely</a></li>		
				<li><a href="#" data-value="University of Surrey">University of Surrey</a></li>			
				<li><a href="#" data-value="Vision360">Vision360</a></li>	
				<li><a href="#" data-value="Yecco">Yecc</a></li>		
	          </ul>
	          <span role="button" class="input-group-addon dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          <span class="caret"></span></span>
	        </div> 
	      </td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td>
	    	<br>       
        	<div class="form-group">
	        <label for="firstname"><strong>First Name </strong></label>
	        <input type="text" id="firstname" name="firstname" placeholder= "First Name"  value="${userSession.firstname}" size="20" maxlength="20" class="form-control" />
	    	</div>  
	    </td>	    	
	    </tr>
	    </table> 
	    <hr style =" border-width: 2px">
	 	<div class="form-group" align="center">
		        <label for="cudpass"> <strong><i>Enter Password:</i></strong></label>
		        <input type="password" id="cudpass" name="cudpass" style="display: inline;" placeholder= "Enter Password to confirm the changes" value="" size="20" maxlength="60" class="form-control" required/>
	        	  <input type="submit" value="Save Changes" onclick="return confirmAction1()" class="btn btn-default btn-lg"/>
	    </div>         
	  
	</form> 
	</fieldset>
  	<br>	
    
     <fieldset  style="text-align:left;border:1px solid #c0c0c0;margin:0 1px;padding:0.35em 0.625em 0.75em; width:350px" >  
 		<legend > <b><em><font face="Times New Roman">Reset your password:-</font></em></b></legend>
        <form method="post" action="/tihm-pki-ra/secure/account?id=rpwd">
 		 	  <table >
 		 	  <tr>
 		 	  <td>
	            <div class="form-group">
		        <label for="password1">Current Password <span class="requis">*</span></label>
		        <input type="password" id="oldpassword" name="oldpassword" placeholder= "Current Password" value="" size="20" maxlength="60" class="form-control" required />
	        	</div>
			 </td>
			 </tr>
			 <tr>	
			 <td>			        	
	        	<div class="form-group">
		        <label for="newpassword">New Password<span class="requis">*</span></label>
		        <input type="password" id="newpassword" name="newpassword" placeholder= "New Password" value="" size="20" maxlength="60" class="form-control" required/>
		   	 	</div>
		     </td>
		     </tr>	
		     <tr>
		     <td>
		     <div class="form-group">
			 <label for="confirmnewpassword">Confirm Password<span class="requis">*</span></label>
			 <input type="password" id="confirmnewpassword" name="confirmnewpassword" placeholder= " Re-type New Password"  size="20" maxlength="60" class="form-control" required/>
			 </div>
			 </td>
			 </tr>
			 </table>
			 <input type="submit" value="Change Password" onclick="return confirmAction2()" class="btn btn-default btn-lg"  />			    		
		    </form>
	    </fieldset>
	    
	<script>
	$(function () {
	    $('.dropdown-menu a').click(function () {
	        console.log($(this).attr('data-value'));
	        $(this).closest('.dropdown').find('input.countrycode').val($(this).attr('data-value'));
	    });
	});	
	</script>
	 <script>
          	  function confirmAction1(){
              var confirmed = confirm("Confirmation Message: Are you sure? This will change your account detials.");
              return confirmed;}           
      </script>
      	 <script>
          	  function confirmAction2(){
              var confirmed = confirm("Reset Password Warning Message: Are you sure? This will reset your password account.");
              return confirmed;}           
      </script>
    </jsp:body>
</t:template>