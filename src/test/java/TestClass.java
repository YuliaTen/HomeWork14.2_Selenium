import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestClass {

    static ChromeDriver driver;

    @BeforeAll
    static void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        System.setProperty("webdriver.chrome.driver","bin/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    public static List<String> dataPath(){
        return List.of("//*[@id='genesis-content']//a[text()='Java 11 Tutorial']",
                "//*[@id='genesis-content']//a[text()='Java 7 Tutorial']",
                "//*[@id='genesis-content']//a[text()='Generics Tutorial']",
                "//*[@id='genesis-content']//a[text()='Object Oriented Principles']");
    }


    @ParameterizedTest
    @MethodSource("dataPath")
    @DisplayName("Проверка сайта на открытие ссылок, нахождение блоков и переключение темы")
    public void testSite(String path){
        //1Для сайта https://howtodoinjava.com/ реализовать следующий тест:
        //Зайти в 4 различных (по вашему выбору) секции уроков Java Core.
        driver.get("https://howtodoinjava.com/");
        WebElement linkJavaCore = driver.findElement
                (By.xpath(path));
        linkJavaCore.click();
        //Для каждой из секций проверить что:
        //Присутствует левый блок тематического меню.
        WebElement rigthSidebar = driver.findElement
                (By.xpath("//*[@id='genesis-sidebar-primary']"));
        Assertions.assertNotNull(rigthSidebar,"Нет правого темаического меню");

        //Присутствует верхний блок навигационного меню.
        WebElement UpSidebar = driver.findElement
                (By.xpath("//*[@id='ocs-site']/div/header/div"));
        Assertions.assertNotNull(UpSidebar,"Нет верхнего блока навигационного меню");

        //Присутствует тумблер переключения темной/светлой темы.
        WebElement themeColor = driver.findElement
                (By.xpath("//*[@id='menu-item-17363']"));
        Assertions.assertNotNull(themeColor,"Отсутствует тумблер переключения темной/светлой темы");


        //Тумблер переключить. Состояние тумблера изменилось.
        WebElement newColor = driver.findElement
                (By.xpath("//*[@id='menu-item-17363']/div/div[1]"));
        String text = themeColor.getAttribute("class");
        newColor.click();
        Assertions.assertNotEquals(text,themeColor.getAttribute("name"),"Тема не поменялась");



    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }
}
