package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class FriendsInfoPrinter {
    private WebDriver driver;
    private  Map<String, String> configDictionary;
    private String[] links;
    private Random random;
    private final int FRIENDS_NUMBER_TO_PRINT = 20;
    public FriendsInfoPrinter(WebDriver driver, Map<String, String> configDictionary, Random random) {
        this.driver = driver;
        this.configDictionary = configDictionary;
        this.random = random;
    }
    private void setFriendsLinks(){
        List<WebElement> friendsElements = driver.findElements(By.xpath(configDictionary.get("friendsElements")));

        String[] links = new String[friendsElements.size()];

        for (int index = 0; index < friendsElements.size(); index++)
            links[index] = friendsElements.get(index).getAttribute("href");

        this.links = links;
    }
    public void printFriendsInfo() throws InterruptedException {
        setFriendsLinks();
        System.out.println("");
        System.out.println("Друзей найдено: " + this.links.length);
        System.out.println("");
        int friendsCounter = 0;

        for (String link: this.links){
            if (friendsCounter >= FRIENDS_NUMBER_TO_PRINT){
                System.out.println("Выведено информацию о 20 друзьях");
                return;
            }
            friendsCounter++;
            driver.get(link);

            System.out.println("Ссылка на профиль " + friendsCounter + "-го друга: " + link);

            WebElement nameElement = driver.findElement(By.xpath(configDictionary.get("nameElement")));
            String name = nameElement.getText();
            System.out.println("Имя, фамилия: " + name);

            this.printFriendsDetail();

            System.out.println();
            Thread.sleep(random.nextInt(1500, 2500));
        }
        System.out.println("Выведено информацию о " + friendsCounter + " друзьях");
    }
    private void printFriendsDetail(){
        try{
            WebElement friendsDetailElement = driver.findElement(By.xpath(configDictionary.get("friendsDetailElement")));

            String info = friendsDetailElement.getText();
            System.out.println("Краткая информация: " + info);
        } catch (Exception e) {
            System.out.println("Нет текстовой краткой информации");
        }
    }
}
