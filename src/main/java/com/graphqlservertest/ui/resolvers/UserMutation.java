package com.graphqlservertest.ui.resolvers;


import com.graphqlservertest.service.UserService;
import com.graphqlservertest.dto.UserDto;
import com.graphqlservertest.ui.models.OperationStatusModel;
import com.graphqlservertest.ui.models.UserDetailsRequestModel;
import com.graphqlservertest.ui.models.UserRest;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMutation implements GraphQLMutationResolver {
    @Autowired
    private UserService userService;

    public UserRest createUser(UserDetailsRequestModel userDetails){
        UserDto userDto = new UserDto();
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(userDetails,userDto);
        userDto = userService.createUser(userDto);
        BeanUtils.copyProperties(userDto,userRest);
        return userRest;
    }

    public UserRest updateUser(String userId, UserDetailsRequestModel userDetails){
        UserDto userDto = new UserDto();
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(userDetails,userDto);
        userDto = userService.updateUser(userId,userDto);
        BeanUtils.copyProperties(userDto,returnValue);
        return returnValue;
    }

    public OperationStatusModel deleteUser(String userId){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        userService.deleteUser(userId);
        operationStatusModel.setOperationName("DELETE");
        operationStatusModel.setOperationResult("SUCCES");
        return operationStatusModel;
    }
}
