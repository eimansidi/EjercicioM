package com.eiman.aeropuerto.dao;

import com.eiman.aeropuerto.db.DatabaseConnection;
import com.eiman.aeropuerto.models.Aeropuerto;
import com.eiman.aeropuerto.models.AeropuertoPrivado;
import com.eiman.aeropuerto.models.Direccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ejecuta las consultas para la tabla Aeropuertos Privados
 */
public class AeropuertoPrivadoDAO {
    /**
     * Busca un aeropuerto privado por medio de su id
     *
     * @param id id del aeropuerto a buscar
     * @return aeropuerto privado o null
     */
    public static AeropuertoPrivado getAeropuerto(int id) {
        DatabaseConnection connection;
        AeropuertoPrivado aeropuerto = null;
        try {
            connection = new DatabaseConnection();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen,numero_socios FROM aeropuertos,aeropuertos_privados WHERE id=id_aeropuerto AND id = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_aeropuerto = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int anio_inauguracion = rs.getInt("anio_inauguracion");
                int capacidad = rs.getInt("capacidad");
                int id_direccion = rs.getInt("id_direccion");
                Direccion direccion = DireccionDAO.getDireccion(id_direccion);
                Blob imagen = rs.getBlob("imagen");
                Aeropuerto airport = new Aeropuerto(id,nombre,anio_inauguracion,capacidad,direccion,imagen);
                int numero_socios = rs.getInt("numero_socios");
                aeropuerto = new AeropuertoPrivado(airport,numero_socios);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return aeropuerto;
    }

    /**
     * Carga los datos de la tabla AeropuertoPrivado y los devuelve para usarlos en un listado de aeropuertos
     *
     * @return listado de aeropuertos privados para cargar en un tableview
     */
    public static ObservableList<AeropuertoPrivado> cargarListado() {
        DatabaseConnection connection;
        ObservableList<AeropuertoPrivado> airportList = FXCollections.observableArrayList();
        try{
            connection = new DatabaseConnection();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen,numero_socios FROM aeropuertos,aeropuertos_privados WHERE id=id_aeropuerto";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int anio_inauguracion = rs.getInt("anio_inauguracion");
                int capacidad = rs.getInt("capacidad");
                int id_direccion = rs.getInt("id_direccion");
                Direccion direccion = DireccionDAO.getDireccion(id_direccion);
                Blob imagen = rs.getBlob("imagen");
                Aeropuerto aeropuerto = new Aeropuerto(id,nombre,anio_inauguracion,capacidad,direccion,imagen);
                int numero_socios = rs.getInt("numero_socios");
                AeropuertoPrivado airport = new AeropuertoPrivado(aeropuerto,numero_socios);
                airportList.add(airport);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return airportList;
    }

    /**
     * Modifica los datos de un aeropuerto en la BD
     *
     * @param aeropuerto Aeropuerto con datos nuevos
     * @param aeropuertoNuevo Nuevos datos de aeropuerto a modificarDireccion
     * @return true/false
     */
    public static boolean modificarAeropuertoPrivado(AeropuertoPrivado aeropuerto, AeropuertoPrivado aeropuertoNuevo) {
        DatabaseConnection connection;
        PreparedStatement pstmt;
        try {
            connection = new DatabaseConnection();

            String consulta = "UPDATE aeropuertos_privados SET numero_socios = ? WHERE id_aeropuerto = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);

            pstmt.setInt(1, aeropuertoNuevo.getNumero_socios());
            pstmt.setInt(2, aeropuerto.getAeropuerto().getId());

            int filasAfectadas = pstmt.executeUpdate();

            System.out.println("Actualizado aeropuerto");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Crea un nuevo aeropuerto privado en la BD
     *
     * @param aeropuerto Aeropuerto privado con datos nuevos
     * @return true/false
     */
    public static boolean insertarAeropuertoPrivado(AeropuertoPrivado aeropuerto) {
        DatabaseConnection connection;
        PreparedStatement pstmt;

        try {
            connection = new DatabaseConnection();

            String consulta = "INSERT INTO aeropuertos_privados (id_aeropuerto,numero_socios) VALUES (?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta);

            pstmt.setInt(1, aeropuerto.getAeropuerto().getId());
            pstmt.setInt(2, aeropuerto.getNumero_socios());

            int filasAfectadas = pstmt.executeUpdate();

            pstmt.close();
            connection.closeConnection();
            return (filasAfectadas > 0);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un aeropuerto privado
     *
     * @param aeropuerto AeropuertoPrivado a eliminarDireccion
     * @return a boolean
     */
    public  static boolean eliminarAeropuertoPrivado(AeropuertoPrivado aeropuerto){

        DatabaseConnection connection;
        PreparedStatement pstmt;
        try {
            connection = new DatabaseConnection();
            String consulta = "DELETE FROM aeropuertos_privados WHERE (id_aeropuerto = ?)";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, aeropuerto.getAeropuerto().getId());
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
