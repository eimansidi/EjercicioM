package com.eiman.aeropuerto.dao;

import com.eiman.aeropuerto.db.DatabaseConnection;
import com.eiman.aeropuerto.models.Direccion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ejecuta las consultas para la tabla Direcciones
 */
public class DireccionDAO {
    /**
     * Obtiene los datos de la tabla Dirección
     *
     * @param id de la dirección a buscar
     * @return dirección o null
     */
    public static Direccion getDireccion(int id) {
        DatabaseConnection connection;
        Direccion direccion = null;
        try {
            connection = new DatabaseConnection();
            String consulta = "SELECT id,pais,ciudad,calle,numero FROM direcciones WHERE id = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String pais = rs.getString("pais");
                String ciudad = rs.getString("ciudad");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");
                direccion = new Direccion(id, pais, ciudad, calle, numero);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return direccion;
    }

    /**
     * Modifica los datos de una dirección en la BD
     *
     * @param direccion Dirección con datos nuevos
     * @param direccionNueva Nuevos datos de la dirección a modificarDireccion
     * @return true/false
     */
    public static boolean modificarDireccion(Direccion direccion, Direccion direccionNueva) {
        DatabaseConnection connection;
        PreparedStatement pstmt;
        try {
            connection = new DatabaseConnection();
            String consulta = "UPDATE direcciones SET pais = ?,ciudad = ?,calle = ?,numero = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, direccionNueva.getPais());
            pstmt.setString(2, direccionNueva.getCalle());
            pstmt.setString(3, direccionNueva.getCalle());
            pstmt.setInt(4, direccionNueva.getNumero());
            pstmt.setInt(5, direccion.getId());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Crea una nueva dirección en la BD
     *
     * @param direccion Dirección con datos nuevos
     * @return id/-1
     */
    public  static int insertarDireccion(Direccion direccion) {
        DatabaseConnection connection;
        PreparedStatement pstmt;
        try {
            connection = new DatabaseConnection();
            String consulta = "INSERT INTO direcciones (pais,ciudad,calle,numero) VALUES (?,?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, direccion.getPais());
            pstmt.setString(2, direccion.getCiudad());
            pstmt.setString(3, direccion.getCalle());
            pstmt.setInt(4, direccion.getNumero());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    pstmt.close();
                    connection.closeConnection();
                    return id;
                }
            }
            pstmt.close();
            connection.closeConnection();
            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Elimina una dirección
     *
     * @param direccion Dirección a eliminarDireccion
     * @return boolean Si se eliminó o no
     */
    public  static boolean eliminarDireccion(Direccion direccion){
        DatabaseConnection connection;
        PreparedStatement pstmt;
        try {
            connection = new DatabaseConnection();
            String consulta = "DELETE FROM direcciones WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, direccion.getId());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
