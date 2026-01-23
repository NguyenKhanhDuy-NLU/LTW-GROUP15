package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.AmenityDAO;
import vn.edu.nlu.fit.demo1.dao.RoomDAO;
import vn.edu.nlu.fit.demo1.model.Room;

public class RoomService {
    private RoomDAO roomDAO = new RoomDAO();
    private AmenityDAO amenityDAO = new AmenityDAO();

    public Room getRoomDetail(int id) {
        Room room = roomDAO.findById(id);
        if (room != null) {
            room.setAmenities(amenityDAO.getByRoomId(id));
        }
        return room;
    }
}
