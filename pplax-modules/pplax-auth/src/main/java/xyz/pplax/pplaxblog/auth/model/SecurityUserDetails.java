package xyz.pplax.pplaxblog.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;

import java.util.Collection;

public class SecurityUserDetails extends User {

    private String uid;

    private String salt;

    private String userInfoUid;

    private String roleUid;

    public SecurityUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserInfoUid() {
        return userInfoUid;
    }

    public void setUserInfoUid(String userInfoUid) {
        this.userInfoUid = userInfoUid;
    }

    public String getRoleUid() {
        return roleUid;
    }

    public void setRoleUid(String roleUid) {
        this.roleUid = roleUid;
    }
}
