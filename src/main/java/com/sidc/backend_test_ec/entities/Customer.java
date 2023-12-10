package com.sidc.backend_test_ec.entities;

import com.sidc.backend_test_ec.interfaces.IEntity;
import com.sidc.backend_test_ec.interfaces.IResponseEntityBody;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Customer implements IEntity, Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String petImgUrl;

  public Long getId() {
    return id;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getEmail() { return this.email; }
  public void setEmail(String email) { this.email = email; }
  public boolean hasPet() { return this.petImgUrl != null; }
  public String getPetImgUrl() { return petImgUrl; }
  public void setPetImgUrl(String petImgUrl) { this.petImgUrl = petImgUrl; }

  @Override
  public String toString() {
    return String.format(
            "Customer[id=%d, firstName='%s', lastName='%s', email='%s']",
            id, firstName, lastName, email);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    return prime + ((id == null) ? 0 : id.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) return false;
    if (this == obj) return true;
    if (getClass() != obj.getClass()) return false;
    final Customer actorTask = (Customer) obj;
    return id.equals(actorTask.getId());
  }

  @Override
  public IResponseEntityBody toResponseEntityBody() {
    CustomerResponseEntityBody c = new CustomerResponseEntityBody();

    c.id = this.id;
    c.firstName = this.firstName;
    c.lastName = this.lastName;
    c.email = this.email;
    c.petImgUrl = (this.petImgUrl != null) ? this.petImgUrl : "";

    return c;
  }


  private static class CustomerResponseEntityBody implements IResponseEntityBody{
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String petImgUrl;

    @Override
    public Long getId() {
      return this.id;
    }
  }
}
