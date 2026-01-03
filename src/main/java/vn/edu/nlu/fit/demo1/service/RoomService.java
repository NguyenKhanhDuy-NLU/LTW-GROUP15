package vn.edu.nlu.fit.demo1.service;


import vn.edu.nlu.fit.demo1.dao.RoomDAO;
import vn.edu.nlu.fit.demo1.model.Room;



public class RoomService {

    private RoomDAO roomDAO = new RoomDAO();

    public Room getRoomById(int id) {
        return roomDAO.findById(id);
    }
}
