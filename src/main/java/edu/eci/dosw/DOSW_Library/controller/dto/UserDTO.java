package edu.eci.dosw.DOSW_Library.controller.dto;

import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private Role role;
}