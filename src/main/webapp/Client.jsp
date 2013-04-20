<%@ page import="servlet.ClientBean"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
ClientBean clientBean = new ClientBean();

List<String> myIndexes = clientBean.getMyIndexes(request);

pageContext.setAttribute("myIndexes", myIndexes);
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
<c:forEach items="${students.testIndexes}" var="testindex">
    <p>${testindex}</p>
</c:forEach>

<p>All available indexes:<p>
<c:forEach items="${students.indexes}" var="index">
    <p>${index}</p>
</c:forEach>





<p>Indexes to change:<p>
<c:forEach items="${myIndexes}" var="item">
    ${item} <br />
    <FORM METHOD=POST ACTION="ClientSend">
	Enter new value for index ${item}  <INPUT TYPE=TEXT NAME="Value" SIZE=20><BR>
	<input type="hidden" name="index" value=${item} />
	<P><INPUT TYPE=SUBMIT>
</FORM>
</c:forEach>
<p>end<p>






</body>
</html>