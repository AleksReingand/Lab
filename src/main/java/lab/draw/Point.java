package lab.draw;

public class Point extends Shape
{
    @Override
    void draw(Graphics graph)
    {
        System.out.println("thickness: " + graph.getThickness() + "color: " + graph.getColor() + " I am point");
    }
}
