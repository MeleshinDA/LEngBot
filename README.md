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
```
$ java -jar 
```
5. Чтобы развернуть бота на Heroku, нужно выполнить следующие действия
```
$ heroku login
$ heroku create
$ heroku git:remote -a <имя приложения>
// создайте репозиторий git и сделайте коммит
$ git push heroku main
```
Теперь нужно зайти в личный кабинет на heroku и посмотреть логи: More-->View logs. Если всё в порядке, то осталось запустить Dyno: Overview-->Configure Dynos--> сменить off на on.

Теперь бот запущен на heroku.
# Task 1
При первой использовании бот делится информацией о своей работе и предлагает пройти тесть для определения уровня знания английского языка или ввести его вручную. После прохождения теста пользователю устанавливается уровень английского. Пример:
![image](https://user-images.githubusercontent.com/43640874/195336903-2ae80b68-bd90-4d2c-9fd1-b3dd6a41fd1b.png)
