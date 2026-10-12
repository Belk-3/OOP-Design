import java.util.*;

import Enums.AttendanceStatus;
import Enums.Faculty;
import Enums.LessonType;
import Enums.ManagerType;
import Enums.TeacherTitle;
import Enums.Term;
import Exceptions.CreditLimitException;
import Exceptions.LowHIndexException;
import Researching.RequestStatus;
import Researching.ResearchPaper;
import Researching.ResearcherDecorator;
import Researching.ResearcherRequest;
import Users.Admin;
import Users.Employee;
import Users.FinanceManager;
import Users.Manager;
import Users.Student;
import Users.Teacher;
import Users.User;
import Utils.AuthService;
import Utils.Course;
import Utils.Database;
import Utils.Lesson;
import Utils.Mark;
import Utils.Message;
import Utils.News;
import Utils.RegistrationRequest;
import Utils.Transcript;
import Utils.UserFactory;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static Database db;
    private static AuthService auth;

    public static void main(String[] args) {
        db   = Database.getInstance();
        auth = new AuthService();
        seedData();

        while (true) {
            printBanner();
            System.out.println("  1. Login");
            System.out.println("  0. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": login(); break;
                case "0":
                    db.serialize();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void seedData() {
        Admin admin = new Admin("a1", "admin", "admin123", "Alex", "Morgan",
                200000, "Administration", new Date());
        db.save(admin);

        Teacher t1 = new Teacher("t1", "teacher1", "pass123", "John", "Carter",
                1500000, "CS", new Date(), TeacherTitle.LECTOR);

        Teacher t2 = new Teacher("t2", "prof1", "pass123", "Robert", "Hayes",
                2000000, "CS", new Date(), TeacherTitle.PROFESSOR);

        Teacher t3 = new Teacher("t3", "teacher2", "pass123", "Emily", "Brooks",
                1300000, "Math", new Date(), TeacherTitle.SENIOR_LECTOR);

        db.save(t1);
        db.save(t2);
        db.save(t3);

        Student s1 = new Student("s1", "student1", "pass123", "Anna", "Smith", 1, Faculty.SITE);
        Student s2 = new Student("s2", "student2", "pass123", "Maria", "Lopez", 2, Faculty.BS);
        Student s3 = new Student("s3", "student3", "pass123", "Sara", "Kim", 2, Faculty.SCE);
        Student s4 = new Student("s4", "student4", "pass123", "Bekzhan", "Myrzabekov", 2, Faculty.ISE);
        Student s5 = new Student("s5", "student5", "pass123", "Lily", "Evans", 3, Faculty.SITE);
        Student s6 = new Student("s6", "student6", "pass123", "Tom", "Walker", 4, Faculty.ISE);

        db.save(s1);
        db.save(s2);
        db.save(s3);
        db.save(s4);
        db.save(s5);
        db.save(s6);

        Manager mgr = new Manager("m1", "manager1", "pass123", "David", "Miller",
                180000, "Management", new Date(), ManagerType.DEPARTMENT);
        db.save(mgr);

        FinanceManager fm = new FinanceManager("f1", "finance1", "pass123", "Grace", "Lee",
                170000, "Finance", new Date());
        fm.setBudget(5000000);
        db.save(fm);

        Course oop = new Course("CS101", "OOP", 3, Term.Spring, Faculty.SITE);
        Course algo = new Course("CS201", "Algorithms", 4, Term.Spring, Faculty.SITE);
        Course math = new Course("MATH101", "Calculus", 3, Term.Spring, Faculty.ISE);
        Course dbCourse = new Course("CS301", "Database Systems", 3, Term.Fall, Faculty.SITE);
        Course web = new Course("CS305", "Web Development", 4, Term.Fall, Faculty.SCE);

        db.addCourse(oop);
        db.addCourse(algo);
        db.addCourse(math);
        db.addCourse(dbCourse);
        db.addCourse(web);

        t1.addCourse(oop);
        t1.addCourse(web);
        t2.addCourse(algo);
        t2.addCourse(dbCourse);
        t3.addCourse(math);

        try {
            s1.registerForCourse(oop);
            s1.registerForCourse(algo);

            s2.registerForCourse(algo);
            s2.registerForCourse(math);

            s3.registerForCourse(web);
            s3.registerForCourse(oop);

            s4.registerForCourse(math);
            s4.registerForCourse(dbCourse);

            s5.registerForCourse(oop);
            s5.registerForCourse(web);

            s6.registerForCourse(algo);
            s6.registerForCourse(dbCourse);
        } catch (CreditLimitException e) {
            System.out.println(e.getMessage());
        }

        Mark m1 = new Mark(s1, oop, 25, 27, 35);
        Mark m2 = new Mark(s1, algo, 20, 22, 30);
        Mark m3 = new Mark(s2, algo, 28, 26, 38);
        Mark m4 = new Mark(s2, math, 10, 5, 15);
        Mark m5 = new Mark(s3, web, 24, 25, 34);
        Mark m6 = new Mark(s4, math, 18, 20, 28);
        Mark m7 = new Mark(s5, oop, 29, 28, 37);
        Mark m8 = new Mark(s6, dbCourse, 21, 19, 30);

        s1.addMark(m1);
        s1.addMark(m2);
        s2.addMark(m3);
        s2.addMark(m4);
        s3.addMark(m5);
        s4.addMark(m6);
        s5.addMark(m7);
        s6.addMark(m8);

        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        Lesson l1 = new Lesson(LessonType.LECTURE, oop, t1, now.minusDays(7), "101", 90);
        Lesson l2 = new Lesson(LessonType.PRACTICE, oop, t1, now.minusDays(3), "102", 90);
        Lesson l3 = new Lesson(LessonType.LECTURE, algo, t2, now.minusDays(5), "201", 90);
        Lesson l4 = new Lesson(LessonType.PRACTICE, math, t3, now.minusDays(2), "301", 90);
        Lesson l5 = new Lesson(LessonType.LECTURE, web, t1, now.minusDays(1), "202", 90);

        l1.markAttendance(s1, AttendanceStatus.PRESENT);
        l1.markAttendance(s3, AttendanceStatus.ABSENT);
        l1.markAttendance(s5, AttendanceStatus.PRESENT);

        l2.markAttendance(s1, AttendanceStatus.ABSENT);
        l2.markAttendance(s3, AttendanceStatus.PRESENT);
        l2.markAttendance(s5, AttendanceStatus.LATE);

        l3.markAttendance(s1, AttendanceStatus.PRESENT);
        l3.markAttendance(s2, AttendanceStatus.PRESENT);
        l3.markAttendance(s6, AttendanceStatus.ABSENT);

        l4.markAttendance(s2, AttendanceStatus.ABSENT);
        l4.markAttendance(s4, AttendanceStatus.PRESENT);

        l5.markAttendance(s3, AttendanceStatus.PRESENT);
        l5.markAttendance(s5, AttendanceStatus.PRESENT);

        db.addLesson(l1);
        db.addLesson(l2);
        db.addLesson(l3);
        db.addLesson(l4);
        db.addLesson(l5);
    }

    private static void login() {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        User user = auth.authenticate(username, password);
        if (user == null) {
            System.out.println(" Login failed. Check credentials.\n");
            return;
        }
        System.out.println(" Welcome, " + user.getName() + "!\n");

        if      (user instanceof Admin)          adminMenu((Admin) user);
        else if (user instanceof Teacher)        teacherMenu((Teacher) user);
        else if (user instanceof Student)        studentMenu((Student) user);
        else if (user instanceof FinanceManager) financeMenu((FinanceManager) user);
        else if (user instanceof Manager)        managerMenu((Manager) user);
        else System.out.println("No menu for this user type.");
    }

    private static void studentMenu(Student s) {
        while (true) {
            System.out.println("\n======= STUDENT MENU (" + s.getName() + ") =======");
            System.out.println("  1. View my courses");
            System.out.println("  2. Register for a course");
            System.out.println("  3. Drop a course");
            System.out.println("  4. View current attestation marks");
            System.out.println("  5. View attestation (ATT1 + ATT2 + FINAL)");
            System.out.println("  6. View transcript (GPA)");
            System.out.println("  7. View my attendance");
            System.out.println("  8. View my debts");
            System.out.println("  9. View news");
            System.out.println("  10. Rate a teacher");
            System.out.println("  11. Request to become researcher");
            System.out.println("  12. Change password");
            System.out.println("  0. Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1":
                    s.viewJournal();
                    break;
                case "2":
                    listCourses();
                    System.out.print("Enter course ID: ");
                    String cid = sc.nextLine().trim();
                    Course c = findCourse(cid);
                    if (c == null) { System.out.println("Course not found."); break; }
                    try { s.registerForCourse(c); }
                    catch (CreditLimitException e) { System.out.println("❌ " + e.getMessage()); }
                    break;
                case "3":
                    s.viewJournal();
                    System.out.print("Enter course ID to drop: ");
                    String did = sc.nextLine().trim();
                    Course dc = findCourse(did);
                    if (dc == null) { System.out.println("Course not found."); break; }
                    s.dropCourse(dc);
                    break;
                case "4":
                    s.viewCurrentAttestation();
                    break;
                case "5":
                    s.viewAttestation();
                    break;
                case "6":
                    Transcript tr = s.getTranscript();
                    tr.print();
                    break;
                case "7":
                    s.viewAttendance();
                    break;
                case "8":
                    s.viewDebts();
                    break;
                case "9":
                    s.viewNews();
                    break;
                case "10":
                    listTeachers();
                    System.out.print("Enter teacher ID: ");
                    String tid = sc.nextLine().trim();
                    Teacher teacher = findTeacher(tid);
                    if (teacher == null) { System.out.println("Teacher not found."); break; }
                    System.out.print("Rating (1-5): ");
                    try {
                        int r = Integer.parseInt(sc.nextLine().trim());
                        s.rateTeacher(teacher, r);
                    } catch (NumberFormatException e) { System.out.println("Invalid number."); }
                    break;
                case "11":
                    s.becomeResearcher();
                    break;
                case "12":
                    System.out.print("New password: ");
                    s.changePassword(sc.nextLine().trim());
                    break;
                case "0":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void teacherMenu(Teacher t) {
        while (true) {
            System.out.println("\n======= TEACHER MENU (" + t.getName() + ") =======");
            System.out.println("  1. View my courses");
            System.out.println("  2. View my students");
            System.out.println("  3. Put lesson mark for student");
            System.out.println("  4. View student marks");
            System.out.println("  5. Mark attendance");
            System.out.println("  6. View news");
            System.out.println("  7. Send message");
            System.out.println("  8. View inbox");
            System.out.println("  9. Research: add paper (professor only)");
            System.out.println("  10. Research: view my papers");
            System.out.println("  11. Check my salary");
            System.out.println("  12. Change password");
            System.out.println("  0. Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1":
                    List<Course> tc = t.viewCourses();
                    if (tc.isEmpty()) System.out.println("No courses.");
                    for (Course course : tc) System.out.println("  " + course);
                    break;
                case "2":
                    List<Student> students = t.viewStudents();
                    if (students.isEmpty()) System.out.println("No students.");
                    for (Student st : students) System.out.println("  " + st);
                    break;
                case "3":
                    putLessonMarkMenu(t);
                    break;
                case "4":
                    viewStudentMarksMenu(t);
                    break;
                case "5":
                    markAttendanceMenu(t);
                    break;
                case "6":
                    t.viewNews();
                    break;
                case "7":
                    sendMessageMenu(t);
                    break;
                case "8":
                    List<Message> inbox = t.getMessages();
                    if (inbox.isEmpty()) System.out.println("Inbox empty.");
                    for (Message m : inbox) System.out.println("  " + m);
                    break;
                case "9":
                    addResearchPaperMenu(t);
                    break;
                case "10":
                    viewResearchPapers(t);
                    break;
                case "11":
                    t.checkSalary();
                    break;
                case "12":
                    System.out.print("New password: ");
                    t.changePassword(sc.nextLine().trim());
                    break;
                case "0":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void putLessonMarkMenu(Teacher t) {
        List<Student> students = t.viewStudents();
        if (students.isEmpty()) { System.out.println("No students."); return; }
        System.out.println("Select student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i+1) + ". " + students.get(i).getName() + " " + students.get(i).getSurname());
        System.out.print("Number: ");
        try {
            int si = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (si < 0 || si >= students.size()) { System.out.println("Invalid."); return; }
            Student st = students.get(si);

            List<Course> commonCourses = new ArrayList<>();
            for (Course c : t.getCourses())
                if (c.getEnrolledStudents().contains(st)) commonCourses.add(c);
            if (commonCourses.isEmpty()) { System.out.println("No common courses."); return; }

            System.out.println("Select course:");
            for (int i = 0; i < commonCourses.size(); i++)
                System.out.println("  " + (i+1) + ". " + commonCourses.get(i).getName());
            System.out.print("Number: ");
            int ci = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (ci < 0 || ci >= commonCourses.size()) { System.out.println("Invalid."); return; }
            Course course = commonCourses.get(ci);

            // Find or create mark for this student/course
            Mark mark = null;
            for (Mark m : st.getMarks()) {
                if (m.getCourse().equals(course)) { mark = m; break; }
            }
            if (mark == null) {
                mark = new Mark(st, course, 0, 0, 0);
                st.addMark(mark);
            }

            System.out.println("Current attestation period: ATT" + mark.getCurrentAtt());
            System.out.println("Current score for ATT" + mark.getCurrentAtt() + ": " + mark.getCurrentAttScore() + "/30");
            System.out.println("Options:");
            System.out.println("  1. Add lesson score (max 30 total per attestation)");
            System.out.println("  2. Move to next attestation period");
            System.out.println("  3. Set final exam score");
            System.out.print("Choice: ");
            String opt = sc.nextLine().trim();

            if (opt.equals("1")) {
                System.out.print("Lesson score (remaining: " + (30 - mark.getCurrentAttScore()) + "): ");
                double score = Double.parseDouble(sc.nextLine().trim());
                if (mark.addLessonScore(score)) {
                    System.out.println(" Score added. ATT" + mark.getCurrentAtt() + " total: " + mark.getCurrentAttScore() + "/30");
                }
            } else if (opt.equals("2")) {
                mark.nextAttestation();
            } else if (opt.equals("3")) {
                System.out.print("Final exam score (0-40): ");
                double fin = Double.parseDouble(sc.nextLine().trim());
                mark.setFinalExam(fin);
                System.out.println(" Final exam set: " + fin + "/40");
                System.out.println("   Total: " + mark.calcTotal() + "/100 (" + mark.getLetterGrade() + ")");
                if (mark.getLetterGrade().equals("F")) {
                    double debt = st.calcDebt();
                    System.out.println(" Student has FAILED. Debt generated: " + String.format("%,.0f ₸", debt));
                }
            } else {
                System.out.println("Invalid.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }

    private static void viewStudentMarksMenu(Teacher t) {
        List<Student> students = t.viewStudents();
        if (students.isEmpty()) { System.out.println("No students."); return; }
        for (Student st : students) {
            System.out.println("\n  " + st.getName() + " " + st.getSurname() + ":");
            if (st.getMarks().isEmpty()) System.out.println("    No marks.");
            for (Mark m : st.getMarks()) {
                System.out.println("    Course: " + m.getCourse().getName());
                System.out.println("      ATT1: " + m.getAtt1() + "/30  ATT2: " + m.getAtt2() + "/30  FINAL: " + m.getFinalExam() + "/40");
                System.out.println("      Total: " + m.calcTotal() + "/100  Grade: " + m.getLetterGrade());
            }
        }
    }

    private static void markAttendanceMenu(Teacher t) {
        List<Course> tc = t.viewCourses();
        if (tc.isEmpty()) { System.out.println("No courses."); return; }
        System.out.println("Select course:");
        for (int i = 0; i < tc.size(); i++)
            System.out.println("  " + (i+1) + ". " + tc.get(i).getName());
        System.out.print("Number: ");
        try {
            int ci = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (ci < 0 || ci >= tc.size()) { System.out.println("Invalid."); return; }
            Course course = tc.get(ci);

            List<Student> students = course.getEnrolledStudents();
            if (students.isEmpty()) { System.out.println("No students in course."); return; }
            System.out.println("Select student:");
            for (int i = 0; i < students.size(); i++)
                System.out.println("  " + (i+1) + ". " + students.get(i).getName());
            System.out.print("Number: ");
            int si = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (si < 0 || si >= students.size()) { System.out.println("Invalid."); return; }
            Student st = students.get(si);

            System.out.println("Status: 1=PRESENT  2=ABSENT  3=LATE");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(sc.nextLine().trim());
            AttendanceStatus status = (choice == 1) ? AttendanceStatus.PRESENT
                                   : (choice == 3) ? AttendanceStatus.LATE
                                   : AttendanceStatus.ABSENT;
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            Lesson lesson = new Lesson(LessonType.LECTURE, course, t, now, "TBD", 90);
            lesson.markAttendance(st, status);
            db.addLesson(lesson);
            System.out.println(" Attendance marked: " + st.getName() + " -> " + status);
        } catch (NumberFormatException e) { System.out.println("Invalid."); }
    }

    private static void addResearchPaperMenu(Teacher t) {
        t.becomeResearcher();
        ResearcherDecorator rd = t.getResearcherDecorator();
        System.out.print("Paper title: ");
        String title = sc.nextLine().trim();
        System.out.print("Journal: ");
        String journal = sc.nextLine().trim();
        System.out.print("Citations: ");
        try {
            int cit = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Pages: ");
            int pages = Integer.parseInt(sc.nextLine().trim());
            System.out.print("DOI: ");
            String doi = sc.nextLine().trim();
            ResearchPaper paper = new ResearchPaper(title, Arrays.asList(t.getName()), new Date(), pages, cit, journal, doi);
            rd.addPaper(paper);
            System.out.println("Paper added. H-Index: " + rd.getHIndex());
        } catch (NumberFormatException e) { System.out.println("Invalid number."); }
    }

    private static void viewResearchPapers(Teacher t) {
        if (!t.isResearcher()) { System.out.println("Not a researcher yet."); return; }
        List<ResearchPaper> papers = t.getResearcherDecorator().getPapers();
        if (papers.isEmpty()) { System.out.println("No papers."); return; }
        System.out.println("H-Index: " + t.getResearcherDecorator().getHIndex());
        for (ResearchPaper p : papers) System.out.println("  " + p);
    }

    private static void sendMessageMenu(Employee sender) {
        System.out.println("Available employees:");
        List<Employee> emps = new ArrayList<>();
        for (User u : db.getAllUsers()) {
            if (u instanceof Employee && !u.getId().equals(sender.getId())) {
                emps.add((Employee) u);
                System.out.println("  " + emps.size() + ". " + u.getName() + " " + u.getSurname() + " (" + u.getClass().getSimpleName() + ")");
            }
        }
        if (emps.isEmpty()) { System.out.println("No other employees."); return; }
        System.out.print("Number: ");
        try {
            int i = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (i < 0 || i >= emps.size()) { System.out.println("Invalid."); return; }
            System.out.print("Message: ");
            String msg = sc.nextLine().trim();
            sender.sendMessage(emps.get(i), msg);
        } catch (NumberFormatException e) { System.out.println("Invalid."); }
    }

    private static void managerMenu(Manager mgr) {
        while (true) {
            System.out.println("\n======= MANAGER MENU (" + mgr.getName() + ") =======");
            System.out.println("  1.  View all students (sorted by GPA)");
            System.out.println("  2.  View all courses");
            System.out.println("  3.  Add new course");
            System.out.println("  4.  Assign teacher to course");
            System.out.println("  5.  View reports");
            System.out.println("  6.  Manage news");
            System.out.println("  7.  Approve/reject course registration requests");
            System.out.println("  8.  Approve/reject researcher requests");
            System.out.println("  9.  Assign researcher supervisor to student");
            System.out.println("  10. Fire employee");
            System.out.println("  11. Deduct student (>3 retakes on one subject)");
            System.out.println("  12. Send message");
            System.out.println("  13. View inbox");
            System.out.println("  14. Change password");
            System.out.println("  0.  Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1":  mgr.viewStudentsSortedByGPA(); break;
                case "2":  listCourses(); break;
                case "3":  addCourseMenu(mgr); break;
                case "4":  assignTeacherMenu(mgr); break;
                case "5":  mgr.checkReport(); break;
                case "6":  manageNewsMenu(mgr); break;
                case "7":  approveRejectRegistrationMenu(mgr); break;
                case "8":  approveRejectResearcherMenu(mgr); break;
                case "9":  assignSupervisorMenu(mgr); break;
                case "10": fireEmployeeMenu(mgr); break;
                case "11": deductStudentMenu(mgr); break;
                case "12": sendMessageMenu(mgr); break;
                case "13":
                    List<Message> inbox = mgr.getMessages();
                    if (inbox.isEmpty()) System.out.println("Inbox empty.");
                    for (Message m : inbox) System.out.println("  " + m);
                    break;
                case "14":
                    System.out.print("New password: ");
                    mgr.changePassword(sc.nextLine().trim());
                    break;
                case "0": System.out.println("Logged out."); return;
                default:   System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCourseMenu(Manager mgr) {
        System.out.print("Course ID: ");    String id   = sc.nextLine().trim();
        System.out.print("Course name: ");  String name = sc.nextLine().trim();
        System.out.print("Credits (1-6): "); int credits;
        try { credits = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid."); return; }
        System.out.println("Term: 1=Spring 2=Fall 3=Summer");
        Term term = Term.Spring;
        String tc = sc.nextLine().trim();
        if (tc.equals("2")) term = Term.Fall;
        else if (tc.equals("3")) term = Term.Summer;
        System.out.println("Faculty: 1=SITE 2=BS 3=ISE 4=SE 5=SAM 6=SEPI");
        Faculty fac = Faculty.SITE;
        String fc = sc.nextLine().trim();
        Faculty[] facs = Faculty.values();
        try { int fi = Integer.parseInt(fc) - 1; if (fi>=0&&fi<facs.length) fac = facs[fi]; } catch(Exception e) {}
        Course c = new Course(id, name, credits, term, fac);
        mgr.addCourseForRegistration(c);
    }

    private static void assignTeacherMenu(Manager mgr) {
        listTeachers();
        System.out.print("Teacher ID: ");
        String tid = sc.nextLine().trim();
        Teacher t = findTeacher(tid);
        if (t == null) { System.out.println("Teacher not found."); return; }
        listCourses();
        System.out.print("Course ID: ");
        String cid = sc.nextLine().trim();
        Course c = findCourse(cid);
        if (c == null) { System.out.println("Course not found."); return; }
        mgr.assignTeacher(t, c);
    }

    private static void manageNewsMenu(Manager mgr) {
        System.out.println("  1. View news  2. Publish news");
        System.out.print("Choice: ");
        String ch = sc.nextLine().trim();
        if (ch.equals("1")) {
            mgr.manageNews();
        } else if (ch.equals("2")) {
            System.out.print("Title: ");    String title = sc.nextLine().trim();
            System.out.print("Content: ");  String content = sc.nextLine().trim();
            News news = new News(title, content, mgr);
            news.publish();
        }
    }

    private static void approveRejectRegistrationMenu(Manager mgr) {
        List<RegistrationRequest> reqs = mgr.getRequests();
        if (reqs.isEmpty()) { System.out.println("No course registration requests."); return; }
        for (int i = 0; i < reqs.size(); i++)
            System.out.println("  " + (i+1) + ". " + reqs.get(i));
        System.out.print("Select request #: ");
        try {
            int i = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (i < 0 || i >= reqs.size()) { System.out.println("Invalid."); return; }
            System.out.print("1=Approve  2=Reject: ");
            String ch = sc.nextLine().trim();
            if (ch.equals("1")) mgr.approveRegistration(reqs.get(i));
            else                 mgr.rejectRegistration(reqs.get(i));
        } catch (NumberFormatException e) { System.out.println("Invalid."); }
    }

    private static void approveRejectResearcherMenu(Manager mgr) {
        List<ResearcherRequest> reqs = mgr.getResearcherRequests();
        if (reqs.isEmpty()) { System.out.println("No researcher requests."); return; }
        // show only pending
        List<ResearcherRequest> pending = new ArrayList<>();
        for (ResearcherRequest r : reqs)
            if (r.getStatus() == RequestStatus.PENDING) pending.add(r);
        if (pending.isEmpty()) { System.out.println("No pending researcher requests."); return; }
        for (int i = 0; i < pending.size(); i++)
            System.out.println("  " + (i+1) + ". " + pending.get(i));
        System.out.print("Select request #: ");
        try {
            int i = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (i < 0 || i >= pending.size()) { System.out.println("Invalid."); return; }
            System.out.print("1=Approve  2=Reject: ");
            String ch = sc.nextLine().trim();
            if (ch.equals("1")) mgr.approveResearcherRequest(pending.get(i));
            else                 mgr.rejectResearcherRequest(pending.get(i));
        } catch (NumberFormatException e) { System.out.println("Invalid."); }
    }

    private static void assignSupervisorMenu(Manager mgr) {
        System.out.println("Select student:");
        List<Student> students = getStudentList();
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i+1) + ". " + students.get(i).getName());
        System.out.print("Number: ");
        try {
            int si = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (si < 0 || si >= students.size()) { System.out.println("Invalid."); return; }
            Student st = students.get(si);

            System.out.println("Select researcher (professor with papers):");
            List<Teacher> profs = new ArrayList<>();
            for (User u : db.getAllUsers())
                if (u instanceof Teacher && ((Teacher)u).isResearcher()) profs.add((Teacher)u);
            if (profs.isEmpty()) { System.out.println("No researchers found."); return; }
            for (int i = 0; i < profs.size(); i++)
                System.out.println("  " + (i+1) + ". " + profs.get(i).getName() + " H-Index: " + profs.get(i).getResearcherDecorator().getHIndex());
            System.out.print("Number: ");
            int pi = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (pi < 0 || pi >= profs.size()) { System.out.println("Invalid."); return; }
            try { mgr.assignResearcher(st, profs.get(pi).getResearcherDecorator()); }
            catch (LowHIndexException e) { System.out.println("X " + e.getMessage()); }
        } catch (NumberFormatException e) { System.out.println("Invalid."); }
    }

    private static void fireEmployeeMenu(Manager mgr) {
        System.out.println("Select employee to fire:");
        List<Employee> emps = new ArrayList<>();
        for (User u : db.getAllUsers()) {
            if (u instanceof Employee && !(u instanceof Manager && u.getId().equals(mgr.getId()))) {
                emps.add((Employee) u);
                System.out.println("  " + emps.size() + ". " + u.getName() + " " + u.getSurname()
                    + " [" + u.getClass().getSimpleName() + "]");
            }
        }
        if (emps.isEmpty()) { System.out.println("No employees found."); return; }
        System.out.print("Number (0 to cancel): ");
        try {
            int i = Integer.parseInt(sc.nextLine().trim());
            if (i == 0) return;
            i--;
            if (i < 0 || i >= emps.size()) { System.out.println("Invalid."); return; }
            Employee emp = emps.get(i);
            System.out.print("Confirm fire " + emp.getName() + " " + emp.getSurname() + "? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                mgr.fireEmployee(emp);
            } else {
                System.out.println("Cancelled.");
            }
        } catch (NumberFormatException e) { System.out.println("Invalid."); }
    }

    private static void deductStudentMenu(Manager mgr) {
        System.out.println("Students with retake issues (>3 retakes on one subject):");
        List<Student> eligible = new ArrayList<>();
        for (User u : db.getAllUsers()) {
            if (u instanceof Student) {
                Student s = (Student) u;
                if (s.getMaxRetakeCount() > 3) {
                    eligible.add(s);
                    System.out.println("  " + eligible.size() + ". " + s.getName() + " " + s.getSurname()
                        + " | Max retakes: " + s.getMaxRetakeCount());
                }
            }
        }
        if (eligible.isEmpty()) {
            System.out.println("No students qualify for deduction (none has >3 retakes on one subject).");
            return;
        }
        System.out.print("Number to deduct (0 to cancel): ");
        try {
            int i = Integer.parseInt(sc.nextLine().trim());
            if (i == 0) return;
            i--;
            if (i < 0 || i >= eligible.size()) { System.out.println("Invalid."); return; }
            Student st = eligible.get(i);
            System.out.print("Confirm deduction of " + st.getName() + " " + st.getSurname() + "? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                mgr.deductStudent(st);
            } else {
                System.out.println("Cancelled.");
            }
        } catch (NumberFormatException e) { System.out.println("Invalid."); }
    }

    private static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n======= ADMIN MENU (" + admin.getName() + ") =======");
            System.out.println("  1. View all users");
            System.out.println("  2. Add user");
            System.out.println("  3. Remove user");
            System.out.println("  4. View system logs");
            System.out.println("  5. Generate report");
            System.out.println("  6. View news");
            System.out.println("  7. Send message");
            System.out.println("  8. View inbox");
            System.out.println("  9. Change password");
            System.out.println("  0. Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": admin.manageUsers(); break;
                case "2": addUserMenu(admin); break;
                case "3": removeUserMenu(admin); break;
                case "4": admin.viewLogFiles(); break;
                case "5": admin.generateReport(); break;
                case "6": admin.viewNews(); break;
                case "7": sendMessageMenu(admin); break;
                case "8":
                    List<Message> inbox = admin.getMessages();
                    if (inbox.isEmpty()) System.out.println("Inbox empty.");
                    for (Message m : inbox) System.out.println("  " + m);
                    break;
                case "9":
                    System.out.print("New password: ");
                    admin.changePassword(sc.nextLine().trim());
                    break;
                case "0": System.out.println("Logged out."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addUserMenu(Admin admin) {
        System.out.println("Role: 1=STUDENT 2=TEACHER 3=MANAGER 4=FINANCE 5=ADMIN");
        System.out.print("Choice: ");
        String roleChoice = sc.nextLine().trim();
        String role;
        switch (roleChoice) {
            case "1": role = "STUDENT"; break;
            case "2": role = "TEACHER"; break;
            case "3": role = "MANAGER"; break;
            case "4": role = "FINANCE"; break;
            case "5": role = "ADMIN"; break;
            default: System.out.println("Invalid role."); return;
        }
        System.out.print("Username: ");  String uname = sc.nextLine().trim();
        System.out.print("Password: ");  String pass  = sc.nextLine().trim();
        System.out.print("First name: "); String fname = sc.nextLine().trim();
        System.out.print("Last name: ");  String lname = sc.nextLine().trim();
        User u = UserFactory.createUser(role, uname, pass, fname, lname);
        if (u != null) {
            admin.addUser(u);
            System.out.println("User created: " + u);
        }
    }

    private static void removeUserMenu(Admin admin) {
        admin.manageUsers();
        System.out.print("Enter user ID to remove: ");
        String id = sc.nextLine().trim();
        User u = (User) db.load(id);
        if (u == null) { System.out.println("User not found."); return; }
        admin.removeUser(u);
    }

    private static void financeMenu(FinanceManager fm) {
        while (true) {
            System.out.println("\n======= FINANCE MANAGER MENU (" + fm.getName() + ") =======");
            System.out.println("  1. View budget");
            System.out.println("  2. Pay salaries to all employees");
            System.out.println("  3. Pay salary to specific employee");
            System.out.println("  4. Check debts (all students)");
            System.out.println("  5. Check debts (specific student)");
            System.out.println("  6. Set budget");
            System.out.println("  7. Send message");
            System.out.println("  8. View inbox");
            System.out.println("  9. Change password");
            System.out.println("  0. Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": fm.viewBudget(); break;
                case "2": fm.manageSalary(); break;
                case "3":
                    System.out.println("Employees:");
                    List<Employee> emps = new ArrayList<>();
                    for (User u : db.getAllUsers())
                        if (u instanceof Employee && !u.getId().equals(fm.getId())) {
                            emps.add((Employee)u);
                            System.out.println("  " + emps.size() + ". " + u.getName() + " salary=" + ((Employee)u).getSalary());
                        }
                    System.out.print("Number: ");
                    try {
                        int i = Integer.parseInt(sc.nextLine().trim()) - 1;
                        if (i >= 0 && i < emps.size()) fm.giveSalary(emps.get(i));
                    } catch (NumberFormatException e) { System.out.println("Invalid."); }
                    break;
                case "4":
                    fm.viewAllDebts();
                    break;
                case "5":
                    List<Student> stus = getStudentList();
                    for (int i=0;i<stus.size();i++) System.out.println("  "+(i+1)+". "+stus.get(i).getName()+" "+stus.get(i).getSurname());
                    System.out.print("Number: ");
                    try {
                        int i = Integer.parseInt(sc.nextLine().trim()) - 1;
                        if (i>=0&&i<stus.size()) fm.checkDebts(stus.get(i));
                    } catch (NumberFormatException e) { System.out.println("Invalid."); }
                    break;
                case "6":
                    System.out.print("New budget amount: ");
                    try { fm.setBudget(Double.parseDouble(sc.nextLine().trim())); System.out.println("Budget updated."); }
                    catch (NumberFormatException e) { System.out.println("Invalid."); }
                    break;
                case "7": sendMessageMenu(fm); break;
                case "8":
                    List<Message> inbox = fm.getMessages();
                    if (inbox.isEmpty()) System.out.println("Inbox empty.");
                    for (Message m : inbox) System.out.println("  " + m);
                    break;
                case "9":
                    System.out.print("New password: ");
                    fm.changePassword(sc.nextLine().trim());
                    break;
                case "0": System.out.println("Logged out."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void printBanner() {

        System.out.println("\n============================================");
        System.out.println("       UNIVERSITY INFORMATION SYSTEM");
        System.out.println("============================================");
    }

    private static void listCourses() {
        System.out.println("=== Available Courses ===");
        for (Course c : db.getCourses()) System.out.println("  [" + c.getId() + "] " + c.getName() + " (" + c.getCredits() + " cr)");
    }

    private static void listTeachers() {
        System.out.println("=== Teachers ===");
        for (User u : db.getAllUsers())
            if (u instanceof Teacher) System.out.println("  [" + u.getId() + "] " + u.getName() + " " + u.getSurname());
    }

    private static Course findCourse(String id) {
        for (Course c : db.getCourses()) if (c.getId().equals(id)) return c;
        return null;
    }

    private static Teacher findTeacher(String id) {
        User u = (User) db.load(id);
        return (u instanceof Teacher) ? (Teacher) u : null;
    }

    private static List<Student> getStudentList() {
        List<Student> list = new ArrayList<>();
        for (User u : db.getAllUsers()) if (u instanceof Student) list.add((Student)u);
        return list;
    }
}
