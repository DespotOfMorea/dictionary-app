package com.gzs.daos;

import com.gzs.model.Translation;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class TranslationDaoImpl extends DatabaseDao implements TranslationDao  {

    private static PreparedStatement getByTerm1Idstatement;
    private static PreparedStatement getByTerm2Idstatement;

    static {
        tableName = "translations";
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByTerm1Idstatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term1id = ?");
            getByTerm2Idstatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term2id = ?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET term1id=?, term2id=?, priority=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            statements.add(getByTerm1Idstatement);
            statements.add(getByTerm2Idstatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Translation> getAll() {
        return getAllFromDatabase();
    }

    @Override
    public Translation get(int id) {
        Translation data = getterFromInt(getByIdStatement,id);
        return nullCheck(data);
    }

    @Override
    public Translation getByTerm1Id(int term1ID) {
        Translation data = getterFromInt(getByTerm1Idstatement,term1ID);
        return nullCheck(data);
    }

    @Override
    public Translation getByTerm2Id(int term2ID) {
        Translation data = getterFromInt(getByTerm2Idstatement,term2ID);
        return nullCheck(data);
    }

    private Translation nullCheck(Translation translation){
        if (translation==null){
            translation = new Translation();
        }
        return translation;
    }

    protected Translation getFromResultSet(ResultSet resultSet) {
        Translation translation = new Translation();
        try {
            int id = resultSet.getInt("ID");
            int term1ID = resultSet.getInt("Term1ID");
            int term2ID = resultSet.getInt("Term2ID");
            int priority = resultSet.getInt("Priority");
            translation = new Translation(id, term1ID, term2ID, priority);
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

                return successfulAction(insertStatement.executeUpdate());
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

                return successfulAction(updateStatement.executeUpdate());
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
            return deleteFromDatabase(translation.getId());
        } else {
            return false;
        }
    }
}
