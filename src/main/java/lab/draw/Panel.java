package lab.draw;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Panel
{
    @NonNull
    private Graphics graph;

    public void drawShape(Shape shape)
    {
        shape.draw(graph);
    }

}
