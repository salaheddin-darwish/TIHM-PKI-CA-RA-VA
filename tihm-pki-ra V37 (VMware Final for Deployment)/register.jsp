<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title">User Register Form</jsp:attribute>
    
    <jsp:body>
    <legend style =" border-width: 3px"><b><em><font face="Times New Roman">RA User Registration</font></em></b></legend>
	<form method="post" action="/tihm-pki-ra/register">				
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td width="300">
		 	<div class="form-group" >
	        <label for="mail">Email <span class="requis">*</span></label>
	        <input type="text" id="email" name="email" placeholder="Email Address or Username" value="${param.email}" size="20" maxlength="60" class="form-control" />
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
	        <label for="lastname">Last Name <span class="requis">*</span></label>
	        <input type="text" id="lastname" name="lastname" placeholder= "Last Name" value="${param.lastname}" size="20" maxlength="20" class="form-control" />
	    	</div>	
	    </td>	    
	    </tr>
	    <tr>
        <td width="300"> 
            <label for="Organization">Organization <span class="requis">*</span></label>
	        <div class="input-group dropdown">	        
	          <input type="text" id="organization" name="organization" placeholder= "Organization Name"  class="form-control countrycode dropdown-toggle" value="${param.organization}" >
	          <ul class="dropdown-menu">
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
				<li><a href="#" data-value="Yecco">Yecco</a></li>		
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
	        <label for="firstname">First Name <span class="requis">*</span></label>
	        <input type="text" id="firstname" name="firstname" placeholder= "First Name"  value="${param.firstname}" size="20" maxlength="20" class="form-control" />
	    	</div>  
	    </td>	    	
	    </tr>
	    <tr>
	    <td>
	    <br>	             
        <div class="form-group">
	        <label for="password1">Password <span class="requis">*</span></label>
	        <input type="password" id="password1" name="password1" placeholder= "Password" value="${param.passwords}" size="20" maxlength="60" class="form-control" />
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
	    <td> &nbsp;</td>
	    </tr>
	    <tr>
	    <td>
	    <br>	            
	     <div class="form-group">
	        <label for="retyppassword">Confirm Password <span class="requis">*</span></label>
	        <input type="password" id="password2" name="password2" placeholder= "Re-password" value="${param.passwords}" size="20" maxlength="60" class="form-control" />
	    </div>
	    </td> 
        </tr> 
	    </table> 
	    <hr style =" border-width: 3px">         
	    <input type="submit" value="Register" class="btn btn-default btn-lg"  />
	     <input name="Reset" type="reset" class="btn btn-default btn-lg" value="RESET"  onclick="return resetForm(this.form);"/> 
	</form> 
	<script>
	$(function () {
	    $('.dropdown-menu a').click(function () {
	        console.log($(this).attr('data-value'));
	        $(this).closest('.dropdown').find('input.countrycode').val($(this).attr('data-value'));
	    });
	});
	
	</script>
    </jsp:body>
</t:template>