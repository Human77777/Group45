<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>User Main Page</title>
<!-- Include CSS and JavaScript here -->
</head>
<body>
    <h2>Flight Search</h2>
    <form action="FlightSearchServlet" method="POST">
        Departure: <input type="text" name="departure"><br>
        Arrival: <input type="text" name="arrival"><br>
        Date: <input type="date" name="date"><br>
        <input type="radio" id="oneWay" name="tripType" value="One-Way">
        <label for="oneWay">One-Way</label>
        <input type="radio" id="roundTrip" name="tripType" value="Round-Trip">
        <label for="roundTrip">Round-Trip</label><br>
        <input type="checkbox" id="flexibleDates" name="flexibleDates">
        <label for="flexibleDates">Flexible Dates (+/- 3 days)</label><br>
        <input type="submit" value="Search">
    </form>
    
    <!-- Links to account management and customer service -->
    <a href="UserAccountManagement.jsp">Account Management</a>
    <a href="CustomerServiceInteraction.jsp">Customer Service</a>
</body>
</html>
