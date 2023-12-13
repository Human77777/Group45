<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight Results</title>
</head>
<body>
    <h2>Flight Search Results</h2>
    <c:if test="${not empty flights}">
        <table border="1">
            <thead>
                <tr>
                    <th>Airline</th>
                    <th>Flight Number</th>
                    <th>Departure Airport</th>
                    <th>Destination Airport</th>
                    <th>Departure Time</th>
                    <th>Arrival Time</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="flight" items="${flights}">
                    <tr>
                        <td>${flight.airlineCompanyId}</td>
                        <td>${flight.flightNumber}</td>
                        <td>${flight.departureAirport}</td>
                        <td>${flight.destinationAirport}</td>
                        <td>${flight.departureTime}</td>
                        <td>${flight.arrivalTime}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
