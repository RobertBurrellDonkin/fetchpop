# fetchpop
A personal app to automate bulk operations on a <a href='https://tools.ietf.org/html/rfc1939' rel=tag>POP3</a> email account. 

## State Of Play

Refactoring into a test-driven object-oriented application guided by end to end tests from a legacy script.

## A Note

This is a personal app. Made by me, for me to scratch a personal itch. Make of it what you will. 

Please don't expect me to undertake unpaid development on it for you. 

## Use Cases

Well, none yet. Will arrive in due course.

##

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

* Efficiently serve over IMAP mail originally delivered to many accounts beyond the limits of clients like Thunderbird to cache locally;
* Full access to archives even when machine is offline;
* Advanced filtering using SIEVE;
* Portability, able to zip and relocate;
* Mail accessed via POP3

But the James FetchPop implementation has known limitations. Would need a complete refactor and proper modularization. After my injury

* not reasonable to ask myself to make the time to drive this approach through a community based process;
* unable to run personal Apache James server for a few years building up a major backlog.

### Finely Grained Exceptions

This app throws finely grained exceptions to allow precise diagnosis even at statistical scale. Probably a little much but the habit's good.




