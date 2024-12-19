package com.ygornacif.projetoCatalog.DTO;

import com.ygornacif.projetoCatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO{
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 8, message = "Deve ter no minimo 8 caractéres")
    private String password;

    public UserInsertDTO(){
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
