
package uzh.tomdb.db.indexes;

import java.util.ArrayList;
import java.util.List;

import net.tomp2p.peers.Number160;

/**
 * 
 * A helper class that stores the interval (DST).
 *
 * @author Thomas Bocek / Francesco Luminati
 *
 */
public class DSTBlock {

    private final int from;
    private final int to;
    private final String column;

    /**
     * @param from Interval from
     * @param to Interval to
     */
    public DSTBlock(int from, int to, String column) {
        if (from > to) {
            throw new IllegalArgumentException("from cannot be greater than to");
        }
        this.from = from;
        this.to = to;
        this.column = column;
    }

    @Override
    public String toString() {
        return "DSTBlock:"+column+":[" + from + ".." + to + "]";
    }

    public Number160 getHash() {
        return Number160.createHash(toString());
    }

    /**
     * @return Split the interval into two parts
     */
    public List<DSTBlock> split() {
        int mid = (from + to) / 2;

        List<DSTBlock> retVal = new ArrayList<>();
        retVal.add(new DSTBlock(from, mid, column));
        retVal.add(new DSTBlock(mid + 1, to, column));
        return retVal;
    }

    /**
     * @param nr From where to split
     * @return split the interval at the given position
     */
    public DSTBlock split(final int nr) {
        int mid = (from + to) / 2;

        if (nr <= mid) {
            // left interval
            return new DSTBlock(from, mid, column);
        } else {
            // right interval
            return new DSTBlock(mid + 1, to, column);
        }
    }

    /**
     * @return The size of the interval.
     */
    public int size() {
        return (to - from) + 1;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}

