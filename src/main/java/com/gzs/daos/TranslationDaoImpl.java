package com.gzs.daos;

import com.gzs.model.Translation;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TranslationDaoImpl extends DatabaseDao implements TranslationDao  {

    private static List<PreparedStatement> statements;
    private static PreparedStatement getAllStatement;
    private static PreparedStatement getByIdStatement;
    private static PreparedStatement getByTerm1IdStatement;
    private static PreparedStatement getByTerm2IdStatement;
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement deleteStatement;

    static {
        String tableName = "translations";
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByTerm1IdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term1id = ?");
            getByTerm2IdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term2id = ?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET term1id=?, term2id=?, priority=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            statements = new ArrayList<>();
            statements.add(getAllStatement);
            statements.add(getByIdStatement);
            statements.add(getByTerm1IdStatement);
            statements.add(getByTerm2IdStatement);
            statements.add(insertStatement);
            statements.add(updateStatement);
            statements.add(deleteStatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Translation> getAll() {
        return getAllFromDatabase(getAllStatement);
    }

    @Override
    public Translation get(int id) {
        Translation data = getterFromInt(getByIdStatement,id);
        return nullCheck(data).orElse(new Translation());
    }

    @Override
    public Translation getByTerm1Id(int term1ID) {
        Translation data = getterFromInt(getByTerm1IdStatement,term1ID);
        return nullCheck(data).orElse(new Translation());
    }

    @Override
    public Translation getByTerm2Id(int term2ID) {
        Translation data = getterFromInt(getByTerm2IdStatement,term2ID);
        return nullCheck(data).orElse(new Translation());
    }

    @Override
    protected Translation getFromResultSet(ResultSet resultSet) {
        Translation translation = new Translation();
        TermDao termDao = new TermDaoImpl();
        try {
            int id = resultSet.getInt("ID");
            int term1ID = resultSet.getInt("Term1ID");
            int term2ID = resultSet.getInt("Term2ID");
            int priority = resultSet.getInt("Priority");
            translation = new Translation(id, termDao.get(term1ID), termDao.get(term2ID), priority);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return translation;
    }

    @Override
    public boolean insert(Translation translation) {
        if (translation!=null) {
            try {
                int i = 1;
                insertStatement.setInt(i++, translation.getId());
                insertStatement.setInt(i++, translation.getTerm1ID().getId());
                insertStatement.setInt(i++, translation.getTerm2ID().getId());
                insertStatement.setInt(i++, translation.getPriority());

                return insertStatement.executeUpdate()==1;
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Translation translation) {
        if (translation!=null) {
            try {
                int i = 1;
                updateStatement.setInt(i++, translation.getTerm1ID().getId());
                updateStatement.setInt(i++, translation.getTerm2ID().getId());
                updateStatement.setInt(i++, translation.getPriority());
                updateStatement.setInt(i++, translation.getId());

                return updateStatement.executeUpdate()==1;
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Translation translation) {
        if (translation!=null) {
            return deleteFromDatabase(deleteStatement,translation.getId());
        } else {
            log.warn("User tried to delete Translation with null value from data.");
            return false;
        }
    }

    public static void endStatements() {
        statements.forEach(statement -> endStatement(statement));
    }
}
