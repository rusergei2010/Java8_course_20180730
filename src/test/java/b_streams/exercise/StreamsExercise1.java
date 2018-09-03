package b_streams.exercise;

import static b_streams.data.Generator.generateEmployeeList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import b_streams.data.Employee;
import b_streams.data.Generator;
import b_streams.data.JobHistoryEntry;

public class StreamsExercise1 {
    // https://youtu.be/kxgo7Y4cdA8 Сергей Куксенко и Алексей Шипилёв — Через тернии к лямбдам, часть 1
    // https://youtu.be/JRBWBJ6S4aU Сергей Куксенко и Алексей Шипилёв — Через тернии к лямбдам, часть 2

    // https://youtu.be/O8oN4KSZEXE Сергей Куксенко — Stream API, часть 1
    // https://youtu.be/i0Jr2l3jrDA Сергей Куксенко — Stream API, часть 2

    @Test
    public void getAllEpamEmployees() {
        List<Employee> allEmployee = Generator.generateEmployeeListWithEpamExperience();

        final List<Employee> expected = new ArrayList<>();

        for (Employee employee : allEmployee) {
            final List<JobHistoryEntry> jobHistory = employee.getJobHistory();
            boolean isEpamEmployee = false;
            for (JobHistoryEntry jobHistoryEntry : jobHistory) {
                if("epam".equals(jobHistoryEntry.getEmployer())) {
                    isEpamEmployee = true;
                }
            }
            if (isEpamEmployee) {
                expected.add(employee);
            }
        }

        List<Employee> epamEmployees = null;
        // TODO all persons with experience in epam
        epamEmployees = allEmployee.stream()
                .filter(a -> a.getJobHistory().stream().anyMatch(b -> b.toString().contains("epam")))
                .collect(Collectors.toList());



        assertTrue(expected.size() == epamEmployees.size(), "Expected size" + expected.size());
        assertTrue(expected.containsAll(epamEmployees), "Wrong result");
    }

    @Test
    public void getEmployeesStartedFromEpam() {
        List<Employee> allEmployee = Generator.generateEmployeeListWithEpamExperience();

        final List<Employee> expected = new ArrayList<>();

        for (Employee employee : allEmployee) {
            if("epam".equals(employee.getJobHistory().iterator().next().getEmployer())) {
                expected.add(employee);
            }
        }

        List<Employee> epamEmployees = null;
        // TODO all persons with first experience in epam
        epamEmployees = allEmployee.stream()
                .filter(a -> a.getJobHistory().get(0).toString().contains("epam"))
                .collect(Collectors.toList());

        assertNotNull(epamEmployees);
        assertFalse(epamEmployees.isEmpty());

        assertTrue(expected.size() == epamEmployees.size(), "Expected size" + expected.size());
        assertTrue(expected.containsAll(epamEmployees), "Wrong result");
    }

    @Test
    public void sumEpamDurations() {
        final List<Employee> employees = generateEmployeeList();

        Integer expected = 0;

        for (Employee e : employees) {
            for (JobHistoryEntry j : e.getJobHistory()) {
                if (j.getEmployer().equals("epam")) {
                    expected += j.getDuration();
                }
            }
        }

        //TODO sum of all durations in epam job histories
        Integer result = employees.stream()
                .flatMap(employee -> employee.getJobHistory().stream())
                .filter(j -> j.toString().contains("epam"))
                .mapToInt(JobHistoryEntry::getDuration)
                .sum();
        assertEquals(expected, result);
    }

}