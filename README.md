# LEngBot
Телеграмм Бот помогаюищй определить уровень знания английского языка и выучить новые слова.
# Deploy
Инструкция по развертыванию бота локально и на Heroku

Requirements:
* git
* jdk-19
* maven
* heroku
1. Клонируем репозиторий 
```
$ git clone https://github.com/MeleshinDA/LEngBot
```
2. Заходим в папку, где лежит проект и собираем решение. В папке с проектом должна появиться папка target.
```
$ mvn install
```
3. Добавить нужные характеристики в src/main/resources/application.properties
![image](https://user-images.githubusercontent.com/43640874/195334566-d9cd8bca-219e-493b-9485-708ae15f29a4.png)
4. Запуск бота локально.
5. Чтобы развернуть бота на Heroku, нужно выполнить следующие действия
