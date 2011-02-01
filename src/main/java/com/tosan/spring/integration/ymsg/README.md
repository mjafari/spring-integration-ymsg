Introduction
============
spring-integration-ymsg is a library that provide Spring Integration components (specially channel adapters) to connect a program to Yahoo Messaging servers.
	
License
=======
[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

Roots
=====
Really spring-integration-ymsg is an imitation of spring-integration-xmpp except it uses ymsg protocol instead of xmpp (Jabber protocol).
	
Features
========
You have ymsg namespace support with below elements:

* ymsg-connection
* inbound-channel-adapter
* outbound-channel-adapter
* presence-inbound-channel-adapter
* presence-outbound-channel-adapter
* header-enricher

Future
======
We intend that spring-integration-ymsg be part of Spring Integration. IN addition Spring Integration can unify all IM supporters part in somethings like spring-integration-im.

But these are all Spring Integration team choice.

Getting Started
================
This is a library so there is no installation guide. Just put it in your pom.xml dependencies section.

See associated test cases as sample.
