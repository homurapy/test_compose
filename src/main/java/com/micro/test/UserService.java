package com.micro.test;

import java.util.List;

public interface UserService {
    List<UserDto> findAll ();
    UserDto findById( Long id);
    UserDto save (UserDto userDto);
    void deleteById (Long id);
    UserDto addSubscriptionByUserId(Long id, Long idSub);
    List<Subscription> getTopUsersSubscriptions();
}