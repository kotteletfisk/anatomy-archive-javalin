package dat.dto;

import dat.entities.Role;

public record RegisterDTO(String username, String password, Role role) { }
