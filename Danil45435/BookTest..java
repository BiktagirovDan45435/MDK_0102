Задание1
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        // Создаем книгу "Война и мир" перед каждым тестом
        book = new Book("Война и мир", "Лев Толстой");
    }

    @Test
    void testBookInitialAvailability() {
        // Проверяем, что книга доступна (isAvailable() = true)
        assertTrue(book.isAvailable(), "Книга должна быть доступна после создания");
        assertNull(book.getBorrowedBy(), "Книга не должна быть взята кем-либо");
    }
}

Задание2
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;

    @BeforeEach
    void setUp() {
        // Создаем новую книгу перед каждым тестом
        book = new Book("Мастер и Маргарита", "М. Булгаков");
    }

    @Test
    void testBorrowBook() {
        // 1. Выдача книги студенту "Петров"
        boolean result = book.borrow("Петров");

        assertTrue(result, "Книга должна быть успешно выдана");
        assertFalse(book.isAvailable(), "Книга не должна быть доступна");
        assertEquals("Петров", book.getBorrowedBy(), "Книга должна быть у Петрова");
    }

    @Test
    void testReturnBook() {
        // Подготовка: выдаем книгу
        book.borrow("Петров");

        // 2. Возврат книги
        boolean result = book.returnBook();

        assertTrue(result, "Книга должна быть успешно возвращена");
        assertTrue(book.isAvailable(), "Книга должна быть доступна");
        assertNull(book.getBorrowedBy(), "Поле borrowedBy должно быть null");
    }
}
Задание3
import org.junit.jupiter.api.*;
        import static org.junit.jupiter.api.Assertions.*;

// 1. Измененный класс Book
class Book {
    private String title;
    private String author;
    private boolean isAvailable;
    private String borrowedBy;
    // Статический счетчик успешных выдач
    public static int totalBorrowings = 0;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowedBy = null;
    }

    public boolean borrow(String studentName) {
        if (isAvailable) {
            isAvailable = false;
            borrowedBy = studentName;
            totalBorrowings++; // Увеличение счетчика при успешной выдаче
            return true;
        }
        return false;
    }

    public boolean returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            borrowedBy = null;
            return true;
        }
        return false;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}

// 2. Тестовый класс
class BookTest {
    private Book book;

    @BeforeAll
    static void initAll() {
        // Инициализация счетчика = 0 перед всеми тестами
        Book.totalBorrowings = 0;
        System.out.println("Тесты начались. Счетчик обнулен.");
    }

    @BeforeEach
    void setUp() {
        book = new Book("War and Peace", "Tolstoy");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        // Проверка увеличения счетчика после каждого метода, если это был тест заимствования
        System.out.println("После теста " + testInfo.getDisplayName() +
                ", текущий totalBorrowings: " + Book.totalBorrowings);
    }

    @AfterAll
    static void tearDownAll() {
        // Вывод общего количества выдач
        System.out.println("Общее количество успешных выдач: " + Book.totalBorrowings);
    }

    @Test
    void testSuccessfulBorrow() {
        assertTrue(book.borrow("Student1"));
        assertEquals(1, Book.totalBorrowings);
    }

    @Test
    void testFailedBorrow() {
        book.borrow("Student1");
        assertFalse(book.borrow("Student2")); // Книга уже занята
        assertEquals(1, Book.totalBorrowings); // Счетчик не должен вырасти
    }
}