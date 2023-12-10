PRELUDE:

    This project was developed on a Debian-based Linux VM and thus, all commands suggested are Linux-specific. However,
    most can be easily modified for Windows and Mac.

    The project uses Spring Boot, MySQL, and packages the application and DB into Docker containers that offer their
    respective services on the localhost. The only assumed software on the examiner's computer is the Docker Engine.

    In the interest of time, I omitted several things that were not specifically requested in the PDF--most notably,
    tests, security (e.g., authentication, hardcoded passwords, etc.), and a good logger. I did, however, attempt to
    code for extensibility and believe these could easily be added (e.g., passwords in a .env file).

    Lastly, I'd like to take a moment to thank you for having a fair initial assessment of skills. Regardless of outcome,
    for I may not meet your standards, I do believe this to be far superior to the myriad LeetCode challenges I've been
    sentenced to in other interviews. I appreciate that SiDC takes into consideration skills that are relevant to the
    position being applied for. Thank you.

    - Eliot


TO RUN:

    FROM PROJECT ROOT:

        $ ./build_process.sh

    NOTE: if the above fails

        $ ./gradlew clean && ./gradlew build && docker compose build && docker compose up


TO TEST REST SERVICES:

    CREATE NEW CUSTOMER

        $ curl http://localhost:8080/customer/add -H "Content-Type: application/json" -d '{"firstName":"Bobo", "lastName":"McGee", "email":"bobo.m@b.com", "id":1}'

    READ CUSTOMER

        $ curl http://localhost:8080/customer/get_customer/1

    EDIT CUSTOMER

        $ curl -X PUT http://localhost:8080/customer/edit_customer -H "Content-Type: application/json" -d '{"firstName":"Bonobo", "lastName":"McGeek", "email":"B.M@b.com", "id":1}'
        $ curl http://localhost:8080/customer/get_customer/1

    DELETE CUSTOMER

        $ curl -X DELETE http://localhost:8080/customer/delete_customer/1
        $ curl http://localhost:8080/customer/get_customer/1


TO TEST API INTEGRATION:

    FETCH A PET FOR CUSTOMER

        $ curl http://localhost:8080/customer/get_pet_for_customer/1