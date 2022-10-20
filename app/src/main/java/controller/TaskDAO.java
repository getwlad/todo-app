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
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Wladmir Rodrigues
 */
public class TaskDAO {

    public void save(Task task)  {
        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = conn.prepareStatement(sql);
            
            //Definindo valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            
            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao salvar a tarefa "
                    + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(Task task) {
        String sql = "UPDATE tasks SET "
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
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
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao atualizar a tarefa "
                    + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int taskId)  {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = conn.prepareStatement(sql);
            
            //Definindo valores do statement
            statement.setInt(1, taskId);
            
            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao deletar a tarefa " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Task> getAll(int idProject)  {
        String sql = "SELECT * from tasks where idProject = ?";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Task> tasks = new ArrayList<>();
        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = conn.prepareStatement(sql);
            
            //Definindo valores do statement
            statement.setInt(1, idProject);
            //Executando a query
            rs = statement.executeQuery();
            
            //Obtendo valores e adicionado a lista de tarefas
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setIdProject(rs.getInt("idProject"));
                task.setName(rs.getString("name"));
                task.setDescription(rs.getString("description"));
                task.setNotes(rs.getString("notes"));
                task.setIsCompleted(rs.getBoolean("completed"));
                task.setDeadline(rs.getDate("deadline"));
                task.setCreatedAt(rs.getDate("createdAt"));
                task.setUpdatedAt(rs.getDate("updatedAt"));
                tasks.add(task);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao obter lista de tarefas " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, rs);
        }
        //Retornando tarefas
        return tasks;

    }

}
