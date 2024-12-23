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
CREATE TABLE IF NOT EXISTS "JobHistory" (
	"JobHistoryID"	INTEGER,
	"EmployeeID"	INTEGER,
	"JobTitle"	TEXT,
	"CompanyName"	TEXT,
	"StartDate"	TEXT,
	"EndDate"	TEXT,
	"City"	TEXT,
	"Description"	TEXT,
	"QuitReason"	TEXT,
	"IsDeleted"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("JobHistoryID"),
	CONSTRAINT "EmployeeID" FOREIGN KEY("EmployeeID") REFERENCES "Employee"("EmployeeID")
);
CREATE TABLE IF NOT EXISTS "JobPosition" (
	"JobPositionID"	INTEGER,
	"JobPositionName"	TEXT,
	"HardSkill1"	TEXT,
	"HardSkill2"	TEXT,
	"SoftSkill1"	TEXT,
	"SoftSkill2"	TEXT,
	"IsDeleted"	INTEGER DEFAULT 0,
	PRIMARY KEY("JobPositionID")
);
CREATE TABLE IF NOT EXISTS "SprintEvaluation" (
	"EmployeeID"	INTEGER,
	"SprintEvaluationID"	INTEGER,
	"Feelings"	TEXT,
	"FavoriteTask"	TEXT,
	"ProficientTask"	TEXT,
	"DreadTask"	TEXT,
	"PotentialTask" TEXT,
	"Notes" TEXT,
	"SubmissionDate"	TEXT,
	PRIMARY KEY("SprintEvaluationID"),
	FOREIGN KEY("EmployeeID") REFERENCES "Employee"("EmployeeID")
);
CREATE TABLE IF NOT EXISTS "SurveySatisfaction" (
	"SurveyID"	INTEGER,
	"EmployeeID"	INTEGER,
	"SubmissionDate"	TEXT,
	"SatisfactionLevel"	INTEGER,
	"GrowthOpportunities"	TEXT,
	"FavoriteAspect"	TEXT,
	"CommunicationRating"	INTEGER,
	"AdditionalComments"	TEXT,
	PRIMARY KEY("SurveyID"),
	CONSTRAINT "EmployeeID" FOREIGN KEY("EmployeeID") REFERENCES "Employee"("EmployeeID")
);
INSERT INTO "Employee" ("EmployeeID","FirstName","LastName","DateOfBirth","JobTitle","Department","WorkLocation","EmploymentStatus","Email","PhoneNumber","HourlyRate","Notes","IsDeleted","HardSkill1","HardSkill2","SoftSkill1","SoftSkill2","IsManager","IsCEO") VALUES (0,'Kyle','Owen','1990-01-15','QA','Development','MSU','Full-time','kyle.owen@example.com','9876543210',18.0,'TestNotes',0,'Python','C++','Emotional Intelligence','Flexibility',1,1),
 (1,'Bob','Smith','1991-02-17','Junior Developer','Development','Office','Full-time','bob.smith@example.com','1234567890',36.5,'Test Notes',0,'Go','PHP','Emotional Intelligence','Professionalism',0,0),
 (2,'James','Duncan','1990-01-15','Sales Representative','Sales','Office','Full-time','james.duncan@example.com','6958741230',25.0,'Test',0,'JavaScript','C#','Flexibility','Public speaking',1,0),
 (3,'Sarah','Johnson','1993-04-25','Customer Service','Support','Office','Part-time','sarah.jonson@example.com','3216549870',21.0,'Try to avoid doing reports',0,'C#','Java','Professionalism','Organization',0,0),
 (4,'Alice','Johnson','1994-05-30','Senior Developer','Development','Office','Full-time','alice.johnson@example.com','5551234567',40.0,'Lead on Project X',0,'Rust','Machine Learning','Emotional Intelligence','Self-motivation',0,0),
 (5,'Gabe','Brown','1995-06-12','Marketing Manager','Management','Office','Full-time','mike.brown@example.com','5559876543',38.0,'Oversaw campaign Y',0,'Swift','Ruby','Communication','Integrity',0,0),
 (6,'Emily','Davis','1990-01-15','HR Specialist','Management','Office','Part-time','emily.davis@example.com','5552468109',20.0,'Recruitment and benefits',0,'JavaScript','Rust','Emotional Intelligence','Self-confidence',0,0),
 (7,'Tom','Wilson','1997-08-22','Finance Analyst','Support','Remote','Full-time','tom.wilson@example.com','5551357912',37.0,'Quarterly reports',0,'C#','Python','Communication','Open-mindedness',0,0),
 (8,'Linda','Garcia','1998-09-05','Junior QA Tester','Development','Office','Intern','linda.garcia@example.com','5558642097',35.0,'Test scripts and automation',0,'Go','JavaScript','Organization','Self-confidence',0,0),
 (9,'Nancy','Miller','1999-10-10','Senior Software Engineer','Development','Remote','Full-time','nancy.miller@example.com','5553214321',42.0,'Lead developer for app Z',0,NULL,NULL,NULL,NULL,0,0),
 (10,'Gary','Martinez','2000-11-15','Product Designer','Sales','Office','Intern','gary.martinez@example.com','5556574839',38.0,'User interface design',0,'PHP','Go','Problem-solving','Leadership',0,0),
 (11,'Sophia','Lee','2001-12-20','Sales Manager','Sales','Office','Full-time','sophia.lee@example.com','5557890123',40.0,'Overseeing sales strategy',0,'GIT','Go','Professionalism','Public speaking',0,0),
 (12,'Brian','Wilson','2002-01-25','DevOps Engineer','Development','Office','Full-time','brian.wilson@example.com','5551239876',40.0,'Infrastructure and deployment',0,'Python',NULL,'Self-confidence','Teamwork',0,0),
 (13,'Olivia','Anderson','1996-07-10','Customer Support Specialist','Support','Office','Part-time','olivia.anderson@example.com','5552468135',24.0,'Customer inquiries and support',0,'Kotlin','Ruby','Organization','Integrity',0,0);
