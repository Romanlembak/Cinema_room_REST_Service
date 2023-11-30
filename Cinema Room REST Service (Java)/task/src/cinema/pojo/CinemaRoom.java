package cinema.pojo;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CinemaRoom {
    private int rows;
    private int columns;
    private Set<Seat> seats;

    public CinemaRoom() {
    }

    public CinemaRoom(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        seats = Collections.synchronizedSet(new TreeSet<Seat>( Comparator.naturalOrder()));
        initSeats(rows, columns);
    }

    private void initSeats(int rows, int columns) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                seats.add(new Seat(i, j));
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Set<Seat> getSeats() {
        return Collections.synchronizedSet(new TreeSet<>(seats));
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = Collections.synchronizedSet(new TreeSet<>(seats));
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public void removeSeat(Seat seat) {
        seats.remove(seat);
    }
}
