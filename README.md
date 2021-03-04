# Kafka Streams Demo App

# Running Locally (cd to project dir)
The only dependency for running these examples is [Docker Compose][docker].

[docker]: https://docs.docker.com/compose/install/

Once Docker Compose is installed, you can start the local Kafka cluster using the following command:

```sh
$ docker-compose -f kafka-single-broker.yml up --d
```

Now, log into the broker, since this is where we will be running our commands.
```sh
$ docker-compose exec kafka bash
```

## Create a topic
Once you're logged into the broker, run the following command to create a topic called `foobar`:

```sh
$ kafka-topics \
    --bootstrap-server localhost:9092 \
    --create \
    --partitions 1 \
    --replication-factor 1 \
    --topic foobar 
```
* Create topics `input-topic-1`, `input-topic-2`,`output-topic-1`,`output-topic-2` using above command



## Describe a topic
Once you've created the `foobar` topic, you can describe it using the following command:

```sh
$ kafka-topics \
    --bootstrap-server localhost:9092 \
    --describe \
    --topic foobar
```

## Produce data to a topic
The following command will allow you to produce data to the `foobar` topic that we created earlier. Run the following to be dropped into a prompt:

```sh
$ kafka-console-producer \
    --bootstrap-server localhost:9092 \
    --property key.separator=, \
    --property parse.key=true \
    --topic foobar
```

Once you are in the prompt, produce a few records. Keys and values are separated by `,`, and you'll need to hit `<Enter>` on your keyboard after each row.

```sh
1,mitch
2,elyse
3,isabelle
4,sammy
```

When you are finished, press `Control-C` on your keyboard to exit the prompt.

## Consuming data from a topic
Run the following command to consume the data we just produced to the `foobar` topic in the section above.

```sh
$ kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic foobar \
    --from-beginning
```

You should see the following output:
```sh
mitch
isabelle
sammy
elyse
```


## Run the Streams processing Application
 ```
 gradlew bootRun
 ```

## Cleanup
Once you are done, log out of the broker by typing `exit` and run the following command to tear down the local Kafka cluster:

```sh
docker-compose down
```

  
