package cinema.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class Seat implements Comparable<Seat>{
    @Min(1)
    @Max(9)
    private int row;
    @Min(1)
    @Max(9)
    private int column;
    @Nullable
    private int price;

    public Seat() {
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        price = getRow() <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int compareTo(Seat otherSeat) {
        int result = getRow() - otherSeat.getRow();
        if (result == 0) {
            result = getColumn() - otherSeat.getColumn();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && column == seat.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
