BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Employee" (
	"EmployeeID"	INTEGER,
	"FirstName"	TEXT,
	"LastName"	TEXT,
	"DateOfBirth"	TEXT,
	"JobTitle"	TEXT,
	"Department"	TEXT,
	"WorkLocation"	TEXT,
	"EmploymentStatus"	TEXT,
	"Email"	TEXT,
	"PhoneNumber"	TEXT,
	"HourlyRate"	REAL,
	"Notes"	TEXT,
	"IsDeleted"	INTEGER NOT NULL DEFAULT 0,
	"HardSkill1"	TEXT,
	"HardSkill2"	TEXT,
	"SoftSkill1"	TEXT,
	"SoftSkill2"	TEXT,
	"IsManager"	NUMERIC NOT NULL DEFAULT 0,
	"IsCEO"	NUMERIC NOT NULL DEFAULT 0,
	PRIMARY KEY("EmployeeID")
);
CREATE TABLE IF NOT EXISTS "HardSkills" (
	"HardSkill"	TEXT,
	"IsDeleted"	INTEGER NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS "JobHistory" (
	"JobHistoryID"	INTEGER,
	"EmployeeID"	INTEGER,
	"JobTitle"	TEXT,
	"CompanyName"	TEXT,
	"StartDate"	TEXT,
	"EndDate"	TEXT,
	"City"	TEXT,
	"Description"	INTEGER,
	"QuitReason"	TEXT,
	PRIMARY KEY("JobHistoryID"),
	CONSTRAINT "EmployeeID" FOREIGN KEY("EmployeeID") REFERENCES "Employee"("EmployeeID")
);
CREATE TABLE IF NOT EXISTS "SoftSkills" (
	"SoftSkill"	TEXT,
	"IsDeleted"	INTEGER NOT NULL DEFAULT 0
);
INSERT INTO "Employee" ("EmployeeID","FirstName","LastName","DateOfBirth","JobTitle","Department","WorkLocation","EmploymentStatus","Email","PhoneNumber","HourlyRate","Notes","IsDeleted","HardSkill1","HardSkill2","SoftSkill1","SoftSkill2","IsManager","IsCEO") VALUES (0,'Kyle','Owen','05/25/1975','QA','DEV','MSU','Intern','kyle.owen@example.com','9876543210',18.0,'TestNotes',0,NULL,NULL,NULL,NULL,0,0),
 (1,'Bob','Smith','02/25/1986','Junior Developer','DEV','Office','Full-time','bob.smith@example.com','1234567890',36.5,'Test Notes',0,NULL,NULL,NULL,NULL,0,0),
 (2,'James','Duncan','01/01/2000','Sales Representative','SLS','Office','Full-time','james.duncan@example.com','6958741230',25.0,NULL,0,NULL,NULL,NULL,NULL,0,0),
 (3,'Sarah','Jonson','04/04/1995','Customer Service','SPT','Office','Part-time','sarah.jonson@example.com','3216549870',21.0,'Notes?
',0,NULL,NULL,NULL,NULL,0,0),
 (4,'Alice','Johnson','1987-03-15','Senior Developer','DEV','Office','Full-time','alice.johnson@example.com','5551234567',40.0,'Lead on Project X',0,NULL,NULL,NULL,NULL,0,0),
 (5,'HelloWorld','Brown','1990-07-20','Marketing Manager','MKT','Office','Full-time','mike.brown@example.com','5559876543',38.0,'Oversaw campaign Y',0,NULL,NULL,NULL,NULL,0,0),
 (6,'Emily','Davis','1992-11-11','HR Specialist','HR','Office','Part-time','emily.davis@example.com','5552468109',20.0,'Recruitment and benefits',0,NULL,NULL,NULL,NULL,0,0),
 (7,'Tom','Wilson','1985-08-30','Finance Analyst','FIN','Remote','Full-time','tom.wilson@example.com','5551357912',37.0,'Quarterly reports',0,NULL,NULL,NULL,NULL,0,0),
 (8,'Linda','Garcia','1998-06-05','Junior QA Tester','DEV','Office','Intern','linda.garcia@example.com','5558642097',35.0,'Test scripts and automation',0,NULL,NULL,NULL,NULL,0,0),
 (9,'Nancy','Miller','1984-12-12','Senior Software Engineer','DEV','Remote','Full-time','nancy.miller@example.com','5553214321',42.0,'Lead developer for app Z',0,NULL,NULL,NULL,NULL,0,0),
 (10,'Gary','Martinez','1993-05-22','Product Designer','SLS','Office','Full-time','gary.martinez@example.com','5556574839',38.0,'User interface design',0,NULL,NULL,NULL,NULL,0,0),
 (11,'Sophia','Lee','1991-09-18','Sales Manager','SLS','Office','Full-time','sophia.lee@example.com','5557890123',40.0,'Overseeing sales strategy',0,NULL,NULL,NULL,NULL,0,0),
 (12,'Brian','Wilson','1989-02-28','DevOps Engineer','DEV','Office','Full-time','brian.wilson@example.com','5551239876',40.0,'Infrastructure and deployment',0,NULL,NULL,NULL,NULL,0,0),
 (13,'Olivia','Anderson','1996-07-10','Customer Support Specialist','SPT','Office','Part-time','olivia.anderson@example.com','5552468135',24.0,'Customer inquiries and support',0,NULL,NULL,NULL,NULL,0,0);
INSERT INTO "HardSkills" ("HardSkill","IsDeleted") VALUES ('Java',0),
 ('OOP',0),
 ('Python',0),
 ('C++',0),
 ('C#',0),
 ('JavaScript',0),
 ('TypeScript',0),
 ('Ruby',0),
 ('Go',0),
 ('Swift',0),
 ('Kotlin',0),
 ('Rust',0),
 ('PHP',0),
 ('Machine Learning',0),
 ('GIT',0);
INSERT INTO "JobHistory" ("JobHistoryID","EmployeeID","JobTitle","CompanyName","StartDate","EndDate","City","Description","QuitReason") VALUES (1,1,'Developer Intern','Murray State University','9/17/2004','9/17/2005','Murray','Created a web application for MSU',NULL);
INSERT INTO "SoftSkills" ("SoftSkill","IsDeleted") VALUES ('Teamwork',0),
 ('Leadership',0),
 ('Emotional Intelligence',0),
 ('Organization',0),
 ('Flexibility',0),
 ('Communication',0),
 ('Self-motivation',0),
 ('Problem-solving',0),
 ('Openness to learning',0),
 ('Integrity',0),
 ('Self-confidence',0),
 ('Public speaking',0),
 ('Open-mindedness',0),
 ('Professionalism',0),
 ('Positive attitude',0);
COMMIT;
