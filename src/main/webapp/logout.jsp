<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%
//Checking if the session already exists
    if (session != null) {
        session.invalidate(); // Invalidating the session
    }

    // Redirecting to the login page
    response.sendRedirect("login.jsp");
%>