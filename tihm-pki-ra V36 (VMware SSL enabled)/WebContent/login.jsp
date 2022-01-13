<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Login</jsp:attribute>
    
    <jsp:body>
	<form method="post" action="/tihm-pki-ra/login">
		<legend ><b><em><font face="Times New Roman">Login</font></em></b></legend>
		
        <div class="form-group">
	        <label for="email">Email or Username <span class="requis">*</span></label>	      
<%-- 	        <input type="email" id="email" name="email" value="${param.email}"  --%>
<!-- 	        placeholder="Email or Username" class="form-control" autofocus="true"/> -->
	       <input type="text" id="email" name="email" value="${param.email}" 
	        placeholder="Email or Username" class="form-control" autofocus="true" required/>
	    </div>
	             
        <div class="form-group">
	        <label for="password">Password <span class="requis">*</span></label>
	        <input type="password" id="password" name="password" value="${param.password}" 
	                placeholder="Password"  class="form-control" required/>
	    </div>
	<table> 
	 <tr>
	 <td>	                        
	<input type="submit" value="Login" class="btn btn-default btn-lg" />
	</form>
	</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	    <td> &nbsp;</td>
	<td>
	<a href="/tihm-pki-ra/register" class="btn btn-default btn-lg">Sign up</a>	
	</td>
	</tr>
	</table>
	<br>
	<br>
	<a href='mailto:salaheddin.darwish@rhul.ac.uk'>  <i><u> Forgotten Password, please contact Customer Support </u></i></a>
    </jsp:body>
</t:template>	