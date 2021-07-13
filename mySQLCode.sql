
CREATE DATABASE flashcardsdb;
 USE flashcardsdb;
SELECT answer
    FROM writtencards
    WHERE question = "what is 2 + 2?";
SELECT * FROM writtencards;
USE flashcardsdb;
INSERT INTO writtencards VALUES
	("math", "what is 3!", "6", NULL);
    SELECT answer FROM writtencards WHERE question = "What is the square root of 4?  ";



