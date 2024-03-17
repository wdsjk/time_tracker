package wdsjk.project.timetrackerassignment.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import wdsjk.project.timetrackerassignment.dto.user.UserRequest;
import wdsjk.project.timetrackerassignment.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService userService;

    @Mock
    UserRequest userRequest;

    @InjectMocks
    UserController controller;

    @Test
    void createUser_MustReturnValidResponse() {
//        var expected = ResponseEntity
//                .status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("User successfully created");
//        Mockito.doReturn(expected).when(this.controller).createUser(userRequest);

        var responseEntity = this.controller.createUser(userRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals("User successfully created", responseEntity.getBody());
    }

    // TODO: finish testing
}