TO RUN:

    FROM PROJECT ROOT:

        $ ./build_process.sh

    NOTE: if the above fails

        $ ./gradlew clean && ./gradlew build && docker compose build && docker compose up


TO TEST REST SERVICES:

    CREATE NEW CUSTOMER:

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