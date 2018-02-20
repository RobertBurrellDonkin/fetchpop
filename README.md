# FetchPop
A personal app to automate bulk operations on a <a href='https://tools.ietf.org/html/rfc1939' rel=tag>POP3</a> email account. 

## State Of Play

Refactoring into a test-driven object-oriented application guided by end to end tests from a legacy script.

## Use Cases

## GH-5 Set Account Details 

> As a User
>
> I want To Set Account Details

## Key Technologies

* <a href='https://commons.apache.org/proper/commons-net/' rel='tag'>Apache Commons Net</a> 
* <a href='https://projects.spring.io/spring-boot/' rel=tag>Spring Boot</a>

## Configuration

Spring Boot standard application properties. Users are encouraged to use custom profiles.

### Example Command Line

```java -jar -Dapplication.user="POP3 User" -Dapplication.cred="POP3 passwd" -Dapplication.host.name="POP3 Server Host Name" -Dapplication.host.port=999 fetchpop-app-0.0.1-SNAPSHOT.jar```

### Example YAML

Save 

```
application:
  user: me@example.org
  cred: ABCDEF
  host: 
    name: pop3.example.org
    port: 999
```
as ```config/account.yml```

Then run 

```
java -jar -Dspring.profiles.active="account" fetchpop-app-0.0.1-SNAPSHOT.jar
```

## Features And Fixes

### 0.0.1 

* GH-5 As a User I want To Set Account Details

## Rationale 

Though the Java community seems to produce fewer command line utilities than Ruby or Python (say), Java has unexploited advantages. Technologies such as <a href='https://projects.spring.io/spring-boot/' rel=tag>Spring Boot</a> 

### A Spring-esque Command Line

This is a Spring-esque command line app. It has few aspirations to be Unix-esque, Ruby-esque or Python-esque, grand as those approached undoubtedly are.

### Why Spring Boot...? 

* Reduced boiler plate
* Easy creation of executables
* Advanced end to end tests with minimal boiler plate

In this case I'm relaxed about
* Lots of dependencies
* Bigger executable

### Apache James FetchPop Limitations

For many years, I ran a local personal fork of <a href='https://james.apache.org/'>Apache James</a>. Key features

* Efficiently serve over <a href='https://tools.ietf.org/html/rfc3501'>IMAP</a> mail originally delivered to many accounts beyond the limits of clients like Thunderbird to cache locally;
* Full access to archives even when machine is offline;
* Advanced filtering using <a href='https://www.ietf.org/rfc/rfc3028.txt'>SIEVE</a>;
* Portability, able to zip and relocate;
* Mail accessed via POP3

But the James FetchPop implementation has known limitations. Would need a complete refactor and proper modularization. After my injury

* not reasonable to ask myself to make the time to drive this approach through a community based process;
* unable to run personal Apache James server for a few years building up a major backlog.

### Finely Grained Exceptions

This app throws finely grained exceptions to allow precise diagnosis even at statistical scale. Probably a little much but the habit's good.

## A Little Note

This is a personal app. Made by me, for me to scratch a personal itch. Make of it what you will. 

Please don't expect me to undertake unpaid development on it for you. 