INSERT INTO "JobHistory" ("JobHistoryID","EmployeeID","JobTitle","CompanyName","StartDate","EndDate","City","Description","QuitReason","IsDeleted") VALUES (1,1,'Developer Intern','Murray State University','2015-01-01','2016-01-01','Murray','Created a web application for MSU','This is quit reason',0),
 (2,1,'Junior Developer','CodeWorks','2016-02-15','2017-02-15','New York','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Relocated to a new city',0),
 (3,2,'Sales Manager','SalesCo','2017-03-20','2018-03-20','Chicago','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Career advancement',0),
 (4,2,'Sales Associate','Retail World','2018-04-10','0001-01-01','Chicago','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Promoted to Sales Manager',0),
 (5,3,'Data Analyst','DataTech','2019-05-05','2020-05-05','Austin','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Company downsizing',0),
 (6,3,'Intern','DataTech','2020-06-15','2021-06-15','Austin','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Completed internship program',0),
 (7,4,'Project Manager','BuildRight','2021-07-01','2022-07-01','Seattle','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Family reasons',0),
 (8,4,'Construction Supervisor','BuildRight','2022-08-10','2023-08-10','Seattle','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Promoted to Project Manager',0),
 (9,5,'Marketing Specialist','Creative Minds','2023-09-05','2024-09-05','Los Angeles','Developed digital marketing campaigns, managed social media channels, and increased brand engagement by 20%','Wanted to explore new industry',0),
 (10,5,'Junior Marketing Analyst','AdVentures','2019-01-01','2020-01-01','Los Angeles','Assisted in market research, analyzed campaign performance, and created monthly reports for clients','Accepted position at Creative Minds',0),
 (11,6,'Network Engineer','NetSolutions','2020-02-20','2021-02-20','Houston','Designed and implemented network infrastructure for clients, ensured network security, and improved uptime by 15%','Relocated to a different state',0),
 (12,6,'IT Support Specialist','NetSolutions','2021-03-15','2022-03-15','Houston test','Provided technical support to clients, resolved network issues, and conducted training for new hires','Promoted to Network Engineer',0),
 (13,7,'Operations Manager','Logistics Hub','2022-04-25','2023-04-25','Dallas','Oversaw daily operations, managed a team of 20 employees, and optimized supply chain processes to reduce costs by 10%','Seeking a more strategic role',0);
INSERT INTO "JobPosition" ("JobPositionID","JobPositionName","HardSkill1","HardSkill2","SoftSkill1","SoftSkill2","IsDeleted") VALUES (1,'Software Engineer','Java','SQL','Teamwork','Problem-Solving',1),
 (2,'Data Analyst','Python','PHP','Emotional Intelligence','Communication',0),
 (3,'Frontend Developer','OOP','JavaScript','Flexibility','Positive attitude',0),
 (4,'Backend Developer','JavaScript','TypeScript','Public speaking','Public speaking',0),
 (5,'Mobile App Developer','Python','Java','Teamwork','Leadership',0),
 (6,'DevOps Engineer','Kotlin','Ruby','Open-mindedness','Public speaking',0),
 (7,'AI/ML Engineer','Python','Java','Communication','Open-mindedness',0),
 (8,'Cybersecurity Specialist','Penetration Testing','Cryptography','Decision-Making','Ethical Awareness',1),
 (9,'Cloud Engineer','C#','Java','Leadership','Teamwork',0),
 (10,'Database Administrator','C#','Python','Self-motivation','Professionalism',0),
 (11,'TEST',NULL,'C#','Leadership','Leadership',1),
 (12,'Testing','Python','Java','Time Management','Leadership',1),
 (13,'Buissness Analyst','C#','Java','Teamwork','Leadership',0),
 (14,'',NULL,NULL,NULL,NULL,1),
 (15,'Product Owner','C#','Python','Teamwork','Leadership',0);
