/**
 * 
 */
package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author POOJA
 *
 */
@Entity(name = "UserCredential")
@Table(name = "UserCrendential")
public class UserCredential {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="isAdmin")
	private boolean admin;
	
	@Column(name="isProfileComplete")
	private boolean profileComplete;
	
	public UserCredential() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean isAdmin) {
		this.admin = isAdmin;
	}

	public boolean getProfileComplete() {
		return profileComplete;
	}

	public void setProfileComplete(boolean isProfileComplete) {
		this.profileComplete = isProfileComplete;
	}

	@Override
	public String toString() {
		return "UserCredential [id=" + id + ", username=" + username + ", password=" + password + ", admin=" + admin
				+ ", profileComplete=" + profileComplete + "]";
	}

}
