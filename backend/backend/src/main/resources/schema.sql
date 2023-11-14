CREATE TABLE "user" (
                        id INT AUTO_INCREMENT NOT NULL,
                        profilepic VARCHAR(255) NOT NULL,
                        firstname VARCHAR(255) NOT NULL,
                        lastname VARCHAR(255) NOT NULL,
                        mail VARCHAR(255) NOT NULL,
                        gender VARCHAR(255),
                        age INT,
                        companyType VARCHAR(255) NOT NULL,
                        tag VARCHAR(255) NOT NULL,
                        status VARCHAR(255) NOT NULL,
                        userType VARCHAR(255) NOT NULL,
                        postalCode VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id)
);
