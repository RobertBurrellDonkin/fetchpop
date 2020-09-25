# FetchPop
A personal app to automate bulk operations on a <a href='https://tools.ietf.org/html/rfc1939' rel=tag>POP3</a> email account.

Under <a href='https://opensource.org/licenses/MIT' rel='license'>MIT License</a>.

There's more on the <a href='https://github.com/RobertBurrellDonkin/fetchpop/wiki'>wiki</a>.

## State Of Play

Refactoring into a test-driven object-oriented application guided by end to end tests from a legacy script.

## Usage Notes

Based around the idea of a series of operations. Each operation is an isolated session. 

Pass the name of the operation on the command line as no-argument options. For example

```java -jar ~/source/fetchpop/target/fetchpop-app-0.0.3-SNAPSHOT.jar status```

### Operations

| Name | Function |
|-------|------------|
| status | Connects to the POP3 server, asks for status and then logs to console. |

## Use Cases

### <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/1'>GH-14</a> Debug Profile

> As a User
>
> I want a profile with full debugging logs

Activate profile `debug` to log debugging to console

```java -jar -Dspring.profiles.active="debug" fetchpop-app-0.0.1-SNAPSHOT.jar```

## <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/2'>GH-2</a> Operation - Print Status

> When I perform a PrintStatusOperation
> Then the number of messages and message box size should be output

Output should be printed main logger. See Quiet Profile.

## <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/1'>GH-1</a> Quiet Profile

> As a User
>
> I want a profile consistent with UNIX output standards

By default, FetchPop is Spring-Boots-esque and logs to console. 

Activate profile `quiet` to suppress 

Implemented using <a href='https://logback.qos.ch/' rel='tag'>Logback</a> markers.

## <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/5'>GH-5</a> Set Account Details 

> As a User
>
> I want To Set Account Details

## <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/4'>GH-4</a> Debug Low Level Commands 

> As a Diagnoser of Problems
> I want to see low level communication

Apache Commons Net supports listening for low level communications. These messages are printed to the special logger named "protocol". 

## <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/30'>GH-30</a> Plain Text

To enable smoke testing and edge cases, setting `application.host.tls` to false will force plain 
text connection.

```
application:
  user: alice
  cred: Rabbit
  host:
    name: localhost
    port: 110
    tls: false
```


## <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/29'>GH-29</a> Docker Smoke Container

It's often inconvenient to play around against a live email server. The container in the `smoke` directory
describes a docker image that runs a `POP3` server with user `alice` password `rabbit` and some sample emails. 

To build and run this server:

```
$ cd smoke
$ docker build -t pop3server .
$ docker run -p110:110 pop3server
```
To connect, use 

```
application:
  user: alice
  cred: Rabbit
  host:
    name: localhost
    port: 110
    tls: false
```

A profile has been set up called `smoke` which allows Status (for example) to be run as follows:

```mvn spring-boot:run -Dspring-boot.run.profiles=smoke,debug -Dspring-boot.run.arguments=Status```

## <a href='https://github.com/RobertBurrellDonkin/fetchpop/issues/29'>GH-29</a> Print Message Info

This fetches header information for messages and prints key headers to standard output.

**Sample Use Cases**
 * Assess the contents of a mailbox before archiving 

For example

```
mvn spring-boot:run -Dspring-boot.run.profiles=smoke,quiet -Dspring-boot.run.arguments=Info
```

might output

```
From: John Doe <jdoe@machine.example> Message-ID: <1234@local.machine.example> Subject: Saying Hello
From: "Joe Q. Public" <john.q.public@example.com> Message-ID: <5678.21-Nov-1997@example.com>
From: Pete <pete@silly.example> Message-ID: <testabcd.1234@silly.example>
From: Mary Smith <mary@example.net> Message-ID: <3456@example.net> Subject: Re: Saying Hello
```




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
as ```config/application-account.yml```

Then run 

```
java -jar -Dspring.profiles.active="account" fetchpop-app-0.0.1-SNAPSHOT.jar
```

## Features And Fixes

Just pre-releases for personal use.

### Next Release

### 0.0.4
 *  GH-28: Print Message Info 
 *  GH-29: Docker POP3 smoke server
 *  GH-30: Allow plain connections through configuration

### 0.0.3

 *  GH-25: Spring boot console app with status
 *  GH-23: Upgrade to Spring Boot 2
 *  GH-21: Warn when account data not set

### 0.0.2
 * GH-19 Add Debug Profile
 * GH-4 Debug protocol comms
 * GH-2 Status command

### 0.0.1 

* GH-1 Add Main Logger
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



