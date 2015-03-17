# JobsCrawler
Design Patterns Course Project - Jobs Crawler

0- Create Initial Job Profile (dice.com, careerbuilder.com, monster.com... etc)

1- Network Sniffer (Live HTTP Headers): Capture SessionID from header

https://chrome.google.com/webstore/detail/live-http-headers/iaiioopjkcekapmldfgbebdclcnpgnlo?hl=en

2- Send SessionID in Java with every request (Try to do it Dynamically)

http://stackoverflow.com/questions/7679916/jsoup-connection-with-basic-access-authentication

3- Send request with .connection() in JSoup

http://jsoup.org/apidocs/org/jsoup/Connection.html

4- Parsing HTML and collecting vacancies

http://javarevisited.blogspot.com/2014/09/how-to-parse-html-file-in-java-jsoup-example.html

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
.header("Referer","http://rivalregions.com/")
.header("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36")
.header("X-Requested-With", "XMLHttpRequest")
//.cookie(genUrl(),cookie)
.get();

