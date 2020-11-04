package lab.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Seniorities
{
    JUNIOR(1, "Junior", 0, 14000),
    MIDDLE(2, "Middle", 14000, 21000),
    SENIORS(3, "Seniors", 21000, Integer.MAX_VALUE);

    final private int id;
    final private String type;
    final private long salaryMin;
    final private long salaryMax;

    public Seniorities getSeniriotyById(int id)
    {
        return Arrays.stream(Seniorities.values()).filter(s -> s.getId() == id).findFirst().orElseThrow(() -> new RuntimeException("Wrong id"));
    }

}
