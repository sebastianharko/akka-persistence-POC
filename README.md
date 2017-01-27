Quick Start 
===========

Use *ccm* to start a Cassandra cluster.

Get *ccm* from https://github.com/pcmanus/ccm. Follow the instructions to install.

On Mac OS X you may need to:
```
sudo ifconfig lo0 alias 127.0.0.2 up
sudo ifconfig lo0 alias 127.0.0.3 up
```

Create a *test* cluster:

``` ccm create test -v 3.0.8 -n 3 -s ```

Run the command side of the application: ```$sbt run``` and select ```WriteApp```

Initialize an order:

```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/order/initialize -d '{"idOrder":"1", "idUser": 42}'
```

Cancel the order:

```
 curl -H "Content-Type: application/json" -X POST http://localhost:8080/order/cancel -d '{"idOrder":"1", "idUser": 42}'
```

Note that if you try to cancel the order again, you'll get a rejection message.

```
{"message":"command rejected"}
```

Connect to Cassandra using *cqlsh*.

```
$ ccm node1 cqlsh
```

Issue the following commands:

``` 
Connected to test at 127.0.0.1:9042.
[cqlsh 5.0.1 | Cassandra 3.0.8 | CQL spec 3.4.0 | Native protocol v4]
Use HELP for help.
cqlsh> USE akka;
cqlsh:akka> SELECT persistence_id, sequence_nr, ser_manifest, blobastext(event) AS event FROM messages;

 persistence_id | sequence_nr | ser_manifest                            | event
----------------+-------------+-----------------------------------------+-----------------------------
              1 |           1 | poc.persistence.events.OrderInitialized | {"idOrder":"1","idUser":42}
              1 |           2 |   poc.persistence.events.OrderCancelled | {"idOrder":"1","idUser":42}

(2 rows)
cqlsh:akka>
```

Now run the read side of the application: ```$sbt run``` and select ```ReadApp```

Issue the following curl commands:

```
$ curl -X GET http://localhost:8080/users/42
```

You should see this:

```
{"history":[{"UserInitializedOrder":{"idOrder":"1","idUser":42}},{"UserCancelledOrder":{"idOrder":"1","idUser":42}}]}
```

Go into *cqlsh* again. You should see this.

```
$ ccm node1 cqlsh
Connected to test at 127.0.0.1:9042.
[cqlsh 5.0.1 | Cassandra 3.0.8 | CQL spec 3.4.0 | Native protocol v4]
Use HELP for help.
cqlsh> USE akka;
cqlsh:akka> SELECT persistence_id, sequence_nr, ser_manifest, blobastext(event) AS event FROM messages;

 persistence_id | sequence_nr | ser_manifest                                                   | event
----------------+-------------+----------------------------------------------------------------+-----------------------------
              1 |           1 |                        poc.persistence.events.OrderInitialized | {"idOrder":"1","idUser":42}
              1 |           2 |                          poc.persistence.events.OrderCancelled | {"idOrder":"1","idUser":42}
 stream-manager |           1 | poc.persistence.read.streammanager.events.ProgressAcknowledged |         {"i":1481825522840}
 stream-manager |           2 | poc.persistence.read.streammanager.events.ProgressAcknowledged |         {"i":1481825526154}
             42 |           1 |          poc.persistence.read.user.events.UserInitializedOrder | {"idOrder":"1","idUser":42}
             42 |           2 |            poc.persistence.read.user.events.UserCancelledOrder | {"idOrder":"1","idUser":42}

(6 rows)
cqlsh:akka>

```

Done!

You can always remove the *test* Cassandra cluster in *ccm* by writing:

```
ccm remove test
```

Running a Cluster
=================

Run

```
$ sbt universal:packageBin
```

This produces a zip file.
 
 ```
 $ cd target/universal
 $ unzip akka-persistence-poc-2.4.4.zip
 ```

This produces an executable file under ```./akka-persistence-poc-2.4.4/bin```

Run the seed node:

```
$ ./akka-persistence-poc
```

Run two more nodes:

```
$ ./akka-persistence-poc -Dclustering.port=2552 -Dweb.port=8081
```

```
$ ./akka-persistee-poc -Dclustering.port=2553 -Dweb.port=8082
```


Monitoring the Actors
=====================

This integrates with Lightbend Monitoring. The easiest way to see it in action is to pull a Docker image that includes StatsD, Graphite, Grafana. One such image can be found here.

To start a container out of this image:

docker run -e SECRET_KEY='random-secret-key' -p 80:80 -p 3000:3000 -p 2003:2003 -p 2004:2004 -p 7002:7002 -p 8125:8125/udp -p 8126:8126 -d samsaffron/graphite
Once the container is running you can go to http://localhost:3000 to open Grafana.

You'll have to import the Graphite data source from http://localhost:80 and import the dashboards from https://github.com/typesafehub/cinnamon-ui. You need at least the Actor Dashboard and the JVM Dashboard.

Note that the app works with/without StatsD running. In other words, not having StatsD running won't affect your environment.
