package org.tonguetied.usermanagement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * A user of the system.
 * 
 * @author bsion
 *
 */
@Entity
@AccessType("property")
@NamedQuery(name="get.users",query="from User u order by u.username")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements UserDetails {
    private Long id;
    private String username;
    transient private String password;
    transient private String repeatedPassword; 
    private String firstName;
    private String lastName;
    private boolean isEnabled;
    private String email;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private SortedSet<UserRight> userRights;
    private GrantedAuthority[] grantedAuthorities;
    
    // This attribute is used for optimistic concurrency control in DB    
    private Integer version;
    
    private static final long serialVersionUID = -7800860686467033859L;
    
    /**
     * Create a new instance of the User object.
     */ 
    public User() {
        this.userRights = new TreeSet<UserRight>();
    }
    
    /**
     * Create a new instance of the User object.
     * 
     * @param username unique name of the user
     * @param password user's password
     * @param firstName the user's given name
     * @param lastName the surname of the user
     * @param email the email address of the user
     * @param isEnabled flag indicating if the user is active
     * @param isAccountNonExpired flag indicating whether the user's account 
     * has expired
     * @param isAccountNonLocked flag indicating whether the user is locked or 
     * unlocked 
     * @param isCredentialsNonExpired flag indicating whether the user's 
     * credentials (password) has expired
     */
    public User(String username, String password, String firstName, String lastName, String email, boolean isEnabled, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnabled = isEnabled;
        this.email = email;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.userRights  = new TreeSet<UserRight>();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }
        
    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the first name of the User
     */
    @Column(nullable=false)
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the first name of the User to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the last name of the User
     */
    @Column(nullable=false)
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the last name of the User to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the password of the User
     */
    @Column(nullable=false)
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set for the User
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the repeatedPassword
     */
    @Transient
    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    /**
     * @param repeatedPassword the repeatedPassword to set
     */
    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    /**
     * @return the username of the User
     */
    @Column(unique=true, nullable=false)
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username of the User to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * This field is used for user authentication
     * 
     * @return an indicator specifying if the user is active or not.
     */
    @Column(nullable=false)
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled the active flag to set
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return the email address of this User
     */
    @Column(nullable=false)
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the set of authorized permissions granted to a User
     */
    @CollectionOfElements(fetch=FetchType.LAZY)
    @Sort(type=SortType.NATURAL)
    @JoinTable(name="authorities", joinColumns=@JoinColumn(name="userid"))
    @Cascade(CascadeType.ALL)
    public SortedSet<UserRight> getUserRights() {
        return userRights;
    }

    /**
     * @param userRights the authorized permissions to set
     */
    public void setUserRights(SortedSet<UserRight> userRights) {
        this.userRights = userRights;
        if (userRights != null && !userRights.isEmpty()) {
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            GrantedAuthority authority;
            for (UserRight userRight: getUserRights()) {
                authority = 
                    new GrantedAuthorityImpl(userRight.getPermission().name());
                authorities.add(authority);
            }
            
            this.setAuthorities(authorities.toArray(
                    new GrantedAuthority[authorities.size()]));
        }
    }
    
    /**
     * Convenience method to add a permission to this User.
     * 
     * @param userRight the authorized permission to add
     * @throws IllegalArgumentException if the <tt>userRight</tt> is 
     * <tt>null</tt>
     */
    public void addUserRight(UserRight userRight) {
        if (userRight == null) {
            throw new IllegalArgumentException("userRight cannot be null");
        }
            
        this.userRights.add(userRight);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (this.grantedAuthorities != null) {
            authorities = new HashSet<GrantedAuthority>(
                    Arrays.asList(this.grantedAuthorities));
        }
        GrantedAuthority authority = 
            new GrantedAuthorityImpl(userRight.getPermission().name());
        if (!authorities.contains(authority)) {
            authorities.add(authority);
        }
        this.setAuthorities(authorities.toArray(
                new GrantedAuthority[authorities.size()]));
    }

    /* (non-Javadoc)
     * @see org.acegisecurity.userdetails.UserDetails#isAccountNonExpired()
     */
    @Column(nullable=false)
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }
    
    /**
     * @param isAccountNonExpired the flag indicating if the account has 
     * expired
     */
    public void setAccountNonExpired(boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    /* (non-Javadoc)
     * @see org.acegisecurity.userdetails.UserDetails#isAccountNonLocked()
     */
    @Column(nullable=false)
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    /**
     * @param isAccountNonLocked the isAccountNonLocked to set
     */
    public void setAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    /* (non-Javadoc)
     * @see org.acegisecurity.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Column(nullable=false)
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    /**
     * @param isCredentialsNonExpired the isCredentialsNonExpired to set
     */
    public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    /**
     * Returns a cloned array of {@link GrantedAuthority}s. This is done for 
     * security reasons, as the authorities are mutable.
     * 
     * @return the {@link GrantedAuthority}s for this user.
     */
    @Transient
    public GrantedAuthority[] getAuthorities() {
        return grantedAuthorities == null? null: grantedAuthorities.clone();
    }
    
    public void setAuthorities(GrantedAuthority[] authorities) {
//        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority array");
//        for (int i = 0; i < authorities.length; i++) {
//            Assert.notNull(authorities[i], "Granted authority element " + i + " is null - GrantedAuthority[] cannot contain any null elements");
//        }
        this.grantedAuthorities = authorities;
    }
    
    /**
     * This field is used for optimistic locking.
     * 
     * @return the version
     */
    @Version
    @Column(name="OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    /**
     * This field is used for optimistic locking.
     * 
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        // a good optimization
        if (this == obj)
            isEqual = true;
        else if (obj != null && getClass() == obj.getClass()) 
        {
            final User other = (User) obj;
            EqualsBuilder builder = new EqualsBuilder();
            isEqual = builder.append(username, other.username).
                    append(firstName, other.firstName).
                    append(lastName, other.lastName).
                    append(isEnabled, other.isEnabled).
                    append(email, other.email).
                    append(isAccountNonExpired, other.isAccountNonExpired).
                    append(isAccountNonLocked, other.isAccountNonLocked).
                    append(isCredentialsNonExpired, other.isCredentialsNonExpired).
//                    append(userRights, other.userRights).
                    isEquals();  
        }
            
        return isEqual;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 19);
        int hashCode = builder.append(username).
                    append(lastName).
                    append(firstName).
                    append(isEnabled).
                    append(email).
                    append(isAccountNonExpired).
                    append(isAccountNonLocked).
                    append(isCredentialsNonExpired).
//                    append(userRights).
                    toHashCode();
        
        return hashCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}