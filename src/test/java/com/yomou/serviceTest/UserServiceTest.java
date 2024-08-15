package com.yomou.serviceTest;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.yomou.dto.UserRegistrationRequestDto;
import com.yomou.exception.YomouException;
import com.yomou.repository.UserRepository;
import com.yomou.service.AsyncSendGridService;
import com.yomou.service.UserService;
import com.yomou.util.PasswordHashingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    @Mock
    private PasswordHashingUtil passwordHashingUtil;

    @MockBean
    private AsyncSendGridService sendGridService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepository, passwordHashingUtil,sendGridService);
    }


    /**
     * email, userNameともにユニークな場合にユーザーが登録されるか
     */
    @Test
    @DatabaseSetup("/dbunit/userExistsData.xml")
    @ExpectedDatabase(value = "/dbunit/expectUserCreatedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Transactional
    public void testCreateUser_WhenUnique() {

        when(passwordHashingUtil.encodePassword(anyString())).thenReturn("encodedPassword");

        UserRegistrationRequestDto dto = new UserRegistrationRequestDto("unique", "Password", "unique@test.com");
        userService.createUser(dto);
    }

    /**
     * userNameがユニークでない場合に例外が出るか
     */
    @Test
    @DatabaseSetup("/dbunit/userExistsData.xml")
    @Transactional
    public void testUserNotCreate_WhenEmailNotUnique() {
        when(passwordHashingUtil.encodePassword(anyString())).thenReturn("encodedPassword");

        UserRegistrationRequestDto dto = new UserRegistrationRequestDto("unique", "Password", "exits@exists.com");
        assertThrows(YomouException.class, () -> userService.createUser(dto));
    }

    /**
     * emailがユニークでない場合に例外が出るか
     */
    @Test
    @DatabaseSetup("/dbunit/userExistsData.xml")
    @Transactional
    public void testUserNotCreate_WhenUserNameNotUnique() {
        when(passwordHashingUtil.encodePassword(anyString())).thenReturn("encodedPassword");

        UserRegistrationRequestDto dto = new UserRegistrationRequestDto("exists", "Password", "unique@test.com");
        assertThrows(YomouException.class, () -> userService.createUser(dto));
    }

    @AfterTransaction
    public void tearDown() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM users");

            statement.executeUpdate("ALTER SEQUENCE users_id_seq RESTART with 1");
        }
    }
}
