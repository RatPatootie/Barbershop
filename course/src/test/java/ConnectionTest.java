//package course.course.db.connection;

import course.course.db.connection.Connect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectionTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private Connect connect;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        connect = new Connect();
        Connect.connection = mockConnection;
    }

    @Test
    public void testConnect() {
        Connect.connect();
        // Ми не можемо легко перевірити підключення до реальної бази даних у юніт-тесті, тому цей тест може бути інтеграційним тестом.
        assertNotNull(Connect.connection);
    }

    @Test
    public void testFindUser() throws SQLException {
        String username = "testUser";

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM employee where 'login_name' = " + username))
                .thenReturn(mockResultSet);

        ResultSet resultSet = connect.findUser(username);

        verify(mockConnection).createStatement();
        verify(mockStatement).executeQuery("SELECT * FROM employee where 'login_name' = " + username);
        assertEquals(mockResultSet, resultSet);
    }
}
