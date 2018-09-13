package com.dome.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "t_user")
public class User implements UserDetails ,Serializable {
    private static final long serialVersionUID = -383540476492602568L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "r_users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<? extends GrantedAuthority> authorities = getAuthorities(this.roles);
        return authorities;
    }

    /**
     * 获取权限
     *
     * @param roles 角色集合
     * @return 角色的授权集合
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPermissionNames(roles), getRoleNames(roles));
    }
    /**
     * @param permissions 用户的permissions
     * @param roles       用户角色
     * @return 所有授权包括角色和资源权限
     */
    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions, List<String> roles) {

        List<String> roles_and_permission = new ArrayList<>();
        roles_and_permission.addAll(permissions);
        roles_and_permission.addAll(roles);
        List<GrantedAuthority> authorities = roles_and_permission
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }
    /**
     * 获取所有权限名
     *
     * @param roles
     * @return
     */
    private List<String> getPermissionNames(Collection<Role> roles) {
        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        permissions.addAll(collection.stream().map(Permission::getName).collect(Collectors.toList()));
        return permissions;
    }
    /**
     * @param roles 角色集合
     * @return 角色名集合
     */
    private List<String> getRoleNames(Collection<Role> roles) {
        List<String> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        return roleNames;
    }
    /**
     * 判断用户是否过期
     *
     * @return 不过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 返回账户是否被停用
     *
     * @return 是否被停用
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    /**
     * @return 授权是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}
