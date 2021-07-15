## Part 1: Test it with SQL
id int primary key
employer varchar(255)
name varchar(255)
skills varchar(255)

## Part 2: Test it with SQL
SELECT * FROM techjobs.employer WHERE (location = "St. Louis City");

## Part 3: Test it with SQL
DROP TABLE techjobs.job;

## Part 4: Test it with SQL
SELECT distinct skill.name, skill.description
FROM job_skills
JOIN job on job.id = job_skills.jobs_id
JOIN skill on skill.id = job_skills.skills_id
ORDER BY skill.name asc;