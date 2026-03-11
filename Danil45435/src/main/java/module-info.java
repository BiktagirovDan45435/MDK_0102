module ru.demo.danil45435 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.demo.danil45435 to javafx.fxml;
    exports ru.demo.danil45435;
}