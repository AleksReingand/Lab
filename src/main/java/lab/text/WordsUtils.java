package lab.text;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


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
        return bf.lines().flatMap(words -> Arrays.stream(words.split(" "))).mapToInt(String::length).average().getAsDouble();
    }

    public static long mostPopularWord(String text)
    {
        return Arrays.stream(text.split(" "))
                .collect(Collectors.groupingBy((word) -> word, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(()-> new RuntimeException("Empty test"))
                .getValue();
    }

}
