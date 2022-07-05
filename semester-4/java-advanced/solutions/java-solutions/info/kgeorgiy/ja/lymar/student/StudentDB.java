package info.kgeorgiy.ja.lymar.student;

import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.GroupQuery;
import info.kgeorgiy.java.advanced.student.Student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pavel Lymar
 */
public class StudentDB implements GroupQuery {
    private static final Comparator<Student> BY_NAME_COMPARATOR =
            Comparator.comparing(Student::getLastName, Comparator.reverseOrder())
                    .thenComparing(Student::getFirstName, Comparator.reverseOrder())
                    .thenComparing(Student::getId);

    private Stream<Map.Entry<GroupName, List<Student>>> getGroupStream(Collection<Student> students) {
        return students.stream().collect(Collectors.groupingBy(Student::getGroup)).entrySet().stream();
    }

    private List<Group> getGroup(Collection<Student> students) {
        return getGroupStream(students)
                .sorted(Map.Entry.comparingByKey()).map(a -> new Group(a.getKey(), a.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getGroup(sortStudentsByName(students));
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getGroup(sortStudentsById(students));
    }

    private GroupName getLargestGroupBy(Collection<Student> students,
                                        Comparator<Map.Entry<GroupName, List<Student>>> comparator) {
        return getGroupStream(students).max(comparator).map(Map.Entry::getKey).orElse(null);
    }

    @Override
    public GroupName getLargestGroup(Collection<Student> students) {
        return getLargestGroupBy(students,
                Comparator.comparingInt((Map.Entry<GroupName, List<Student>> group) -> group.getValue().size())
                        .thenComparing(Map.Entry.comparingByKey()));
    }

    @Override
    public GroupName getLargestGroupFirstName(Collection<Student> students) {
        return getLargestGroupBy(
                students,
                Comparator.comparingInt(
                        (Map.Entry<GroupName, List<Student>> group) -> getDistinctFirstNames(group.getValue()).size()
                ).thenComparing(Map.Entry.<GroupName, List<Student>>comparingByKey().reversed())
        );
    }

    private <T> List<T> getStudentsInfo(List<Student> students, Function<Student, T> function) {
        return students.stream().map(function).collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getStudentsInfo(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getStudentsInfo(students, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(List<Student> students) {
        return getStudentsInfo(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return students.stream().map(student -> student.getFirstName() + " " + student.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new TreeSet<>(getFirstNames(students));
    }

    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students.stream().max(Student::compareTo)
                .orElse(new Student(0, "", "", GroupName.M3239)).getFirstName();
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream().sorted(Comparator.comparingInt(Student::getId)).collect(Collectors.toList());
    }

    private Stream<Student> sortedStudents(Collection<Student> students) {
        return students.stream().sorted(StudentDB.BY_NAME_COMPARATOR);
    }

    private List<Student> findStudent(Collection<Student> students, Predicate<Student> predicate) {
        return students.stream().filter(predicate).sorted(StudentDB.BY_NAME_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortedStudents(students).collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findStudent(students, student -> student.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findStudent(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return findStudent(students, student -> student.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(String::compareTo)));
    }
}
