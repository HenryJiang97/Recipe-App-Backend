# Recipe

## Requirements

A recent Gradle (>= 6.1.1 but < 7.0.0) and JDK 8.

## Building

`./gradlew build`

## Testing

`./gradlew test`

## Running

`./gradlew run`

The server will start on port 5000 by default.

## Deploying to Heroku

Configure as normal and use `git push heroku master`.

`./gradlew build deployHeroku` works to deploy without pushes...sometimes.

## Spotless?

Spotless automatically formats code. If it detects errors, run `./gradlew spotlessApply`
to automatically fix them. `./gradlew spotlessCheck` can be used to directly invoke
Spotless.
