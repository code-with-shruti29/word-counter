# text-processor

REST API which implements part of a text processing library.
## Description

The current capabilities of API include:

1. Counting the most frequent word in the given text and returning the count.
2. Finding the number of times a word is present in the given text.
3. Finding most frequent n words in the given text.

## Running

1. Java 8 and Maven 3 should be installed.

2. Change directory to the root folder of the application.

3. Run the below maven command to generate the JAR file.

```bash
mvn clean package
```

4. Run the JAR file

```bash
java -jar target/text-processor-0.0.1-SNAPSHOT.jar
```

5. Navigate to below URL - the URL will be redirected to Swagger UI

```bash
http://localhost:8080/swagger-ui.html
```

## Endpoints

1. get most frequent n words (/api/text-processor/words/most-frequent-n-words/{n}) - HTTP POST

- Sample Request - URL

```
/api/text-processor/words/most-frequent-n-words/3
```
- Sample Request - Body

```
{
  "text": "The sun shines over the beautiful lake."
}
```
- Sample Response

```
{
  "wordFrequencies": [
    {
      "word": "the",
      "frequency": 2
    },
    {
      "word": "beautiful",
      "frequency": 1
    },
    {
      "word": "lake",
      "frequency": 1
    }
  ]
}
```

2. find number of times the word exists in a given text (/api/text-processor/words/word/frequency) - HTTP POST

- Sample Request - Body

```
{
  "text": "The sun shines over the beautiful lake.",
  "word": "the"
}
```

- Sample Response

```
{
  "wordFrequency": 2
}
```

3. Counting the most frequent word in the given text and returning the count.(/api/text-processor/words/word/highest-frequency) - HTTP POST

- Sample Request - Body

```
{
  "text": "The sun shines over the beautiful lake."
}
```

- Sample Response

```
{
  "highestFrequency": 2
}
```

## Technologies

1. Java 8

2. Spring boot

3. Maven 3

6. Mockito

7. Swagger UI

8. Lombok


