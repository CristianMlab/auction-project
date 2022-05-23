package daos;

import config.DbConnection;
import model.Bid;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BidDbDAO implements DAO<Bid>{

    private Connection connection;

    public BidDbDAO() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public Bid get(int id) {
        return null;
    }

    @Override
    public List<Bid> getAll() {
        List<Bid> bids = new ArrayList<>();
        String query = "select * from bids";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                double value = resultSet.getDouble(1);
                LocalDateTime time = resultSet.getTimestamp(2).toLocalDateTime();
                int userId  = resultSet.getInt(3);
                int lotId = resultSet.getInt(4);
                bids.add(new Bid(value, time, userId, lotId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bids;
    }

    @Override
    public void save(Bid bid) {
        String query = "insert into bids values (?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, bid.getValue());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(bid.getTime()));
            preparedStatement.setInt(3, bid.getUserId());
            preparedStatement.setInt(4, bid.getLotId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bid bid, String[] params) {

    }

    @Override
    public void delete(Bid bid) {
        String query = "delete from bids where value = ? AND user_id = ? AND lot_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, bid.getValue());
            preparedStatement.setInt(2, bid.getUserId());
            preparedStatement.setInt(3, bid.getLotId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
