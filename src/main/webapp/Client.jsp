<%@ page import="servlet.ClientBean"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
ClientBean clientBean = new ClientBean();
%>

<html>
<head>
<title>Broker Client - Main menu</title>
</head>
<body>

<jsp:useBean id="students" 
                    class="servlet.ClientBean"> 
   
</jsp:useBean>

<H1>Welcome <%= request.getRemoteUser() %></H1><br>

<p>You can change the following indexes:<p>
<c:forEach items="${clientBean.testIndexes}" var="testindex">
    <p>${testindex}</p>
</c:forEach>

<p>All available indexes:<p>
<c:forEach items="${students.indexes}" var="index">
    <p>${index}</p>
</c:forEach>





<p>once again:<p>
<c:forEach items="${clientBean.getMyIndexes(request)}" var="item">
    ${item} <br />
</c:forEach>
<p>end<p>






</body>
</html>