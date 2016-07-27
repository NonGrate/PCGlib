package kg.nongrate.pcglib.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by arseniii on 3/12/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rectangle {
    private int top;
    private int left;
    private int bottom;
    private int right;
}
