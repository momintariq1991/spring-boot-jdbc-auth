package org.spring.boot.jdbc.auth.service;

import org.spring.boot.jdbc.auth.domain.ApplicationRole;
import org.spring.boot.jdbc.auth.domain.ApplicationUser;
import org.spring.boot.jdbc.auth.domain.CustomApplicationUser;
import org.spring.boot.jdbc.auth.dto.ApplicationRoleDto;
import org.spring.boot.jdbc.auth.dto.ApplicationUserDto;
import org.spring.boot.jdbc.auth.repository.ApplicationRoleRepository;
import org.spring.boot.jdbc.auth.repository.ApplicationUserRepository;
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
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationRoleRepository applicationRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ApplicationUserService(final ApplicationUserRepository applicationUserRepository,
                                  final ApplicationRoleRepository applicationRoleRepository,
                                  @Lazy final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationRoleRepository = applicationRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findByUsername(emailAddress);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username " + emailAddress + " not found");
        }
        return userOptional.map(CustomApplicationUser::new).get();
    }

    public void save(ApplicationUser applicationUser) {
        applicationUser.setId(UUID.randomUUID().toString());
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        Set<ApplicationRole> applicationRoleSet = applicationUser.getApplicationRoles();
        applicationUser.setApplicationRoles(null);
        applicationUserRepository.save(applicationUser);

        for (ApplicationRole applicationRole : applicationRoleSet) {
            applicationRole.setApplicationUser(applicationUser);
            applicationRoleRepository.save(applicationRole);
        }
    }

    public List<ApplicationUserDto> getAllUsers() {
        List<ApplicationUser> applicationUsers =  applicationUserRepository.findAll();
        return applicationUsers
                .stream()
                .map(this::getDtoFromEntity)
                .collect(Collectors.toList());
    }

    private ApplicationUserDto getDtoFromEntity(ApplicationUser applicationUser) {
        String id = applicationUser.getId();
        String username = applicationUser.getUsername();
        String password = applicationUser.getPassword();
        Set<ApplicationRole> applicationRoles = applicationUser.getApplicationRoles();

        ApplicationUserDto applicationUserDto = new ApplicationUserDto()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRoles(applicationRoles.stream().map(this::getDtoFromEntity).collect(Collectors.toSet()));
        return applicationUserDto;
    }

    private ApplicationRoleDto getDtoFromEntity(ApplicationRole applicationRole) {
        Integer id = applicationRole.getId();
        String name = applicationRole.getName();

        return new ApplicationRoleDto()
                .setId(id)
                .setName(name);
    }
}
