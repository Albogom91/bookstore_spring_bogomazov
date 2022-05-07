package com.belhard.bookstore.service.dto;

import java.util.Objects;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleDto roleDto;

    public enum RoleDto {
        ADMIN,
        MANAGER,
        CUSTOMER
    }

    public UserDto() {

    }

    public UserDto(String firstName, String lastName, String email, String password, RoleDto roleDto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleDto = roleDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDto getRoleDto() {
        return roleDto;
    }

    public void setRoleDto(RoleDto roleDto) {
        this.roleDto = roleDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id)
                && Objects.equals(firstName, userDto.firstName)
                && Objects.equals(lastName, userDto.lastName)
                && Objects.equals(email, userDto.email)
                && Objects.equals(password, userDto.password)
                && roleDto == userDto.roleDto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, roleDto);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleDto=" + roleDto +
                '}';
    }
}
