package org.openemp.api.user.util;

/**
 * The type User principal.
 */
public class UserPrincipal  { //implements UserDetails
//
//    private static final long serialVersionUID = 1L;
//
//    private final User user;
//
//    public UserPrincipal(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        if (user.getRole() != null) authorities.add(new SimpleGrantedAuthority(user.getRole()));
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.getActive();
//    }
}
