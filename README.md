# JobsCrawler
Design Patterns Course Project - Jobs Crawler

We will use Singleton Pattern to Login One Time only and make One Session per website!

We will use Strategy Pattern to organize our code, where we will apply different algorithm (strategy) for each job website, it will be easier in the future to add new algorithms for new website in the future.

0- Create Initial Job Profile (dice.com, careerbuilder.com, monster.com... etc)

Dice.com 
http://www.cybercoders.com
Account: jobscrawlerproject@gmail.com --- Please ask for password!

to login: using POST: https://www.dice.com/dashboard/login with variables(email, password)

URL to call: https://www.dice.com/jobs?q=Java

1- Network Sniffer (Live HTTP Headers): Capture SessionID from header

https://chrome.google.com/webstore/detail/live-http-headers/iaiioopjkcekapmldfgbebdclcnpgnlo?hl=en

2- Send SessionID in Java with every request (Try to do it Dynamically)

http://stackoverflow.com/questions/6432970/jsoup-posting-and-cookie

http://stackoverflow.com/questions/7679916/jsoup-connection-with-basic-access-authentication

3- Send request with .connection() in JSoup

http://jsoup.org/apidocs/org/jsoup/Connection.html

4- Parsing HTML and collecting vacancies

http://javarevisited.blogspot.com/2014/09/how-to-parse-html-file-in-java-jsoup-example.html

http://blog.tallan.com/2012/07/26/parsing-html-using-jsoup-library/comment-page-1/

http://jsoup.org/cookbook/input/load-document-from-url

http://www.mkyong.com/java/how-to-automate-login-a-website-java-example/

5- Parsing each vacancy and collect (Email addresses) OR (Post Information)

6- Apply for Job and save track of applied Jobs in DB


sample JSoup XMLHttpRequest with cookies

Document doc = Jsoup.connect(jurl)
.header("Accept","text/html, */*; q=0.01")
.header("Accept-Encoding","gzip,deflate,sdch")
.header("Accept-Language","ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4")
.header("Connection","keep-alive")
.header("Cookie",cookie)
.header("Host","rivalregions.com")
.header("Referer","http://mum.edu/")
.header("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36")
.header("X-Requested-With", "XMLHttpRequest")
//.cookie(genUrl(),cookie)
.get();

