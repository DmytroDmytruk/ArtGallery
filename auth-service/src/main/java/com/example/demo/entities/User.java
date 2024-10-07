package com.example.demo.entities;

import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class User implements UserDetails   {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(columnDefinition = "LONGBLOB")
    @Lob
    private byte[] avatar;
    
    @OneToMany(mappedBy = "user")
    private List<Likes> likes;
    
    @OneToMany(mappedBy = "user")
    private List<Image> images;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	 @Override
	 public boolean isAccountNonExpired() {
	     return true;
	 }

	 @Override
	 public boolean isAccountNonLocked() {
	     return true;
	 }

	 @Override
	 public boolean isCredentialsNonExpired() {
	     return true;
	 }

	 @Override
	 public boolean isEnabled() {
	     return true;
	 } 
}

