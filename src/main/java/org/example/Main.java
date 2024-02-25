package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        Main main = new Main();
        Map<String, String> configDictionary = main.getConfigDictionary();

        WebDriver driver = main.getDriver(configDictionary.get("webDriverPath"));

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        driver.get("https://facebook.com");

        Authorizator authorizator = new Authorizator(driver, configDictionary.get("login"), configDictionary.get("password"));
        authorizator.authorization();

        Thread.sleep(random.nextInt(7000, 10000));
        driver.get("https://www.facebook.com/friends/list");
        Thread.sleep(random.nextInt(7000, 10000));
        main.scrollingFriendList(driver, javascriptExecutor, random, configDictionary);

        FriendsInfoPrinter executor = new FriendsInfoPrinter(driver, configDictionary, random);
        executor.printFriendsInfo();
    }
    private Map<String, String> getConfigDictionary(){
        Map<String, String> configDictionary = new HashMap<String, String>();
        try {
            FileInputStream authorizationFIS = new FileInputStream("src/main/resources/AuthorizationDataConfig.properties");
            FileInputStream pathFIS =          new FileInputStream("src/main/resources/PathConfig.properties");
            Properties property = new Properties();

            property.load(authorizationFIS);
            configDictionary.put("login",    property.getProperty("fb.login"));
            configDictionary.put("password", property.getProperty("fb.password"));

            property.load(pathFIS);
            configDictionary.put("webDriverPath",                      property.getProperty("webDriverPath"));
            configDictionary.put("friendsElements",                    property.getProperty("xpath.friendsElements"));
            configDictionary.put("nameElement",                        property.getProperty("xpath.nameElement"));
            configDictionary.put("friendsDetailElement",               property.getProperty("xpath.friendsDetailElement"));
            configDictionary.put("lastVisibleFriendOnFriendListPage",  property.getProperty("xpath.lastVisibleFriendOnFriendListPage"));
            configDictionary.put("firstVisibleFriendOnFriendListPage", property.getProperty("xpath.firstVisibleFriendOnFriendListPage"));
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            System.exit(1);
        }
        return configDictionary;
    }
    private WebDriver getDriver(String path){
        System.setProperty("webdriver.chrome.driver", path);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);

        //wait for WebElement
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //wait for loading page
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        return driver;
    }

    private void scrollingFriendList(WebDriver driver, JavascriptExecutor javascriptExecutor, Random random, Map<String, String> configDictionary) throws InterruptedException {
        WebElement lastVisibleFriendOnFriendListPage = driver.findElement(By.xpath(configDictionary.get("lastVisibleFriendOnFriendListPage")));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", lastVisibleFriendOnFriendListPage);
        Thread.sleep(random.nextInt(1000, 3000));

        WebElement firstVisibleFriendOnFriendListPage = driver.findElement(By.xpath(configDictionary.get("firstVisibleFriendOnFriendListPage")));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", firstVisibleFriendOnFriendListPage);
        Thread.sleep(random.nextInt(1000, 3000));
    }
}