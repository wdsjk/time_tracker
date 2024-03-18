package wdsjk.project.timetrackerassignment.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import wdsjk.project.timetrackerassignment.dto.user.UpdateUserRequest;
import wdsjk.project.timetrackerassignment.dto.user.UserRequest;
import wdsjk.project.timetrackerassignment.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService userService;

    @Mock
    UserRequest userRequest;
    @Mock
    UpdateUserRequest updateUserRequest;

    @InjectMocks
    UserController controller;

    @Test
    void createUser_MustReturnValidResponse() {
        var expectedResponse = "User successfully created";
        Mockito.doReturn(expectedResponse).when(this.userService).createUser(userRequest);

        var responseEntity = this.controller.createUser(userRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void updateUser_MustReturnValidResponse() {
        var expectedResponse = "User successfully updated";
        Mockito.doReturn(expectedResponse).when(this.userService).updateUser(updateUserRequest);

        var responseEntity = this.controller.updateUser(updateUserRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(expectedResponse, responseEntity.getBody());
    }

//    @Test
//    void updateUser_MustReturnException() {
//        var expectedResponse = "Username can't be blank!";
//        Mockito.do
//
//        var responseEntity = this.controller.updateUser(updateUserRequest);
//
//        Assertions.assertNotNull(responseEntity);
//        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
//        Assertions.assertEquals(expectedResponse, responseEntity.getBody());
//    } I'm tired :(
}