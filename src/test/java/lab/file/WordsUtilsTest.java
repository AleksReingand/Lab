package lab.file;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;

public class WordsUtilsTest
{
    private static File file = new File("testLines.txt");

    @Test
    public void countWords()
    {
        int amount = WordsUtils.culculatorWords(file);
        Assert.assertEquals(6, amount);
    }

    @Test
    public void avrWords()
    {
        double avrLength = WordsUtils.averageWords(file);
        Assert.assertEquals(4, avrLength, 0.00001);
    }
}