package daos;

import config.DbConnection;
import model.Auction;
import model.Lot;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionDbDAO implements DAO<Auction> {
    private Connection connection;


    public AuctionDbDAO() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public Auction get(int id) {
        String query = "select * from auctions where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString(2);
            LocalDateTime closingDatetime  = resultSet.getTimestamp(3).toLocalDateTime();
            String details = resultSet.getString(4);

            Auction auc = new Auction(id, name, closingDatetime, details);
            LotDbDAO lotDbDAO = new LotDbDAO();
            List<Lot> lots = lotDbDAO.getAll();
            for (Lot lot: lots) {
                if(lot.getAuctionId() == id)
                    auc.addLot(lot);
            }
            return auc;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Auction> getAll() {
        List<Auction> auctions = new ArrayList<>();
        String query = "select * from auctions";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                LocalDateTime closingDatetime  = resultSet.getTimestamp(3).toLocalDateTime();
                String details = resultSet.getString(4);
                auctions.add(new Auction(id, name, closingDatetime, details));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auctions;
    }

    @Override
    public void save(Auction auction) {
        String query = "insert into auctions values (?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, auction.getId());
            preparedStatement.setString(2, auction.getName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(auction.getClosingDatetime()));
            preparedStatement.setString(4, auction.getDetails());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Auction auction, String[] params) {

    }

    @Override
    public void delete(Auction auction) {
        String query = "delete from auctions where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, auction.getId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
