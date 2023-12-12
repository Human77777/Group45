package l;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FlightSearchServlet")
public class FlightSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String departure = request.getParameter("departure");
        String arrival = request.getParameter("arrival");
        String departureDate = request.getParameter("departureDate");
        String returnDate = request.getParameter("returnDate"); // for round-trip
        String tripType = request.getParameter("tripType"); // "one-way" or "round-trip"
        boolean isFlexible = request.getParameter("isFlexible") != null; // checkbox for flexible dates

        List<Flight> flights = new ArrayList<>();
        String sql = buildFlightSearchQuery(departure, arrival, departureDate, returnDate, tripType, isFlexible);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/System", "root", "Example@2022#");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, departure);
            ps.setString(2, arrival);

            if (isFlexible) {
                // Set parameters for flexible dates
                ps.setString(3, departureDate); // actual SQL function for date flexibility will be needed
                // More date flexibility handling here
            } else {
                ps.setString(3, departureDate);
            }

            if ("round-trip".equals(tripType)) {
                ps.setString(4, returnDate); // or the next index depending on the flexibility handling
            }

            ResultSet rs = ps.executeQuery();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Flight flight = new Flight();
                    flight.setAirlineCompanyId(rs.getInt("airline_company_id"));
                    flight.setFlightNumber(rs.getString("flight_number"));
                    flight.setDepartureAirport(rs.getString("departure_airport"));
                    flight.setDestinationAirport(rs.getString("destination_airport"));
                    flight.setDepartureTime(rs.getString("departure_time"));
                    flight.setArrivalTime(rs.getString("arrival_time"));
                    
                    flights.add(flight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log this exception to logging system
        }

        request.setAttribute("flights", flights);
        request.getRequestDispatcher("/flightsResults.jsp").forward(request, response);
    }
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
                      String days, char type) {
            this.airlineCompanyId = airlineCompanyId;
            this.flightNumber = flightNumber;
            this.departureAirport = departureAirport;
            this.destinationAirport = destinationAirport;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
            this.days = days;
            this.type = type;
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