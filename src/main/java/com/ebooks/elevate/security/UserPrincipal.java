package com.ebooks.elevate.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ebooks.elevate.entity.UserVO;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;
	private long userId;
    private String userName;
    private String password;
    private Long orgId;
    private Collection<? extends GrantedAuthority> authorities;
    
    public UserPrincipal(long userId, String userName, String password,Long orgId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.orgId=orgId;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserVO user) {

        /**List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());**/

        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrincipal(
                user.getId(),
                user.getUserName(),
                user.getPassword(),user.getOrgId(),
                authorities
        );
    }

    public static UserPrincipal create(UserVO user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        //userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
    
    public Long getOrgId() {
        return orgId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

	@Override
	public String getUsername() {
		return userName;
	}

}
