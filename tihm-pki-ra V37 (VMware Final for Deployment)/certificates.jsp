<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>

   <jsp:attribute name="title">Certificates Directory</jsp:attribute> 
    <jsp:body>    
    <h2><b><em><font face="Times New Roman">Certificates Directory</font></em></b></h2>
    <p>
    <a href="/tihm-pki-ra/secure/newcsr" class="btn btn-default btn-lg">New certificate</a>
    <input type="button" value="Refresh" class="btn btn-default btn-lg" onClick="history.go(0)">
    
    </p>
	<hr style =" border-width: 3px">
        <c:choose>
            <c:when test="${empty certsignreqs}">
                <p class="alert alert-info">No certificate Sign Requests to display.</p>
            </c:when>
            <c:otherwise>
<%-- 			<table id="example" class="table table-bordered table-striped"> --%>
			<table id="example" class="display" cellspacing="0" width="100%">
			      <thead>
			        <tr>
			          <th>#</th>
			          <th><div align="center">CRT Type</div></th>
			          <th><div align="center">Common Name </div></th>
			          <th><div align="center">Country</div></th>
			          <th><div align="center">State(Province)</div></th>
			          <th><div align="center">Locale(City)</div></th>
			          <th><div align="center">Organization/Device Provider</div></th>
			          <th><div align="center">Organization Dept./ Device Type</div></th>
			          <th><div align="center">Request Date</div></th>
			          <th><div align="center">CRT Serial No.</div></th>
			          <th><div align="center" ><font color ="red">Actions</font></div></th>
			        </tr>
			      </thead>
			      <tbody>
			      	<c:forEach items="${certsignreqs}" var="certsignreq">
			      	  <c:choose>
						  <c:when test="${certsignreq.revokest == 'REVOKED'}">
						  	<tr class="revoked">
						  </c:when>
						  <c:otherwise>
						  	<tr>
					      </c:otherwise>
		              </c:choose>		      	  
			      	 
			      	  <td></td>
			      	  <td><c:out value =" ${certsignreq.certType}"/></td>
			          <td><c:out value =" ${certsignreq.commonName }"/></td>
			          <td><div align="center"><c:out value = "${certsignreq.country }"/></div></td>
			          <td><div align="center"><c:out value = "${certsignreq.stateprovince }"/></div></td>
			          <td><div align="center"><c:out value = "${certsignreq.locale}"/></div></td>
			          <td><div align="center"><c:out value = "${certsignreq.organization}"/></div></td>
			          <td><div align="center"><c:out value = "${certsignreq.organization_unit}"/></div></td>			          
			          <td>
			          <c:if test="${certsignreq.assouser == userSession.id}">  
			          <c:out value = "${certsignreq.date}"/>
			          </c:if>
			          </td>
			          <td><div align="center"><c:out value = "${certsignreq.cer_serial_number}"/></div></td>			          			          
			          <td>
			              <c:if test="${certsignreq.assouser == userSession.id}">  
					          <div>   
					          <a href="/tihm-pki-ra/secure/certificates/edit?id=<c:out value="${certsignreq.fileId}"/>&download=csr" target="_self" class="btn btn-primary btn-block">CSR File Download</a>		           
							  </div>
						  </c:if>						  						 				  
						  <c:choose>
						   <c:when test="${not empty certsignreq.cer_serial_number}">
								  <div>
								  	<a href="/tihm-pki-ra/secure/certificates/edit?id=<c:out value="${certsignreq.fileId}"/>&download=crt" target="_self" class="btn btn-info btn-block" display="inline">Certificate Download</a>
								  </div>								  
								 	<c:if test="${certsignreq.assouser == userSession.id}">  
									<div>																		
									 <c:if test="${certsignreq.revokest == 'NOT_REVOKED'}">
						     			<a href="/tihm-pki-ra/secure/certificates/edit?id=<c:out value="${certsignreq.cer_serial_number}"/>&revoke=true"  onclick="return confirmAction()" target="_self" class="btn btn-warning btn-block">Revoke Now</a>
						  			 </c:if>
						  			 <c:if test="${ certsignreq.revokest == 'REVO_IN_PROGRESS'}">
						     			<span class="btn btn-warning btn-block">Revocation In progress</span>
						  			 </c:if>
						  			</div>
					  			  </c:if>					  			 
						  		  <c:if test="${certsignreq.revokest == 'REVOKED'}">						  			 
						     			<span class="btn btn-danger btn-block">Revoked</span>						     	   
						  		 </c:if>						 
						  </c:when>
						  <c:otherwise>
								  	<span class="btn btn-warning btn-block">No Certificate Available</span>							  	
						  </c:otherwise>
				           
				     </c:choose>			             			          
					 </td>			          
			        </tr>
			        </c:forEach>
			      </tbody>
		    </table>
            </c:otherwise>
        </c:choose> 
          <script>
          	  function confirmAction(){
              var confirmed = confirm("Revocation Warning Message: Are you sure? This will revoke this certificate.");
              return confirmed;}           
          </script>
    </jsp:body>
</t:template>