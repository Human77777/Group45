package controller;


import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FlightSearchServlet")
public class FlightSearchServlet extends HttpServlet {
    
	private String buildFlightSearchQuery(String departure, String arrival, String departureDate, boolean isFlexible) {
        String sql = "SELECT * FROM flight WHERE departure_airport = ? AND destination_airport = ?";
        if (departureDate != null && !departureDate.trim().isEmpty()) {
            if (isFlexible) {
                sql += " AND departure_time BETWEEN DATE_SUB(?, INTERVAL 3 DAY) AND DATE_ADD(?, INTERVAL 3 DAY)";
            } else {
                sql += " AND DATE(departure_time) = ?";
            }
        }
        return sql;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String departure = request.getParameter("departure").toUpperCase();
        String arrival = request.getParameter("arrival").toUpperCase();
        String departureDateString = request.getParameter("date"); 
        boolean isFlexible = request.getParameter("isFlexible") != null;
        
        List<Flight> flight = new ArrayList<>();
        String sql = buildFlightSearchQuery(departure, arrival, departureDateString, isFlexible);
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/System", "root", "Example@2022#");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, departure);
            ps.setString(2, arrival);
            int paramIndex = 3;
            
            if (departureDateString != null && !departureDateString.trim().isEmpty()) {
                if (isFlexible) {
                    Timestamp departureTimestamp = Timestamp.valueOf(departureDateString + " 00:00:00");
                    ps.setTimestamp(paramIndex++, departureTimestamp);
                    ps.setTimestamp(paramIndex, departureTimestamp);  
                } else {
                    ps.setDate(paramIndex, java.sql.Date.valueOf(departureDateString));  
                }
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flight.add(new Flight(
                    rs.getInt("airline_company_id"),
                    rs.getString("flight_number"),
                    rs.getString("departure_airport"),
                    rs.getString("destination_airport"),
                    rs.getString("departure_time"),
                    rs.getString("arrival_time"),
                    rs.getString("days"),
                    rs.getString("type").charAt(0)
                ));
            }
            if (flight.isEmpty()) {
                System.out.println("No flights found for the query: " + sql);
            } else {
                System.out.println(flight.size() + " flights found.");
                System.out.println("SQL Query: " + sql);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage()); // Logging the exception message
            request.setAttribute("error", "There was an error processing your request.");
            request.getRequestDispatcher("/main.jsp").forward(request, response);
            return;
        }

        request.setAttribute("flights", flight);
        request.getRequestDispatcher("/flightsResults.jsp").forward(request, response);
    }
    
    /*
    private String buildFlightSearchQuery(String departure, String arrival, String departureDate, String returnDate, String tripType, boolean isFlexible) {
        String sql = "SELECT * FROM flights WHERE departure_airport = ? AND destination_airport = ?";
        
        // For flexible dates, add or subtract 3 days to the departure date
        if (isFlexible) {
            sql += " AND departure_time BETWEEN DATE_SUB(?, INTERVAL 3 DAY) AND DATE_ADD(?, INTERVAL 3 DAY)";
        } else {
            sql += " AND departure_time = ?";
        }

        // If it's a round-trip, add return date criteria
        if ("round-trip".equals(tripType)) {
            sql += " AND return_time = ?";
        }

        return sql;
    }
    */
    
    
    public class Flight {
        private int airlineCompanyId;
        private String flightNumber;
        private String departureAirport;
        private String destinationAirport;
        private String departureTime;
        private String arrivalTime;
        private String days;
        private char type;

        // Constructor
        public Flight(int airlineCompanyId, String flightNumber, String departureAirport,
                      String destinationAirport, String departureTime, String arrivalTime,
                      String days, char i) {
            this.airlineCompanyId = airlineCompanyId;
            this.flightNumber = flightNumber;
            this.departureAirport = departureAirport;
            this.destinationAirport = destinationAirport;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
            this.days = days;
            this.type = i;
        }

        // Getters and setters for each property
        public int getAirlineCompanyId() { return airlineCompanyId; }
        public void setAirlineCompanyId(int airlineCompanyId) { this.airlineCompanyId = airlineCompanyId; }

        public String getFlightNumber() { return flightNumber; }
        public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

        public String getDepartureAirport() { return departureAirport; }
        public void setDepartureAirport(String departureAirport) { this.departureAirport = departureAirport; }

        public String getDestinationAirport() { return destinationAirport; }
        public void setDestinationAirport(String destinationAirport) { this.destinationAirport = destinationAirport; }

        public String getDepartureTime() { return departureTime; }
        public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

        public String getArrivalTime() { return arrivalTime; }
        public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

        public String getDays() { return days; }
        public void setDays(String days) { this.days = days; }

        public char getType() { return type; }
        public void setType(char type) { this.type = type; }

    }
    
    

}