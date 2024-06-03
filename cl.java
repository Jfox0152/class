import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Teacher {
    private String name;
    private String subject;

    public Teacher(String name, String subject) {
        this.name = name;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Subject: " + subject;
    }

}

class УчительСервис {
    private List<Teacher> teachers;

    public УчительСервис() {
        this.teachers = new ArrayList<>();
    }

    public void createTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void editTeacher(int index, Teacher updatedTeacher) {
        if (index >= 0 && index < teachers.size()) {
            teachers.set(index, updatedTeacher);
        } else {
            throw new IndexOutOfBoundsException("Teacher not found");
        }
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public Teacher getTeacher(int index) {
        if (index >= 0 && index < teachers.size()) {
            return teachers.get(index);
        } else {
            throw new IndexOutOfBoundsException("Teacher not found");
        }
    }
}

class УчебнаяГруппа {
    private List<Teacher> teachers;

    public УчебнаяГруппа() {
        this.teachers = new ArrayList<>();
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}

class Поток implements Iterable<УчебнаяГруппа> {
    private List<УчебнаяГруппа> groups;

    public Поток() {
        this.groups = new ArrayList<>();
    }

    public void addGroup(УчебнаяГруппа group) {
        groups.add(group);
    }

    public List<УчебнаяГруппа> getGroups() {
        return groups;
    }

    @Override
    public Iterator<УчебнаяГруппа> iterator() {
        return groups.iterator();
    }
}

class StreamComparator implements java.util.Comparator<Поток> {
    @Override
    public int compare(Поток s1, Поток s2) {
        return Integer.compare(s1.getGroups().size(), s2.getGroups().size());
    }
}

class ПотокСервис {
    public void sortStreams(List<Поток> streams) {
        Collections.sort(streams, new StreamComparator());
    }
}

class УчительВью {
    Scanner scanner;

    public УчительВью() {
        this.scanner = new Scanner(System.in);
    }

    public void displayTeachers(List<Teacher> teachers) {
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println(i + ". " + teachers.get(i));
        }
    }

    public Teacher getTeacherDetails() {
        System.out.print("Enter teacher's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter teacher's subject: ");
        String subject = scanner.nextLine();
        return new Teacher(name, subject);
    }

    public int getTeacherIndex() {
        System.out.print("Enter teacher index: ");
        return scanner.nextInt();
    }

    public void displayStreams(List<Поток> streams) {
        for (int i = 0; i < streams.size(); i++) {
            System.out.println("Stream " + i + " has " + streams.get(i).getGroups().size() + " groups.");
        }
    }
}

class УчительКонтроллер {
    private УчительСервис service;
    private УчительВью view;
    private ПотокСервис streamService;
    private List<Поток> streams;

    public УчительКонтроллер() {
        this.service = new УчительСервис();
        this.view = new УчительВью();
        this.streamService = new ПотокСервис();
        this.streams = new ArrayList<>();
    }

    public void createTeacher() {
        Teacher teacherDetails = view.getTeacherDetails();
        service.createTeacher(teacherDetails);
    }

    public void editTeacher() {
        try {
            int index = view.getTeacherIndex();
            view.scanner.nextLine(); 
            Teacher updatedTeacherDetails = view.getTeacherDetails();
            service.editTeacher(index, updatedTeacherDetails);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayTeachers() {
        view.displayTeachers(service.getTeachers());
    }

    public void createStream() {
        Поток stream = new Поток();
        streams.add(stream);
    }

    public void addGroupToStream(int streamIndex, УчебнаяГруппа group) {
        if (streamIndex >= 0 && streamIndex < streams.size()) {
            streams.get(streamIndex).addGroup(group);
        } else {
            throw new IndexOutOfBoundsException("Stream not found");
        }
    }

    public void displayStreams() {
        view.displayStreams(streams);
    }

    public void sortStreams() {
        streamService.sortStreams(streams);
    }
}

public class cl {
    public static void main(String[] args) {
        УчительКонтроллер controller = new УчительКонтроллер();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1. Create Teacher");
                System.out.println("2. Edit Teacher");
                System.out.println("3. Display Teachers");
                System.out.println("4. Create Stream");
                System.out.println("5. Add Group to Stream");
                System.out.println("6. Display Streams");
                System.out.println("7. Sort Streams");
                System.out.println("8. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        controller.createTeacher();
                        break;
                    case 2:
                        controller.editTeacher();
                        break;
                    case 3:
                        controller.displayTeachers();
                        break;
                    case 4:
                        controller.createStream();
                        break;
                    case 5:
                        System.out.print("Enter stream index: ");
                        int streamIndex = scanner.nextInt();
                        scanner.nextLine(); 
                        УчебнаяГруппа group = new УчебнаяГруппа();
                        group.addTeacher(new Teacher("Teacher 1", "Math"));
                        group.addTeacher(new Teacher("Teacher 2", "Science"));
                        controller.addGroupToStream(streamIndex, group);
                        break;
                    case 6:
                        controller.displayStreams();
                        break;
                    case 7:
                        controller.sortStreams();
                        controller.displayStreams();
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
