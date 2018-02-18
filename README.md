# fetchpop
A personal app to archive messages from a POP email account. Includes workarounds for thousands of messages.

# Implementation

## Why Spring Boot...? 

* Reduced boiler plate
* Easy creation of executables

Cons that Don't Apply

* More dependencies
* Bigger executable

Refactoring

Applying copy then rewrite strategy; will port feature by feature with tests.

Wrinkles about testing, need to run a POP3 server; 

#Context

##My Itch



## Apache James FetchPop Limitations

For many years, I ran a local personal fork of Apache James. Key features

* Efficiently serve over IMAP mail originally delivered to many accounts beyond the limits of clients like Thunderbird to cache locally;
* Full access to archives even when machine is offline;
* Advanced filtering using SIEVE;
* Portability, able to zip and relocate;
* Mail accessed via POP3

But the James FetchPop implementation has known limitations. Would need a complete refactor and proper modularization. 


