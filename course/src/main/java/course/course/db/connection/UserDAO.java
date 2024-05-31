package course.course.db.connection;

import java.sql.*;

public class UserDAO {
    private static Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public ResultSet findUser(String username) throws SQLException {
        String sql = "SELECT * FROM employee WHERE login_name = ? LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        return preparedStatement.executeQuery();
    }
    public ResultSet findBarber() throws SQLException {
        String sql = "SELECT id, first_name,emp_url FROM employee ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }
    public ResultSet findBarber(int serviceId) throws SQLException {
        String sql = "SELECT * FROM employee join employee_service on employee.id=employee_service.emp_id  where service_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(serviceId));
        return preparedStatement.executeQuery();
    }
    public ResultSet findService(int barberId) throws SQLException {
        String sql = "SELECT service_name,service_type.id  FROM employee " +
                "join employee_service on employee.id=employee_service.emp_id " +
                "join service_type on employee_service.service_id =service_type.id " +
                "where employee.id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(barberId));
        return preparedStatement.executeQuery();
    }
    public ResultSet findService() throws SQLException {
        String sql = "SELECT service_name,id FROM service_type ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }
    public static ResultSet findUsedZones(String date) throws SQLException {
        String sql = "SELECT zones_booking.zone_id FROM zones_booking " +
                "join booking on zones_booking.booking_time_id = booking.booking_time_id " +
                "where booking.date = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, date);
        return preparedStatement.executeQuery();
    }
    public static ResultSet findZones() throws SQLException {
        String sql = "SELECT id,start_time FROM zones ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }
    public ResultSet findServiceDuration(String service) throws SQLException {
        String sql = "SELECT zone_req FROM service_type where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, service);
        return preparedStatement.executeQuery();
    }
    public void setBooking (String username,String phoneNumber,String barber, String service,String time, String date) throws SQLException {
        String sql = "INSERT INTO booking (customer_name, customer_phone, employee_id, service_type, date, booking_time_id, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, phoneNumber);
        preparedStatement.setInt(3, Integer.parseInt(barber));
        preparedStatement.setInt(4, Integer.parseInt(service));
        preparedStatement.setDate(5, Date.valueOf(date));
        preparedStatement.setInt(6, Integer.parseInt(time));
        preparedStatement.executeUpdate();

    }
    public void setDuration(String time, String time_id) throws SQLException {
        String sql = "INSERT INTO zones_booking (zone_id , booking_time_id ) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, time_id);
        preparedStatement.setString(2, time);
        preparedStatement.executeUpdate();
    }
    public ResultSet sellectBooking() throws SQLException {
        String sql = "SELECT count(*) as enum , date as date " +
                "FROM booking " +
                "group by date";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }
    public ResultSet sellectBookingInfo(String time , String date ) throws SQLException {
        String sql = "SELECT " +
                "booking.customer_name as customer_name , " +
                "booking.customer_phone as customer_phone , " +
                "service_type.service_name as service , " +
                "employee.first_name as employee , " +
                "service_type.service_price as price  " +
                "FROM  booking " +
                "join employee on employee.id = booking.employee_id " +
                "join service_type on service_type.id = booking.service_type  " +
                "join zones_booking on booking.booking_time_id=zones_booking.booking_time_id " +
                "where zones_booking.zone_id=? and date=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, time);
        preparedStatement.setString(2, date);
        return preparedStatement.executeQuery();
    }
    public void deleteZones(String booking_time_id) throws SQLException {
        String sql = "DELETE FROM zones_booking WHERE booking_time_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, booking_time_id);
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Deleted " + rowsAffected + " rows for booking_time_id: " + booking_time_id);

    }
    public void deleteBooking(String booking_time_id,String date) throws SQLException {
        String sql = "DELETE FROM booking WHERE booking_time_id = ? and date = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, booking_time_id);
        preparedStatement.setString(2, date);
        int rowsAffected =preparedStatement.executeUpdate();
        System.out.println("Deleted " + rowsAffected + " rows for booking_type_id: " + booking_time_id);

    }

    public ResultSet findFreeZones(String EmployeeId ,String  dayOfWeak ,String pastTime ) throws SQLException {
        String sql = "SELECT  zones.id " +
                "FROM  booking " +
                "join employee on employee.id = booking.employee_id " +
                "join service_type on service_type.id = booking.service_type  " +
                "join zones_booking on booking.booking_time_id=zones_booking.booking_time_id " +
                "join zones on zones_booking.zone.id=zones.id" +
                "where employee.id=? and zones.start_time >=? and DAYOFWEEK(booking.date)=?+1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, EmployeeId);
        preparedStatement.setString(2, pastTime);
        preparedStatement.setString(3,dayOfWeak);
        return preparedStatement.executeQuery();
    }

}
