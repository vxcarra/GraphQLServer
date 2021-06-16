package com.graphqlservertest.ui.resolvers;

import com.graphqlservertest.service.UserService;
import com.graphqlservertest.dto.UserDto;
import com.graphqlservertest.ui.models.UserRest;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class UserSubscription implements GraphQLSubscriptionResolver {
    @Autowired
    private UserService userService;

    public Publisher<List<UserRest>> getUsers(){
        return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                () -> {
                    List<UserRest> returnValue = new ArrayList<>();
                    List<UserDto>  userDtoList = this.userService.getUsers(1,100);

                    for (UserDto user: userDtoList){
                        UserRest userRest = new UserRest();
                        BeanUtils.copyProperties(user,userRest);
                        returnValue.add(userRest);
                    }
                    subscriber.onNext(returnValue);
                },0,1, TimeUnit.SECONDS);
    }
}
