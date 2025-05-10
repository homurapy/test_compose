package com.micro.test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;
    

    @Override
    public List<UserDto> findAll() {
        return userMapper.toListDto(userRepository.findAll());
    }

    @Override
    public UserDto findById(Long id) {
        return Optional.of(getById(id)).map(userMapper::modelToDto).get();
    }

    @Override
    @Transactional
    public UserDto save(UserDto book) {
        return userMapper.modelToDto(userRepository.save(
                userMapper.dtoToModel(book)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var book = getById(id);
        userRepository.delete(book);
    }

    @Override
    public UserDto addSubscriptionByUserId(Long id, Long idSub) {
        UserDto dto = findById(id);
        dto.getSubscriptions().add(subscriptionRepository.findById(idSub).orElseThrow(RuntimeException::new));
        save(dto);
        return dto;
    }

    @Override
    public List<Subscription> getTopUsersSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .sorted(Comparator.comparing(subscription -> ((Subscription)subscription).getUserList().size()).reversed())
                .limit(3).collect(Collectors.toList());
    }

    private User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "User with id: " + id + " not found"));
    }
}