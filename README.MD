# web-crawler

## Task

 Web Crawler
 A simple web crawler in a springboot,jsoup.
The crawler should:

- be limited to one domain. Given a starting URL – say  http://www.prudential.co.uk 
- it should visit all pages within the domain, but not follow the links to external sites such as Google or Twitter.

The output should be a simple structured site map (this does not need to be a traditional XML sitemap - just some sort of output to reflect 
what your crawler has discovered), showing links to other pages under the same domain, links to external URLs and links to static content 
such as images for each respective page.

- ensure your code is clear and demonstrates good practices.


## Description
This project is using **jsoup(1.11.3)** internally to crawl pages. Application has used **dropwizard** library for metrics along with **spring boot actuator**,
**springfox-swagger2** for api documentation.  

Main tradeoffs:

- Application do not have support for redirection currently.

## Building the program

In order to build the program, the following is required

- Java 8 JDK
- Maven 3.5.0

## Setup
    git $ clone https://github.com/kanchhedwa/web-crawler.git
    $ cd webcrawler-java/
    $ mvn spring-boot:run

## Sample URL 
	http://localhost:8080/web-crawler/apis/v1.0/crawler?pageUrl=https://www.prudential.co.uk&crawlDepth=5


Mandatory parameter:

    pageUrl           page url to crawl
    
Optional parameters:

    crawlDepth               max level of nested crawling

## Sample Output

![Sample Output](https://github.com/kanchhedwa/web-crawler/blob/master/sample-output.PNG?raw=true)

## TODO
	1. Update unit test cases.
	2. Adding AuditLogging component (Filter).
	3. Implement spring cloud stack support such as config server, circuit breaker(Hystrix),Eureka,ZUUL.
	4. Improvement on monitoring (stats) like Grafana dash board.
	5. Implement users valid feedback.
