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
	"EmployeeRoleID"	INTEGER,
	PRIMARY KEY("EmployeeID"),
	CONSTRAINT "EmployeeRolesID" FOREIGN KEY("EmployeeRoleID") REFERENCES "EmployeeRoles"("EmployeeRoleID")
);
CREATE TABLE IF NOT EXISTS "EmployeeRoles" (
	"EmployeeRoleID"	INTEGER,
	"EmployeeRole"	TEXT,
	"ViewEmployeeInfo"	INTEGER,
	"AddEmployee"	INTEGER,
	"EditEmployee"	INTEGER,
	"DeleteEmployee"	INTEGER,
	"ViewManagerInfo"	INTEGER,
	PRIMARY KEY("EmployeeRoleID")
);
CREATE TABLE IF NOT EXISTS "JobHistory" (
	"JobHistoryID"	INTEGER,
	"EmployeeID"	INTEGER,
	"JobTitle"	TEXT,
	"CompanyName"	TEXT,
	"StartDate"	TEXT,
	"EndDate"	TEXT,
	"Description"	INTEGER,
	"City"	TEXT,
	PRIMARY KEY("JobHistoryID"),
	CONSTRAINT "EmployeeID" FOREIGN KEY("EmployeeID") REFERENCES "Employee"("EmployeeID")
);
INSERT INTO "Employee" ("EmployeeID","FirstName","LastName","DateOfBirth","JobTitle","Department","WorkLocation","EmploymentStatus","Email","PhoneNumber","HourlyRate","Notes","IsDeleted","EmployeeRoleID") VALUES (0,'Kyle','Owen','05/25/1975','QA','DEV','MSU','Intern','kyle.owen@example.com','9876543210',18.0,'TestNotes',0,2),
 (1,'Bob','Smith','02/25/1986','Junior Developer','DEV','Office','Full-time','bob.smith@example.com','1234567890',36.5,'Test Notes',0,1),
 (2,'James','Duncan','01/01/2000','Sales Representative','SLS','Office','Full-time','james.duncan@example.com','6958741230',25.0,NULL,0,1),
 (3,'Sarah','Jonson','04/04/1995','Customer Service','SPT','Office','Part-time','sarah.jonson@example.com','3216549870',21.0,'Notes?
',0,1),
 (4,'Alice','Johnson','1987-03-15','Senior Developer','DEV','Office','Full-time','alice.johnson@example.com','5551234567',40.0,'Lead on Project X',0,2),
 (5,'HelloWorld','Brown','1990-07-20','Marketing Manager','MKT','Office','Full-time','mike.brown@example.com','5559876543',38.0,'Oversaw campaign Y',0,2),
 (6,'Emily','Davis','1992-11-11','HR Specialist','HR','Office','Part-time','emily.davis@example.com','5552468109',20.0,'Recruitment and benefits',0,1),
 (7,'Tom','Wilson','1985-08-30','Finance Analyst','FIN','Remote','Full-time','tom.wilson@example.com','5551357912',37.0,'Quarterly reports',0,2),
 (8,'Linda','Garcia','1998-06-05','Junior QA Tester','DEV','Office','Intern','linda.garcia@example.com','5558642097',35.0,'Test scripts and automation',0,2),
 (9,'Nancy','Miller','1984-12-12','Senior Software Engineer','DEV','Remote','Full-time','nancy.miller@example.com','5553214321',42.0,'Lead developer for app Z',0,3),
 (10,'Gary','Martinez','1993-05-22','Product Designer','SLS','Office','Full-time','gary.martinez@example.com','5556574839',38.0,'User interface design',0,2),
 (11,'Sophia','Lee','1991-09-18','Sales Manager','SLS','Office','Full-time','sophia.lee@example.com','5557890123',40.0,'Overseeing sales strategy',0,2),
 (12,'Brian','Wilson','1989-02-28','DevOps Engineer','DEV','Office','Full-time','brian.wilson@example.com','5551239876',40.0,'Infrastructure and deployment',0,2),
 (13,'Olivia','Anderson','1996-07-10','Customer Support Specialist','SPT','Office','Part-time','olivia.anderson@example.com','5552468135',24.0,'Customer inquiries and support',0,2);
INSERT INTO "EmployeeRoles" ("EmployeeRoleID","EmployeeRole","ViewEmployeeInfo","AddEmployee","EditEmployee","DeleteEmployee","ViewManagerInfo") VALUES (1,'Manager',1,1,1,1,0),
 (2,'Regular Employee',0,0,0,0,0),
 (3,'CEO',1,1,1,1,1);
INSERT INTO "JobHistory" ("JobHistoryID","EmployeeID","JobTitle","CompanyName","StartDate","EndDate","Description","City") VALUES (1,1,'Developer Intern','Murray State University','9/17/2004','9/17/2005','Created a web application for MSU','Murray');
COMMIT;
