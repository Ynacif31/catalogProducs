package com.ygornacif.projetoCatalog.repositories;

import com.ygornacif.projetoCatalog.entities.User;
import com.ygornacif.projetoCatalog.entities.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(nativeQuery = true, value = """
    SELECT tb_user.email AS username, tb_user.password, tb_role.id AS role_id, tb_role.authority
    FROM tb_user
    INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
    INNER JOIN tb_role ON tb_user_role.role_id = tb_role.id
    WHERE tb_user.email = :email
    """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
