#!/bin/sh

./gradlew clean && ./gradlew build && docker compose build && docker compose up