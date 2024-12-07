package com.ygornacif.projetoCatalog.entities.projections;

public interface UserDetailsProjection {
    String getUsername();
    String getPassword();
    Long getRoleId();
    String getAuthority();

}
