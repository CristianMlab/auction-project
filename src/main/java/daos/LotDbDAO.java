package daos;

import config.DbConnection;
import model.BidHistory;
import model.DefaultItem;
import model.Lot;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LotDbDAO implements DAO<Lot>{

    private Connection connection;
    private BidHistoryDbDAO bidHistoryDbDAO;
    private ItemDbDAO itemDbDAO;


    public LotDbDAO() throws SQLException {
        this.connection = DbConnection.getInstance();
        bidHistoryDbDAO = new BidHistoryDbDAO();
        itemDbDAO = new ItemDbDAO();
    }

    @Override
    public Lot get(int id) {
        String query = "select * from lots where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int auctionId = resultSet.getInt(2);
            String lotName  = resultSet.getString(3);
            Double startingBid = resultSet.getDouble(4);
            LocalDateTime closingDatetime = resultSet.getTimestamp(5).toLocalDateTime();

            Lot lot = new Lot(lotName, id, startingBid, auctionId, closingDatetime);
            BidHistory history = bidHistoryDbDAO.get(id);
            DefaultItem item = itemDbDAO.get(id);
            lot.setBids(history);
            lot.setItem(item);
            return lot;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Lot> getAll() {
        List<Lot> lots = new ArrayList<>();
        String query = "select * from lots";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                int auctionId = resultSet.getInt(2);
                String lotName  = resultSet.getString(3);
                Double startingBid = resultSet.getDouble(4);
                LocalDateTime closingDatetime = resultSet.getTimestamp(5).toLocalDateTime();
                Lot lot = new Lot(lotName, id, startingBid, auctionId, closingDatetime);
                BidHistory history = bidHistoryDbDAO.get(id);
                DefaultItem item = itemDbDAO.get(id);
                lot.setBids(history);
                lot.setItem(item);
                lots.add(lot);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lots;
    }

    @Override
    public void save(Lot lot) {
        String query = "insert into lots values (?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, lot.getId());
            preparedStatement.setInt(2, lot.getAuctionId());
            preparedStatement.setString(3, lot.getLotName());
            preparedStatement.setDouble(4, lot.getStartingBid());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(lot.getClosingDatetime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Lot lot, String[] params) {

    }

    @Override
    public void delete(Lot lot) {
        String query = "delete from lots where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, lot.getId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
