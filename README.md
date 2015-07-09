# README #

Hi Rick this is a very simple example of the inter VM example using Qbit.
 
To start the Server, `./gradlew qbit:qbitServer`

Then run the client `./gradlew qbit:qbitClient`

All this does is to ping back System.currentTimeMillis to calculate round trip
millis.

So the best round trip time I have every had is 16ms, which is 37 messages per second.
I understand that I can times 37 * lots of threads, but for me that is not the contract
of a micro service.

What I am interested in here is the latency round trip time,  Is there anything that I am
doing wrong here.

Is there any way that we can condition the socket, disable nagle for example?