INSERT INTO "SprintEvaluation" ("EmployeeID","SprintEvaluationID","Feelings","FavoriteTask","ProficientTask","DreadTask","PotentialTask","Notes","SubmissionDate") VALUES (0,1,'Happy','Design UI','Frontend Development','Bug Fixing','UX Research','Great progress this sprint.','2024-01-15'),
 (0,2,'Motivated','Implement Features','API Integration','Code Review','Automated Testing','Needs to improve documentation.','2024-02-15'),
 (0,3,'Content','Team Meetings','Project Planning','Debugging','Performance Optimization','Excellent teamwork.','2024-03-15'),
 (1,4,'Excited','Database Design','Backend Development','Deployment','Security Enhancements','Shows strong technical skills.','2024-01-20'),
 (1,5,'Satisfied','API Development','Data Modeling','Testing','Scalability Improvements','Consistently meets deadlines.','2024-02-20'),
 (1,6,'Focused','Refactoring Code','System Architecture','Client Communication','DevOps Automation','Needs to participate more in discussions.','2024-03-20'),
 (1,7,'Inspired','Research New Technologies','Microservices Implementation','Legacy Code Maintenance','Continuous Integration','Great initiative in learning new tools.','2024-04-20'),
 (2,8,'Energetic','Feature Development','Code Optimization','User Feedback','Machine Learning Integration','Improves code quality over time.','2024-01-25'),
 (2,9,'Calm','Writing Documentation','System Testing','Manual Testing','AI Enhancements','Helpful to team members.','2024-02-25'),
 (2,10,'Determined','Bug Tracking','API Development','Code Merging','Cloud Services Integration','Excellent problem-solving skills.','2024-03-25'),
 (3,11,'Positive','Client Meetings','Requirement Analysis','Task Estimation','Prototype Development','Shows leadership qualities.','2024-01-30'),
 (3,12,'Engaged','Sprint Planning','Task Delegation','Resource Management','Risk Assessment','Needs to delegate tasks more effectively.','2024-02-28'),
 (3,13,'Optimistic','Performance Reviews','Team Building','Conflict Resolution','Strategic Planning','Great at maintaining team morale.','2024-03-30'),
 (3,14,'Proactive','Market Research','Competitive Analysis','Budget Planning','Vendor Management','Demonstrates strategic thinking.','2024-04-30'),
 (4,15,'Happy','Coding Features','Unit Testing','Integration Testing','Deployment Scripts','Writes clean and maintainable code.','2024-01-10'),
 (4,16,'Motivated','Code Reviews','Refactoring','Technical Debt Management','Continuous Deployment','Needs to enhance testing coverage.','2024-02-10'),
 (4,17,'Content','Pair Programming','Module Development','Version Control','Containerization','Collaborates well with peers.','2024-03-10'),
 (5,18,'Excited','Design Patterns','System Optimization','Load Balancing','Database Migrations','Excellent understanding of system architecture.','2024-01-18'),
 (5,19,'Satisfied','API Documentation','Service Integration','Error Handling','Logging Enhancements','Consistently delivers high-quality work.','2024-02-18'),
 (5,20,'Focused','Performance Tuning','Caching Strategies','API Rate Limiting','Monitoring Tools Implementation','Proactive in identifying performance issues.','2024-03-18'),
 (6,21,'Energetic','Frontend Styling','Responsive Design','Accessibility Improvements','Cross-Browser Testing','CSS Framework Integration','2024-01-22'),
 (6,22,'Calm','JavaScript Enhancements','State Management','Component Optimization','Client-Side Validation','CSS Framework Integration','2024-02-22'),
 (6,23,'Determined','Animation Implementation','User Experience Testing','Progressive Web Apps','Localization Features','Enhances user engagement through UI improvements.','2024-03-22'),
 (7,24,'Positive','Data Analysis','Report Generation','Data Visualization','ETL Processes','Data Pipeline Optimization','2024-01-05'),
 (7,25,'Engaged','Machine Learning Models','Data Cleaning','Feature Engineering','Model Deployment','Strong analytical and statistical skills.','2024-02-05'),
 (7,26,'Optimistic','A/B Testing','User Behavior Tracking','Predictive Analytics','Big Data Processing','Innovative approaches to data challenges.','2024-03-05'),
 (8,27,'Proactive','Network Configuration','Server Maintenance','Cloud Infrastructure Management','Disaster Recovery Planning','Ensures system reliability and uptime.','2024-01-12'),
 (8,28,'Happy','Security Audits','Firewall Management','Access Control','Intrusion Detection Systems','Maintains robust security measures.','2024-02-12'),
 (8,29,'Motivated','Backup Solutions','System Monitoring','Capacity Planning','Virtualization Technologies','Keeps infrastructure scalable and secure.','2024-03-12'),
 (8,30,'Content','Performance Monitoring','Load Testing','Network Optimization','Cloud Cost Management','Efficiently manages resources.','2024-04-12'),
 (9,31,'Excited','Content Creation','SEO Optimization','Social Media Management','Analytics Reporting','Creates engaging and optimized content.','2024-01-08'),
 (9,32,'Satisfied','Campaign Planning','Email Marketing','Graphic Design','Copywriting','Delivers creative marketing solutions.','2024-02-08'),
 (9,33,'Focused','Market Research','Brand Strategy','Advertising','Public Relations','Enhances brand presence effectively.','2024-03-08'),
 (10,34,'Energetic','Quality Assurance','Test Case Development','Automated Testing','Bug Tracking','Ensures product quality and reliability.','2024-01-17'),
 (10,35,'Calm','Regression Testing','Performance Testing','Security Testing','User Acceptance Testing','Thorough in identifying defects.','2024-02-17'),
 (10,36,'Determined','Test Automation','Continuous Integration','Release Management','Environment Setup','Improves testing efficiency.','2024-03-17'),
 (11,37,'Positive','Customer Support','Issue Resolution','Feedback Collection','Knowledge Base Management','Provides excellent customer service.','2024-01-25'),
 (11,38,'Engaged','Live Chat Support','Ticket Management','User Training','Onboarding Assistance','Patient and helpful with users.','2024-02-25'),
 (11,39,'Optimistic','Support Documentation','Customer Satisfaction Surveys','Technical Support','Account Management','Enhances customer experience.','2024-03-25'),
 (12,40,'Proactive','Financial Reporting','Budget Tracking','Expense Management','Forecasting','Investment Analysis','2024-01-14'),
 (12,41,'Happy','Tax Preparation','Payroll Management','Audit Compliance','Cost Reduction Strategies','Ensures financial compliance.','2024-02-14'),
 (12,42,'Motivated','Cash Flow Management','Financial Planning','Risk Assessment','Financial Modeling','Provides strategic financial insights.','2024-03-14'),
 (13,43,'Content','Legal Research','Contract Drafting','Compliance Monitoring','Intellectual Property Management','Policy Development','2024-01-19'),
 (13,44,'Excited','Case Management','Regulatory Filings','Dispute Resolution','Legal Advisory','Strong attention to detail.','2024-02-19'),
 (13,45,'Satisfied','Client Consultations','Legal Documentation','Risk Management','Corporate Governance','Provides reliable legal support.','2024-03-19');
