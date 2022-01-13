<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title">Registration Authority (main)</jsp:attribute>
    <jsp:body>
    <h1 align= "center"><b><em><font face="Times New Roman">The TIHM Registration Authority(PKI)</font></em></b></h1>
    <hr style =" border-width: 3px">
    <p align="justify"><b>This website is developed as a front end for the Public Key Infrastructure (PKI) in the <a href= "http://www.sabp.nhs.uk/tihm">TIHM (Technology Integrated Health Management)</a> project. 
    	The website mainly facilitates a number of important user activities that typically occurs in Registration Authority,
    	such as certificate signing request submission, certificate archive and certificate revocation. 
    	However, in order for users to take advantage of the website services,  they initially need to create their new user login account in the register(sign up) page.</b><p>
    <hr style =" border-width: 3px">
    <br>
    <br>
    <br>   
    <br>
    <br>
    <table width="100%" cellspacing="100" table-layout="fixed"  margin-right="auto" margin-left="auto">   
    <tr>    
    <td width="40%"><a href="/tihm-pki-ra/register" class="btn btn-primary custom"><font size="5">Register (Sign up)</font></a></td>
        <td width="5%">&nbsp;</td>
    <td width="5%">&nbsp;</td>
     <c:choose>
     <c:when test="${not empty userSession and userSession.authenticated}">
       	<td width="45%"><a href="/tihm-pki-ra/secure/account" class="btn btn-Info custom"><font size="5">User Account</font></a></td>
     </c:when>
     <c:otherwise>
    	<td width="45%"><a href="/tihm-pki-ra/login" class="btn btn-Info custom"><font size="5">Login</font></a></td>
    </c:otherwise> 
    </c:choose> 
    </tr>
    <tr>
    <td width="45%">&nbsp;</td>
    <td width="5%">&nbsp;</td>
    <td width="5%">&nbsp;</td>
    <td width="45%">&nbsp;</td>
    </tr>
    <tr>
    <td width="45%">&nbsp;</td>
    <td width="5%">&nbsp;</td>
    <td width="5%">&nbsp;</td>
    <td width="45%">&nbsp;</td>
    </tr>
    <c:if test="${not empty userSession and userSession.authenticated}">
    <tr>
	<td width="45%"><a href="/tihm-pki-ra/secure/certificates" class="btn btn-success custom"><font size="5">Certificates Directory</font></a></td>
    <td width="5%">&nbsp;</td>
    <td width="5%">&nbsp;</td>
    <td width="45%"><a href="/tihm-pki-ra/secure/cersignreq-edit.jsp" class="btn btn-warning custom"><font size="5">New Certificates Signing Request</font></a></td>
    </tr>
    </c:if>	
    </table>
    <br>
    <br>   
    <br>
   
<!--     <div id="footer"> -->
<%--     <%@ include file="footer.jspf" %> --%>
<!--     </div> -->
    </jsp:body>    
</t:template>