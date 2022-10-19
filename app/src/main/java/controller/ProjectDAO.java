/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionFactory;
import model.Project;

/**
 *
 * @author Wladmir Rodrigues
 */
public class ProjectDAO {

    public void save(Project project) {
        String sql = "INSERT INTO projects (name,"
                + "description,"
                + "createdAt,"
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Definindo valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao salvar a projeto "
                    + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET "
                + "name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ? "
                + "WHERE id = ?";
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Definindo valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            //Executando a query
            statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao atualizar o projeto "
                    + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Definindo valores do statement
            statement.setInt(1, projectId);

            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao deletar o projeto " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * from projects";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Project> projects = new ArrayList<>();
        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Executando a query
            rs = statement.executeQuery();

            //Obtendo valores e adicionado a lista de tarefas
            while (rs.next()) {
                Project proj = new Project();
                proj.setId(rs.getInt("id"));
                proj.setName(rs.getString("name"));
                proj.setDescription(rs.getString("description"));
                proj.setCreatedAt(rs.getDate("createdAt"));
                proj.setUpdatedAt(rs.getDate("updatedAt"));
                projects.add(proj);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao obter lista de projetos " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, rs);
        }
        //Retornando projetos
        return projects;

    }
}
