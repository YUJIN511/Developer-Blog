package com.papple.blog.social;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.papple.blog.models.ERole;
import com.papple.blog.models.Role;
import com.papple.blog.models.User;
import com.papple.blog.repository.RoleRepository;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey, name, email, picture;
    
    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }
    public static OAuthAttributes ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private RoleRepository roleRepository;
    public User toEntity() {
        
        Set<Role> roles = new HashSet<>();
        Role userRole = new Role(1,ERole.ROLE_USER);
        roles.add(userRole);
        
        return User.builder()
                .nickname(name)
                .email(email)
                .profile(picture)
                .roles(roles)
                .build();
    }
}