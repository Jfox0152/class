import java.util.ArrayList;
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
}

class УчительКонтроллер {
    private УчительСервис service;
    private УчительВью view;

    public УчительКонтроллер() {
        this.service = new УчительСервис();
        this.view = new УчительВью();
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
}

public class Main {
    public static void main(String[] args) {
        УчительКонтроллер controller = new УчительКонтроллер();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1. Create Teacher");
                System.out.println("2. Edit Teacher");
                System.out.println("3. Display Teachers");
                System.out.println("4. Exit");

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
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
