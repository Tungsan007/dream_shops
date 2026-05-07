package com.dailycodework.dream_shops.service.user;

import com.dailycodework.dream_shops.exceptions.ResourcesNotFoundException;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.repository.UserRepository;
import com.dailycodework.dream_shops.requests.CreateUserRequest;
import com.dailycodework.dream_shops.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourcesNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourcesNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> { throw new ResourcesNotFoundException("User Not found");});
    }
}
