build:
	.\gradlew clean build

install:
	.\gradlew clean install

run-dist:
	.\build\install\app\bin\app.bat


run-dist-h:
	.\build\install\app\bin\app.bat -h

run-dist-V:
	.\build\install\app\bin\app.bat -V

.PHONY: build install run-dist run-dist-h run-dist-V