INSERT INTO "SurveySatisfaction" ("SurveyID","EmployeeID","SubmissionDate","SatisfactionLevel","GrowthOpportunities","FavoriteAspect","CommunicationRating","AdditionalComments") VALUES (1,0,'2023-10-01',4,'Yes','Collaborative team environment',5,'Keep up the good work!'),
 (2,1,'2023-10-02',3,'No','Flexible work hours',4,'Would appreciate more growth opportunities.'),
 (3,2,'2023-10-03',5,'Yes','Challenging projects',5,'Very satisfied with my role.'),
 (4,3,'2023-10-04',2,'No','Office location',3,'Communication could be improved.'),
 (5,4,'2023-10-05',4,'Yes','Supportive management',4,'Happy with the current work culture.'),
 (6,5,'2023-10-06',3,'Yes','Good benefits package',3,'Looking forward to more training opportunities.'),
 (7,6,'2023-10-07',5,'Yes','Innovation encouraged',5,'Great place to work!'),
 (8,7,'2023-10-08',2,'No','Colleagues are friendly',2,'Workload is overwhelming at times.'),
 (9,8,'2023-10-09',4,'Yes','Learning opportunities',4,'Appreciate the mentorship programs.'),
 (10,9,'2023-10-10',3,'No','Remote work options',3,'Would like more clarity on promotion paths.'),
 (11,10,'2023-10-11',5,'Yes','Company culture',5,'I feel valued and recognized.'),
 (12,11,'2023-10-12',1,'No','Salary is acceptable',2,'Considering other opportunities due to lack of growth.');
COMMIT;
