<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title"> Sign Certificate Request</jsp:attribute>
    
    <jsp:body>
    <h2><b><em><font face="Times New Roman">Create NEW Certificate Signing Request</font></em></b></h2>
    
	<form id = "cersignreqform" method="post" action="/tihm-pki-ra/secure/newcsr" enctype="multipart/form-data">
       <table width="100%" border="0" cellspacing="0" cellpadding="0" >
		  <tr>
		    <td width="50%">&nbsp;</td>
		    <td width="50%">&nbsp;</td>
		  </tr> 
		  <tr >
		   <td colspan="2">
		     <label for="certTyperadio"><font color="grey"><strong>  Certificate Type</strong><span class="requis">*:</span></font></label>
		     <input type="radio" id="certTyperadio" name="certTyperadio" value="user"   checked="checked" required />&nbsp;<i>User Certificate</i>&nbsp;&nbsp;                 
        	 <input type="radio" id="certTyperadio" name="certTyperadio" value="device" ${param.certTyperadio =='device'?'checked':''} required/>&nbsp;Device Certificate &nbsp;             	
             <hr style =" border-width: 2px"/>
		   </td>
		  </tr>
		  <tr>      
        	<td >
	        	<div class="form-group">
	        	<label for="commonName"><strong>Common Name </strong><span class="requis">* </span></label>
		        <font size="2"> (if the certificate request is for device, <br/>it is highly recommended to use UUID for this field)</font>
		        <input type=text id="commonName" name="commonName" placeholder = "Common Name or UUID" value="${param.commonName}" size="20" maxlength="60" class="form-control" />
    	   		</div>
	  		</td>
	  		<td>
		        <div class="form-group">
			        <label for="country"><strong>Country</strong><span class="requis">*</span></label>
			        <input type=text id="country" name="country"  placeholder = "Country Name" value="${param.country}" size="20" maxlength="60" class="form-control" />
			    </div>
			</td>
		 </tr>
		 <tr>
			<td>
			   <div class="form-group">
	        	<label for="stateprovince"><strong>State / Province</strong>(User & Device)<span class="requis">*</span></label>
	        	<input type=text id="stateprovince" name="stateprovince" placeholder= "State or Province" value="${param.stateprovince}" size="20" maxlength="60" class="form-control" />
	    	   </div>
	        </td>
	        <td>
	    	   <div class="form-group">
	        	<label for="locale"><strong> Locale / City</strong>(User & Device)<span class="requis">*</span></label>
	        	<input type=text id="locale" name="locale" placeholder= "Locale or City" value="${param.locale}" size="20" maxlength="60" class="form-control" />
	   		   </div>
	   	   </td>
	   	 </tr>
	   	 <tr>
	   	 <td>
 		 <table>
	           <tr>
	           <td>
 		  <div class="form-group"> 
	        <label for="organization"><strong> User Organization / Device Provider </strong><span class="requis">*</span></label>
<%-- 	        <input type=text id="organization" name="organization" placeholder="Organization" value="${param.organization}" size="20" maxlength="60" class="form-control" /> --%>
	    	   <div class="input-group dropdown">	        

	           <input type="text" id="organization" name="organization" placeholder= "Organization Name"  class="form-control countrycode dropdown-toggle" value="${param.organization}" >
	          	<ul class="dropdown-menu" >
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
	          <span class="caret"></span>
	          </span>	      
	          </div> 
	    </div> 
	    </td>
	    </tr>
	    </table>	    
	     </td>
	     <td>  
	     	<div class="form-group">
	        <label for="organization_unit"> <strong>User Organization Department / Device Type</strong></label>
	        <input type=text id="organization_unit" name="organization_unit" placeholder="Organization Dept." value="${param.organization_unit}" size="20" maxlength="60" class="form-control" />
	    	</div>
	    </td>
	    </tr> 
	    <tr>
	    <td colspan="2"> 
	     	<div class="form-group">
	        	<label for="csrFile"><strong> Upload Certificate Signing Request File (*.csr)</strong><span class="requis">*</span></label>
	        	<input type="file" name="file" />
	    	</div>
	    	<br>
	    	 <font size="3"> <i> <strong> Note: </strong></i> The user must generate the csr file in the machine where the private key is saved using any convenient crypto libraries such as OpenSSL, Java Keytool, etc.  
	    	 The user guide of creating csr  using OPENSSL is available(<a href = "/tihm-pki-ra/files/openssl-cs.pdf">download</a></font>)
	    </td>
	   </tr>	    
	    <tr>	       
	    <td colspan="2">
	    <hr style =" border-width: 3px">
	    <input type="submit" value="Submit Request"  class="btn btn-default btn-lg" /> &nbsp;	    	
	    <input name="Reset" type="reset" class="btn btn-default btn-lg" value="RESET"  onclick="return resetForm(this.form);"/>    
	    </td>	          
	    </tr>
	    </table>	    
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