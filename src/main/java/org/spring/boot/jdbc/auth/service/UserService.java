package org.spring.boot.jdbc.auth.service;

import org.spring.boot.jdbc.auth.domain.CustomUser;
import org.spring.boot.jdbc.auth.domain.Role;
import org.spring.boot.jdbc.auth.domain.User;
import org.spring.boot.jdbc.auth.dto.RoleDto;
import org.spring.boot.jdbc.auth.dto.UserDto;
import org.spring.boot.jdbc.auth.repository.RoleRepository;
import org.spring.boot.jdbc.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       @Lazy final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(emailAddress);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username " + emailAddress + " not found");
        }
        return userOptional.map(CustomUser::new).get();
    }

    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roleSet = user.getRoles();
        user.setRoles(null);
        userRepository.save(user);

        for (Role role : roleSet) {
            role.setUser(user);
            roleRepository.save(role);
        }
    }

    public List<UserDto> getAllUsers() {
        List<User> users =  userRepository.findAll();
        return users
                .stream()
                .map(this::getDtoFromEntity)
                .collect(Collectors.toList());
    }

    private UserDto getDtoFromEntity(User user) {
        String id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        Set<Role> roles = user.getRoles();

        UserDto userDto = new UserDto()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRoles(roles.stream().map(this::getDtoFromEntity).collect(Collectors.toSet()));
        return userDto;
    }

    private RoleDto getDtoFromEntity(Role role) {
        Integer id = role.getId();
        String name = role.getName();

        return new RoleDto()
                .setId(id)
                .setName(name);
    }
}
