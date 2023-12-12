<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Flight Results</title>
</head>
<body>
    <h2>Flight Search Results</h2>
    <c:if test="${not empty flights}">
        <table border="1">
            <tr>
                <th>Airline</th>
                <th>Flight Number</th>
                <th>Departure Airport</th>
                <th>Destination Airport</th>
                <th>Departure Time</th>
                <th>Arrival Time</th>
                <!-- Add more columns as needed -->
            </tr>
            <c:forEach var="flight" items="${flights}">
                <tr>
                    <td>${flight.airlineCompanyId}</td>
                    <td>${flight.flightNumber}</td>
                    <td>${flight.departureAirport}</td>
                    <td>${flight.destinationAirport}</td>
                    <td>${flight.departureTime}</td>
                    <td>${flight.arrivalTime}</td>
                    <!-- Fill in other details -->
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty flights}">
        <p>No flights found.</p>
    </c:if>
</body>
</html>
