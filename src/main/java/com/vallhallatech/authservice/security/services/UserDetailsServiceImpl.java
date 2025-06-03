package com.vallhallatech.authservice.security.services;

import com.vallhallatech.authservice.persistance.entities.User;
import com.vallhallatech.authservice.security.entities.UseDetailsImpl;
import com.vallhallatech.authservice.services.IUserRoleService;
import com.vallhallatech.authservice.services.IUserService;
import com.vallhallatech.authservice.web.dtos.responses.GetRoleResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserService userService;
    private final IUserRoleService userRoleService;

    public UserDetailsServiceImpl(IUserService userService, IUserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username);
        List<GetRoleResponse> roles = userRoleService.getRolesByUserId(user.getId());

        return new UseDetailsImpl(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<GetRoleResponse> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
