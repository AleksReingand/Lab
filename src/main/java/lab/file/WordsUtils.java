package lab.file;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;


public class WordsUtils
{
    @SneakyThrows
    public static int culculatorWords(File file)
    {
        return (int)new BufferedReader(new FileReader(file)).lines().flatMap(words -> Arrays.stream(words.split(" "))).count();
    }

    @SneakyThrows
    public static double averageWords(File file)
    {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        return bf.lines().flatMap(words -> Arrays.stream(words.split(" "))).mapToInt(word -> word.length()).average().getAsDouble();
    }
}
