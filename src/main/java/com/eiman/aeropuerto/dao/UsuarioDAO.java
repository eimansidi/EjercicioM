package com.eiman.aeropuerto.dao;

import com.eiman.aeropuerto.db.DatabaseConnection;
import com.eiman.aeropuerto.models.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ejecuta las consultas para la tabla Usuarios
 */
public class UsuarioDAO {
    /**
     * Busca un usuario por medio de su nombre de usuario
     *
     * @param usuario nombre de usuario a buscar
     * @return usuario o null
     */
    public static Usuario getUsuario(String usuario) {
        DatabaseConnection connection;
        Usuario user = null;
        try {
            connection = new DatabaseConnection();
            String consulta = "SELECT usuario,password FROM usuarios WHERE usuario = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nom_usuario = rs.getString("usuario");
                String password = rs.getString("password");
                user = new Usuario(nom_usuario, password);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
