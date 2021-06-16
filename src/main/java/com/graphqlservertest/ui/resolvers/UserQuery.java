package com.graphqlservertest.ui.resolvers;


import com.graphqlservertest.service.UserService;
import com.graphqlservertest.dto.UserDto;
import com.graphqlservertest.ui.models.UserRest;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserQuery implements GraphQLQueryResolver {

    @Autowired
    private UserService userService;

    public List<UserRest> getUsers(final int count){
        List<UserDto> userDtoList = new ArrayList<>();
        List<UserRest> returnValue = new ArrayList<>();

        userDtoList = this.userService.getUsers(1,count);

        for (UserDto user: userDtoList){
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(user,userRest);
            returnValue.add(userRest);
        }
        return returnValue;
    }

    public UserRest getUser(String userId){
        UserRest userRest = new UserRest();
        UserDto userDto = new UserDto();

        userDto = userService.getUserByUserId(userId);

        BeanUtils.copyProperties(userDto, userRest);

        return userRest;
    }
}
