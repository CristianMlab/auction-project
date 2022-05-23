package daos;

import config.DbConnection;
import model.DefaultItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDbDAO implements DAO<DefaultItem> {

    private Connection connection;

    public ItemDbDAO() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public DefaultItem get(int id) {
        String query = "select * from default_items where lot_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String description = resultSet.getString(2);
            return new DefaultItem(id, description);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DefaultItem> getAll() {
        List<DefaultItem> defaultItems = new ArrayList<>();
        String query = "select * from default_items";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);
                defaultItems.add(new DefaultItem(id, description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultItems;
    }

    @Override
    public void save(DefaultItem defaultItem) {
        String query = "insert into default_items values (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, defaultItem.getLotId());
            preparedStatement.setString(2, defaultItem.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DefaultItem defaultItem, String[] params) {

    }

    @Override
    public void delete(DefaultItem defaultItem) {
        String query = "delete from default_items where lot_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, defaultItem.getLotId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
