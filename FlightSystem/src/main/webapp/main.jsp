<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Main Search Page</title>
</head>
<body>
    <h2>Welcome to the Flight Search System</h2>
    <form action="FlightSearchServlet" method="post">
        Departure: <input type="text" list="airports" name="departure">
        <datalist id="airports">
        </datalist>
        <br>
        
        Arrival: <input type="text" list="airports" name="arrival">
        <br>
        
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
    <a href="UserAccountManagement.jsp">Account Management</a><br>
    <a href="CustomerServiceInteraction.jsp">Customer Service</a>
</body>


</html>