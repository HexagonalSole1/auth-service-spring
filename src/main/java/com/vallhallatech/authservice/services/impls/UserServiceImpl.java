package com.vallhallatech.authservice.services.impls;

import com.vallhallatech.authservice.mappers.IUserMapper;
import com.vallhallatech.authservice.persistance.entities.Role;
import com.vallhallatech.authservice.persistance.entities.User;
import com.vallhallatech.authservice.persistance.entities.pivots.UserRole;
import com.vallhallatech.authservice.persistance.repositories.IRoleRepository;
import com.vallhallatech.authservice.persistance.repositories.IUserRepository;
import com.vallhallatech.authservice.persistance.repositories.IUserRoleRepository;
import com.vallhallatech.authservice.services.IUserService;
import com.vallhallatech.authservice.web.dtos.requests.CreateUserRequest;
import com.vallhallatech.authservice.web.dtos.responses.BaseResponse;
import com.vallhallatech.authservice.web.dtos.responses.InfoUserResponse;
import com.vallhallatech.authservice.web.exceptions.ConflictException;
import com.vallhallatech.authservice.web.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;
    private final IRoleRepository roleRepository;
    private final IUserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final IUserMapper userMapper;

    public UserServiceImpl(IUserRepository repository, IRoleRepository roleRepository, IUserRoleRepository userRoleRepository, IUserMapper userMapper) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {
        Optional<User> optionalUser = getOptionalUserByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            throw new ConflictException("User already exists with this email");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 🔹 Crear el usuario
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setFCM(request.getFcm());
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        repository.save(user);

        // 🔹 Determinar qué roles asignar
        List<String> rolesToAssign = (request.getRoles() == null || request.getRoles().isEmpty())
                ? List.of("USER") // Rol por defecto si no envían roles
                : request.getRoles();

        // 🔹 Asignar los roles al usuario
        for (String roleName : rolesToAssign) {
            // Buscar el rol, o crearlo si no existe
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        // Si el rol no existe, lo creamos
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        newRole.setCreatedAt(LocalDateTime.now());
                        newRole.setUpdatedAt(LocalDateTime.now());
                        return roleRepository.save(newRole);
                    });

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }

        return BaseResponse.builder()
                .data(userMapper.toCreateUserResponse(user))
                .message("User created successfully with roles: " + rolesToAssign)
                .success(true)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse getUserInfo(String email) {

        User user = getByEmail(email);

       InfoUserResponse infoUserResponse =  InfoUserResponse.builder().id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        return BaseResponse.builder()
                .data(infoUserResponse)
                .message("UserInfo Get successfully" )
                .success(true)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(User.class));
    }

    @Override
    public Optional<User> getOptionalUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}